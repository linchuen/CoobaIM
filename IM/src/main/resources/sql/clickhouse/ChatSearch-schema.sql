CREATE TABLE t_chat_search (
    id UInt64 NOT NULL,
    room_id Int64 NOT NULL,
    message_gram String NOT NULL,
    chat_id Int64 NOT NULL,
    created_time DateTime DEFAULT now() NOT NULL
)
ENGINE = MergeTree()
PARTITION BY toYYYYMM(created_time)
ORDER BY (id);

ALTER TABLE t_chat_search ADD PROJECTION proj_uk_roomId
( SELECT  *  ORDER BY room_id, message_gram, chat_id, id );