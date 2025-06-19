console.log("=== Record Collection ===");

var collection = {
    "2548": {
        "Album": "Slippery When Wet",
        "Artist": "Bon Jovi",
        "Tracks": [
            "Let It Rock",
            "You Give Love a Bad Name"
        ]
    },
    "2468": {
        "Album": "1999",
        "Artist": "Prince",
        "Tracks": [
            "1999",
            "Little Red Corvette"
        ]
    },
    "1245": {
        "Artist": "Robert Palmer",
        "Tracks": []
    },
    "5439": {
        "Album": "ABBA Gold"
    }
};

var collectionCopy = JSON.parse(JSON.stringify(collection));

function updateRecords(id, prop, value) {
    if (value === "") {
        delete collection[id][prop];
    } else if (prop === "Tracks") {
        collection[id][prop] = collection[id][prop] || [];
        collection[id][prop].push(value);
    } else {
        collection[id][prop] = value;
    }

    return collection;
}

updateRecords(2468, "Tracks", "Test");
console.log(JSON.stringify(updateRecords(5439, "Artist", "ABBA")));