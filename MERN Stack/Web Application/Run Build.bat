@echo off
setlocal enabledelayedexpansion

npm install && npm install --prefix Frontend && npm run build --prefix Frontend
node Backend/Server.js