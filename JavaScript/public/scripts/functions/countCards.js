console.log("=== Counting Cards ===");

function cc(card) {
    var count = 0;

    switch (card) {
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
            count++;
            break;
        case 10:
        case "J":
        case "Q":
        case "K":
        case "A":
            count--;
            break;
    }

    var holdBet = 'Hold';

    if (count > 0) {
        holdBet = 'Bet';
    }

    return `${count} ${holdBet}`;
}

console.log(cc(4));