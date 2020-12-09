Create Table: CREATE TABLE `orders0` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `goodLists` varchar(1000) NOT NULL,
  `total_price` int NOT NULL,
  `status` int NOT NULL,
  `deliver_status` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4