-- \dt Shows Tables in Docker

-- Create Users Table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100),
    date_of_birth DATE
);

-- Insert Sample Data into Users Table
INSERT INTO
    users (
        first_name,
        last_name,
        email,
        date_of_birth
    )
VALUES (
        'Olivia',
        'Martinez',
        'olivia.martinez@example.com',
        '1990-03-14'
    ),
    (
        'Liam',
        'Johnson',
        'liam.johnson@example.com',
        '1988-07-22'
    ),
    (
        'Emma',
        'Brown',
        'emma.brown@example.com',
        '1995-11-05'
    ),
    (
        'Noah',
        'Davis',
        'noah.davis@example.com',
        '1992-01-30'
    ),
    (
        'Ava',
        'Garcia',
        'ava.garcia@example.com',
        '1987-09-12'
    ),
    (
        'William',
        'Miller',
        'william.miller@example.com',
        '1993-06-18'
    ),
    (
        'Sophia',
        'Wilson',
        'sophia.wilson@example.com',
        '1998-12-02'
    ),
    (
        'James',
        'Moore',
        'james.moore@example.com',
        '1985-04-25'
    ),
    (
        'Isabella',
        'Taylor',
        'isabella.taylor@example.com',
        '2000-08-08'
    ),
    (
        'Benjamin',
        'Anderson',
        'benjamin.anderson@example.com',
        '1991-05-16'
    );

-- Query to Select All Users
SELECT * FROM users;

-- Query to Select Distinct Emails
SELECT DISTINCT email FROM users;

-- Update Email for User with First Name 'James'
UPDATE users
SET
    email = 'new_email@example.com'
WHERE
    first_name = 'James';

-- Create Films Table
CREATE TABLE films (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    release_date DATE,
    price DECIMAL(5, 2),
    rating VARCHAR(10),
    user_rating DECIMAL(2, 1) CHECK (
        user_rating >= 1
        AND user_rating <= 5
    )
);

-- Insert Sample Data into Films Table
INSERT INTO
    films (
        title,
        release_date,
        price,
        rating,
        user_rating
    )
VALUES (
        'The Shawshank Redemption',
        '1994-09-23',
        9.99,
        'R',
        4.8
    ),
    (
        'The Godfather',
        '1972-03-24',
        12.99,
        'R',
        4.9
    ),
    (
        'Inception',
        '2010-07-16',
        14.99,
        'PG-13',
        4.7
    ),
    (
        'The Dark Knight',
        '2008-07-18',
        13.99,
        'PG-13',
        4.9
    ),
    (
        'Pulp Fiction',
        '1994-10-14',
        8.99,
        'R',
        4.6
    ),
    (
        'Forrest Gump',
        '1994-07-06',
        7.99,
        'PG-13',
        4.5
    ),
    (
        'The Matrix',
        '1999-03-31',
        9.49,
        'R',
        4.7
    ),
    (
        'Toy Story',
        '1995-11-22',
        6.99,
        'G',
        4.4
    ),
    (
        'The Lion King',
        '1994-06-24',
        7.49,
        'G',
        4.3
    ),
    (
        'Avengers: Endgame',
        '2019-04-26',
        15.99,
        'PG-13',
        4.2
    ),
    (
        'Jurassic Park',
        '1993-06-11',
        10.99,
        'PG-13',
        4.0
    ),
    (
        'Titanic',
        '1997-12-19',
        11.99,
        'PG-13',
        4.1
    );

-- Query to Select All Films
SELECT * FROM films;

-- Query to Select First 5 Films
SELECT * FROM films LIMIT 5;

-- Query to Count Total Number of Films
SELECT COUNT(*) FROM films;

SELECT SUM(price) AS "Total Price" FROM films;

-- Query to Calculate Average User Rating
SELECT AVG(user_rating) AS "Average User Rating" FROM films;

-- Query to Find Highest and Lowest Film Prices
SELECT MAX(price) AS "Highest Price", MIN(price) AS "Lowest Price"
FROM films;

-- Query to Group Films by Rating and Calculate Average User Rating per Rating
SELECT rating AS "Rating", AVG(user_rating) AS "Average User Rating"
FROM films
GROUP BY
    rating;

-- Create Film Category Table
CREATE TABLE film_category (
    category_id SERIAL PRIMARY KEY,
    film_id INTEGER REFERENCES films (id),
    category_name VARCHAR(50) NOT NULL
);

-- Insert Sample Data into Film Category Table
INSERT INTO
    film_category (film_id, category_name)
VALUES (1, 'Drama'),
    (2, 'Crime'),
    (3, 'Sci-Fi'),
    (4, 'Action'),
    (5, 'Crime'),
    (6, 'Drama'),
    (7, 'Sci-Fi'),
    (8, 'Animation'),
    (9, 'Animation'),
    (10, 'Action'),
    (11, 'Sci-Fi'),
    (12, 'Romance');

-- Verify Film Categories
SELECT * FROM film_category;

-- Create Actors Table
CREATE TABLE actors (
    actor_id SERIAL PRIMARY KEY,
    actor_name VARCHAR(255) NOT NULL
);

