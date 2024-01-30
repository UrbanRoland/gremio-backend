ALTER TABLE public.project
DROP COLUMN IF EXISTS team_member_ids;

ALTER TABLE public.project
DROP COLUMN IF EXISTS issue_ids;

ALTER TABLE public.project
ALTER COLUMN project_lead_id DROP NOT NULL;

ALTER TABLE public.project
DROP CONSTRAINT IF EXISTS fk_project_lead;

ALTER TABLE public.project
ADD CONSTRAINT fk_project_lead
FOREIGN KEY (project_lead_id) REFERENCES public.users(id);

CREATE INDEX IF NOT EXISTS idx_project_project_lead_id
ON public.project USING btree (project_lead_id);