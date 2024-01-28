-- Create password_reset_token table
CREATE TABLE password_reset_token (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255),
    user_id BIGINT,
    creation_date TIMESTAMP
);

-- Create users table with foreign key reference to password_reset_token
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    refresh_token VARCHAR(255),
    password_reset_token_id BIGINT,
    role VARCHAR(255),
    FOREIGN KEY (password_reset_token_id) REFERENCES password_reset_token(id)
);

-- Create project table
CREATE TABLE project (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    key VARCHAR(255),
    project_lead_id BIGINT,
    description TEXT,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    status VARCHAR(255),
    category VARCHAR(255),
    issue_ids BIGINT ARRAY,
    team_member_ids BIGINT ARRAY
);

-- Create issue table with foreign key reference to project
CREATE TABLE issue (
    id SERIAL PRIMARY KEY,
    assignee_ids BIGINT ARRAY,
    title VARCHAR(255),
    due TIMESTAMP,
    project_id BIGINT,
    description TEXT,
    status VARCHAR(255),
    priority VARCHAR(255),
    FOREIGN KEY (project_id) REFERENCES project(id)
);