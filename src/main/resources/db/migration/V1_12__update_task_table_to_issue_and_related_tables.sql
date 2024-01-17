-- Rename the Task table to Issue
ALTER TABLE task RENAME TO issue;

-- Rename the task_id column to issue_id in the issue table
ALTER TABLE task_aud RENAME TO issue_aud;

-- Rename the task_id column to issue_id in the issue table
ALTER TABLE task_assignee RENAME TO issue_assignee;

-- Rename the task_id column to issue_id in the issue_assignee table
ALTER TABLE task_assignee_aud RENAME TO issue_assignee_aud;

-- Rename the task_id column to issue_id in the issue_assignee table
ALTER TABLE issue_assigneeRENAME COLUMN task_id TO issue_id;

-- Rename the task_id column to issue_id in the issue_assignee table
ALTER TABLE issue_assignee_aud RENAME COLUMN task_id TO issue_id;