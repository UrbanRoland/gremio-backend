CREATE TABLE IF NOT EXISTS public.password_reset_token (
    id bigserial PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    creation_date TIMESTAMP,
    CONSTRAINT fk_password_reset_token_user FOREIGN KEY (user_id) REFERENCES "users" (id),
    CONSTRAINT uk_password_reset_token_token UNIQUE (token)
);