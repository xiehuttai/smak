package com.sanmo.smak.framework.example.dao;

import com.sanmo.smak.framework.example.dao.util.PropertiesUtil;
import com.sanmo.smak.framework.example.model.Customer;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;

public class CustomerDbHelper {

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    /* commons-dbutils实现自动封装查询结果为java对象 */
    private static final QueryRunner QUERY_RUNNER;
    /* 使用ThreadLocal隔离线程之间的连接 */
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    static {

        Properties properties= PropertiesUtil.loadProps("mysql.properties");
        DRIVER= properties.getProperty("jdbc.driver");
        URL= properties.getProperty("jdbc.url");
        USERNAME= properties.getProperty("jdbc.username");
        PASSWORD= properties.getProperty("jdbc.password");
        QUERY_RUNNER= new QueryRunner();
        CONNECTION_HOLDER=new ThreadLocal<Connection>();

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> queryEntityList(Class<T> entityClass, Object... params){
        List<T> entityList = new ArrayList<>();
        String sql = "select * from "+ getTableName(entityClass);
        try {
            entityList= QUERY_RUNNER.query(getConnection(),sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return entityList;
    }

    public static<T> T getEntity(Class<T> entityClass,long id){
        String sql="select * from "+ getTableName(entityClass)+ " where id=?";
        T query= null;
        try {
            query = QUERY_RUNNER.query(getConnection(), sql, new BeanHandler<T>(entityClass),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    public static int executeUpdate(String sql, Object...  params){
        int rows=0;
        Connection connection = getConnection();
        try {
            rows = QUERY_RUNNER.update(connection, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return rows;
    }

    public static<T> boolean insertEntity(Class<T> entityClass , Map<String ,Object> fieldMap){
        if (MapUtils.isEmpty(fieldMap))
            return false;
        String sql="insert into " + getTableName(entityClass);
        StringBuilder columns= new StringBuilder(" (");
        StringBuilder values=new StringBuilder("(");
        for (String fieldName: fieldMap.keySet()){
            columns.append(fieldName).append(",");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(","),columns.length(),")");
        values.replace(values.lastIndexOf(","),values.length(),")");
        sql+= columns+" values " + values;
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql,params)==1;
    }

    public static<T> boolean updateEntity(Class<T> entityClass ,long id, Map<String , Object> fieldMap){
        if (MapUtils.isEmpty(fieldMap))
            return false;
        String sql="update "+ getTableName(entityClass) + " set ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName: fieldMap.keySet()){
            columns.append(fieldName).append("=?, ");
        }
        sql += columns.subSequence(0, columns.lastIndexOf(",")) + " where id=?";
        ArrayList<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        return executeUpdate(sql,params)==1;
    }

    public static <T> boolean deleteEntity(Class<T> entityClass,long id){
        String sql= "delete from " + getTableName(entityClass)+" where id=?";
        return executeUpdate(sql,id)==1;
    }

    private static String getTableName(Class entityClass) {
        /* mysql默认大小写不敏感 */
        return entityClass.getSimpleName().toLowerCase();
    }

    public static Connection getConnection() {
        Connection connection = CONNECTION_HOLDER.get();
        if (connection ==null) {
            try {
              connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
        return connection;
    }

    public static void closeConnection(){
        Connection connection = CONNECTION_HOLDER.get();
        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }
}
