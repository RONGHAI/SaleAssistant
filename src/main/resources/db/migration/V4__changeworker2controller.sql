
update navigations set worker = REPLACE(worker, "com.ecbeta.app.engine.worker", "com.ecbeta.app.engine.controller");
update navigations set worker = REPLACE(worker, "Worker", "Controller") ;
