import express from "express";
import fs from "fs";
import path from "path";
import { fileURLToPath } from "url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const app = express();
app.use(express.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, "public")));
app.set("view engine", "ejs");

let currentFolder = null;

app.get("/", (req, res) => {
    let images = [];
    let videos = [];

    if (currentFolder) {
        const files = fs.readdirSync(currentFolder);
        images = files.filter(f => [".jpg", ".jpeg", ".png", ".gif"].includes(path.extname(f).toLowerCase()));
        videos = files.filter(f => [".mp4", ".mov", ".webm"].includes(path.extname(f).toLowerCase()));
    }

    res.render("index", {
        images,
        videos,
        folderPath: currentFolder || ""
    });
});

app.get("/Media/:fileName", (req, res) => {
   if (!currentFolder) {
       return res.status(404).send("Folder Not Found");
   }

   const filePath = path.join(currentFolder, req.params.fileName);

   if (!fs.existsSync(filePath)) {
       return res.status(404).send("File Not Found");
   }

   res.sendFile(filePath);
});

app.post("/Set-Folder", (req, res) => {
    currentFolder = req.body.folderPath;

    res.redirect("/");
});

export default app;