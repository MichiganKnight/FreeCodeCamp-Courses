const {PythonShell} = require("python-shell");

const express = require("express");
const path = require("path");
const app = express();
const port = 3000;

let options = {
    mode: "text",
    pythonOptions: ['-u'],
    scriptPath: path.join(__dirname, "scripts/"),
    args: []
}

PythonShell.run("TNA.py", options).then(messages => {
    console.log("Python Script Finished");
    console.log("Results: ", messages);

    app.use(express.static(path.join(__dirname, "public")));
    app.use("/scripts", express.static(path.join(__dirname, "scripts")));

    app.listen(port, () => {
        console.log(`App Running at: 127.0.0.1:${port}`);
    });
}).catch(err => {
    console.error("Error Running Python Script: ", err);
    process.exit(1);
});