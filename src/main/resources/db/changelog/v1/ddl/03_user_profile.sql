CREATE TABLE user_profile
(
    id         UUID PRIMARY KEY             DEFAULT gen_random_uuid(),
    account_id UUID UNIQUE         NOT NULL, -- ссылка на account
    username   VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(100),
    last_name  VARCHAR(100),
    avatar_url VARCHAR(512),
    bio        TEXT,
    birth_date DATE,
    created_at TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_user_profile_username ON user_profile(username);

COMMENT ON TABLE user_profile IS 'Профиль пользователя с персональными данными, связан с таблицей account.';
COMMENT ON COLUMN user_profile.id IS 'Уникальный идентификатор профиля пользователя.';
COMMENT ON COLUMN user_profile.account_id IS 'Ссылка на связанную учетную запись пользователя.';
COMMENT ON COLUMN user_profile.username IS 'Уникальное имя (никнейм) пользователя.';
COMMENT ON COLUMN user_profile.first_name IS 'Имя пользователя.';
COMMENT ON COLUMN user_profile.last_name IS 'Фамилия пользователя.';
COMMENT ON COLUMN user_profile.avatar_url IS 'Ссылка на аватар пользователя.';
COMMENT ON COLUMN user_profile.bio IS 'Краткая биография или описание пользователя.';
COMMENT ON COLUMN user_profile.birth_date IS 'Дата рождения пользователя.';
COMMENT ON COLUMN user_profile.created_at IS 'Дата и время создания профиля.';
COMMENT ON COLUMN user_profile.updated_at IS 'Дата и время последнего изменения профиля.';