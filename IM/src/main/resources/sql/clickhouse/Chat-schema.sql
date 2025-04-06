CREATE TABLE im.t_chat (
    id UInt64 NOT NULL,
    uuid String NOT NULL,
    room_id Int64 NOT NULL,
    user_id Int64 NOT NULL,
    name String NOT NULL,
    message String NOT NULL,
    url String NULL,
    type Int8 NOT NULL,
    version Int32 NULL,
    created_time DateTime64 DEFAULT now() NOT NULL
)
ENGINE = MergeTree()
PARTITION BY toYYYYMM(created_time)
ORDER BY (id);

ALTER TABLE im.t_chat ADD PROJECTION proj_idx_roomId
( SELECT  *  ORDER BY room_id, user_id, id );