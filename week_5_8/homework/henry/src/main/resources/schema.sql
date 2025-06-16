CREATE TABLE IF NOT EXISTS pokemon_favorite (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pokemon_id INT NOT NULL,
    pokemon_name VARCHAR(255) NOT NULL,
    pokemon_image VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (pokemon_id)
); 