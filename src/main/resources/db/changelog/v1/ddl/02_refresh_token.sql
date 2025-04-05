CREATE TABLE refresh_token
(
    id         UUID PRIMARY KEY             DEFAULT gen_random_uuid(),
    account_id UUID                NOT NULL,
    token      VARCHAR(512) UNIQUE NOT NULL,
    expires_at TIMESTAMP           NOT NULL,
    revoked    BOOLEAN             NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_refresh_token_account ON refresh_token (account_id);
CREATE INDEX idx_refresh_token_token ON refresh_token (token);

COMMENT ON TABLE refresh_token IS 'Хранит refresh-токены пользователей для повторной аутентификации.';
COMMENT ON COLUMN refresh_token.id IS 'Уникальный идентификатор записи.';
COMMENT ON COLUMN refresh_token.account_id IS 'Идентификатор аккаунта, к которому относится токен.';
COMMENT ON COLUMN refresh_token.token IS 'Строковое представление refresh-токена.';
COMMENT ON COLUMN refresh_token.expires_at IS 'Дата и время истечения срока действия токена.';
COMMENT ON COLUMN refresh_token.revoked IS 'Признак отзыва токена (true, если токен отозван).';
