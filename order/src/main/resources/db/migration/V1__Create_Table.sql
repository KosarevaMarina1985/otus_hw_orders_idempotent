CREATE TABLE orders (
                       id varchar(255) NOT NULL,
                       version bigint(20) NOT NULL,
                       user_id bigint(20) NOT NULL,
                       price numeric NOT NULL,
                       status varchar(255) NOT NULL,
                       PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE outbox (
                        id bigint(20) NOT NULL AUTO_INCREMENT,
                        aggregate_id varchar(255) NOT NULL,
                        type varchar(255) NOT NULL,
                        payload varchar(2000) NOT NULL,
                        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;