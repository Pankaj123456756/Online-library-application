@echo off
echo 🌐 Creating Online Library .com Website...
echo.

echo Step 1: Initialize Git Repository...
git init
git add .
git commit -m "Library Management System - Ready for Deploy"

echo.
echo Step 2: GitHub Setup Required!
echo.
echo 1. Go to https://github.com and create new repository
echo 2. Name: library-management
echo 3. Copy the repository URL
echo.
echo Step 3: Push to GitHub (after creating repository)
echo git remote add origin YOUR_GITHUB_URL
echo git branch -M main
echo git push -u origin main
echo.

echo Step 4: Deploy to Vercel (Frontend)
echo 1. Go to https://vercel.com
echo 2. Click "New Project"
echo 3. Import your GitHub repository
echo 4. Select "frontend" folder
echo 5. Click "Deploy"
echo.
echo Your Website: https://your-library.vercel.app
echo.

echo Step 5: Deploy Backend to Railway
echo 1. Go to https://railway.app
echo 2. Click "New Project"  
echo 3. Import your GitHub repository
echo 4. Add MySQL database
echo 5. Deploy
echo.
echo Your API: https://your-library-api.up.railway.app
echo.

echo Step 6: Custom Domain (Optional)
echo 1. Buy domain: onlinelibrary.com
echo 2. Configure DNS to point to Vercel
echo 3. Update CORS settings
echo.

echo 🎉 Your Online Library will be live at:
echo https://onlinelibrary.com
echo.
echo 📋 Login Credentials:
echo Admin - admin/admin123
echo Librarian - librarian/librarian123
echo.
echo 📖 See VERCEL_DEPLOY.md for detailed instructions
echo.
pause
