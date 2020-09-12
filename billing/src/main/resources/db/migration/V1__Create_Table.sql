CREATE TABLE account
(
    id      bigint(20) NOT NULL AUTO_INCREMENT,
    version bigint(20) NOT NULL,
    user_id bigint(20) NOT NULL,
    balance numeric    NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE processed_messages
(
    id bigint(20) NOT NULL,
    version bigint(20) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;