const BASE_URL = "http://localhost:8081";

const usernameInput = document.getElementById("username-input");
const emailInput = document.getElementById("email-input");
const passwordInput = document.getElementById("password-input");
const confirmPasswordInput = document.getElementById("confirm-password-input");
const registerButton = document.getElementById("register-button");

registerButton.addEventListener("click", processRegistration);

async function processRegistration() {
    const username = usernameInput.value;
    const email = emailInput.value;
    const password = passwordInput.value;
    const confirmPassword = confirmPasswordInput.value;

    if (username === "" || email === "" || password === "" || confirmPassword === "") {
        return;
    }

    if (password !== confirmPassword) {
        alert("Passwords Do Not Match");
    }

    const registerBody = {
        username,
        email,
        password
    };

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
        body: JSON.stringify(registerBody)
    };

    try {
        const response = await fetch(`${BASE_URL}/register`, requestOptions);

        if (response.status === 201) {
            setTimeout(window.location.href = "/login-page.html", 500);
        } else if (response.status === 409) {
            alert("Username or Email Already Exists");
        } else {
            alert("An Unknown Error Occurred");
        }
    } catch (error) {
        console.error(error);

        alert("Failed to Register");
    }
}