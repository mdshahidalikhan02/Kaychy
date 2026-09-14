CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100),
                       phone_number VARCHAR(30),
                       is_active BOOLEAN NOT NULL DEFAULT TRUE,
                       created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_roles (
                            user_id UUID NOT NULL,
                            role_id BIGINT NOT NULL,

                            PRIMARY KEY (user_id, role_id),

                            CONSTRAINT fk_user_roles_user
                                FOREIGN KEY (user_id)
                                    REFERENCES users (id)
                                    ON DELETE CASCADE,

                            CONSTRAINT fk_user_roles_role
                                FOREIGN KEY (role_id)
                                    REFERENCES roles (id)
                                    ON DELETE CASCADE
);

INSERT INTO roles (name)
VALUES
    ('CUSTOMER'),
    ('TAILOR'),
    ('ADMIN');