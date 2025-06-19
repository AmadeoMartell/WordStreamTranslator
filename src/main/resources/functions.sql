CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE OR REPLACE FUNCTION create_api_key(p_description TEXT DEFAULT NULL)
    RETURNS TEXT AS $$
DECLARE
    new_key TEXT := uuid_generate_v4()::TEXT;
BEGIN
    INSERT INTO api_keys(api_key, description)
    VALUES (new_key, p_description);
    RETURN new_key;
END;
$$ LANGUAGE plpgsql SECURITY DEFINER;