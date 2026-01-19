CREATE TABLE CHEF
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    is_admin BOOLEAN
);

CREATE TABLE RECIPE
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    instructions VARCHAR(255) NOT NULL,
    chef_id      INT,
    FOREIGN KEY (chef_id) REFERENCES CHEF (id)
);

CREATE TABLE INGREDIENT
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE RECIPE_INGREDIENT
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    recipe_id     INT            NOT NULL,
    ingredient_id INT            NOT NULL,
    vol           DECIMAL(10, 2) NOT NULL,
    unit          VARCHAR(20)    NOT NULL,
    is_metric     BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (recipe_id) REFERENCES RECIPE (id),
    FOREIGN KEY (ingredient_id) REFERENCES INGREDIENT (id)
);

DELETE
FROM RECIPE_INGREDIENT;
DELETE
FROM RECIPE;
DELETE
FROM CHEF;
DELETE
FROM INGREDIENT;

ALTER TABLE CHEF
    ALTER COLUMN id RESTART WITH 1;
ALTER TABLE INGREDIENT
    ALTER COLUMN id RESTART WITH 1;
ALTER TABLE RECIPE
    ALTER COLUMN id RESTART WITH 1;
ALTER TABLE RECIPE_INGREDIENT
    ALTER COLUMN id RESTART WITH 1;

INSERT INTO CHEF (username, email, password, is_admin)
VALUES
    ('JoeCool', 'snoopy@null.com', 'redbarron', false),
    ('CharlieBrown', 'goodgrief@peanuts.com', 'thegreatpumpkin', false),
    ('RevaBuddy', 'revature@revature.com', 'codelikeaboss', false),
    ('ChefTrevin', 'trevin@revature.com', 'trevature', true);

INSERT INTO RECIPE (name, instructions, chef_id)
VALUES
    ('carrot soup', 'Put carrot in water.  Boil.  Maybe salt.', 1),
    ('potato soup', 'Put potato in water.  Boil.  Maybe salt.', 2),
    ('tomato soup', 'Put tomato in water.  Boil.  Maybe salt.', 2),
    ('lemon rice soup', 'Put lemon and rice in water.  Boil.  Maybe salt.', 4),
    ('stone soup', 'Put stone in water.  Boil.  Maybe salt.', 4);

INSERT INTO INGREDIENT (name)
VALUES
    ('carrot'),
    ('potato'),
    ('tomato'),
    ('lemon'),
    ('rice'),
    ('stone');


INSERT INTO RECIPE_INGREDIENT (id, recipe_id, ingredient_id, vol, unit)
VALUES
    (default, 1, 1, 1, 'cups'),
    (default, 2, 2, 2, 'cups'),
    (default, 3, 3, 2, 'cups'),
    (default, 4, 4, 1, 'Tbs'),
    (default, 4, 5, 2, 'cups');