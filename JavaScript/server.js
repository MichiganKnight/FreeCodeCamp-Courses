const express = require('express');
const fs = require('fs');
const path = require('path');

const app = express();
const PORT = 3000;
const scriptsDir = path.join(__dirname, 'public', 'scripts');

app.use(express.static('public'));

app.get('/api/scripts', (req, res) => {
    const scriptsList = [];

    const walk = (dir, relPath = '') => {
        const files = fs.readdirSync(dir);
        files.forEach(file => {
            const fullPath = path.join(dir, file);
            const relFilePath = path.join(relPath, file);

            if (fs.statSync(fullPath).isDirectory()) {
                walk(fullPath, relFilePath);
            } else if (file.endsWith('.js')) {
                const label = path.basename(file, '.js').replace(/([A-Z])/g, ' $1').replace(/^./, c => c.toUpperCase()) + ' Program';
                scriptsList.push({ label, path: `scripts/${relFilePath.replace(/\\/g, '/')}` });
            }
        });
    };

    walk(scriptsDir);
    res.json(scriptsList);
});

app.listen(PORT, () => {
    console.log(`🚀 Server Running at http://localhost:${PORT}`);
});