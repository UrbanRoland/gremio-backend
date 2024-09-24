-- Add new columns to the Project table
ALTER TABLE project
    ADD COLUMN key VARCHAR(20),
    ADD COLUMN description VARCHAR(255),
    ADD COLUMN start_date TIMESTAMP,
    ADD COLUMN end_date TIMESTAMP,
    ADD COLUMN status VARCHAR(255),
    ADD COLUMN category VARCHAR(255);

-- Add a foreign key constraint for the project_lead
ALTER TABLE project
    ADD COLUMN project_lead_id BIGINT,
    ADD CONSTRAINT fk_project_lead FOREIGN KEY (project_lead_id) REFERENCES users(id);

-- Add indexes for performance optimization
CREATE INDEX idx_project_project_lead_id ON project (project_lead_id);

-- Create a new table for the many-to-many relationship between Project and User
CREATE TABLE project_team_members (
    project_id BIGINT,
    user_id BIGINT,
    PRIMARY KEY (project_id, user_id),
    FOREIGN KEY (project_id) REFERENCES project(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create audit table for the Project table
ALTER TABLE project_aud
    ADD COLUMN key VARCHAR(20),
    ADD COLUMN description VARCHAR(255),
    ADD COLUMN start_date TIMESTAMP,
    ADD COLUMN end_date TIMESTAMP,
    ADD COLUMN status VARCHAR(255),
    ADD COLUMN category VARCHAR(255),
    ADD COLUMN project_lead_id BIGINT;


-- Create audit table for the many-to-many relationship between Project and User
CREATE TABLE IF NOT EXISTS project_team_members_aud (
    project_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    assignee_id BIGINT NOT NULL,
    rev int4 NOT NULL,
    revtype int2 NULL,
    team_members_id BIGINT NULL
);