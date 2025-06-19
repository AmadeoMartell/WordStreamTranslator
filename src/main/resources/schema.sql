CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE api_keys (
    id            UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
    api_key       VARCHAR(128)    NOT NULL UNIQUE,
    description   TEXT            NULL,
    created_at    TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE TABLE translation_requests (
    id            UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
    api_key_id    UUID            NOT NULL
        REFERENCES api_keys(id)     ON DELETE RESTRICT,
    ip_address    INET            NOT NULL,
    source_lang   CHAR(2)         NOT NULL,
    target_lang   CHAR(2)         NOT NULL,
    input_text    TEXT            NOT NULL,
    created_at    TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_requests_created_at
    ON translation_requests(created_at);
CREATE INDEX idx_requests_api_key
    ON translation_requests(api_key_id);

CREATE TABLE translation_items (
    id              UUID        PRIMARY KEY DEFAULT uuid_generate_v4(),
    request_id      UUID        NOT NULL
        REFERENCES translation_requests(id) ON DELETE CASCADE,
    word_index      INT         NOT NULL,
    source_word     TEXT        NOT NULL,
    translated_word TEXT        NOT NULL,
    CONSTRAINT uq_item_per_request UNIQUE(request_id, word_index)
);

CREATE INDEX idx_items_request
    ON translation_items(request_id);
