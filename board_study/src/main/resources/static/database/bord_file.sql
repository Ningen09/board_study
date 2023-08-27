CREATE TABLE 'board_file' (
		'id' BIGINT(20) NOT ALL AUTO_INCREMENT COMMENT 'PK',
        'board_id' BIGINT(20) NOT NULL COMMENT '掲示板番号',
        'orig_file_name' VARCHAR(250) NULL DEFAULT NULL COMMENT 'ファイルソ-ス名' ,
        'save_file_name' VARCHAR(500) NULL DEFAULT NULL COMMENT 'ファイル名',
        'file_size' INT(11) NULL DEFAULT '0' COMMENT 'ファイルサイズ',
        'file_path' VARCHAR(10) NULL DEFAULT NULL COMMENT 'ファイル拡張子',
        'delete_yn' CHAR(1) NULL DEFAULT 'N' COMMENT 'ファイルパス',
        'register_time' DATETIME NULL DEFAULT NULL COMMENT '作成日',
        PRIMARY KEY('id') USING BTREE,
        INDEX 'BOARD_ID' ('board_id') USING BTREE
        )
        COMMENT = '掲示板ファイル管理'