/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecbeta.common.core.db;

/**
 *
 * @author ronghai
 */
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class DatabaseHandler implements Serializable, Cloneable {

    private static final Logger logger = Logger.getLogger(DatabaseHandler.class.getName());
    @Autowired
    @PersistenceContext
    private final transient EntityManager entityManager;

    @Override
    public DatabaseHandler clone() throws CloneNotSupportedException {
        return this;
    }

    public DatabaseHandler(EntityManager entityManager) {
        this.entityManager = entityManager;
        logger.log(Level.INFO, "entity maneger" + (entityManager), entityManager);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    private transient Statement statement = null;
    private transient ResultSet resultSet = null;

    public ResultSet execute(String sql) throws SQLException,
            ClassNotFoundException {
        this.close();
        System.out.println(sql);

        entityManager.getTransaction().begin();
        java.sql.Connection connection = entityManager.unwrap(java.sql.Connection.class);
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        entityManager.getTransaction().commit();
        return resultSet;
    }

    public int update(String sql) throws SQLException,
            ClassNotFoundException {
        this.close();
        System.out.println(sql);
        entityManager.getTransaction().begin();
        java.sql.Connection connection = entityManager.unwrap(java.sql.Connection.class);
        statement = connection.createStatement();
        int x = statement.executeUpdate(sql);
        entityManager.getTransaction().commit();
        return x;
    }

    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
            if (statement != null) {
                statement.close();
                statement = null;
            }
        }
        catch (SQLException e) {
            logger.log(Level.WARNING, null, e);
        }
    }

}
