# Script para desplegar la aplicación en Kubernetes

Write-Host "=== Desplegando aplicación en Kubernetes ===" -ForegroundColor Cyan

# Verificar que kubectl esté disponible
if (-not (Get-Command kubectl -ErrorAction SilentlyContinue)) {
    Write-Host "✗ kubectl no está instalado o no está en el PATH" -ForegroundColor Red
    exit 1
}

Write-Host "✓ kubectl detectado" -ForegroundColor Green

# Verificar conexión al clúster
Write-Host "`nVerificando conexión al clúster..." -ForegroundColor Yellow
kubectl cluster-info | Out-Null
if ($LASTEXITCODE -ne 0) {
    Write-Host "✗ No se puede conectar al clúster de Kubernetes" -ForegroundColor Red
    Write-Host "  Asegúrate de tener Minikube, Kind o Docker Desktop con K8s habilitado" -ForegroundColor Yellow
    exit 1
}
Write-Host "✓ Conectado al clúster" -ForegroundColor Green

# Aplicar manifiestos en orden
Write-Host "`n--- Paso 1: Creando volúmenes persistentes ---" -ForegroundColor Yellow
kubectl apply -f k8s/mysql-postgres-pv-pvc.yaml
if ($LASTEXITCODE -ne 0) {
    Write-Host "✗ Error creando PVCs" -ForegroundColor Red
    exit 1
}
Write-Host "✓ PVCs creados" -ForegroundColor Green

Write-Host "`n--- Paso 2: Desplegando bases de datos ---" -ForegroundColor Yellow
kubectl apply -f k8s/databases.yaml
if ($LASTEXITCODE -ne 0) {
    Write-Host "✗ Error desplegando bases de datos" -ForegroundColor Red
    exit 1
}
Write-Host "✓ Bases de datos desplegadas" -ForegroundColor Green

Write-Host "`n--- Esperando que las bases de datos estén listas (30s) ---" -ForegroundColor Yellow
Start-Sleep -Seconds 30

Write-Host "`n--- Paso 3: Desplegando microservicios y frontend ---" -ForegroundColor Yellow
kubectl apply -f k8s/microservices.yaml
if ($LASTEXITCODE -ne 0) {
    Write-Host "✗ Error desplegando microservicios" -ForegroundColor Red
    exit 1
}
Write-Host "✓ Microservicios y frontend desplegados" -ForegroundColor Green

Write-Host "`n=== Despliegue completado ===" -ForegroundColor Cyan

# Mostrar estado
Write-Host "`n--- Estado de los Pods ---" -ForegroundColor Yellow
kubectl get pods

Write-Host "`n--- Estado de los Services ---" -ForegroundColor Yellow
kubectl get services

# Instrucciones de acceso
Write-Host "`n=== Instrucciones de acceso ===" -ForegroundColor Cyan

# Detectar el tipo de servicio del frontend
$frontendService = kubectl get service frontend-app -o jsonpath='{.spec.type}' 2>$null

if ($frontendService -eq "LoadBalancer") {
    Write-Host "`nEl frontend está configurado como LoadBalancer." -ForegroundColor Yellow
    
    # Verificar si es Minikube
    if (Get-Command minikube -ErrorAction SilentlyContinue) {
        Write-Host "`nPara acceder al frontend en Minikube, ejecuta:" -ForegroundColor Green
        Write-Host "  minikube service frontend-app" -ForegroundColor White
    } else {
        Write-Host "`nEsperando IP externa..." -ForegroundColor Yellow
        $externalIP = kubectl get service frontend-app -o jsonpath='{.status.loadBalancer.ingress[0].ip}' 2>$null
        if ($externalIP) {
            Write-Host "Accede al frontend en: http://${externalIP}" -ForegroundColor Green
        } else {
            Write-Host "`nO usa port-forward para acceder localmente:" -ForegroundColor Green
            Write-Host "  kubectl port-forward service/frontend-app 8080:80" -ForegroundColor White
            Write-Host "  Luego visita: http://localhost:8080" -ForegroundColor White
        }
    }
}

Write-Host "`n=== Comandos útiles ===" -ForegroundColor Cyan
Write-Host "Ver logs de un pod:" -ForegroundColor Yellow
Write-Host "  kubectl logs <nombre-del-pod>" -ForegroundColor White
Write-Host "`nVer detalles de un pod:" -ForegroundColor Yellow
Write-Host "  kubectl describe pod <nombre-del-pod>" -ForegroundColor White
Write-Host "`nEliminar todo:" -ForegroundColor Yellow
Write-Host "  kubectl delete -f k8s/" -ForegroundColor White
