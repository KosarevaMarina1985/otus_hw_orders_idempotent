CREATE TABLE users (
                       id bigint(20) NOT NULL AUTO_INCREMENT,
                       first_name varchar(50) NOT NULL,
                       last_name varchar(50) NOT NULL,
                       email varchar(50) NOT NULL,
                       PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;