-- SQLBook: Code
-- Create table login
CREATE TABLE IF NOT EXISTS public.login
(
    id BIGSERIAL PRIMARY KEY,
    npm VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nama VARCHAR(255)
);
