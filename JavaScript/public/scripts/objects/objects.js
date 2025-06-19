console.log("=== Objects ===");

var myDog = {
    "Name": "Dog",
    "Legs": 3,
    "Tails": 2,
    "Friends": []
};

console.log(myDog);

console.log("\n=== Dot Notation ===");

var testObj = {
    "Hat": "Baseball Hat",
    "Shirt": "T-Shirt",
    "Shoes": "Sandals"
};

console.log(testObj.Hat);

console.log("\n=== Bracket Notation ===");

var testObj = {
    "An Entree": "Hamburger",
    "My Side": "Veggies",
    "The Drink": "Water"
};

console.log(testObj["An Entree"]);
console.log(testObj["The Drink"]);

var testObj2 = {
    12: "Namath",
    16: "Montana",
    19: "Unitas"
};

var playerNumber = 16;
var player = testObj2[playerNumber];

console.log(player);

console.log("\n=== Updating Object Properties ===");

var myDog = {
    "Name": "Coder",
    "Legs": 4,
    "Tails": 1,
    "friends": ["Nobody", "Everyone"]
};

myDog.Name = "Confused Coder";

console.log(myDog.Name);

myDog['bark'] = "Woof!";

console.log(myDog.bark);
console.log(JSON.stringify(myDog));

delete myDog.bark;

console.log(JSON.stringify(myDog));

console.log("\n=== Object Lookup ===");

function phoneticLookup(val) {
    var result = "";

    var lookup = {
        "Alpha": "Adams",
        "Bravo": "Boston",
        "Charlie": "Chicago",
        "Delta": "Denver",
        "Echo": "Easy",
        "Foxtrot": "Frank"
    };

    result = lookup[val];

    return result;
}

console.log(phoneticLookup("Charlie"));
console.log(phoneticLookup("Foxtrot"));

var myObj = {
    Gift: "Lego",
    Pet: "Dog",
    Bed: "Mattress"
};

function checkObj(checkProp) {
    if (myObj.hasOwnProperty(checkProp)) {
        return myObj[checkProp];
    } else {
        return "Not Found";
    }
}

console.log(checkObj("Gift"));
console.log(checkObj("Hello"));

console.log("\n=== Complex Objects ===");

var myMusic = [
    {
        "Artist": "Billy Joel",
        "Title": "Piano Man",
        "Release_Year": 1973,
        "Formats": [
            "CD",
            "8T",
            "LP"
        ],
        "Gold": true
    },
    {
        "Artist": "Jeff Favignano",
        "Title": "LSPDFR",
        "Release_Year": 2018,
        "Formats": [
            "Youtube Video"
        ]
    }
]

var myStorage = {
    "Car": {
        "Inside": {
            "Glove Box": "Maps",
            "Passenger Seat": "Snacks"
        },
        "Outside": {
            "Trunk": "Groceries"
        }
    }
};

var gloveBoxContents = myStorage.Car.Inside["Glove Box"];

console.log(gloveBoxContents);

var myPlants = [
    {
        Type: "Flowers",
        List: [
            "Rose",
            "Tulip",
            "Dandelion"
        ]
    },
    {
        Type: "Trees",
        List: [
            "Fir",
            "Pine",
            "Birch"
        ]
    }
];

var secondTree = myPlants[1].List[1];

console.log(secondTree);