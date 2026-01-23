# 🌐 Online Library .com - Web Deployment

## 🚀 Fastest Way - Vercel + Railway

### Step 1: Push to GitHub
```bash
git init
git add .
git commit -m "Library Management System"
git branch -M main
git remote add origin https://github.com/yourusername/library-management.git
git push -u origin main
```

### Step 2: Deploy Frontend to Vercel
1. Go to **vercel.com**
2. Click **"New Project"**
3. Import GitHub repository
4. Select **frontend** folder
5. Click **"Deploy"**

**Your Link:** https://onlinelibrary.vercel.app

### Step 3: Deploy Backend to Railway
1. Go to **railway.app**
2. Click **"New Project"**
3. Import GitHub repository
4. Select **backend** folder
5. Add Environment Variables:
   - `DATABASE_URL`: `postgresql://user:pass@host:port/db`
   - `JWT_SECRET`: `your-secret-key`
6. Click **"Deploy"**

**Your API Link:** https://onlinelibrary-api.up.railway.app

## 🔗 Custom Domain Setup

### Option 1: Buy Domain
- **GoDaddy**: onlinelibrary.com ($12/year)
- **Namecheap**: onlinelibrary.com ($9/year)
- **Freenom**: onlinelibrary.tk (free)

### Option 2: Configure DNS
```
A Record: @ -> Vercel IP (76.76.21.21)
A Record: www -> Vercel IP (76.76.21.21)
```

### Option 3: Update Vercel
1. Go to Vercel dashboard
2. Click **"Settings"** → **"Domains"**
3. Add **onlinelibrary.com**
4. Verify DNS

## 📱 Final Links Structure

### Free Links (Immediate)
- **Website**: https://onlinelibrary.vercel.app
- **API**: https://onlinelibrary-api.up.railway.app

### Custom Domain (After Purchase)
- **Website**: https://onlinelibrary.com
- **API**: https://api.onlinelibrary.com

## 🔧 Code Changes Required

### Frontend Changes
```javascript
// frontend/src/services/AuthContext.js
const API_BASE_URL = process.env.NODE_ENV === 'production' 
  ? 'https://onlinelibrary-api.up.railway.app'
  : 'http://localhost:8080';
```

### Backend Changes
```java
// backend/src/main/resources/application-prod.properties
spring.web.cors.allowed-origins=https://onlinelibrary.vercel.app
```

## 🚀 One-Click Deploy Commands

### Frontend (Vercel CLI)
```bash
npm i -g vercel
cd frontend
vercel --prod
```

### Backend (Railway CLI)
```bash
npm i -g @railway/cli
cd backend
railway login
railway init
railway up
```

## 💰 Cost Breakdown

### Free Tier (Recommended)
- **Vercel**: Free forever
- **Railway**: Free tier ($5/month after)
- **Total**: $0-5/month

### Custom Domain
- **Domain**: $10-15/year
- **Total**: $10-20/year

## ⚡ Quick Deploy - 5 Minutes

### Option 1: Use My Links
1. Fork my repository
2. Connect to Vercel + Railway
3. Get your links instantly

### Option 2: Your Own Setup
1. Push your code to GitHub
2. Deploy to Vercel (2 minutes)
3. Deploy to Railway (3 minutes)

## 🎯 Ready-to-Use Links

After deployment, you'll have:
- **Live Website**: https://onlinelibrary.vercel.app
- **Working Login**: admin/admin123
- **Full Features**: Books, Members, Issues

## 📋 Deployment Checklist

- [ ] GitHub repository ready
- [ ] Vercel account created
- [ ] Railway account created
- [ ] Frontend deployed to Vercel
- [ ] Backend deployed to Railway
- [ ] CORS configured
- [ ] Custom domain (optional)

## 🌟 Alternative Platforms

### Netlify + Heroku
- **Frontend**: Netlify
- **Backend**: Heroku
- **Links**: onlinelibrary.netlify.app

### GitHub Pages + Render
- **Frontend**: GitHub Pages
- **Backend**: Render
- **Links**: onlinelibrary.github.io

Choose any option and your **onlinelibrary.com** will be live! 🎉
