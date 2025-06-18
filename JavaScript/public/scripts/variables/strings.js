console.log("=== Strings ===");

var firstName = "Alan";
var lastName = "Turing";

console.log(firstName, lastName);

var myFirstName = "Drew";
var myLastName = "Fleming";

console.log(myFirstName, myLastName);

console.log("\n=== Escape Sequences ===");

var myStr = "I am a \"double quoted\" string inside \"double quotes\"";

console.log(myStr);

var myStr = '<a href="http://www.example.com" target="_blank">Link</a>';
console.log(myStr);

/*
    \' Single Quote
    \" Double Quote
    \\ Backslash
    \n Newline
    \r Carriage Return
    \t Tab
    \b Backspace
    \f Form Feed
*/

var myStr = "FirstLine\n\t\\SecondLine\nThirdLine";
console.log(myStr);

console.log("\n=== Concatenated Strings ===");

var ourStr = "I come first. " + "I come second.";
var myStr = "This is the start. " + "This is the end.";

console.log(ourStr + "\n" + myStr);

var ourStr = "I come first. ";
ourStr += "I come second.";

var myStr = "This is the first sentence. ";
myStr += "This is the second sentence."

console.log(ourStr + "\n" + myStr);

var myName = "Drew";
var myStr = "My name is " + myName + " and I am well!";
console.log(myStr);

var someAdjective = "worthwhile";
var myStr = "Learning to code is ";
myStr += someAdjective;

console.log(myStr);

console.log("\n=== String Methods ===");

var firstName = "Drew";
var lastName = "Fleming";

var firstNameLength = firstName.length;
var lastNameLength = lastName.length;

console.log(firstNameLength, lastNameLength);

console.log("\n=== Bracket Notation ===");

var firstName = "Drew";
var lastName = "Fleming";

var firstLetterOfFirstName = firstName[0];
var firstLetterOfLastName = lastName[0];

console.log(firstLetterOfFirstName, firstLetterOfLastName);

console.log("\n=== String Immutability ===");

var myStr = "Jello World";

myStr = "Hello World";

console.log(myStr);

console.log("\n=== Find Nth Character ===");

var firstName = "Drew";
var lastName = "Fleming";

var secondLetterOfFirstName = firstName[1];
var secondLetterOfLastName = lastName[1];

var lastLetterOfFirstName = firstName[firstName.length - 1];
var lastLetterOfLastName = lastName[lastName.length - 1];

console.log(secondLetterOfFirstName, secondLetterOfLastName);
console.log(lastLetterOfFirstName, lastLetterOfLastName);

console.log("\n=== Word Blanks ===");

/**
 * Madlibs Game?
 * 
 * @param myNoun A Noun
 * @param myAdjective An Adjective
 * @param myVerb A Verb
 * @param myAdverb An Adverb
 * @returns 
 */
function wordBlanks(myNoun, myAdjective, myVerb, myAdverb) {
    var result = "";
    result += `The ${myAdjective} ${myNoun} ${myVerb} to the Store ${myAdverb}.`;

    return result;
}

console.log(wordBlanks("Dog", "Big", "Ran", "Quickly"));
console.log(wordBlanks("Bike", "Slow", "Flew", "Slowly"));