# 🐳 Docker Deployment - Library Management System

## 🚀 Quick Deploy

### Step 1: Install Docker
Download and install Docker Desktop from: https://www.docker.com/products/docker-desktop

### Step 2: Build and Run
Open terminal in project directory and run:
```bash
docker-compose up --build
```

### Step 3: Access Application
- **Application:** http://localhost:8080
- **Database:** localhost:3306 (MySQL)

## 🔐 Login Credentials
- **Admin:** admin / admin123
- **Librarian:** librarian / librarian123

## 📋 Docker Services

### MySQL Container
- **Image:** mysql:8.0
- **Port:** 3306
- **Database:** library_db
- **User:** libraryuser
- **Password:** librarypass

### Application Container
- **Build:** Multi-stage (React + Spring Boot)
- **Port:** 8080
- **Auto-restart:** unless-stopped

## 🛠️ Docker Commands

### Start Services
```bash
docker-compose up -d
```

### Stop Services
```bash
docker-compose down
```

### View Logs
```bash
docker-compose logs -f app
```

### Rebuild Application
```bash
docker-compose up --build --force-recreate
```

### Access Database
```bash
docker exec -it library-mysql mysql -u libraryuser -p librarypass
```

## 📁 Docker Files Created

1. **Dockerfile** - Multi-stage build configuration
2. **docker-compose.yml** - Service orchestration
3. **docker-entrypoint.sh** - Startup script
4. **.dockerignore** - Build optimization
5. **application-docker.properties** - Docker-specific config

## 🔧 Configuration

### Environment Variables
- `MYSQL_ROOT_PASSWORD=root123`
- `MYSQL_DATABASE=library_db`
- `MYSQL_USER=libraryuser`
- `MYSQL_PASSWORD=librarypass`

### Volumes
- `mysql_data` - Persistent database storage
- `data.sql` - Initial sample data

## 🌐 Production Deployment

### Option 1: Docker Hub
```bash
# Build and push to Docker Hub
docker build -t yourusername/library-system .
docker push yourusername/library-system
```

### Option 2: Cloud Providers
- **AWS ECS/EKS**
- **Google Cloud Run**
- **Azure Container Instances**
- **DigitalOcean App Platform**

### Option 3: VPS Deployment
```bash
# On VPS with Docker installed
git clone <your-repo>
cd library-management
docker-compose up -d
```

## 🔍 Troubleshooting

### Port Already in Use
```bash
# Check what's using port 8080
netstat -tulpn | grep :8080
# Or use different port
docker-compose up --scale app=1 -p 8081:8080
```

### Database Connection Issues
```bash
# Check MySQL container status
docker-compose ps mysql
# View MySQL logs
docker-compose logs mysql
```

### Build Issues
```bash
# Clean build
docker-compose down --volumes
docker system prune -f
docker-compose up --build
```

## 📊 Monitoring

### Container Status
```bash
docker-compose ps
```

### Resource Usage
```bash
docker stats
```

### Logs
```bash
docker-compose logs -f
```

## 🚀 One-Command Deploy
```bash
git clone <repository>
cd library-management
docker-compose up --build -d
echo "Application running at http://localhost:8080"
```

Done! Your Library Management System is now running in Docker containers! 🎉
