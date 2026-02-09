# Script para construir las imágenes Docker para Kubernetes
# Puede usarse con Minikube, Kind o Docker Desktop con Kubernetes

Write-Host "=== Construyendo imágenes para Kubernetes ===" -ForegroundColor Cyan

# Detectar el entorno de Kubernetes
$K8S_ENV = "docker-desktop"  # Por defecto

if (Get-Command minikube -ErrorAction SilentlyContinue) {
    Write-Host "Detectado: Minikube" -ForegroundColor Green
    $K8S_ENV = "minikube"
} elseif (Get-Command kind -ErrorAction SilentlyContinue) {
    Write-Host "Detectado: Kind" -ForegroundColor Green
    $K8S_ENV = "kind"
} else {
    Write-Host "Usando: Docker Desktop" -ForegroundColor Green
}

# Función para construir imagen según el entorno
function Build-K8sImage {
    param(
        [string]$ImageName,
        [string]$BuildContext
    )
    
    Write-Host "`n--- Construyendo $ImageName ---" -ForegroundColor Yellow
    
    switch ($K8S_ENV) {
        "minikube" {
            # Para Minikube, usar el daemon de Docker de Minikube
            minikube image build -t ${ImageName}:latest $BuildContext
        }
        "kind" {
            # Para Kind, construir con Docker y cargar en el clúster
            docker build -t ${ImageName}:latest $BuildContext
            kind load docker-image ${ImageName}:latest
        }
        default {
            # Para Docker Desktop, simplemente construir con Docker
            docker build -t ${ImageName}:latest $BuildContext
        }
    }
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ $ImageName construida exitosamente" -ForegroundColor Green
    } else {
        Write-Host "✗ Error construyendo $ImageName" -ForegroundColor Red
        exit 1
    }
}

# Construir todas las imágenes
Build-K8sImage -ImageName "autor-ms" -BuildContext "./autor-ms"
Build-K8sImage -ImageName "planes-ms" -BuildContext "./test"
Build-K8sImage -ImageName "polizas-ms" -BuildContext "./Polina"
Build-K8sImage -ImageName "frontend-seguros" -BuildContext "./frontend-seguros"

Write-Host "`n=== Todas las imágenes construidas exitosamente ===" -ForegroundColor Cyan
Write-Host "`nPara desplegar en Kubernetes, ejecuta:" -ForegroundColor Yellow
Write-Host "  .\deploy-k8s.ps1" -ForegroundColor White
