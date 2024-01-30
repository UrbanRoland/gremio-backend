CREATE TABLE public.project_team_members (
	project_id int8 NOT NULL,
	user_id int8 NOT NULL,
	CONSTRAINT project_team_members_pkey PRIMARY KEY (project_id, user_id),
	CONSTRAINT project_team_members_project_id_fkey FOREIGN KEY (project_id) REFERENCES public.project(id),
	CONSTRAINT project_team_members_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id)
);