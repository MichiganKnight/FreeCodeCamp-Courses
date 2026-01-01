let content = document.getElementById("content");
const user = returnUserObject("user1", "password123");

displayUserObject(user);

function returnUserObject(inputUsername, inputPassword) {
    return {
        username: inputUsername,
        password: inputPassword
    };
}

function displayUserObject(userObject) {
    if (content !== null) {
        content.innerText = `Username: ${userObject.username} | Password: ${userObject.password}`;
    }
}