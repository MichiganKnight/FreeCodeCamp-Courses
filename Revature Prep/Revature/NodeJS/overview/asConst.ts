const CARS = [
    {
        manufacturer: "Ford",
        model: "Mustang",
        bodyStyle: "Sedan"
    },
    {
        manufacturer: "Tesla",
        model: "Model 3",
        bodyStyle: "SUV"
    },
    {
        manufacturer: "Chevrolet",
        model: "Camaro",
        bodyStyle: "Sedan"
    },
    {
        manufacturer: "Honda",
        model: "Civic",
        bodyStyle: "Sedan"
    },
    {
        manufacturer: "Toyota",
        model: "Camry",
        bodyStyle: "Sedan"
    }
] as const;

type Car = typeof CARS[number];

const newCar: Car = {
    manufacturer: "Ford",
    model: "F-150",
    bodyStyle: "Truck"
};