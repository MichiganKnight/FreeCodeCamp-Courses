import { app, BrowserWindow, dialog, ipcMain } from "electron";
import expressApp from "./app.js";

let mainWindow;
let server;

function createWindow() {
    mainWindow = new BrowserWindow({
        show: false,
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false
        }
    });

    mainWindow.maximize();
    mainWindow.setMenu(null);
    mainWindow.show();
    mainWindow.loadURL("http://localhost:3000");
}

app.whenReady().then(() => {
    server = expressApp.listen(3000, () => {
        console.log(`✅ Server Running at http://localhost:3000`);
    });

    createWindow();
});

ipcMain.handle("select-folder", async () => {
    if (!mainWindow) return null;
    const result = await dialog.showOpenDialog(mainWindow, {
        properties: ["openDirectory"],
    });
    if (result.canceled) return null;
    return result.filePaths[0]; // absolute path
});

app.on("window-all-closed", () => {
    if (process.platform !== "darwin") app.quit();
    if (server) server.close();
});