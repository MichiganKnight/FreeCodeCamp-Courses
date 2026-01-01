let opening = document.getElementById("opening");
let theRest = document.getElementById("theRest");

(function() {
    setTimeout(toBe, 1000);
})();

function friendsRomansCountrymen() {
    opening.innerText = "Friends, Romans, Countrymen, ";
    setTimeout(placeholder, 1000);
}

function toBe() {
    opening.textContent += "To be, ";
    setTimeout(orNotToBe, 1000);
}

function thatIsTheQuestion() {
    opening.textContent += "that is the question: ";
    setTimeout(finishSpeech, 1000);
}

function myKingdomForAHorse() {
    theRest.textContent = "My kingdom for a horse! ";
    setTimeout(placeholder, 1000);
}

function orNotToBe() {
    opening.textContent += "or not to be, ";
    setTimeout(thatIsTheQuestion, 1000);
}

function placeholder() {
    console.log("This placeholder function does nothing. ");
}

function lendMeYourEars() {
    opening.textContent += "lend me your ears. ";
    setTimeout(placeholder, 1000);
}

function aHorse() {
    opening.innerText += "A horse! ";
    setTimeout(placeholder, 1000);
}

function finishSpeech() {
    theRest.innerText += `Whether 'tis nobler in the mind to suffer
    The slings and arrows of outrageous fortune,
    Or to take arms against a sea of troubles
    And by opposing end them. To die—to sleep,
    No more; and by a sleep to say we end
    The heart-ache and the thousand natural shocks
    That flesh is heir to: 'tis a consummation
    Devoutly to be wish'd. To die, to sleep;
    To sleep, perchance to dream—ay, there's the rub:
    For in that sleep of death what dreams may come,
    When we have shuffled off this mortal coil,
    Must give us pause—there's the respect
    That makes calamity of so long life.
    For who would bear the whips and scorns of time,
    Th'oppressor's wrong, the proud man's contumely,
    The pangs of dispriz'd love, the law's delay,
    The insolence of office, and the spurns
    That patient merit of th'unworthy takes,
    When he himself might his quietus make
    With a bare bodkin? Who would fardels bear,
    To grunt and sweat under a weary life,
    But that the dread of something after death,
    The undiscovere'd country, from whose bourn
    No traveller returns, puzzles the will,
    And makes us rather bear those ills we have
    Than fly to others that we know not of?
    Thus conscience doth make cowards of us all,
    And thus the native hue of resolution
    Is sicklied o'er with the pale cast of thought,
    And enterprises of great pith and moment
    With this regard their currents turn awry
    And lose the name of action.`;
}