CREATE TABLE IF NOT EXISTS post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TIMESTAMP,
    city_id INT,
    visible BOOLEAN NOT NULL DEFAULT false
);