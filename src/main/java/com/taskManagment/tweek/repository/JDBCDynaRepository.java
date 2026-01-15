package com.taskManagment.tweek.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JDBCDynaRepository {

    private static final Logger logger =LoggerFactory.getLogger(JDBCDynaRepository.class);
    //used constructor injection rather than field injection
    //   @Autowired
    //private JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JDBCDynaRepository(JdbcTemplate jdbcTemplate){
        this.namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(jdbcTemplate);
    }


    public String executeSQL(String strSql, Map<String,Object> sqlParams){
        try{
            //NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(jdbcTemplate);
            logger.debug("Executing SQL: {} with params: {}", strSql, sqlParams);
            int result = namedParameterJdbcTemplate.update(strSql, sqlParams);
            logger.debug("Rows affected: {}", result);
            return String.valueOf(result);
        }
        catch(DataAccessException e){
            logger.error("Error executing SQL: {}", strSql, e);
            throw new RuntimeException("Failed to execute SQL", e);
        }

    }
    public List<Map<String,Object>> runQuery(String strSQL, Map<String,Object> sqlParams){
        List<Map<String, Object>> resMap=null;
        try{
            //initilized once in start and reuse .
            //NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(jdbcTemplate);
            logger.debug("Running query: {} with params: {}", strSQL, sqlParams);
            resMap=namedParameterJdbcTemplate.queryForList(strSQL,sqlParams);
            logger.debug("Query returned {} rows", resMap.size());
            return (resMap);
        }
        catch(DataAccessException  e){
            logger.error("Error running query: {}", strSQL, e);
            throw new RuntimeException("Failed to execute query", e);
        }
    }
}
