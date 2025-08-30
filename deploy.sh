#!/bin/bash
# Master deployment script for MySuperProject
# Execute this script from the project root directory.

set -e # Exit immediately if a command exits with a non-zero status.

# --- PART 1: LOCAL GIT SETUP ---
echo "
\[PHASE 1/3] Initializing local Git repository..."

# Navigate to the project directory
cd /home/B/Downloads/Tina/app/MySuperProject/

if [ -d ".git" ]; then
  echo "Git repository already initialized."
else
  git init -b main
  echo "Git repository initialized."
fi

git add .
# Check if there are any changes to commit
if git diff-index --quiet HEAD --; then
  echo "No changes to commit. Working tree clean."
else
  git commit -m "feat: initial commit of full-stack SuperMap project"
  echo "Initial commit created successfully."
fi

echo "✅ Git setup complete."

# --- PART 2: GITHUB PUSH ---
echo "
\[PHASE 2/3] Pushing code to GitHub..."

echo "Please go to https://github.com/new and create a new, empty, public repository."
read -p "Enter your GitHub username: " GITHUB_USERNAME
read -p "Enter your new GitHub repository name: " GITHUB_REPO

REMOTE_URL="https://github.com/${GITHUB_USERNAME}/${GITHUB_REPO}.git"

# Check if the remote 'origin' already exists
if git remote | grep -q 'origin'; then
  git remote set-url origin "$REMOTE_URL"
  echo "Updated existing remote 'origin'."
else
  git remote add origin "$REMOTE_URL"
  echo "Added new remote 'origin'."
fi

echo "Pushing to main branch..."
git push -u origin main

echo "✅ Code pushed to GitHub successfully."

# --- PART 3: RENDER DEPLOYMENT INSTRUCTIONS ---
echo "
\[PHASE 3/3] Instructions for deploying on Render.com..."

echo "
Step 1: Create PostgreSQL Database on Render"
echo "  - From Render Dashboard, click New+ > PostgreSQL."
echo "  - Give it a unique name (e.g., supermap-db) and select the Free plan."
echo "  - After creation, find the 'Internal Connection URL' and copy it."

echo "
Step 2: Deploy the Backend Server"
echo "  - Click New+ > Web Service and select your new GitHub repository."
echo "  - Settings:"
echo "    - Name: supermap-server"
echo "    - Root Directory: server"
echo "    - Build Command: npm install"
echo "    - Start Command: npm start"
echo "    - Plan: Free"
echo "  - Go to the 'Environment' tab before creating."
echo "  - Add Environment Variable: Key=DATABASE_URL, Value=<PASTE THE INTERNAL CONNECTION URL>"
echo "  - Click 'Create Web Service'. Wait for it to deploy and copy the public URL (e.g., https://supermap-server.onrender.com)."

echo "
Step 3: Deploy the Frontend Dashboard"
echo "  - Click New+ > Static Site and select the same repository."
echo "  - Settings:"
echo "    - Name: supermap-dashboard"
echo "    - Root Directory: dashboard"
echo "    - Build Command: npm run build"
echo "    - Publish Directory: build"
echo "  - Before creating, go to 'Environment' and add a variable:"
echo "    - Key: REACT_APP_API_URL, Value=<PASTE THE PUBLIC SERVER URL FROM STEP 2>"
echo "  - Click 'Create Static Site'."

echo "
Step 4: Final Update for Android App"
echo "  - Open this file: /home/B/Downloads/Tina/app/MySuperProject/client/app/src/main/java/com/lab/poc/supermap/ApiClient.java"
echo "  - Change the BASE_URL variable to your public server URL."
echo "  - Re-build the APK with: ./gradlew assembleDebug"

echo "

🎉 DEPLOYMENT SCRIPT FINISHED. Please follow the instructions above to complete the process. 🎉"
