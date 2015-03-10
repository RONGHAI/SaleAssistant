-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2013 年 12 月 11 日 04:43
-- 服务器版本: 5.5.31
-- PHP 版本: 5.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `sales_assistant`
--
-- CREATE DATABASE IF NOT EXISTS `sales_assistant` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
-- USE `sales_assistant`;

-- --------------------------------------------------------

--
-- 表的结构 `accounts`
--

CREATE TABLE IF NOT EXISTS `accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) NOT NULL,
  `email` varchar(10) NOT NULL,
  `user_name` text NOT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `carriers`
--

CREATE TABLE IF NOT EXISTS `carriers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `website` text NOT NULL,
  `track_url` text NOT NULL,
  `track_method` enum('POST','GET') NOT NULL DEFAULT 'GET',
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- 转存表中的数据 `carriers`
--

INSERT INTO `carriers` (`id`, `name`, `website`, `track_url`, `track_method`, `disabled`, `add_time`, `update_time`, `note`) VALUES
(1, 'ANL Express 新干线快递', 'http://www.anlexpress.com/', 'http://www.anlexpress.com/index.php/Index-index', 'POST', 0, '2013-12-03 12:04:31', '2013-12-03 00:00:00', NULL),
(2, 'EMS 中国', 'http://www.ems.com.cn', 'http://www.ems.com.cn', 'POST', 0, '2013-12-03 12:06:32', '2013-12-03 00:00:00', NULL),
(3, 'SF Express 顺丰速运', 'http://www.sf-express.com/cn/sc/', 'http://www.sf-express.com/cn/sc/', 'POST', 0, '2013-12-03 12:08:54', '2013-12-03 00:00:00', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `categories`
--

CREATE TABLE IF NOT EXISTS `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `code` varchar(100) NOT NULL,
  `level` int(15) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `clients`
--

CREATE TABLE IF NOT EXISTS `clients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `wangwang` varchar(100) NOT NULL,
  `qq` int(15) NOT NULL,
  `birthday` datetime DEFAULT NULL,
  `gender` enum('M','F','U') NOT NULL DEFAULT 'U',
  `phone` varchar(20) NOT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `clients`
--

INSERT INTO `clients` (`id`, `name`, `wangwang`, `qq`, `birthday`, `gender`, `phone`, `disabled`, `add_time`, `update_time`, `note`) VALUES
(1, '未知用户', '', 0, '2013-12-04 00:00:00', 'U', '', 0, '2013-12-04 16:17:14', '2013-12-05 00:00:00', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `client_addresses`
--

CREATE TABLE IF NOT EXISTS `client_addresses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` int(11) NOT NULL,
  `address` text NOT NULL,
  `phone` varchar(15) NOT NULL,
  `zip_code` int(6) NOT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `currencies`
--

CREATE TABLE IF NOT EXISTS `currencies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `code` varchar(10) NOT NULL,
  `sign` varchar(4) NOT NULL,
  `exchange_rate` decimal(20,8) NOT NULL DEFAULT '1.00000000',
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- 转存表中的数据 `currencies`
--

INSERT INTO `currencies` (`id`, `name`, `code`, `sign`, `exchange_rate`, `disabled`, `add_time`, `update_time`, `note`) VALUES
(1, '人民币', 'CNY', '¥', '1.00000000', 0, '2013-12-03 12:16:21', '0000-00-00 00:00:00', NULL),
(2, '美元', 'USD', '$', '1.00000000', 0, '2013-12-03 12:16:32', '0000-00-00 00:00:00', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `hashtags`
--

CREATE TABLE IF NOT EXISTS `hashtags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hashtag` varchar(20) NOT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `hashtag_products`
--

CREATE TABLE IF NOT EXISTS `hashtag_products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hashtag_id` text NOT NULL,
  `product_id` text NOT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `invoices`
--

CREATE TABLE IF NOT EXISTS `invoices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `client_address_id` int(11) NOT NULL,
  `carrier_id` int(11) NOT NULL,
  `track_number` varchar(20) NOT NULL,
  `status` text NOT NULL,
  `forward_invoice_id` int(11) DEFAULT NULL,
  `shipping_fee` decimal(20,8) NOT NULL,
  `duty` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `shipping_fee_currency_id` int(11) NOT NULL,
  `shipping_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `merchants`
--

CREATE TABLE IF NOT EXISTS `merchants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `code` varchar(10) NOT NULL,
  `website` text NOT NULL,
  `track_url` text NOT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` text NOT NULL,
  `order_time` datetime NOT NULL,
  `order_status` enum('INIT','BUYING','BOUGHT','SHIPPED','RECEIVED','FORWARDED','MONEY_RECEIVED','CLIENT_RECEIVED','CLOSED') NOT NULL DEFAULT 'INIT',
  `client_id` int(11) NOT NULL,
  `cost` decimal(20,8) NOT NULL,
  `discount` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `sale_price` decimal(20,8) NOT NULL,
  `shipping_fee` decimal(20,8) NOT NULL,
  `duty` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `currency_id` int(11) NOT NULL,
  `sale_price_currency_id` int(11) NOT NULL,
  `shipping_fee_currency_id` int(11) NOT NULL,
  `net_profit` decimal(20,8) NOT NULL DEFAULT '0.00000000',
  `net_profit_currency_id` int(11) NOT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `products`
--

CREATE TABLE IF NOT EXISTS `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `code` varchar(100) NOT NULL,
  `english_name` text NOT NULL,
  `chinese_name` text NOT NULL,
  `description` text,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `product_categories`
--

CREATE TABLE IF NOT EXISTS `product_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `product_images`
--

CREATE TABLE IF NOT EXISTS `product_images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `image` longblob,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `product_specifications`
--

CREATE TABLE IF NOT EXISTS `product_specifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `currency_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `code` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  `cost` decimal(20,8) DEFAULT NULL,
  `sale_price` decimal(20,8) NOT NULL,
  `tax` decimal(20,8) NOT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `system_informations`
--

CREATE TABLE IF NOT EXISTS `system_informations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL,
  `value` text NOT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- 转存表中的数据 `system_informations`
--

INSERT INTO `system_informations` (`id`, `code`, `value`, `disabled`, `add_time`, `update_time`, `note`) VALUES
(1, 'DEFAULT_CARRIER', '1', 0, '2013-12-03 12:10:14', '2013-12-03 00:00:00', '默认承运人'),
(2, 'BASE_CURRENCY', '1', 0, '2013-12-03 12:13:46', '0000-00-00 00:00:00', '默认货币'),
(3, 'EXCHANGE_RATE_QUERY_URL', 'http://rate-exchange.appspot.com/currency?from=%s&to=%s&q=%d', 0, '2013-12-03 12:15:13', '0000-00-00 00:00:00', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `usa_orders`
--

CREATE TABLE IF NOT EXISTS `usa_orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usa_order_number` text NOT NULL,
  `real_order_number` text NOT NULL,
  `cost` decimal(20,8) NOT NULL,
  `tax` decimal(20,8) NOT NULL,
  `shipping_fee` decimal(20,8) NOT NULL,
  `discount` decimal(20,8) NOT NULL,
  `real_cost` decimal(20,8) NOT NULL,
  `carrier_id` int(11) NOT NULL,
  `track_number` text,
  `currency_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `usa_order_items`
--

CREATE TABLE IF NOT EXISTS `usa_order_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usa_order_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `product_specification_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `cost` decimal(20,8) NOT NULL,
  `tax` decimal(20,8) NOT NULL,
  `shipping_fee` decimal(20,8) NOT NULL,
  `sale_price` decimal(20,8) NOT NULL,
  `currency_id` int(11) NOT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
