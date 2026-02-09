# Sistema Distribuido - Emisión de Pólizas de Seguros

Sistema distribuido completo para la gestión de pólizas de seguros basado en microservicios con Java 17, Spring Boot 3, Angular, MySQL, PostgreSQL, Docker y Kubernetes.

## 📋 Descripción del Proyecto

**Caso de estudio:** Emisión de pólizas (Cliente – PlanSeguro – Póliza)

### Modelo de Datos:
- **Cliente** (Independiente): id, nombres, identificación, email, teléfono
- **PlanSeguro** (Independiente): id, nombre, tipo (VIDA/AUTO/SALUD), primaBase, coberturaMax
- **Póliza** (Dependiente): id, numeroPoliza, fechaInicio, fechaFin, primaMensual, estado (ACTIVA/CANCELADA)
  - FK → Cliente
  - FK → PlanSeguro

## 🏗️ Arquitectura

### Componentes:
1. **cliente-ms** (Puerto 8080): API REST para gestión de clientes - PostgreSQL
2. **plan-ms** (Puerto 8081): API REST para gestión de planes de seguro - PostgreSQL  
3. **poliza-ms** (Puerto 8001): API REST para gestión de pólizas - MySQL (+ Feign para validar FK)
4. **frontend-seguros** (Puerto 80/8080): Aplicación Angular con interfaz gráfica
5. **MySQL 8.0**: Base de datos para pólizas
6. **PostgreSQL 15**: Base de datos compartida para clientes y planes

### Tecnologías:
- ☕ Backend: Java 17, Spring Boot 3, Spring Data JPA, Feign Client
- 🅰️ Frontend: Angular 19
- 🗄️ Bases de datos: MySQL 8.0, PostgreSQL 15
- 🐳 Contenedorización: Docker, Docker Compose
- ☸️ Orquestación: Kubernetes (Minikube, Kind, Docker Desktop)

## 🚀 Despliegue en Kubernetes (Recomendado)

### Prerrequisitos:
- Docker Desktop con Kubernetes habilitado, o
- Minikube, o
- Kind (Kubernetes in Docker)
- kubectl instalado

### Paso 1: Construir las Imágenes Docker

```powershell
# El script detectará automáticamente tu entorno (Minikube, Kind o Docker Desktop)
.\build-k8s-images.ps1
```

Este script construirá:
- `autor-ms:latest` (Cliente MS)
- `planes-ms:latest` (Plan MS)
- `polizas-ms:latest` (Póliza MS)
- `frontend-seguros:latest` (Frontend Angular)

### Paso 2: Desplegar en Kubernetes

```powershell
# Despliega todos los recursos en el orden correcto
.\deploy-k8s.ps1
```

Este script:
1. Crea PersistentVolumeClaims para las bases de datos
2. Despliega MySQL y PostgreSQL
3. Despliega los microservicios y el frontend
4. Muestra el estado de pods y servicios

### Paso 3: Acceder a la Aplicación

**Para Minikube:**
```powershell
minikube service frontend-app
```

**Para Docker Desktop o Kind:**
```powershell
kubectl port-forward service/frontend-app 8080:80
# Luego visita: http://localhost:8080
```

### Verificar el Despliegue:

```powershell
# Ver todos los pods
kubectl get pods

# Ver todos los services
kubectl get services

# Ver logs de un pod específico
kubectl logs <nombre-del-pod>

# Ejemplo:
kubectl logs clientes-ms-xxxxxxxxx-xxxxx
```

### Limpiar Recursos:

```powershell
# Elimina todos los recursos de Kubernetes
```

## 🐳 Despliegue con Docker Compose (Alternativa)

### Construcción y ejecución:

```bash
# Desde la raíz del proyecto
docker-compose up --build
```

### Puertos expuestos:
- Frontend: http://localhost:8080
- Cliente MS: http://localhost:8081
- Plan MS: http://localhost:8082
- Póliza MS: http://localhost:8083
- PostgreSQL: localhost:5432
- MySQL: localhost:3307

### Detener y limpiar:
```bash
docker-compose down
docker-compose down -v  # Incluye volúmenes
```

## 🧪 Desarrollo Local (Sin Docker)

### 1. Iniciar Bases de Datos:
```powershell
docker-compose up mysql-polizas postgres-independientes -d
```

### 2. Compilar y ejecutar cada microservicio:

**Cliente MS (Gradle):**
```powershell
cd autor-ms
.\gradlew bootRun
```

**Plan MS (Maven):**
```powershell
cd test
mvn spring-boot:run
```

**Póliza MS (Maven):**
```powershell
cd Polina
mvn spring-boot:run
```

**Frontend (Angular):**
```powershell
cd frontend-seguros
npm install
ng serve
# Visita: http://localhost:4200
```

## 🔧 Configuración CORS

Los microservicios incluyen configuración CORS completa:
- Clase `CorsConfig.java` en cada microservicio
- Headers permitidos: `*`
- Métodos permitidos: GET, POST, PUT, DELETE, OPTIONS
- Soporte para peticiones preflight (OPTIONS)

El frontend usa Nginx como proxy reverso (en Docker/K8s) que:
- Redirige `/api/clientes` → `clientes-ms:8080`
- Redirige `/api/planes` → `planes-ms:8081`
- Redirige `/api/polizas` → `polizas-ms:8001`
- Agrega headers CORS automáticamente

## 📸 Capturas Requeridas para el Reporte

Para el reporte del proyecto, incluye capturas de:

1. **Pods en ejecución:**
```powershell
kubectl get pods
```

