const {PythonShell} = require("python-shell");

const express = require("express");
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
    console.log("Python Script Finished");
    console.log("Results: ", messages);

    app.use(express.static(path.join(__dirname, "public")));
    app.use("/scripts", express.static(path.join(__dirname, "scripts")));

    app.listen(port, async () => {
        const serverUrl = `http://localhost:${port}`;
        console.log(`Server Running at: ${serverUrl}`);

        const open = (await import("open")).default;
        await open(serverUrl);
    });
}).catch(err => {
    console.error("Error Running Python Script: ", err);
    process.exit(1);
});