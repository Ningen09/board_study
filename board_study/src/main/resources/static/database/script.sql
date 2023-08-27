CREATE TABLE IF NOT EXISTS 'board' (
	'id' bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	'title' varchar(200) NOT NULL COMMENT 'タイトル',
	'content' text NOT NULL COMMENT '内容',
	'read_cnt' int(11) NOT NULL DEFAULT 0 COMMENT '照会数',
	'register_id' VARCHAR(100) NOT NULL COMMENT '作成者',
	'register_time' DATETIME NULL DEFAULT NULL COMMENT '作成日',
	'update_time' DATETIME NULL DEFAULT NULL COMMENT '修正日',
	PRIMARY KEY ('id')
)	ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT = '掲示板';