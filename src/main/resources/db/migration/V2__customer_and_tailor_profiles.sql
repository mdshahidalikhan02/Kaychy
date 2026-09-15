CREATE TABLE customer_profiles (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_customer_profiles_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE customer_addresses (
    id UUID PRIMARY KEY,
    customer_profile_id UUID NOT NULL,
    label VARCHAR(50) NOT NULL,
    line1 VARCHAR(255) NOT NULL,
    line2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_customer_addresses_profile
        FOREIGN KEY (customer_profile_id) REFERENCES customer_profiles (id) ON DELETE CASCADE
);

CREATE INDEX idx_customer_addresses_profile_id ON customer_addresses (customer_profile_id);

CREATE TABLE tailor_profiles (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL UNIQUE,
    business_name VARCHAR(150) NOT NULL,
    bio TEXT,
    years_of_experience INTEGER,
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tailor_profiles_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE skills (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE tailor_skills (
    tailor_id UUID NOT NULL,
    skill_id BIGINT NOT NULL,
    proficiency_level VARCHAR(20) NOT NULL,
    years_of_experience INTEGER,
    PRIMARY KEY (tailor_id, skill_id),
    CONSTRAINT fk_tailor_skills_tailor
        FOREIGN KEY (tailor_id) REFERENCES tailor_profiles (id) ON DELETE CASCADE,
    CONSTRAINT fk_tailor_skills_skill
        FOREIGN KEY (skill_id) REFERENCES skills (id) ON DELETE CASCADE
);

CREATE TABLE tailor_services (
    id UUID PRIMARY KEY,
    tailor_id UUID NOT NULL,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    price NUMERIC(10, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT 'INR',
    estimated_turnaround_days INTEGER NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tailor_services_tailor
        FOREIGN KEY (tailor_id) REFERENCES tailor_profiles (id) ON DELETE CASCADE,
    CONSTRAINT chk_tailor_services_price_non_negative CHECK (price >= 0),
    CONSTRAINT chk_tailor_services_turnaround_positive CHECK (estimated_turnaround_days > 0)
);

CREATE INDEX idx_tailor_services_tailor_id ON tailor_services (tailor_id);

CREATE TABLE tailor_availability_slots (
    id UUID PRIMARY KEY,
    tailor_id UUID NOT NULL,
    start_time TIMESTAMPTZ NOT NULL,
    end_time TIMESTAMPTZ NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tailor_availability_tailor
        FOREIGN KEY (tailor_id) REFERENCES tailor_profiles (id) ON DELETE CASCADE,
    CONSTRAINT chk_tailor_availability_time_range CHECK (end_time > start_time)
);

CREATE INDEX idx_tailor_availability_tailor_id ON tailor_availability_slots (tailor_id);

INSERT INTO skills (name, description) VALUES
    ('Blouse Stitching', 'Stitching blouses to measurement'),
    ('Bridal Wear', 'Bridal and wedding outfit specialization'),
    ('Alterations', 'Resizing and altering existing garments'),
    ('Western Wear', 'Western-style clothing construction'),
    ('Traditional Wear', 'Traditional/ethnic clothing construction'),
    ('Men''s Tailoring', 'Tailoring for men''s garments'),
    ('Women''s Tailoring', 'Tailoring for women''s garments'),
    ('Children''s Wear', 'Tailoring for children''s clothing'),
    ('Complex Fitting', 'Advanced fitting techniques');