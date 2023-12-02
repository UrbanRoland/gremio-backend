ALTER TABLE IF EXISTS public.task RENAME COLUMN name TO "title";
ALTER TABLE IF EXISTS public.task ADD COLUMN status varchar(255) NULL;