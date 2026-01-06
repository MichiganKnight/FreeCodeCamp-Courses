/*class Animal {
    private species: string;
    private name: string;
    private food: string;
    private age: number;

    constructor(species: string, name: string, food: string, age: number) {
        this.species = species;
        this.name = name;
        this.food = food;
        this.age = age;
    }

    get getSpecies(): string {
        return this.species;
    }

    get getName(): string {
        return this.name;
    }

    get getFood(): string {
        return this.food;
    }

    get getAge(): number {
        return this.age;
    }

    set setFood(newFood: string) {
        this.food = newFood;
    }

    set setAge(newAge: number) {
        this.age = newAge;
    }
}

let animal = new Animal("Dog", "Scooby", "Scooby Snacks", 5);

console.log(animal);

animal.setAge = 6;
animal.setFood = "More Scooby Snacks";

console.log(animal);*/
var __esDecorate = (this && this.__esDecorate) || function (ctor, descriptorIn, decorators, contextIn, initializers, extraInitializers) {
    function accept(f) { if (f !== void 0 && typeof f !== "function") throw new TypeError("Function expected"); return f; }
    var kind = contextIn.kind, key = kind === "getter" ? "get" : kind === "setter" ? "set" : "value";
    var target = !descriptorIn && ctor ? contextIn["static"] ? ctor : ctor.prototype : null;
    var descriptor = descriptorIn || (target ? Object.getOwnPropertyDescriptor(target, contextIn.name) : {});
    var _, done = false;
    for (var i = decorators.length - 1; i >= 0; i--) {
        var context = {};
        for (var p in contextIn) context[p] = p === "access" ? {} : contextIn[p];
        for (var p in contextIn.access) context.access[p] = contextIn.access[p];
        context.addInitializer = function (f) { if (done) throw new TypeError("Cannot add initializers after decoration has completed"); extraInitializers.push(accept(f || null)); };
        var result = (0, decorators[i])(kind === "accessor" ? { get: descriptor.get, set: descriptor.set } : descriptor[key], context);
        if (kind === "accessor") {
            if (result === void 0) continue;
            if (result === null || typeof result !== "object") throw new TypeError("Object expected");
            if (_ = accept(result.get)) descriptor.get = _;
            if (_ = accept(result.set)) descriptor.set = _;
            if (_ = accept(result.init)) initializers.unshift(_);
        }
        else if (_ = accept(result)) {
            if (kind === "field") initializers.unshift(_);
            else descriptor[key] = _;
        }
    }
    if (target) Object.defineProperty(target, contextIn.name, descriptor);
    done = true;
};
var __runInitializers = (this && this.__runInitializers) || function (thisArg, initializers, value) {
    var useValue = arguments.length > 2;
    for (var i = 0; i < initializers.length; i++) {
        value = useValue ? initializers[i].call(thisArg, value) : initializers[i].call(thisArg);
    }
    return useValue ? value : void 0;
};
var Animal = function () {
    var _a;
    var _species_decorators;
    var _species_initializers = [];
    var _species_extraInitializers = [];
    var _name_decorators;
    var _name_initializers = [];
    var _name_extraInitializers = [];
    return _a = /** @class */ (function () {
            function Animal(species, name, food, age) {
                this.species = __runInitializers(this, _species_initializers, void 0);
                this.name = (__runInitializers(this, _species_extraInitializers), __runInitializers(this, _name_initializers, void 0));
                this.food = __runInitializers(this, _name_extraInitializers);
                this.species = species;
                this.name = name;
                this.food = food;
                this.age = age;
            }
            Object.defineProperty(Animal.prototype, "getSpecies", {
                get: function () {
                    return this.species;
                },
                enumerable: false,
                configurable: true
            });
            Object.defineProperty(Animal.prototype, "getName", {
                get: function () {
                    return this.name;
                },
                enumerable: false,
                configurable: true
            });
            Object.defineProperty(Animal.prototype, "getFood", {
                get: function () {
                    return this.food;
                },
                enumerable: false,
                configurable: true
            });
            Object.defineProperty(Animal.prototype, "getAge", {
                get: function () {
                    return this.age;
                },
                enumerable: false,
                configurable: true
            });
            Object.defineProperty(Animal.prototype, "setFood", {
                set: function (newFood) {
                    this.food = newFood;
                },
                enumerable: false,
                configurable: true
            });
            Object.defineProperty(Animal.prototype, "setAge", {
                set: function (newAge) {
                    this.age = newAge;
                },
                enumerable: false,
                configurable: true
            });
            return Animal;
        }()),
        (function () {
            var _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
            _species_decorators = [required];
            _name_decorators = [required];
            __esDecorate(null, null, _species_decorators, { kind: "field", name: "species", static: false, private: false, access: { has: function (obj) { return "species" in obj; }, get: function (obj) { return obj.species; }, set: function (obj, value) { obj.species = value; } }, metadata: _metadata }, _species_initializers, _species_extraInitializers);
            __esDecorate(null, null, _name_decorators, { kind: "field", name: "name", static: false, private: false, access: { has: function (obj) { return "name" in obj; }, get: function (obj) { return obj.name; }, set: function (obj, value) { obj.name = value; } }, metadata: _metadata }, _name_initializers, _name_extraInitializers);
            if (_metadata) Object.defineProperty(_a, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        })(),
        _a;
}();
var animal = new Animal("", "", "Scooby Snacks", 5);
function required(target, key) {
    var currentValue = target[key];
    Object.defineProperty(target, key, {
        get: function () { return currentValue; },
        set: function (value) {
            if (!value) {
                throw new Error("".concat(key, " is a Required Field"));
            }
            currentValue = value;
        }
    });
}
