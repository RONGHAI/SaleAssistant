SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";
CREATE TABLE IF NOT EXISTS `navigations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tier_1` int(11) NOT NULL,
  `tier_2` int(11) NOT NULL,
  `tier_3` int(11) NOT NULL,
  `tier_4` int(11) NOT NULL,
  `worker` text NOT NULL,
  `label` text NOT NULL,
  `order` int(11) DEFAULT 0,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;