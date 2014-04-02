
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

update navigations set worker = REPLACE(worker, "com.ecbeta.app.engine.worker", "com.ecbeta.app.engine.controller")
update navigations set worker = REPLACE(worker, "Worker", "Controller") 
