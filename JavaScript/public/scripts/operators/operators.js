console.log("=== Operators ===");

function testEqual(val) {
    if (val == 12) {
        return "Equal";
    }

    return "Not Equal";
}

function testStrict(val) {
    if (val === 7) {
        return "Equal";
    }

    return "Not Equal";
}

function compareEquality(a, b) {
    if (a === b) {
        return "Equal";
    }

    return "Not Equal";
}

console.log(testEqual(10));

console.log(testStrict(7));
console.log(testStrict('7'));

console.log(compareEquality(10, '10'));

function testNotEqual(val) {
    if (val != 99) {
        return "Not Equal";
    }

    return "Equal";
}

function testStrictNotEqual(val) {
    if (val !== 17) {
        return "Not Equal";
    }

    return "Equal";
}

console.log(testNotEqual(10));

console.log(testStrictNotEqual(17));
console.log(testStrictNotEqual('17'));

function testGreaterThan(val) {
    if (val > 100) {
        return "Over 100";
    }

    if (val > 10) {
        return "Over 10";
    }

    return "10 or Under";
}

function testGreaterOrEqual(val) {
    if (val >= 20) {
        return "20 or Over";
    }

    if (val >= 10) {
        return "10 or Over";
    }

    return "Less Than 10";
}

function testLessThan(val) {
    if (val < 100) {
        return "Less Than 100";
    }

    if (val < 10) {
        return "Less Than 10";
    }

    return "More Than 100";
}

function testLessOrEqual(val) {
    if (val <= 20) {
        return "Smaller or Equal to 20";
    }

    if (val <= 10) {
        return "Smaller or Equal to 10";
    }

    return "Greater Than 20";
}

console.log(testGreaterThan(10));
console.log(testGreaterOrEqual(10));
console.log(testLessThan(10));
console.log(testLessOrEqual(10));

console.log("\n=== Logical Operators ===");

function testLogicalAnd(val) {
    if (val <= 50 && val >= 25) {
        return "Yes";
    }

    return "No";
}

function testLogicalOr(val) {
    if (val < 10 || val > 20) {
        return "Outside";
    }

    return "Inside";
}

console.log(testLogicalAnd(10));
console.log(testLogicalOr(10));

console.log("\n=== Else Statements ===");

function testElse(val) {
    var result = "";

    if (val > 5) {
        result = "Bigger Than 5";
    } else {
        result = "5 or Smaller";
    }

    return result;
}

console.log(testElse(4));

console.log("\n=== Else If Statements ===");

function testElseIf(val) {
    if (val > 10) {
        return "Greater Than 10";
    } else if (val < 5) {
        return "Smaller Than 5";
    } else {
        return "Between 5 and 10";
    }
}

console.log(testElseIf(7));

function orderMyLogic(val) {
    if (val < 5) {
        return "Less Than 5";
    } else if (val < 10) {
        return "Less Than 10";
    } else {
        return "Greater Than or Equal to 10";
    }
}

console.log(orderMyLogic(3));

function testSize(num) {
    if (num < 5) {
        return "Tiny";
    } else if (num < 10) {
        return "Small";
    } else if (num < 15) {
        return "Medium";
    } else if (num < 20) {
        return "Large";
    } else {
        return "Huge";
    }
}

console.log(testSize(7));
console.log(testSize(19));
console.log(testSize(20));

console.log("\n=== Golf Code ===");

var names = ["Hole-In-One!", "Eagle", "Birdie", "Par", "Bogey", "Double Bogey", "Go Home..."];

function golfScore(par, strokes) {
    if (strokes == 1) {
        return names[0];
    } else if (strokes <= par - 2) {
        return names[1];
    } else if (strokes == par - 1) {
        return names[2];
    } else if (strokes == par) {
        return names[3];
    } else if (strokes == par + 1) {
        return names[4];
    } else if (strokes == par + 2) {
        return names[5];
    } else if (strokes == par + 3) {
        return names[6];
    }
}

console.log(golfScore(5, 4));
console.log(golfScore(3, 1));
console.log(golfScore(5, 8));

console.log("\n=== Switch Statements ===");

function caseInSwitch(val) {
    var answer = "";

    switch (val) {
        case 1:
            answer = "Alpha";
            break;
        case 2:
            answer = "Beta";
            break;
        case 3:
            answer = "Gamma";
            break;
        case 4:
            answer = "Delta";
            break;
    }

    return answer;
}

console.log(caseInSwitch(1));
console.log(caseInSwitch(2));
console.log(caseInSwitch(3));

function switchOfStuff(val) {
    var answer = "";

    switch (val) {
        case "A":
            answer = "Apple";
            break;
        case "B":
            answer = "Bird";
            break;
        case "C":
            answer = "Cat";
            break;
        default:
            answer = "Stuff";
            break;
    }

    return answer;
}

console.log(switchOfStuff("A"));
console.log(switchOfStuff(5));

function sequentialSizes(val) {
    var answer = "";

    switch (val) {
        case 1:
        case 2:
        case 3:
            answer = "Low";
            break;
        case 4:
        case 5:
        case 6:
            answer = "Medium";
            break;
        case 7:
        case 8:
        case 9:
            answer = "High";
            break;
    }

    return answer;
}

console.log(sequentialSizes(1));
console.log(sequentialSizes(3));
console.log(sequentialSizes(6));
console.log(sequentialSizes(4));
console.log(sequentialSizes(7));
console.log(sequentialSizes(9));

function finalSwitch(val) {
    var answer = "";

    switch (val) {
        case "Bob":
            answer = "Marley";
            break;
        case 42:
            answer = "The Answer";
            break;
        case 1:
            answer = "There is No #1";
            break;
        case 99:
            answer = "Missed by This Much";
            break;
        case 7:
            answer = "Ate Nine";
            break;
    }

    return answer;
}

console.log(finalSwitch("Bob"));
console.log(finalSwitch(42));