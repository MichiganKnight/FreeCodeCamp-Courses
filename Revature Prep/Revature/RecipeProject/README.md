# Revature Recipe Web Dev App

## Background

This project is a web-based recipe management application with user authentication and admin functionality. It allows
users to view, add, update, delete, and search recipes. Admins have additional capabilities to manage ingredients. The
frontend is built using HTML, CSS, and JavaScript, and communicates with a backend REST API built in Java.

---

## Getting Started

You will be working on the **frontend** of this application.

### Folder Structure

Your work will primarily be within the following folders:

```
src/main/resources/public/frontend/
├── login/
├── register/
├── recipe/
└── ingredients/
```

### Running the Application

The backend runs on: `http://localhost:8081/`

- **If the backend is running, make sure you are not running the automated tests at the same time.**
- Styling via CSS is optional and will not be graded.

---

## Requirements

---

### 1️⃣ User Registration:

📂 Location: `frontend/register/`

Files:

- `register-page.html`
- `register-page.js`

**Requirements:**

- The registration form should include:
    - Username, Email, Password, Repeat Password fields
    - Submit button with ID `register-button`
- JS Should:
    - Validate that all inputs are filled
    - Ensure password and repeated password match
    - Create a registration object and send a POST request to `http://localhost:8081/register`
    - On Success: Redirect to login page
    - On Failure: Show an alert

---

### 2️⃣ User Login and Logout

📂 Location: `frontend/login/` and `frontend/recipe/`

Files:

- `login-page.html`, `login-page.js`
- `recipe-page.html`, `recipe-page.js`

**Requirements:**

- Login form should include Username and Password fields
- JS Should:

    - Create a login request with `username` and `password`
    - On Success:
        - Parse the response (token and admin flag separated by space)
        - Store token in `sessionStorage` under `auth-token`
        - Store admin flag under `is-admin`
        - Redirect to `recipe-page.html`
    - On Failure: Show an alert

- Logout Logic (in `recipe-page.js`) Should:
    - Send POST request to `http://localhost:8081/logout`
    - Clear `sessionStorage` keys
    - Redirect to `login-page.html`

---

### 3️⃣ Recipe Management

📂 Location: `frontend/recipe/`

Files:

- `recipe-page.html`,
- `recipe-page.js`

**Requirements:**

#### HTML

- Add the Following Elements:
    - Add Recipe: name input, instructions input, add button (`add-recipe-submit-input`)
    - Update Recipe: name input, new instructions input, update button
    - Delete Recipe: name input, delete button
    - Search Bar: input and button
    - Recipe List Container (ID: `recipe-list`)
    - Admin-Only Link (ID: `admin-link`) - Initially Hidden

#### JavaScript

- On Page Load, Call:

    - `getRecipes()` to load all recipes
    - `displayAdminLink()` to toggle admin section visibility

- Implement:
    - `addRecipe()`: POST `http://localhost:8081/recipes` with name and instructions
    - `updateRecipe()`: PUT `http://localhost:8081/recipes/{id}` with new instructions
    - `deleteRecipe()`: DELETE `http://localhost:8081/recipes/{id}`
    - `searchRecipes()`: filter recipes locally based on name
    - All Requests Must Use:
    ```javascript
    headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("auth-token")
    }
    ```
    - After each action, refresh the list dynamically

---

### 4️⃣ Ingredient Management (Admin Only)

📂 Location: `ffrontend/ingredients`

Files:

- `ingredients-page.html`,
- `ingredients-page.js`

**Requirements:**

#### HTML

- Add:
    - Add Ingredient: name input and button
    - Delete Ingredient: name input and button
    - Ingredient List Container (ID: `ingredient-list`)

#### JavaScript

- On Page Load, call `getIngredients()`
- Implement:
    - `addIngredient()`: POST `http://localhost:8081/ingredients` with name
    - `deleteIngredient()`: DELETE `http://localhost:8081/ingredients/{id}`
    - `refreshIngredientList()`: Populate list from an array
        - Use:
        ```javascript
        headers: {
            "Authorization": "Bearer " + sessionStorage.getItem("auth-token")
        }
        ```
        - Token and admin status must be verified via `sessionStorage`

---

## CORS Explanation

CORS (Cross-Origin Resource Sharing) is a security feature implemented by browsers to restrict cross-origin HTTP requests initiated from scripts. Since your frontend and backend run on the same machine but from different contexts (e.g., `file://` for frontend vs `http://localhost:8081` for backend), CORS preflight requests ensure that the backend explicitly allows the frontend to interact with it.

Modern browsers send a preflight `OPTIONS` request before making actual requests like `POST`, `PUT`, or `DELETE`. The backend must respond with the appropriate headers:

```http
Access-Control-Allow-Origin: *
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
Access-Control-Allow-Headers: Content-Type, Authorization
```

Your MockServer and backend are pre-configured to include these headers for all necessary routes.

---

## Helpful References

- MDN Fetch API Guide:  
  https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch

- MDN CORS Overview:  
  https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS

- JavaScript `fetch()` syntax and examples:  
  https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API

---