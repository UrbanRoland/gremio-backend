CREATE TABLE public.issue_assignee (
	issue_id int8 NOT NULL,
	assignee_id int8 NOT NULL,
	CONSTRAINT ask_assignee_u_key UNIQUE (assignee_id),
	CONSTRAINT assigned_id_f_key FOREIGN KEY (assignee_id) REFERENCES public.users(id),
	CONSTRAINT task_id_f_key FOREIGN KEY (issue_id) REFERENCES public.issue(id)
);