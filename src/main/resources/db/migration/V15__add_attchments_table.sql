

CREATE TABLE IF NOT EXISTS `attachments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mime` text NOT NULL,
  `extension` varchar(20),
  `content` longblob,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;
