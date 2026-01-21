const BASE_URL = "http://localhost:8081";

const ingredientListElement = document.getElementById("ingredient-list");
const addIngredientNameInput = document.getElementById("add-ingredient-name-input");
const addIngredientButton = document.getElementById("add-ingredient-submit-button");
const deleteIngredientNameInput = document.getElementById("delete-ingredient-name-input");
const deleteIngredientButton = document.getElementById("delete-ingredient-submit-button");

addIngredientButton.addEventListener("click", addIngredient);
deleteIngredientButton.addEventListener("click", deleteIngredient);

let ingredients = [];

window.addEventListener("DOMContentLoaded", async () => {
    await getIngredients();
});

async function addIngredient() {
    const ingredientName = addIngredientNameInput.value.trim();
    if (ingredientName) {
        const response = await fetch(`${BASE_URL}/ingredients`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${sessionStorage.getItem("auth-token")}`
            },
            body: JSON.stringify({
                name: ingredientName
            })
        });

        if (response.ok) {
            addIngredientNameInput.value = "";
            await getIngredients();
            await refreshIngredientList();
        } else {
            alert("Failed to Add Ingredient");
        }
    }
}

async function deleteIngredient() {
    const ingredientName = deleteIngredientNameInput.value.trim();
    const ingredientToDelete = ingredients.find(
        ingredient => ingredient.name.toLowerCase().trim() === ingredientName.toLowerCase().trim()
    );

    if (ingredientToDelete) {
        const response = await fetch(`${BASE_URL}/ingredients/${ingredientToDelete.id}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${sessionStorage.getItem("auth-token")}`
            }
        });

        if (response.ok) {
            deleteIngredientNameInput.value = "";
            await getIngredients();
        } else {
            alert("Failed to Delete Ingredient");
        }
    } else {
        alert("Ingredient Not Found");
    }
}

async function getIngredients() {
    try {
        const response = await fetch(`${BASE_URL}/ingredients`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${sessionStorage.getItem("auth-token")}`
            }
        });

        ingredients = await response.json();

        await refreshIngredientList();
    } catch (error) {
        alert("Failed to Get Ingredients");
    }
}

async function refreshIngredientList() {
    ingredientListElement.innerHTML = "";

    for (let ingredient of ingredients) {
        ingredientListElement.innerHTML += `<li>${ingredient.name}</li>`;
    }
}

