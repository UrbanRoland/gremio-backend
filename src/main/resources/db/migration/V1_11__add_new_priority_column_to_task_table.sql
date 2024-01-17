-- Add the priority column to the Task audit table
ALTER TABLE task_aud
ADD COLUMN priority VARCHAR(128);

-- Add the priority column to the Task table
ALTER TABLE task
ADD COLUMN priority VARCHAR(128);