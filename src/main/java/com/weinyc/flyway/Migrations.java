package com.weinyc.flyway;

import com.weinyc.sa.core.db.DatabaseHandler;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
public class Migrations {
    
    private static final Logger logger = Logger.getLogger(Migrations.class.getName());
    public static void main(String[] args) throws Exception {
        Flyway flyway = new Flyway();
        //ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory
        flyway.setLocations("heroku.migration");
        String url;
        String user;
        String password;
        if (System.getenv("DATABASE_URL") != null && System.getenv("DATABASE_URL").length() > 0) {
            URI uri = new URI(System.getenv("DATABASE_URL"));
            String[] split = uri.getUserInfo() == null ? new String[]{null, null} : uri.getUserInfo().split(":");
            user = split[0];
            password = 1 >= split.length ? null : split[1];
            url = "jdbc:postgresql://" + uri.getHost() + (uri.getPort() >= 0 ? (":" + uri.getPort()) : "") + uri.getPath();
            if (uri.getScheme().contains("mysql")) {
                url = "jdbc:mysql://" + uri.getHost() + ":" + (uri.getPort() >= 0 ? (":" + uri.getPort()) : "") + uri.getPath() + (uri.getQuery() != null ? ("?" + uri.getQuery()) : "");
            }
        } else {
            url = System.getenv("JDBC_DATABASE_URL");
            user = System.getenv("JDBC_DATABASE_USERNAME");
            password = System.getenv("JDBC_DATABASE_PASSWORD");
        }
        flyway.setDataSource(url, user, password);
        try{
            flyway.migrate();
        }catch(FlywayException e){
            logger.log(Level.WARNING, null, e);
            flyway.repair();
            flyway.migrate();
        }
    }
}
