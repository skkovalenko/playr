CREATE TABLE event
(
    id                   UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    name                 VARCHAR(255) NOT NULL,
    description          TEXT,
    start_time           TIMESTAMP    NOT NULL,
    end_time             TIMESTAMP    NOT NULL,
    location             VARCHAR(255),
    max_participants     INTEGER      NOT NULL,
    organizer_account_id UUID         NOT NULL,
    created_at           TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Индекс для быстрого поиска мероприятий по организатору
CREATE INDEX idx_events_organizer ON event (organizer_account_id);

COMMENT ON TABLE event IS 'Мероприятия, созданные пользователями платформы.';
COMMENT ON COLUMN event.id IS 'Уникальный идентификатор мероприятия.';
COMMENT ON COLUMN event.name IS 'Название мероприятия.';
COMMENT ON COLUMN event.description IS 'Подробное описание мероприятия.';
COMMENT ON COLUMN event.start_time IS 'Дата и время начала мероприятия.';
COMMENT ON COLUMN event.end_time IS 'Дата и время окончания мероприятия.';
COMMENT ON COLUMN event.location IS 'Местоположение мероприятия.';
COMMENT ON COLUMN event.max_participants IS 'Максимальное количество участников мероприятия.';
COMMENT ON COLUMN event.organizer_account_id IS 'Аккаунт организатора мероприятия.';
COMMENT ON COLUMN event.created_at IS 'Дата и время создания мероприятия.';
COMMENT ON COLUMN event.updated_at IS 'Дата и время последнего обновления мероприятия.';