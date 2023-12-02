--ALTER SEQUENCE revinfo_seq INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS public.users_aud (
	id bigserial NOT NULL,
	email varchar(255) NULL,
	"password" varchar(255) NULL,
	refresh_token varchar(255) NULL,
	"role" varchar(128) NULL DEFAULT 'ROLE_READ_ONLY'::character varying,
	first_name varchar(255) NULL,
	last_name varchar(255) NULL,
	rev int4 NOT NULL,
    revtype int2 NULL,
	CONSTRAINT users_aud_pkey  PRIMARY KEY (id, rev),
	CONSTRAINT users_aud_revinfo FOREIGN KEY (rev)
	REFERENCES revinfo (rev)
);

CREATE TABLE IF NOT EXISTS public.project_aud (
	id bigserial NOT NULL,
	"name" varchar(255) NULL,
	rev int4 NOT NULL,
    revtype int2 NULL,
	CONSTRAINT project_aud_pkey PRIMARY KEY (id, rev),
	CONSTRAINT project_aud_revinfo FOREIGN KEY (rev)
	REFERENCES revinfo (rev)
);

CREATE TABLE IF NOT EXISTS public.task_aud (
	id bigserial NOT NULL,
    description varchar(255) NULL,
	due timestamp(6) NULL,
	title varchar(255) NULL,
	project_id int8 NULL,
	status varchar(255) NULL,
	rev int4 NOT NULL,
    revtype int2 NULL,
	CONSTRAINT task_aud_pkey  PRIMARY KEY (id, rev),
	CONSTRAINT task_aud_revinfo FOREIGN KEY (rev)
	REFERENCES revinfo (rev)
);

CREATE TABLE IF NOT EXISTS public.task_assignee_aud(
    id bigserial NOT NULL,
    task_id int8 NOT NULL,
	assignee_id int8 NOT NULL,
	rev int4 NOT NULL,
    revtype int2 NULL,
    CONSTRAINT task_assignee_aud_pkey  PRIMARY KEY (id, rev),
    CONSTRAINT task_assignee_aud_revinfo FOREIGN KEY (rev)
    REFERENCES revinfo (rev)
)