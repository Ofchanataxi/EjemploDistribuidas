Write-Host "Iniciando túneles a Kubernetes..." -ForegroundColor Green

# Función para iniciar un proceso en una nueva ventana
function Start-Tunnel {
    param([string]$service, [string]$ports)
    Write-Host "Abriendo túnel para $service en $ports..."
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "kubectl port-forward svc/$service $ports"
}

# 1. Frontend (8080)
Start-Tunnel "frontend-app" "8080:80"

# 2. Clientes (8081)
Start-Tunnel "clientes-ms" "8081:8080"

# 3. Planes (8082)
Start-Tunnel "planes-ms" "8082:8081"

# 4. Pólizas (8083)
Start-Tunnel "polizas-ms" "8083:8001"

Write-Host "¡Túneles iniciados! Se han abierto 4 ventanas nuevas." -ForegroundColor Yellow
Write-Host "No las cierres mientras uses la aplicación."
Write-Host "Ahora recarga http://localhost:8080 en tu navegador."
