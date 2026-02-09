<#
  build-and-deploy.ps1
  Script PowerShell para compilar proyectos (autor-ms/test/Polina/frontend-seguros), construir imágenes Docker
  y desplegar a Kubernetes (Docker Desktop integrado).

  Uso: ejecutar desde la raíz del repo en PowerShell:
    .\build-and-deploy.ps1

  Opciones interactivas: el script preguntará antes de aplicar manifests en Kubernetes.
#>

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

$root = Split-Path -Path $MyInvocation.MyCommand.Definition -Parent
Write-Host "Repo root: $root"

# Nota: la función Run-IfExists fue eliminada (no utilizada) para evitar problemas de parsing.

# 1) Build autor-ms (Gradle)
if (Test-Path "$root\autor-ms\gradlew.bat") {
    Write-Host "Construyendo autor-ms (Gradle)..."
    Push-Location "$root\autor-ms"
    if (Test-Path .\build) { Remove-Item -Recurse -Force .\build }
    .\gradlew.bat --no-daemon clean bootJar --refresh-dependencies
    Pop-Location
} else {
    Write-Host "No se encontró gradlew en autor-ms, omitiendo build de autor-ms."
}

# 2) Build test (Maven)
if (Test-Path "$root\test\mvnw.cmd") {
    Write-Host "Construyendo planes-ms (Maven)..."
    Push-Location "$root\test"
    if (Test-Path .\target) { Remove-Item -Recurse -Force .\target }
    .\mvnw.cmd clean package -DskipTests
    Pop-Location
} else {
    Write-Host "No se encontró mvnw en test, omitiendo build de test."
}

# 3) Build Polina (Maven)
if (Test-Path "$root\Polina\mvnw.cmd") {
    Write-Host "Construyendo polizas-ms (Maven)..."
    Push-Location "$root\Polina"
    if (Test-Path .\target) { Remove-Item -Recurse -Force .\target }
    .\mvnw.cmd clean package -DskipTests
    Pop-Location
} else {
    Write-Host "No se encontró mvnw en Polina, omitiendo build de Polina."
}

# 4) Build frontend (Angular)
if (Test-Path "$root\frontend-seguros\package.json") {
    Write-Host "Construyendo frontend (Angular)..."
    Push-Location "$root\frontend-seguros"
    if (Test-Path .\dist) { Remove-Item -Recurse -Force .\dist }
    $env:NG_CLI_ANALYTICS = 'false'
    npm install
    npm run build -- --configuration=production
    Pop-Location
} else {
    Write-Host "No se encontró frontend-seguros, omitiendo build frontend."
}

# 5) Build Docker images
Write-Host "Construyendo imágenes Docker locales..."
Push-Location $root
if (Test-Path "$root\autor-ms") { docker build -t autor-ms:latest ./autor-ms }
if (Test-Path "$root\test") { docker build -t planes-ms:latest ./test }
if (Test-Path "$root\Polina") { docker build -t polizas-ms:latest ./Polina }
if (Test-Path "$root\frontend-seguros") { docker build -t frontend-seguros:latest ./frontend-seguros }
Pop-Location

# 6) Preguntar antes de desplegar en Kubernetes
$apply = Read-Host "¿Aplicar manifests de Kubernetes ahora? (y/N)"
if ($apply -eq 'y' -or $apply -eq 'Y') {
    Write-Host "Aplicando manifests..."
    kubectl apply -f k8s/mysql-postgres-pv-pvc.yaml
    kubectl apply -f k8s/databases.yaml
    kubectl apply -f k8s/microservices.yaml

    Write-Host "Esperando rollout de deployments (timeout 120s cada uno)..."
    $deployments = @('mysql-polizas','postgres-independientes','clientes-ms','planes-ms','polizas-ms','frontend-app')
    foreach ($d in $deployments) {
        try {
            kubectl rollout status deployment/$d --timeout=120s
        } catch {
            # Mostrar mensaje de error con la excepción
            Write-Host ("Rollout failed or timed out for {0}: {1}" -f $d, ($_.Exception.Message))
        }
    }

    Write-Host "Hecho. Lista de pods:"
    kubectl get pods
    kubectl get svc
} else {
    Write-Host "Omitido despliegue en Kubernetes."
}

Write-Host "Script finalizado."
