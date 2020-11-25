#第六周作业 
#建立一个电子商城的数据库

#用户
CREATE TABLE IF NOT EXISTS `users` (
    `id` int(15) NOT NULL AUTO_INCREMENT,
    `name` varchar(15) NOT NULL,
    `password` varchar(50) NOT NULL,
    `age` int(2)
    PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

#店铺
CREATE TABLE IF NOR EXISTS `stores` (
    `id` int(15) NOT NULL AUTO_INCREMENT,
    `name` varchar(15) NOT NULL,
    `description` varchar(100) NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

#商品
CREATE TABLE IF NOT EXISTS `goods` (
    `id` int(15) NOT NULL AUTO_INCREMENT,
    `name` varchar(15) NOT NULL,
    `description` varchar(100) NOT NULL,
    `price` int(15) NOT NULL,
    `store_id` int(15) NOT NULL,
    PRIMARY KEY (`id`),
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

#订单
CREATE TABLE IF NOT EXISTS `orders` (
    `id` int(15) NOT NULL AUTO_INCREMENT,
    `user_id` int(15) NOT NULL,
    `goodLists` varchar(1000) NOT NULL,
    `total_price` int(11) NOT NULL,
    `status` int(1) NOT NULL,
    `deliver_status` varchar(100) NOT NULL,
    PRIMARY KEY (`id`),
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

#优惠券
CREATE TABLE IF NOT EXISTS `coupon` (
    `id` int(15) NOT NULL AUTO_INCREMENT,
    `good_id` int(15) NOT NULL,
    `cut_price` int(15) NOT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

#满减活动


#购物车
CREATE TABLE IF NOT EXISTS `shoppingCart` (
    `id` int(15) NOT NULL AUTO_INCREMENT,
    `user_id` int(15) NOT NULL,
    `good_id` int(15) NOT NULL,
    `addDate` timestamp DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


