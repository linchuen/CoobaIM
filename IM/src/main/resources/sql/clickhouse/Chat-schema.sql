CREATE TABLE chat (
                id UInt64,
                roomId UInt64,
                userId UInt64,
                name String,
                message String,
                url String,
                type Enum8('TEXT' = 1, 'IMAGE' = 2, 'VIDEO' = 3),
                version Int32,
                createdTime DateTime DEFAULT now()
            )
            ENGINE = ReplacingMergeTree(version)
            PARTITION BY toYYYYMM(createdTime)
            ORDER BY (roomId, userId, id);