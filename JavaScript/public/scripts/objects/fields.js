console.log("=== Object Fields ===");

const createPerson = (name, age, gender) => ({ name, age, gender })

console.log(JSON.stringify(createPerson("Zodiac Hasbro", 56, "Male")));