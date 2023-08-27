CREATE TABLE 'member' (
	'id' BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'シ-クエンス'
    'email' VARCHAR(200) NOT NULL COMMENT 'メ-ル' COLLATE 'utf8mb4_general_ci',
    'pwd' VARCHAR(200) NOT NULL COMMENT 'パスワ-ド' COLLATE 'utf8mb4_general_ci',
    'last_login_time' DATETIME NULL DEFAULT NULL COMMENT '最終ログイン時間',
    'register_time' DATETIME NULL DEFAULT NULL COMMENT '登録日',
    'update_time' DATETIME NULL DEFAULT NULL COMMENT '修正日',
    PRIMARY KEY ('id') USING BTREE,
    UNIQUE INDEX 'email' ('email') USING BTREE
)
COMMENT = '会員'