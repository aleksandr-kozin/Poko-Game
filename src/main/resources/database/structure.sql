START TRANSACTION;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    nick_name VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    status ENUM('ACTIVE', 'NOT_ACTIVE', 'DELETED') NOT NULL DEFAULT 'NOT_ACTIVE',
    role ENUM('ADMIN', 'USER') NOT NULL DEFAULT 'USER'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS locations (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    latitude DECIMAL(15, 10) NOT NULL,
    longitude DECIMAL(15, 10) NOT NULL,
    country VARCHAR(100),
    city VARCHAR(100),
    postal VARCHAR(100),
    state VARCHAR(100),

    UNIQUE KEY UNIQUE_LOCATIONS (latitude, longitude)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS meta_data (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    ip VARCHAR(50) NOT NULL,
    provider VARCHAR(100),
    system_number BIGINT,
    agent_name VARCHAR(100),
    agent_version VARCHAR(10),
    os_name VARCHAR(50),
    os_version VARCHAR(10),
    device_name VARCHAR(50),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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

CREATE TABLE networks (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    network_type ENUM('WIFI', 'REGULAR') NOT NULL,
    signal_strength INTEGER NOT NULL,
    location_id BIGINT UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE users_locations (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT UNSIGNED NOT NULL,
    location_id BIGINT UNSIGNED NOT NULL,
    network_id BIGINT UNSIGNED NOT NULL,
    metadata_id BIGINT UNSIGNED,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE users_locations ADD CONSTRAINT FK_UL_USERS FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE users_locations ADD CONSTRAINT FK_UL_LOCATIONS FOREIGN KEY (location_id) REFERENCES locations(id);
ALTER TABLE users_locations ADD CONSTRAINT FK_UL_NETWORKS FOREIGN KEY (network_id) REFERENCES networks(id);
ALTER TABLE users_locations ADD CONSTRAINT FK_UL_META_DATA FOREIGN KEY (metadata_id) REFERENCES meta_data(id);
ALTER TABLE credentials ADD CONSTRAINT FK_CREDENTIALS_USERS FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE networks ADD CONSTRAINT FK_NETWORKS_LOCATIONS FOREIGN KEY (location_id) REFERENCES locations(id);

COMMIT;