const {PythonShell} = require("python-shell");

const express = require("express");
const expressLayouts = require("express-ejs-layouts");
const fs = require("fs");
const path = require("path");

const app = express();
const port = 3000;

console.log("Loading Python Server App | Please Wait...");

let options = {
    mode: "text",
    pythonOptions: ['-u'],
    scriptPath: path.join(__dirname, "scripts/"),
    args: []
}

PythonShell.run("TNA.py", options).then(async messages => {
    console.log("Python TNA Script Finished");
    console.log("Results: ", messages);

    PythonShell.run("SpankBang.py", options).then(async messages => {
        console.log("Python SpankBang Script Finished");
        console.log("Results: ", messages);

        app.set("view engine", "ejs");
        app.set("views", path.join(__dirname, "views"));
        app.use(expressLayouts);
        app.set("layout", "layout");

        app.use(express.static(path.join(__dirname, "public")));
        app.use("/data", express.static(path.join(__dirname, "data")));

        app.get("/", (req, res) => {
            res.redirect("/tna");
        });

        app.get("/tna", (req, res) => {
            const videosJSON = JSON.parse(fs.readFileSync(path.join(__dirname, "data/TNA.json"), "utf-8"));

            res.render("videoPlayer", {
                title: "TNA Videos",
                heading: "TNA Videos",
                videosJSON
            });
        });

        app.get("/spankbang", (req, res) => {
            const videosJSON = JSON.parse(fs.readFileSync(path.join(__dirname, "data/SpankBang.json"), "utf-8"));

            res.render("videoPlayer", {
                title: "SpankBang Videos",
                heading: "SpankBang Videos",
                videosJSON
            });
        });

        app.listen(port, async () => {
            const serverUrl = `http://localhost:${port}`;
            console.log(`Server Running at: ${serverUrl}`);

            const open = (await import("open")).default;
            await open(serverUrl);
        });
    });
}).catch(err => {
    console.error("Error Running Python Script: ", err);
    process.exit(1);
});