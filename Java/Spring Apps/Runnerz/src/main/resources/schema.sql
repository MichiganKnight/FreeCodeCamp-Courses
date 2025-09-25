CREATE TABLE IF NOT EXISTS run (
   id SERIAL PRIMARY KEY,
   title VARCHAR(250) NOT NULL,
    started_on TIMESTAMP NOT NULL,
    completed_on TIMESTAMP NOT NULL,
    miles INT NOT NULL CHECK (miles > 0),
    location VARCHAR(100) NOT NULL,
    version INT
    );
