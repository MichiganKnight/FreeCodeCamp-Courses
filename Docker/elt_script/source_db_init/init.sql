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