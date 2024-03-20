
DROP TABLE user IF EXISTS;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`)
);

insert into user (user_name, password, email) values ('atul', '123', 'atul@test.com');
insert into user (user_name, password, email) values ('gabriel', '123', 'atul@test.com');
insert into user (user_name, password, email) values ('david', '123', 'atul@test.com');