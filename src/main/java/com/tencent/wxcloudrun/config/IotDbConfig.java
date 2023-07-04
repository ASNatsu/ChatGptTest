package com.tencent.wxcloudrun.config;//package com.kafkaredis.demo.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
//import java.sql.*;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author: zjc
// * @ClassName IotDBConfig
// * @Description TODO IoTDB-JDBC操作工具
// * @date 2021/11/24 14:31
// * @Version 1.0
// */
//@Component
//@Configuration
//public class IotDbConfig {
//
//    private static final Logger log = LoggerFactory.getLogger(IotDbConfig.class);
//
//    @Value("${spring.datasource.username}")
//    private String username;
//
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @Value("${spring.datasource.driver-class-name}")
//    private String driverName;
//
//    @Value("${spring.datasource.url}")
//    private String url;
//
//    @Value("${spring.datasource.initial-size}")
//    private int initialSize;
//
//    @Value("${spring.datasource.min-idle}")
//    private int minIdle;
//
//    @Value("${spring.datasource.max-active}")
//    private int maxActive;
//
//    @Value("${spring.datasource.max-wait}")
//    private int maxWait;
//
//    @Value("${spring.datasource.remove-abandoned}")
//    private boolean removeAbandoned;
//
//    @Value("${spring.datasource.remove-abandoned-timeout}")
//    private int removeAbandonedTimeout;
//
//    @Value("${spring.datasource.time-between-eviction-runs-millis}")
//    private int timeBetweenEvictionRunsMillis;
//
//    @Value("${spring.datasource.min-evictable-idle-time-millis}")
//    private int minEvictableIdleTimeMillis;
//
//    @Value("${spring.datasource.test-while-idle}")
//    private boolean testWhileIdle;
//
//    @Value("${spring.datasource.test-on-borrow}")
//    private boolean testOnBorrow;
//
//    @Value("${spring.datasource.test-on-return}")
//    private boolean testOnReturn;
//
//    private static DruidDataSource iotDbDataSource;
//
//    /**
//     * 使用阿里的druid连接池
//     *
//     * @return
//     */
//    public Connection getConnection() {
//        if (iotDbDataSource == null) {
//            iotDbDataSource = new DruidDataSource();
//            //设置连接参数
//            iotDbDataSource.setUrl(url);
//            iotDbDataSource.setDriverClassName(driverName);
//            iotDbDataSource.setUsername(username);
//            iotDbDataSource.setPassword(password);
//            //配置初始化大小、最小、最大
//            iotDbDataSource.setInitialSize(initialSize);
//            iotDbDataSource.setMinIdle(minIdle);
//            iotDbDataSource.setMaxActive(maxActive);
//            //配置获取连接等待超时的时间
//            iotDbDataSource.setMaxWait(maxWait);
//            //连接泄漏监测
//            iotDbDataSource.setRemoveAbandoned(removeAbandoned);
//            iotDbDataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
//            //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
//            iotDbDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//            iotDbDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//            //防止过期
//            iotDbDataSource.setTestWhileIdle(testWhileIdle);
//            iotDbDataSource.setTestOnBorrow(testOnBorrow);
//            iotDbDataSource.setTestOnReturn(testOnReturn);
//        }
//
//        Connection connection = null;
//        try {
//            connection = iotDbDataSource.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            log.error("iotDB getConnection失败: error={}", e.getMessage());
//        }
//        return connection;
//    }
//
//    public void insert(String sql) {
//        log.info("iotDB insert sql={}", sql);
//        Connection connection = getConnection();
//        Statement statement = null;
//        try {
//            if(connection!=null){
//                statement = connection.createStatement();
//                long systemTime = System.currentTimeMillis();
//                statement.execute(sql);
//                log.info("执行IotDb的sql----[{}],时间：[{}]ms", sql, System.currentTimeMillis()-systemTime);
//            }
//        } catch (SQLException e) {
//            log.error("iotDB insert失败: error={}", e.getMessage());
//        } finally {
//            close(statement, connection);
//        }
//    }
//
//
//
//    /**
//     * 执行单条数据操作
//     *
//     * @param sql
//     */
//    public boolean execute(String sql) {
//        log.info("iotDB execute sql={}", sql);
//        Connection connection = getConnection();
//        PreparedStatement preparedStatement = null;
//        boolean flag = false;
//        try {
//            if (connection != null) {
//                preparedStatement = connection.prepareStatement(sql);
//                long systemTime = System.currentTimeMillis();
//                flag = preparedStatement.execute();
//                log.info("执行IotDb的sql----[{}],时间：[{}]ms", sql, System.currentTimeMillis() - systemTime);
//            }
//        } catch (SQLException e) {
//            log.error("iotDB insert失败: error={}", e.getMessage());
//        } finally {
//            close(preparedStatement, connection);
//        }
//        return flag;
//    }
//
//    /**
//     * 执行批量数据操作
//     *
//     * @param sqls
//     * @return
//     */
//    public Integer executeBatch(List<String> sqls) {
//        Connection connection = getConnection();
//        PreparedStatement preparedStatement = null;
//        int[] flag = null;
//        try {
//            if (connection != null) {
//                for (String sql : sqls) {
//                    log.info("iotDB executeBatch sql={}", sql);
//                    preparedStatement = connection.prepareStatement(sql);
//                    long systemTime = System.currentTimeMillis();
//                    preparedStatement.addBatch(sql);
//                    log.info("执行IotDb的sql----[{}],时间：[{}]ms", sql, System.currentTimeMillis() - systemTime);
//                }
//                flag = preparedStatement.executeBatch();
//            }
//        } catch (SQLException e) {
//            log.error("iotDB 执行失败: error={}", e.getMessage());
//        } finally {
//            close(preparedStatement, connection);
//        }
//        return flag.length;
//    }
//
//
//    /**
//     * 查询操作
//     *
//     * @param sql
//     * @return
//     */
//    public List<Map<String, Object>> executeQuery(String sql) {
//        log.info("iotDB executeQuery sql={}", sql);
//        Connection connection = getConnection();
//        PreparedStatement preparedStatement = null;
//        List<Map<String, Object>> resultList = null;
//        ResultSet resultSet = null;
//        try {
//            if (connection != null) {
//                preparedStatement = connection.prepareStatement(sql);
//                long systemTime = System.currentTimeMillis();
//                resultSet = preparedStatement.executeQuery();
//                log.info("查询IotDb的sql----[{}],时间：[{}]ms", sql, System.currentTimeMillis() - systemTime);
//                resultList = outputResult(resultSet);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            log.error("iotDB query失败: error={}", e.getMessage());
//        } finally {
//            try {
//                if (resultSet != null) {
//                    resultSet.close();
//                }
//            } catch (SQLException e) {
//                log.error("iotDB resultSet关闭异常: error={}", e.getMessage());
//            }
//            close(preparedStatement, connection);
//        }
//        return resultList;
//    }
//
//
//    /**
//     * 输出结果集
//     *
//     * @param resultSet
//     * @return
//     * @throws SQLException
//     */
//    private List<Map<String, Object>> outputResult(ResultSet resultSet) throws SQLException {
//        List<Map<String, Object>> resultList = new ArrayList<>();
//        if (resultSet != null) {
//            ResultSetMetaData metaData = resultSet.getMetaData();
//            int columnCount = metaData.getColumnCount();
//            while (resultSet.next()) {
//                Map resultMap = new HashMap<>(16);
//                for (int i = 1; i <= columnCount; i++) {
//                    String colunmName = metaData.getColumnLabel(i);
//                    if (colunmName.indexOf('.') >= 0) {
//                        colunmName = colunmName.substring(colunmName.lastIndexOf('.') + 1);
//                    }
//                    //过滤 函数()括号
//                    if (colunmName.indexOf(')') >= 0) {
//                        colunmName = colunmName.substring(0, colunmName.lastIndexOf(')'));
//                    }
//                    //时序库自带的时间格式转换
//                    if (colunmName.equals("Time")) {
//                        Long timeStamp = Long.parseLong(resultSet.getString(i));
//                        if (timeStamp > 0) {
//                            Date d = new Date(timeStamp);
//                            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//                            resultMap.put("Time", sf.format(d));
//                        }
//                    } else {
//                        resultMap.put(colunmName, resultSet.getString(i));
//                    }
//                }
//                resultList.add(resultMap);
//            }
//        }
//        return resultList;
//    }
//
//
//    /**
//     * 关闭连接
//     *
//     * @param statement
//     * @param connection
//     */
//    private void close(Statement statement, Connection connection) {
//        try {
//            if (statement != null) {
//                statement.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        } catch (Exception e) {
//            log.error("iotDB close失败: error={}", e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//}
