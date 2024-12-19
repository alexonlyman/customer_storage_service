CREATE TABLE IF NOT EXISTS clients
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS contacts
(
    id        SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    name      VARCHAR(255),
    phone_number  VARCHAR(20),
    email_address VARCHAR(255),
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE
)