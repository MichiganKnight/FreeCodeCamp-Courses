const BASE_URL = "http://localhost:8081";

let recipes = [];

window.addEventListener("DOMContentLoaded", () => {
    const adminLink = document.getElementById("admin-link");
    const logoutButton = document.getElementById("logout-button");
    const recipeList = document.getElementById("recipe-list");
    const searchInput = document.getElementById("search-input");
    const searchButton = document.getElementById("search-button");
    const addNameInput = document.getElementById("add-recipe-name-input");
    const addInstructionsInput = document.getElementById("add-recipe-instructions-input");
    const addRecipeButton = document.getElementById("add-recipe-submit-input");
    const updateNameInput = document.getElementById("update-recipe-name-input");
    const updateInstructionsInput = document.getElementById("update-recipe-instructions-input");
    const updateRecipeButton = document.getElementById("update-recipe-submit-input");
    const deleteInput = document.getElementById("delete-recipe-name-input");
    const deleteRecipeButton = document.getElementById("delete-recipe-submit-input");

    if (sessionStorage.getItem("auth-token")) {
        logoutButton.hidden = false;
    }

    if (sessionStorage.getItem("is-admin")) {
        adminLink.hidden = false;
    }

    addRecipeButton.addEventListener("click", addRecipe);
    updateRecipeButton.addEventListener("click", updateRecipe);
    deleteRecipeButton.addEventListener("click", deleteRecipe);
    searchButton.addEventListener("click", searchRecipes);
    logoutButton.addEventListener("click", processLogout);

    getRecipes();

    async function searchRecipes() {

    }

    async function addRecipe() {
        const newRecipeName = addNameInput.value;
        const newRecipeInstructions = addInstructionsInput.value;

        if (newRecipeName && newRecipeInstructions) {
            const response = await fetch(`${BASE_URL}/recipes`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${sessionStorage.getItem("auth-token")}`
                },
                body: JSON.stringify({
                    name: newRecipeName,
                    instructions: newRecipeInstructions
                })
            });

            if (response.ok) {
                addNameInput.value = "";
                addInstructionsInput.value = "";

                getRecipes();
                refreshRecipeList();
            }
        }
    }

    async function updateRecipe() {
        const updatedRecipeName = updateNameInput.value;
        const updatedRecipeInstructions = updateInstructionsInput.value;

        const updatedRecipe = recipes.find(recipe => recipe.name.toLowerCase() === updatedRecipeName.toLowerCase());
        updatedRecipe.instructions = updatedRecipeInstructions;

        const response = await fetch(`${BASE_URL}/recipes/${updatedRecipe.id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${sessionStorage.getItem("auth-token")}`
            },
            body: JSON.stringify(updatedRecipe)
        });

        if (response.ok) {
            updateNameInput.value = "";
            updateInstructionsInput.value = "";
        } else {
            alert("Failed to Update Recipe");
        }
    }

    async function deleteRecipe() {
        const recipeToDelete = recipes.find(recipe => recipe.name.toLowerCase() === deleteInput.value.toLowerCase());

        const response = await fetch(`${BASE_URL}/recipes/${recipeToDelete.id}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${sessionStorage.getItem("auth-token")}`
            }
        });

        if (response.ok) {
            deleteInput.value = "";
            getRecipes();
            refreshRecipeList();
        } else {
            alert("Failed to Delete Recipe");
        }
    }

    async function getRecipes() {
        const response = await fetch(`${BASE_URL}/recipes`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${sessionStorage.getItem("auth-token")}`
            },
        });

        recipes = await response.json();

        refreshRecipeList();
    }

    function refreshRecipeList() {
        recipeList.innerHTML = "";

        for (let recipe of recipes) {
            recipeList.innerHTML += `<li>${recipe.name}</li>`;
        }
    }

    async function processLogout() {
        const response = await fetch(`${BASE_URL}/logout`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${sessionStorage.getItem("auth-token")}`
            }
        });

        if (response.ok) {
            sessionStorage.removeItem("auth-token");
            sessionStorage.removeItem("is-admin");

            setTimeout(window.location.href = "../login/login-page.html", 500);
        } else {
            alert("Failed to Logout");
        }
    }
});