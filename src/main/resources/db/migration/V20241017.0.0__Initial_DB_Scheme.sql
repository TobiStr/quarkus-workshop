create schema if not exists public;

CREATE OR REPLACE FUNCTION update_last_modified_date()
RETURNS TRIGGER AS $$
BEGIN
   NEW.created_at = CURRENT_TIMESTAMP;
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TABLE IF NOT EXISTS public.appointment
(
    id VARCHAR(3) PRIMARY KEY,
    -- TODO: Add other fields here
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER trg_update_last_modified_date
BEFORE UPDATE ON public.appointment
FOR EACH ROW
EXECUTE FUNCTION update_last_modified_date();
