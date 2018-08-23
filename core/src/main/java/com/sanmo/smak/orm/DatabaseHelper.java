package com.sanmo.smak.orm;


import com.sanmo.smak.config.ConfigHelper;
import com.sanmo.smak.ioc.ClassUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseHelper {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    private static final QueryRunner QUERY_RUNNER;

    private static final BasicDataSource DATA_SOURCE;

    static {
        CONNECTION_HOLDER=new ThreadLocal<>();
        QUERY_RUNNER = new QueryRunner();
        DATA_SOURCE= new BasicDataSource();
        DATA_SOURCE.setDriverClassName(ConfigHelper.getJdbcDriver());
        DATA_SOURCE.setUrl(ConfigHelper.getJdbcUrl());
        DATA_SOURCE.setUsername(ConfigHelper.getJdbcUsername());
        DATA_SOURCE.setPassword(ConfigHelper.getJdbcPassword());
    }

    /**
     * 获得数据源
     * @return
     */
    public static BasicDataSource getDataSource() {
        return DATA_SOURCE;
    }

    /**
     * 获取连接
     * @return
     */
    public static Connection getConnection(){
        Connection connection = CONNECTION_HOLDER.get();
        if (connection==null){
            try {
                connection=DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
        return connection;
    }

    /**
     * 关闭连接
     */
    public static void closeConnection(){
        Connection connection = CONNECTION_HOLDER.get();
        CONNECTION_HOLDER.remove();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启事务
     */
   public static void beginTransaction(){

       Connection connection = getConnection();
       if (connection!=null){
           try {
               connection.setAutoCommit(false);
           } catch (SQLException e) {
               e.printStackTrace();
               logger.error("begin transaction failure");
           }
       }

   }

    /**
     * 提交事务
     */
   public static void commitTransaction(){
       Connection connection = getConnection();
       if (connection!=null){
           try {
               connection.commit();
               closeConnection();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
   }

    /**
     * 回滚事务
     */
   public static void rollbackTransaction(){
       Connection connection = getConnection();
       if(connection!=null){
           try {
               connection.rollback();
               closeConnection();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }

   }

    /**
     * 查询实体
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
   public static <T> T queryEntity(Class<T> entityClass ,String sql ,Object...params){
       T entity = null;
       Connection connection = getConnection();
       try {
           entity= QUERY_RUNNER.query(connection,sql,new BeanHandler<T>(entityClass),params);
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return entity;
   }

    /**
     * 查询实体列表
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
   public static <T> List<T> queryEntityList(Class<T> entityClass,String sql,Object...params){
       List<T> entityList = null;
       Connection connection = getConnection();
       try {
           entityList = QUERY_RUNNER.query(connection, sql, new BeanListHandler<T>(entityClass), params);
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return entityList;
   }

    /**
     * 查询并返回单个列
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
   public static <T> T query(String sql,Object... params){
       T obj=null;
       Connection connection = getConnection();
       try {
           obj= QUERY_RUNNER.query(connection,sql,new ScalarHandler<T>(),params);
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return obj;
   }

    /**
     * 返回多个列值
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> queryList(String sql, Object... params){
        List<T> list=null;
        Connection connection = getConnection();
        try {
            list= QUERY_RUNNER.query(connection,sql,new ColumnListHandler<T>(),params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 查询并返回数组
     */
    public static Object[] queryArray(String sql, Object... params) {
        Object[] resultArray;
        try {
            Connection conn = getConnection();
            resultArray = QUERY_RUNNER.query(conn, sql, new ArrayHandler(), params);
        } catch (SQLException e) {
            logger.error("query array failure", e);
            throw new RuntimeException(e);
        }
        return resultArray;
    }

    /**
     * 查询并返回数组列表
     */
    public static List<Object[]> queryArrayList(String sql, Object... params) {
        List<Object[]> resultArrayList;
        try {
            Connection conn = getConnection();
            resultArrayList = QUERY_RUNNER.query(conn, sql, new ArrayListHandler(), params);
        } catch (SQLException e) {
            logger.error("query array list failure", e);
            throw new RuntimeException(e);
        }
        return resultArrayList;
    }

    /**
     * 查询并返回结果集映射（列名 => 列值）
     */
    public static Map<String, Object> queryMap(String sql, Object... params) {
        Map<String, Object> resultMap;
        try {
            Connection conn = getConnection();
            resultMap = QUERY_RUNNER.query(conn, sql, new MapHandler(), params);
        } catch (SQLException e) {
            logger.error("query map failure", e);
            throw new RuntimeException(e);
        }
        return resultMap;
    }

    /**
     * 查询并返回结果集映射列表（列名 => 列值）
     */
    public static List<Map<String, Object>> queryMapList(String sql, Object... params) {
        List<Map<String, Object>> resultMapList;
        try {
            Connection conn = getConnection();
            resultMapList = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            logger.error("query map list failure", e);
            throw new RuntimeException(e);
        }
        return resultMapList;
    }


    /**
     * 执行更新语句（包括：update、insert、delete）
     */
    public static int update(String sql, Object... params) {
        int rows;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            logger.error("execute update failure", e);
            throw new RuntimeException(e);
        }
        return rows;
    }

    /**
     * 插入实体
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (MapUtils.isEmpty(fieldMap)) {
            logger.error("can not insert entity: fieldMap is empty");
            return false;
        }

        String sql = "INSERT INTO " + entityClass.getSimpleName().toLowerCase();
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + " VALUES " + values;

        Object[] params = fieldMap.values().toArray();

        return update(sql, params) == 1;
    }

    /**
     * 更新实体
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        if (MapUtils.isEmpty(fieldMap)) {
            logger.error("can not update entity: fieldMap is empty");
            return false;
        }

        String sql = "UPDATE " + entityClass.getSimpleName().toLowerCase() + " SET ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(" = ?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id = ?";

        List<Object> paramList = new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();

        return update(sql, params) == 1;
    }

    /**
     * 删除实体
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "DELETE FROM " + entityClass.getSimpleName().toLowerCase() + " WHERE id = ?";
        return update(sql, id) == 1;
    }

    /**
     * 执行 SQL 文件
     */
    public static void executeSqlFile(String filePath) {
        InputStream is = ClassUtil.getClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String sql;
            while ((sql = reader.readLine()) != null) {
                update(sql);
            }
        } catch (Exception e) {
            logger.error("execute sql file failure", e);
            throw new RuntimeException(e);
        }
    }

}
