let employeeInfoElement = document.getElementById('employeeInfo');
let sumElement = document.getElementById('sum');

function submitEmployee() {
    let employeeProfile = ['John', 'Smith', '89,000']
    renderEmployee(...employeeProfile);
}

function sum(...numbers) {
    let sumNumbers = 0;

    for (const number of numbers) {
        sumNumbers += number;
    }

    sumElement.innerText = sumNumbers;
}

function renderEmployee(firstName, lastName, salary) {
    employeeInfoElement.innerText = 'Name: ' + lastName + ', ' + firstName + ' - $' + salary;
}

(function run() {
    submitEmployee();
    sum(1, 2, 3, 4, 5);
})();