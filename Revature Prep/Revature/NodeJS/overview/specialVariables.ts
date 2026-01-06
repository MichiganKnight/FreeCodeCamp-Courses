let v: any = true;
v = "string";
Math.round(v);

let thing: unknown = 1;
thing = "string";
/*thing = {
    func: (): void => {
        console.log("I think therefore I am");
    }
}*/

(thing as { func: Function }).func();