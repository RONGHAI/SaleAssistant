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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.nativejdbc.NativeJdbcExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class DatabaseHandler implements Serializable, Cloneable {

    private static final Logger logger = Logger.getLogger(DatabaseHandler.class.getName());
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public DatabaseHandler clone() throws CloneNotSupportedException {
        return this;
    }

    public DatabaseHandler(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        logger.log(Level.INFO, dataSource.toString());
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        logger.log(Level.INFO, dataSource.toString());
    }

    private transient Statement statement = null;
    private transient ResultSet resultSet = null;

    public List<Map<String, Object>> execute(String sql) throws SQLException,
            ClassNotFoundException {
        this.close();
        System.out.println(sql);
        return this.jdbcTemplate.queryForList(sql);
    }

    public int update(String sql) throws SQLException,
            ClassNotFoundException {
        this.close();
        System.out.println(sql);
        return this.jdbcTemplate.update(sql);
    }

    public void setNativeJdbcExtractor(NativeJdbcExtractor extractor) {
        jdbcTemplate.setNativeJdbcExtractor(extractor);
    }

    public NativeJdbcExtractor getNativeJdbcExtractor() {
        return jdbcTemplate.getNativeJdbcExtractor();
    }

    public void setIgnoreWarnings(boolean ignoreWarnings) {
        jdbcTemplate.setIgnoreWarnings(ignoreWarnings);
    }

    public boolean isIgnoreWarnings() {
        return jdbcTemplate.isIgnoreWarnings();
    }

    public void setFetchSize(int fetchSize) {
        jdbcTemplate.setFetchSize(fetchSize);
    }

    public int getFetchSize() {
        return jdbcTemplate.getFetchSize();
    }

    public void setMaxRows(int maxRows) {
        jdbcTemplate.setMaxRows(maxRows);
    }

    public int getMaxRows() {
        return jdbcTemplate.getMaxRows();
    }

    public void setQueryTimeout(int queryTimeout) {
        jdbcTemplate.setQueryTimeout(queryTimeout);
    }

    public int getQueryTimeout() {
        return jdbcTemplate.getQueryTimeout();
    }

    public void setSkipResultsProcessing(boolean skipResultsProcessing) {
        jdbcTemplate.setSkipResultsProcessing(skipResultsProcessing);
    }

    public boolean isSkipResultsProcessing() {
        return jdbcTemplate.isSkipResultsProcessing();
    }

    public void setSkipUndeclaredResults(boolean skipUndeclaredResults) {
        jdbcTemplate.setSkipUndeclaredResults(skipUndeclaredResults);
    }

    public boolean isSkipUndeclaredResults() {
        return jdbcTemplate.isSkipUndeclaredResults();
    }

    public void setResultsMapCaseInsensitive(boolean resultsMapCaseInsensitive) {
        jdbcTemplate.setResultsMapCaseInsensitive(resultsMapCaseInsensitive);
    }

    public boolean isResultsMapCaseInsensitive() {
        return jdbcTemplate.isResultsMapCaseInsensitive();
    }

    public <T> T execute(ConnectionCallback<T> action) throws DataAccessException {
        return jdbcTemplate.execute(action);
    }

    public <T> T execute(StatementCallback<T> action) throws DataAccessException {
        return jdbcTemplate.execute(action);
    }

 
    public <T> T query(String sql, ResultSetExtractor<T> rse) throws DataAccessException {
        return jdbcTemplate.query(sql, rse);
    }

    public void query(String sql, RowCallbackHandler rch) throws DataAccessException {
        jdbcTemplate.query(sql, rch);
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Map<String, Object> queryForMap(String sql) throws DataAccessException {
        return jdbcTemplate.queryForMap(sql);
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.queryForObject(sql, rowMapper);
    }

    public <T> T queryForObject(String sql, Class<T> requiredType) throws DataAccessException {
        return jdbcTemplate.queryForObject(sql, requiredType);
    }

    public long queryForLong(String sql) throws DataAccessException {
        return jdbcTemplate.queryForLong(sql);
    }

    public int queryForInt(String sql) throws DataAccessException {
        return jdbcTemplate.queryForInt(sql);
    }

    public <T> List<T> queryForList(String sql, Class<T> elementType) throws DataAccessException {
        return jdbcTemplate.queryForList(sql, elementType);
    }

    public List<Map<String, Object>> queryForList(String sql) throws DataAccessException {
        return jdbcTemplate.queryForList(sql);
    }

    public SqlRowSet queryForRowSet(String sql) throws DataAccessException {
        return jdbcTemplate.queryForRowSet(sql);
    }
 
    public int[] batchUpdate(String[] sql) throws DataAccessException {
        return jdbcTemplate.batchUpdate(sql);
    }

    public <T> T execute(PreparedStatementCreator psc, PreparedStatementCallback<T> action) throws DataAccessException {
        return jdbcTemplate.execute(psc, action);
    }

    public <T> T execute(String sql, PreparedStatementCallback<T> action) throws DataAccessException {
        return jdbcTemplate.execute(sql, action);
    }

    public <T> T query(PreparedStatementCreator psc, PreparedStatementSetter pss, ResultSetExtractor<T> rse) throws DataAccessException {
        return jdbcTemplate.query(psc, pss, rse);
    }

    public <T> T query(PreparedStatementCreator psc, ResultSetExtractor<T> rse) throws DataAccessException {
        return jdbcTemplate.query(psc, rse);
    }

    public <T> T query(String sql, PreparedStatementSetter pss, ResultSetExtractor<T> rse) throws DataAccessException {
        return jdbcTemplate.query(sql, pss, rse);
    }

    public <T> T query(String sql, Object[] args, int[] argTypes, ResultSetExtractor<T> rse) throws DataAccessException {
        return jdbcTemplate.query(sql, args, argTypes, rse);
    }

    public <T> T query(String sql, Object[] args, ResultSetExtractor<T> rse) throws DataAccessException {
        return jdbcTemplate.query(sql, args, rse);
    }

    public <T> T query(String sql, ResultSetExtractor<T> rse, Object... args) throws DataAccessException {
        return jdbcTemplate.query(sql, rse, args);
    }

    public void query(PreparedStatementCreator psc, RowCallbackHandler rch) throws DataAccessException {
        jdbcTemplate.query(psc, rch);
    }

    public void query(String sql, PreparedStatementSetter pss, RowCallbackHandler rch) throws DataAccessException {
        jdbcTemplate.query(sql, pss, rch);
    }

    public void query(String sql, Object[] args, int[] argTypes, RowCallbackHandler rch) throws DataAccessException {
        jdbcTemplate.query(sql, args, argTypes, rch);
    }

    public void query(String sql, Object[] args, RowCallbackHandler rch) throws DataAccessException {
        jdbcTemplate.query(sql, args, rch);
    }

    public void query(String sql, RowCallbackHandler rch, Object... args) throws DataAccessException {
        jdbcTemplate.query(sql, rch, args);
    }

    public <T> List<T> query(PreparedStatementCreator psc, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.query(psc, rowMapper);
    }

    public <T> List<T> query(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.query(sql, pss, rowMapper);
    }

    public <T> List<T> query(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.query(sql, args, argTypes, rowMapper);
    }

    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.query(sql, args, rowMapper);
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {
        return jdbcTemplate.query(sql, rowMapper, args);
    }

    public <T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.queryForObject(sql, args, argTypes, rowMapper);
    }

    public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.queryForObject(sql, args, rowMapper);
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {
        return jdbcTemplate.queryForObject(sql, rowMapper, args);
    }

    public <T> T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType) throws DataAccessException {
        return jdbcTemplate.queryForObject(sql, args, argTypes, requiredType);
    }

    public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) throws DataAccessException {
        return jdbcTemplate.queryForObject(sql, args, requiredType);
    }

    public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) throws DataAccessException {
        return jdbcTemplate.queryForObject(sql, requiredType, args);
    }

    public Map<String, Object> queryForMap(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return jdbcTemplate.queryForMap(sql, args, argTypes);
    }

    public Map<String, Object> queryForMap(String sql, Object... args) throws DataAccessException {
        return jdbcTemplate.queryForMap(sql, args);
    }

    public long queryForLong(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return jdbcTemplate.queryForLong(sql, args, argTypes);
    }

    public long queryForLong(String sql, Object... args) throws DataAccessException {
        return jdbcTemplate.queryForLong(sql, args);
    }

    public int queryForInt(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return jdbcTemplate.queryForInt(sql, args, argTypes);
    }

    public int queryForInt(String sql, Object... args) throws DataAccessException {
        return jdbcTemplate.queryForInt(sql, args);
    }

    public <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> elementType) throws DataAccessException {
        return jdbcTemplate.queryForList(sql, args, argTypes, elementType);
    }

    public <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType) throws DataAccessException {
        return jdbcTemplate.queryForList(sql, args, elementType);
    }

    public <T> List<T> queryForList(String sql, Class<T> elementType, Object... args) throws DataAccessException {
        return jdbcTemplate.queryForList(sql, elementType, args);
    }

    public List<Map<String, Object>> queryForList(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return jdbcTemplate.queryForList(sql, args, argTypes);
    }

    public List<Map<String, Object>> queryForList(String sql, Object... args) throws DataAccessException {
        return jdbcTemplate.queryForList(sql, args);
    }

    public SqlRowSet queryForRowSet(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return jdbcTemplate.queryForRowSet(sql, args, argTypes);
    }

    public SqlRowSet queryForRowSet(String sql, Object... args) throws DataAccessException {
        return jdbcTemplate.queryForRowSet(sql, args);
    }

    public int update(PreparedStatementCreator psc) throws DataAccessException {
        return jdbcTemplate.update(psc);
    }

    public int update(PreparedStatementCreator psc, KeyHolder generatedKeyHolder) throws DataAccessException {
        return jdbcTemplate.update(psc, generatedKeyHolder);
    }

    public int update(String sql, PreparedStatementSetter pss) throws DataAccessException {
        return jdbcTemplate.update(sql, pss);
    }

    public int update(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return jdbcTemplate.update(sql, args, argTypes);
    }

    public int update(String sql, Object... args) throws DataAccessException {
        return jdbcTemplate.update(sql, args);
    }

    public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss) throws DataAccessException {
        return jdbcTemplate.batchUpdate(sql, pss);
    }

    public <T> T execute(CallableStatementCreator csc, CallableStatementCallback<T> action) throws DataAccessException {
        return jdbcTemplate.execute(csc, action);
    }

    public <T> T execute(String callString, CallableStatementCallback<T> action) throws DataAccessException {
        return jdbcTemplate.execute(callString, action);
    }

    public Map<String, Object> call(CallableStatementCreator csc, List<SqlParameter> declaredParameters) throws DataAccessException {
        return jdbcTemplate.call(csc, declaredParameters);
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
