function logValue(value: number | string) {
    if (typeof value === "string") {
        console.log(value.toUpperCase());
    } else {
        console.log(value);
    }
}

logValue("hello");
logValue(10);