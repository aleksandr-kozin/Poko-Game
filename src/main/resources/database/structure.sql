START TRANSACTION;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    nick_name VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    status ENUM('ACTIVE', 'NOT_ACTIVE', 'DELETED') NOT NULL DEFAULT 'NOT_ACTIVE',
    role ENUM('ADMIN', 'USER') NOT NULL DEFAULT 'USER'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS user_locations (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    latitude DECIMAL(15, 10),
    longitude DECIMAL(15, 10),
    country VARCHAR(100),
    city VARCHAR(100),
    postal VARCHAR(100),
    state VARCHAR(100),
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS meta_data (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    ip VARCHAR(50) NOT NULL,
    provider VARCHAR(100),
    system_number BIGINT,
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS credentials (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(200) NOT NULL,
    password VARCHAR(100),
    user_id BIGINT UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS jwt_blacklist (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) NOT NULL,
    expiration_date TIMESTAMP NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE user_locations ADD CONSTRAINT FK_USER_LOCATIONS_USER FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE meta_data ADD CONSTRAINT FK_META_DATA_USERS FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE credentials ADD CONSTRAINT FK_CREDENTIALS_USERS FOREIGN KEY (user_id) REFERENCES users(id);

COMMIT;