CREATE TABLE notifications (
                       id bigint(20) NOT NULL AUTO_INCREMENT,
                       user_id bigint(20) NOT NULL,
                       order_id varchar(255),
                       price numeric NOT NULL,
                       order_type varchar(255) NOT NULL,
                       message varchar(255) NOT NULL,
                       datetime datetime,
                       PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;