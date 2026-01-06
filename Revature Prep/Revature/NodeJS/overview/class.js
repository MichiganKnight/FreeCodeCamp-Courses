var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (Object.prototype.hasOwnProperty.call(b, p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        if (typeof b !== "function" && b !== null)
            throw new TypeError("Class extends value " + String(b) + " is not a constructor or null");
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var Person = /** @class */ (function () {
    function Person(name) {
        this.name = name;
    }
    return Person;
}());
var Employee = /** @class */ (function (_super) {
    __extends(Employee, _super);
    function Employee(empCode, name) {
        var _this = _super.call(this, name) || this;
        _this.empCode = empCode;
        return _this;
    }
    Employee.prototype.displayName = function () {
        console.log("Employee Name: ".concat(this.name, " - Employee Code: ").concat(this.empCode));
    };
    Employee.prototype.getSalary = function () {
        return Math.round(Math.random() * 10000);
    };
    return Employee;
}(Person));
var emp = new Employee(1, "Drew Fleming");
emp.displayName();
console.log("Salary: $".concat(emp.getSalary(), ".00"));
