const cube = require('./JestOverview');

test('Cube of 5 is 125', () => {
    expect(cube(5)).toBe(125);
});