CREATE TABLE account
(
    id         UUID PRIMARY KEY             DEFAULT gen_random_uuid(),
    email      VARCHAR(255) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    role       VARCHAR(50)         NOT NULL DEFAULT 'USER',
    status     VARCHAR(20)         NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_account_email ON account(email);

COMMENT ON TABLE account IS 'Учётные данные пользователей (для авторизации и аутентификации).';
COMMENT ON COLUMN account.id IS 'Уникальный идентификатор учётной записи.';
COMMENT ON COLUMN account.email IS 'Email пользователя, используется как логин.';
COMMENT ON COLUMN account.password IS 'Хэшированный пароль пользователя.';
COMMENT ON COLUMN account.role IS 'Роль пользователя (например: USER, ADMIN).';
COMMENT ON COLUMN account.status IS 'Статус аккаунта (ACTIVE, BLOCKED, DELETED).';
COMMENT ON COLUMN account.created_at IS 'Дата и время создания учётной записи.';
COMMENT ON COLUMN account.updated_at IS 'Дата и время последнего обновления записи.';