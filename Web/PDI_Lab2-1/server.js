const fs = require("fs");
const path = require("path");
const express = require('express');

const app = express();
const port = 3000;
var appdata = {
    "last-id": 0
};
const declarationsFile = path.join(__dirname, "declarations.json");
const appdataFile = path.join(__dirname, "vars.json");

// Використовувати рушій шаблонів EJS
app.set('view engine', 'ejs');
// Статичні файли (стилі й форма)
app.use(express.static('public'));

/*************************
 * REST API бази митниці *
 *************************/

// Додавання декларації
app.post("/database", express.urlencoded({extended: true}), (req, res) => {
    const id = ++appdata['last-id'];
    const formData = req.body;
    fs.readFile(declarationsFile, (err, data) => {
        if (err) return res.status(500).json({ message: "Помилка читання файлу!" });
        let declarations = JSON.parse(data);

        if (!formData || Object.keys(formData).length === 0)
            return res.status(400).json({ message: "Немає даних для додавання!" });
        declarations[id] = formData;

        data = JSON.stringify(declarations, null, 2);
        fs.writeFile(declarationsFile, data, (err) => {
            if (err) return res.status(500).json({ message: "Помилка запису до файлу!" });
            // res.json({ message: `Декларацію додано під ID ${id}.` });
            res.redirect('/');
        });
    });
});

// Оновлення декларації
// Форми не підтримують PUT, тому використовуємо POST
app.post("/database-put/:id", express.urlencoded({extended: true}), (req, res) => {
    const id = req.params.id;
    const formData = req.body;
    fs.readFile(declarationsFile, (err, data) => {
        if (err) return res.status(500).json({ message: "Помилка читання файлу!" });

        let declarations = JSON.parse(data);
        if (!declarations[id])
            return res.status(404).json({ message: `Декларація ${id} не знайдена.` });
        if (!formData || Object.keys(formData).length === 0)
            return res.status(400).json({ message: "Немає даних для зміни!" });
        declarations[id] = formData;

        data = JSON.stringify(declarations, null, 2);
        fs.writeFile(declarationsFile, data, (err) => {
            if (err) return res.status(500).json({ message: "Помилка запису до файлу!" });
            // res.json({ message: `Декларацію ${id} оновлено.` });
            res.redirect('/');
        });
    });
});

// Видалення декларації
app.delete("/database/:id", (req, res) => {
    const id = req.params.id;
    fs.readFile(declarationsFile, (err, data) => {
        if (err) return res.status(500).json({ message: "Помилка читання файлу!" });
        let declarations = JSON.parse(data);

        if (!declarations[id])
            return res.status(404).json({ message: `Декларація ${id} не знайдена.` });
        delete declarations[id];

        data = JSON.stringify(declarations, null, 2);
        fs.writeFile(declarationsFile, data, (err) => {
            if (err) return res.status(500).json({ message: "Помилка запису до файлу!" });
            res.json({ message: `Декларацію ${id} видалено.` });
        });
    });
});

// Видалення ВСІХ декларацій
app.delete("/database", (req, res) => {
    if (req.query.confirm !== "true") return res.status(403).json({ message: "Ця небезпечна дія потребує свідомого підтвердження." });
    fs.writeFile(declarationsFile, "{}", (err) => {
        if (err) return res.status(500).json({ message: "Помилка читання файлу!" });
        res.json({ message: "Всі декларації видалено." });
    });
});

