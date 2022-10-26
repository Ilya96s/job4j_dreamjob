CREATE TABLE IF NOT EXISTS candidate (
    id SERIAL PRIMARY KEY,
    name TEXT,
    visible BOOLEAN NOT NULL DEFAULT false
    city_id INT,
    desc TEXT,
    created TIMESTAMP
);