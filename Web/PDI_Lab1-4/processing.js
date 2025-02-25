const form = document.forms["declaration-form"];
const table = document.querySelector("#declarations-table tbody");

if (localStorage.length === 0) clear_records();
else {
    const records = JSON.parse(localStorage.records);
    for (const key in records)
        add_new_row(key, records[key]);
}

function add_declaration() {
    const id = ++localStorage.last_id;
    const record = Object.fromEntries(new FormData(form));

    add_new_row(id, record);

    let declarations = JSON.parse(localStorage.records);
    declarations[id] = record;
    localStorage.records = JSON.stringify(declarations);
}

function export_to_JSON() {
    const records = JSON.parse(localStorage.records);
    for (const key in records) {
        if (!records[key]["patronym"])
            delete records[key]["patronym"];
        const baggage = records[key]["baggage-list"].split("\n");
        if (baggage[0])
            records[key]["baggage-list"] = baggage;
        else
            delete records[key]["baggage-list"];
    }
    const blob = new Blob(
        [JSON.stringify(records, null, '\t')],
        { type: "application/json" }
    );
    window.open(URL.createObjectURL(blob), '_blank');
}
function export_to_XML() {
    const records = JSON.parse(localStorage.records);
    let xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
        + "<customs>\n";
    for (const id in records) {
        const data = records[id];
        const baggage = data["baggage-list"].split("\n");
        xml += `\t<declaration id="${id}">\n`;
        xml += `\t\t<firstname>${data["first-name"]}</firstname>\n`;
        xml += `\t\t<lastname>${data["last-name"]}</lastname>\n`;
        if (data["patronym"])
            xml += `\t\t<patronym>${data["patronym"]}</patronym>\n`;
        xml += `\t\t<nativelanguage>${data["native-language"]}</nativelanguage>\n`;
        xml += `\t\t<purposeofvisit>${data["purpose-of-visit"]}</purposeofvisit>\n`;
        xml += `\t\t<money currency="${data["currency"]}">${data["money"]}</money>\n`;
        xml += `\t\t<baggage>\n`;
        if (baggage[0])
            for (const item of baggage)
                xml += `\t\t\t<item>${item}</item>\n`;
        xml += `\t\t</baggage>\n`;
        xml += `\t\t</declaration>\n`;
    }
    xml += "</customs>\n";
    const blob = new Blob([xml], { type: 'application/xml' });
    window.open(URL.createObjectURL(blob), '_blank');
}

function clear_records() {
    localStorage.last_id = 0;
    localStorage.records = "{}";
    table.innerHTML = "";
}

function add_new_row(id, data) {
    const full_name = [data["last-name"], data["first-name"], data["patronym"]]
        .filter(i => i)
        .join(' ');
    const language = data["native-language"];
    const purpose = data["purpose-of-visit"];
    const money = data["money"] +" "+ data["currency"];
    const baggage = data["baggage-list"];

    const row = table.insertRow();
    row.insertCell().textContent = id;
    row.insertCell().textContent = full_name;
    row.insertCell().textContent = language;
    row.insertCell().textContent = purpose;
    row.insertCell().textContent = money;
    row.insertCell().appendChild(create_baggage_list(baggage));
    row.insertCell().appendChild(create_delete_button(row));
}

function create_baggage_list(baggage) {
    baggage = baggage.split("\n");
    const list = document.createElement("ol");
    if (!baggage[0]) return list;
    for (const item of baggage) {
        const li = document.createElement("li");
        li.textContent = item;
        list.appendChild(li);
    }
    return list;
}

function create_delete_button(row) {
    const deleteButton = document.createElement('button');
    deleteButton.textContent = "Видалити";
    deleteButton.onclick = function() {
        const id = row.cells[0].textContent;

        let declarations = JSON.parse(localStorage.records);
        delete declarations[id];
        localStorage.records = JSON.stringify(declarations);

        row.remove();
    };
    return deleteButton;
}