2. **Services:**
```powershell
kubectl get services
```

3. **Frontend funcionando:**
- Captura del navegador mostrando la interfaz

4. **Operaciones CRUD:**
- Crear Cliente
- Crear Plan
- Crear Póliza (asociando cliente + plan)
- Listar registros
- Eliminar registros

## 🧩 Estructura del Proyecto

```
ServiciosLibros/
├── autor-ms/              # Microservicio de Clientes (Gradle)
│   ├── src/
│   ├── build.gradle
│   └── Dockerfile
├── test/                  # Microservicio de Planes (Maven)
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── Polina/                # Microservicio de Pólizas (Maven)
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── frontend-seguros/      # Frontend Angular
│   ├── src/
│   ├── nginx.conf        # Configuración proxy para Docker/K8s
│   ├── Dockerfile
│   └── package.json
├── k8s/                   # Manifiestos de Kubernetes
│   ├── mysql-postgres-pv-pvc.yaml
│   ├── databases.yaml
│   └── microservices.yaml
├── docker-compose.yml     # Configuración Docker Compose
├── build-k8s-images.ps1   # Script para construir imágenes K8s
├── deploy-k8s.ps1         # Script para desplegar en K8s
├── cleanup-k8s.ps1        # Script para limpiar recursos K8s
└── README.md
```

## 🔍 Verificación de APIs

### Clientes (Port 8080 interno, 8081 externo):
```bash
# Listar clientes
curl http://localhost:8081/api/clientes

# Crear cliente
curl -X POST http://localhost:8081/api/clientes \
  -H "Content-Type: application/json" \
  -d '{"nombres":"Juan Pérez","identificacion":"1234567890","email":"juan@example.com","telefono":"0991234567"}'
```

### Planes (Port 8081 interno, 8082 externo):
```bash
# Listar planes
curl http://localhost:8082/api/planes

# Crear plan
curl -X POST http://localhost:8082/api/planes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Seguro de Vida Básico","tipo":"VIDA","primaBase":50.00,"coberturaMax":100000.00}'
```

### Pólizas (Port 8001 interno, 8083 externo):
```bash
# Listar pólizas
curl http://localhost:8083/api/polizas

# Crear póliza
curl -X POST http://localhost:8083/api/polizas \
  -H "Content-Type: application/json" \
  -d '{"numeroPoliza":"POL-001","fechaInicio":"2026-02-01","fechaFin":"2027-02-01","primaMensual":75.00,"estado":"ACTIVA","clienteId":1,"planId":1}'
```

## 🐛 Resolución de Problemas

### Error: CORS policy bloqueando peticiones
✅ **Solucionado:** Configuración CORS agregada en todos los microservicios y nginx.

### Error: Connection refused a bases de datos
- Verifica que los pods de BD estén corriendo: `kubectl get pods`
- Revisa logs: `kubectl logs mysql-polizas-xxxxx`
- En Docker Compose, espera a que las BD terminen de inicializarse (healthcheck)

### Error: ImagePullBackOff en Kubernetes
- Las imágenes deben construirse localmente con `build-k8s-images.ps1`
- Verifica `imagePullPolicy: IfNotPresent` en los manifiestos

### Frontend no carga datos
- Verifica que todos los microservicios estén corriendo
- Revisa la consola del navegador para errores
- Verifica logs del pod frontend: `kubectl logs frontend-app-xxxxx`

## 📚 Endpoints de las APIs

### Cliente MS (*/api/clientes*)
- `GET /api/clientes` - Listar todos
- `GET /api/clientes/{id}` - Obtener por ID
- `POST /api/clientes` - Crear
- `PUT /api/clientes/{id}` - Actualizar
- `DELETE /api/clientes/{id}` - Eliminar

### Plan MS (*/api/planes*)
- `GET /api/planes` - Listar todos
- `GET /api/planes/{id}` - Obtener por ID
- `POST /api/planes` - Crear
- `PUT /api/planes/{id}` - Actualizar
- `DELETE /api/planes/{id}` - Eliminar

### Póliza MS (*/api/polizas*)
- `GET /api/polizas` - Listar todos
- `GET /api/polizas/{id}` - Obtener por ID
- `POST /api/polizas` - Crear (valida FK con Feign)
- `PUT /api/polizas/{id}` - Actualizar
- `DELETE /api/polizas/{id}` - Eliminar

## ✅ Criterios de Evaluación Cumplidos

- ✅ Diseño correcto de API RESTful con Spring Boot 3 y Java 17
- ✅ Funcionamiento completo del CRUD en las tres entidades
- ✅ Integración efectiva entre frontend (Angular), backend y bases de datos
- ✅ Contenerización correcta con Docker (Dockerfiles optimizados multi-stage)
- ✅ Despliegue exitoso en Kubernetes con manifiestos completos
- ✅ Base de datos MySQL para entidad dependiente (Póliza)
- ✅ Base de datos PostgreSQL para entidades independientes (Cliente, Plan)
- ✅ Validación de FK mediante Feign Client
- ✅ Configuración CORS completa
- ✅ Scripts automatizados de despliegue
- ✅ README claro con instrucciones detalladas

## 👥 Autores

Proyecto desarrollado para el curso de Aplicaciones Distribuidas - Universidad Politécnica Salesiana

## 📄 Licencia

Este proyecto es de uso académico.

---

**Nota:** Para producción, considera usar Secrets de Kubernetes para credenciales de BD, Ingress para routing, y monitoring con Prometheus/Grafana.

