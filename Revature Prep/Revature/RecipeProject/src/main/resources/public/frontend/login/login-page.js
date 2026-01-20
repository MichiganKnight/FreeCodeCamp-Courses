const BASE_URL = "http://localhost:8081";

const usernameInput = document.getElementById("login-input");
const passwordInput = document.getElementById("password-input");
const loginButton = document.getElementById("login-button");
const logoutButton = document.getElementById("logout-button");

loginButton.addEventListener("click", processLogin);

async function processLogin() {
    const username = usernameInput.value;
    const password = passwordInput.value;

    if (username === "" || password === "") {
        return;
    }

    const requestBody = {
        username,
        password
    }

    const requestOptions = {
        method: "POST",
        mode: "cors",
        cache: "no-cache",
        credentials: "same-origin",
        headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Headers": "*"
        },
        redirect: "follow",
        referrerPolicy: "no-referrer",
        body: JSON.stringify(requestBody)
    };

    try {
        const response = await fetch(`${BASE_URL}/login`, requestOptions);

        if (response.status === 200) {
            const [token, isAdminString] = (await response.text()).split(' ');
            const isAdmin = isAdminString === "true";

            sessionStorage.setItem("auth-token", token);
            sessionStorage.setItem("is-admin", isAdmin.toString());

            setTimeout(window.location.href = "../recipe/recipe-page.html", 500);
        } else if (response.status === 401) {
            alert("Incorrect Login");
        } else {
            alert("An Unknown Error Occurred");
        }
    } catch (error) {
        console.error(error);

        alert("Failed to Login");
    }
}