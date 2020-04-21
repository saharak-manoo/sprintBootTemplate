CREATE TABLE users (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username varchar(100) NOT NULL,
  password varchar(100) NOT NULL,
  name varchar(30) NOT NULL,
  surname varchar(30) NOT NULL,
  date_of_birth DATE NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY `username` (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;