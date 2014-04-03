
DELETE FROM navigations;
INSERT INTO `navigations` (`id`, `tier_1`, `tier_2`, `tier_3`, `tier_4`, `worker`, `label`, `order`, `disabled`, `add_time`, `update_time`, `note`) VALUES
(1, 1, 1, 0, 0, 'com.ecbeta.app.engine.controller.HomeController', '主页', 0, 0, NULL, NULL, NULL),
(2, 1, 2, 0, 0, 'com.ecbeta.app.engine.controller.ClientController', '客户', 0, 0, NULL, NULL, NULL),
(3, 1, 0, 0, 0, '', '基础数据', 0, 0, NULL, NULL, NULL);