-- Insert Sample Data into Actors Table
INSERT INTO
    actors (actor_name)
VALUES ('Tim Robbins'),
    ('Morgan Freeman'),
    ('Marlon Brando'),
    ('Al Pacino'),
    ('Leonardo DiCaprio'),
    ('Joseph Gordon-Levitt'),
    ('Christian Bale'),
    ('Heath Ledger'),
    ('John Travolta'),
    ('Samuel L. Jackson'),
    ('Tom Hanks'),
    ('Keanu Reeves'),
    ('Laurence Fishburne'),
    ('Tim Allen'),
    ('Matthew Broderick'),
    ('James Earl Jones'),
    ('Robert Downey Jr.'),
    ('Chris Evans'),
    ('Scarlett Johansson'),
    ('Sam Neill'),
    ('Laura Dern'),
    ('Kate Winslet');

-- Create Film Actors Junction Table
CREATE TABLE film_actors (
    film_id INTEGER REFERENCES films (id),
    actor_id INTEGER REFERENCES actors (actor_id),
    PRIMARY KEY (film_id, actor_id)
);

-- Insert Sample Data into Film_Actors junction Table
INSERT INTO
    film_actors (film_id, actor_id)
VALUES (1, 1),
    (1, 2),
    (2, 3),
    (2, 4),
    (3, 5),
    (3, 6),
    (4, 7),
    (4, 8),
    (5, 9),
    (5, 10),
    (6, 11),
    (7, 12),
    (7, 13),
    (8, 11),
    (8, 14),
    (9, 15),
    (9, 16),
    (10, 17),
    (10, 18),
    (10, 19),
    (11, 20),
    (11, 21),
    (12, 5),
    (12, 22);

-- Verify Film Actors
SELECT * FROM film_actors;

-- Query to Retrieve Films with Their Actors
SELECT f.id, f.title, a.actor_name
FROM
    films f
    INNER JOIN film_actors fa ON f.id = fa.film_id
    INNER JOIN actors a ON fa.actor_id = a.actor_id
ORDER BY f.id;

SELECT f.id AS "Film ID", f.title AS "Title", a.actor_name AS "Actor Name"
FROM
    films f
    INNER JOIN film_actors fa ON f.id = fa.film_id
    INNER JOIN actors a ON fa.actor_id = a.actor_id
ORDER BY f.id;

-- Insert Additional Film
INSERT INTO
    films (
        title,
        release_date,
        price,
        rating,
        user_rating
    )
VALUES (
        'Interstellar',
        '2014-11-07',
        14.99,
        'PG-13',
        4.6
    );

-- Verify Insertion
SELECT * FROM films;

-- Query to Retrieve All Films with Their Actors (Including Films Without Actors)
SELECT f.id, f.title, a.actor_name
FROM
    films f
    LEFT JOIN film_actors fa ON f.id = fa.film_id
    LEFT JOIN actors a ON fa.actor_id = a.actor_id
ORDER BY f.id;

SELECT f.id AS "Film ID", f.title AS "Title", a.actor_name AS "Actor Name"
FROM
    films f
    LEFT JOIN film_actors fa ON f.id = fa.film_id
    LEFT JOIN actors a ON fa.actor_id = a.actor_id
ORDER BY f.id;

-- Insert Actors for the New Film
INSERT INTO
    actors (actor_name)
VALUES ('Matthew McConaughey'),
    ('Anne Hathaway');

-- Link New Film with Its Actors
INSERT INTO
    film_actors (film_id, actor_id)
VALUES (13, 23),
    (13, 24);

-- Query to Retrieve All Films with Their Actors (Including Films Without Actors)
SELECT f.id, f.title, a.actor_name
FROM
    films f
    LEFT JOIN film_actors fa ON f.id = fa.film_id
    LEFT JOIN actors a ON fa.actor_id = a.actor_id
ORDER BY f.id;

SELECT f.id AS "Film ID", f.title AS "Title", a.actor_name AS "Actor Name"
FROM
    films f
    LEFT JOIN film_actors fa ON f.id = fa.film_id
    LEFT JOIN actors a ON fa.actor_id = a.actor_id
ORDER BY f.id;

-- Union Example: Combine Film Titles and Actor Names into a Single List
SELECT title AS name
FROM films
UNION
SELECT actor_name AS name
FROM actors
ORDER BY name;

-- Another Union Example with Different Alias
SELECT title AS name
FROM films
UNION
SELECT actor_name AS actor
FROM actors
ORDER BY name;

-- Subquery Example: Retrieve Film Titles Along with One Lead Actor for Each Film
SELECT title AS "Film Title", (
        SELECT actor_name AS "Actor Name"
        FROM actors a
            JOIN film_actors fa ON a.actor_id = fa.actor_id
        WHERE
            fa.film_id = f.id
        LIMIT 1
    ) AS "Lead Actor"
FROM films f;

SELECT title as "Film Title"
FROM films
WHERE
    id IN (
        SELECT fa.film_id
        FROM film_actors fa
            JOIN actors a ON a.actor_id = fa.actor_id
        WHERE
            a.actor_name IN ('Leonardo DiCaprio', 'Tom Hanks')
    );