// Отримання декларації
app.get("/database/:id", (req, res) => {
    const id = req.params.id;
    const format = req.query.format;
    fs.readFile(declarationsFile, (err, data) => {
        if (err) return res.status(500).json({ message: "Помилка читання файлу!" });
        const declarations = JSON.parse(data);

        const decl = declarations[id];
        if (!decl)
            return res.status(404).json({ message: `Декларація ${id} не знайдена.` });
        if (format === 'xml') {
            let xml = `<?xml version="1.0" encoding="UTF-8"?>\n<declaration id="${id}">\n`;
            const baggage = decl["baggage-list"].split("\n");
            xml += `\t<firstname>${decl["first-name"]}</firstname>\n`;
            xml += `\t<lastname>${decl["last-name"]}</lastname>\n`;
            if (decl["patronym"])
                xml += `\t<patronym>${decl["patronym"]}</patronym>\n`;
            xml += `\t<nativelanguage>${decl["native-language"]}</nativelanguage>\n`;
            xml += `\t<purposeofvisit>${decl["purpose-of-visit"]}</purposeofvisit>\n`;
            xml += `\t<money currency="${decl["currency"]}">${decl["money"]}</money>\n`;
            xml += `\t<baggage>\n`;
            if (baggage[0])
                for (const item of baggage)
                    xml += `\t\t<item>${item}</item>\n`;
            xml += `\t</baggage>\n`;
            xml += `</declaration>\n`;
            res.set('Content-Type', 'application/xml');
            res.send(xml);
        } else {
            res.json(decl);
        }
    });
});

// Отримання ВСІХ декларацій
app.get("/database", (req, res) => {
    const format = req.query.format;
    fs.readFile(declarationsFile, (err, data) => {
        if (err) return res.status(500).json({ message: "Помилка читання файлу!" });

        if (format === 'xml') {
            const declarations = JSON.parse(data);
            let xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<customs>\n";
            for (const id in declarations) {
                const decl = declarations[id];
                const baggage = decl["baggage-list"].split("\n");
                xml += `\t<declaration id="${id}">\n`;
                xml += `\t\t<firstname>${decl["first-name"]}</firstname>\n`;
                xml += `\t\t<lastname>${decl["last-name"]}</lastname>\n`;
                if (decl["patronym"])
                    xml += `\t\t<patronym>${decl["patronym"]}</patronym>\n`;
                xml += `\t\t<nativelanguage>${decl["native-language"]}</nativelanguage>\n`;
                xml += `\t\t<purposeofvisit>${decl["purpose-of-visit"]}</purposeofvisit>\n`;
                xml += `\t\t<money currency="${decl["currency"]}">${decl["money"]}</money>\n`;
                xml += `\t\t<baggage>\n`;
                if (baggage[0])
                    for (const item of baggage)
                        xml += `\t\t\t<item>${item}</item>\n`;
                xml += `\t\t</baggage>\n`;
                xml += `\t\t</declaration>\n`;
            }
            xml += "</customs>\n";
            res.set('Content-Type', 'application/xml');
            res.send(xml);
        } else {
            res.set('Content-Type', 'application/json');
            res.send(data);
        }
    });
});

/*************************
 * Відображення сторінок *
 *************************/

app.get("/form-new", (req, res) => {
    res.redirect("/form-new.html");
})

app.get("/form-edit", (req, res) => {
    const id = req.query.id;
    fs.readFile(declarationsFile, (err, data) => {
        if (err) return res.status(500).json({ message: "Помилка читання файлу!" });
        let declarations = JSON.parse(data);

        if (!declarations[id])
            return res.status(404).json({ message: `Декларація ${id} не знайдена.` });
        res.render('form-edit', {
            id: id,
            declaration: declarations[id]
        });
    });
})

app.get('/', (req, res) => {
    fs.readFile(declarationsFile, (err, data) => {
        if (err) return res.status(500).json({message: "Помилка читання файлу!"});
        const declarations = JSON.parse(data);

        res.render('index', {
            declarations: declarations
        });
    });
});

/**********************
 * Керування сервером *
 **********************/

app.listen(port, () => {
    fs.readFile(appdataFile, (err, data) => {
        if (err) {
            console.info("Could not read app data, writing defaults...");
            fs.writeFile(appdataFile, JSON.stringify(appdata, null, 2), (err) => {
                if (err) {
                    console.error("Could not write default app data, aborting");
                    process.exit(1);
                }
            });
        } else {
            appdata = JSON.parse(data);
        }
        console.log(`Server running at http://localhost:${port}`);
    })
});

process.on('SIGINT', shutdown_server)
process.on('SIGTERM', shutdown_server)
function shutdown_server() {
    fs.writeFile(appdataFile, JSON.stringify(appdata, null, 2), (err) => {
        if (err) console.error("Could not save app data");
        process.exit(0);
    });
}
