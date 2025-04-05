CREATE TABLE user_event
(
    id            UUID PRIMARY KEY     DEFAULT gen_random_uuid(),
    account_id    UUID        NOT NULL, -- REFERENCES account (id),
    event_id      UUID        NOT NULL, -- REFERENCES event (id),
    status        VARCHAR(20) NOT NULL DEFAULT 'REGISTERED', -- REGISTERED, APPROVED, REJECTED, CANCELLED
    registered_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,

    UNIQUE (account_id, event_id)
);

COMMENT ON TABLE user_event IS 'Связь между пользователем и мероприятием (участие)';
COMMENT ON COLUMN user_event.account_id IS 'ID пользователя, который участвует в мероприятии';
COMMENT ON COLUMN user_event.event_id IS 'ID мероприятия';
COMMENT ON COLUMN user_event.status IS 'Статус участия: REGISTERED, APPROVED, REJECTED, CANCELLED';
COMMENT ON COLUMN user_event.registered_at IS 'Когда пользователь зарегистрировался';
COMMENT ON COLUMN user_event.updated_at IS 'Последнее обновление статуса';

CREATE INDEX idx_user_event_account ON user_event (account_id);
CREATE INDEX idx_user_event_event ON user_event (event_id);
