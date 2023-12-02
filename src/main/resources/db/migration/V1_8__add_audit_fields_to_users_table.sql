DO $$BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'users') THEN
        ALTER TABLE public.users
            ADD COLUMN created_by varchar(255) NULL,
            ADD COLUMN creation_date timestamp NULL,
            ADD COLUMN deleted_by varchar(255) NULL,
            ADD COLUMN deleted_date timestamp NULL,
            ADD COLUMN last_modified_by varchar(255) NULL,
            ADD COLUMN last_modified_date timestamp NULL;
    END IF;

        IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'users_aud') THEN
            ALTER TABLE public.users_aud
                ADD COLUMN created_by varchar(255) NULL,
                ADD COLUMN creation_date timestamp NULL,
                ADD COLUMN deleted_by varchar(255) NULL,
                ADD COLUMN deleted_date timestamp NULL,
                ADD COLUMN last_modified_by varchar(255) NULL,
                ADD COLUMN last_modified_date timestamp NULL;
        END IF;
END$$;