CREATE TABLE IF NOT EXISTS project (
    id bigserial NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS task (
    id bigserial NOT NULL,
    description VARCHAR(255),
    due timestamp(6),
    name VARCHAR(255),
    project_id bigint,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS task_assignee (
    task_id bigint NOT NULL,
    assignee_id bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id bigserial NOT NULL,
    email VARCHAR(255),
    name VARCHAR(255),
    password VARCHAR(255),
    refresh_token VARCHAR(255),
    role VARCHAR(128) default 'ROLE_READ_ONLY',
    username VARCHAR(255),
    PRIMARY KEY (id));

ALTER TABLE if EXISTS task_assignee ADD CONSTRAINT ask_assignee_u_key UNIQUE (assignee_id);
ALTER TABLE if EXISTS task ADD CONSTRAINT project_id_f_key FOREIGN KEY (project_id) REFERENCES project;
ALTER TABLE if EXISTS task_assignee ADD CONSTRAINT assigned_id_f_key FOREIGN KEY (assignee_id) REFERENCES users;
ALTER TABLE if EXISTS task_assignee ADD CONSTRAINT task_id_f_key FOREIGN KEY (task_id) REFERENCES task;