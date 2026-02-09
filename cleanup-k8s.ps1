# Script para limpiar/eliminar todos los recursos de Kubernetes

Write-Host "=== Limpiando recursos de Kubernetes ===" -ForegroundColor Cyan

Write-Host "`n--- Eliminando microservicios y frontend ---" -ForegroundColor Yellow
kubectl delete -f k8s/microservices.yaml --ignore-not-found=true

Write-Host "`n--- Eliminando bases de datos ---" -ForegroundColor Yellow
kubectl delete -f k8s/databases.yaml --ignore-not-found=true

Write-Host "`n--- Eliminando PVCs ---" -ForegroundColor Yellow
kubectl delete -f k8s/mysql-postgres-pv-pvc.yaml --ignore-not-found=true

Write-Host "`n--- Verificando que no queden recursos ---" -ForegroundColor Yellow
kubectl get all

Write-Host "`n=== Limpieza completada ===" -ForegroundColor Green
