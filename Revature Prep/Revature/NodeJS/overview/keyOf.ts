/*class demo {
    p: number = 6253;
    r: string = "hgsv jshdv jv bhdcw dbjw gc jshd g hds";
}

class demo1 {
    s: keyof demo;
    example(t: demo): any {
        return t[this.s];
    }
}

let d: demo1 = new demo1();
d.s = "p";
d.s = "r";

var res = d.example(new demo());
console.log(res);

function getDemo<T, K extends keyof T>(vars: T, keysobj: K) {
    return vars[keysobj];
}

function setDemo<T, K extends keyof T>(vars: T, keysobj: K, valuesobj: T[K]) {
    vars[keysobj] = valuesobj;
}

let vars1 = {
    ac: 10,
    bd: "hfds, hscfd hcdf hwcf h we h"
};

let p = getDemo(vars1, "ac");
let q = getDemo(vars1, "bd");
let re = getDemo(vars1, "p", "string");

setDemo(vars1, "p", "string");

console.log(re);*/