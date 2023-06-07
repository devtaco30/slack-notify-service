CREATE TABLE tb_member (
	id serial4 NOT NULL,
	member_email varchar(255) NOT NULL,
	description varchar(255) NULL,
	member_name varchar(30) NOT NULL,
	reg_dt bigint NOT NULL default (EXTRACT(EPOCH FROM NOW()) * 1000),
	CONSTRAINT PK_MEMBER PRIMARY KEY (id),
	CONSTRAINT UK_MEMBER_01 UNIQUE (member_email)
);

CREATE TABLE tb_member_slack_id (
	member_slack_id_seq serial4 NOT NULL,
	member_id int4 NOT NULL,
	member_slack_id varchar(30) NOT NULL,
	reg_dt bigint NOT NULL default (EXTRACT(EPOCH FROM NOW()) * 1000),
	CONSTRAINT PK_MEMBER_SLACK_ID PRIMARY KEY (member_slack_id_seq),
	CONSTRAINT UK_MEMBER_SLACK_ID_01 UNIQUE (member_slack_id),
	CONSTRAINT FK_MEMBER_SLACK_ID_01 FOREIGN KEY (member_id) REFERENCES snts.tb_slack_member (id)
);

CREATE TABLE tb_slack_channel (
	slack_channel_id_seq serial4 NOT NULL,
	slack_channel_id varchar(30) NOT NULL,
	reg_dt bigint NOT NULL default (EXTRACT(EPOCH FROM NOW()) * 1000),
	is_delete boolean NOT NULL default false,
	CONSTRAINT PK_SLACK_CHANNEL PRIMARY KEY (slack_channel_id_seq)
);
CREATE TABLE wb_wave_slack_channel (
	wb_slack_channel_id_seq serial4 NOT NULL,
	wb_slack_channel_id varchar NOT NULL,
	reg_by varchar NOT NULL,
	reg_dt bigint NOT NULL default (EXTRACT(EPOCH FROM NOW()) * 1000),
	mod_by varchar NOT NULL,
	mod_dt bigint NOT NULL default (EXTRACT(EPOCH FROM NOW()) * 1000),
	is_delete boolean NOT NULL default false,
	CONSTRAINT PK_WAVE_SLACK_CHANNEL PRIMARY KEY (wb_slack_channel_id_seq)
);

INSERT INTO tb_member (member_email, description, member_name)
VALUES ('user-email@provider.com', 'TEST ACCOUNT', 'test-member description', 'user-name');

INSERT INTO tb_member_slack_id (member_id, member_slack_id)
VALUES ('1', '@member-id');

INSERT INTO tb_slack_channel (slack_channel_id)
VALUES('slack-channle-id');