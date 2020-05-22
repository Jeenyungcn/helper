# 操作数据库的工具包
## 使用方法
#### 1. 在class根目录创建c3p0-config.xml文件进行数据库连接池的配置,推荐配置如下：
```
<?xml version="1.0" encoding="UTF-8"?>
<!--默认配置-->
<c3p0-config>
    <default-config>
<!--        <property name="automaticTestTable">com.mysql.cj.jdbc.Driver</property>-->
        <property name="jdbcUrl">jdbc:mysql://localhost:3306/test</property>
        <property name="user">root</property>
        <property name="password">123456789</property>

        <property name="checkoutTimeout">30000</property>
        <property name="idleConnectionTestPeriod">30</property>
        <property name="initialPoolSize">10</property>
        <property name="maxIdleTime">30</property>
        <property name="maxPoolSize">100</property>
        <property name="minPoolSize">10</property>
        <property name="maxStatements">200</property>

        <user-overrides user="test-user">
            <property name="maxPoolSize">10</property>
            <property name="minPoolSize">1</property>
            <property name="maxStatements">0</property>
        </user-overrides>
    </default-config>
</c3p0-config>
```

#### 2. 常用API介绍
> 通过执行DBHelper的方法操作数据库

- *DBHelper.insertMap(Map<String,Object> params, String tableName)*
    > 一次插入一条记录，列为key，行为value封装成map传入，tableName为表名

- *DBHelper.insertMapBatch(List<Map<String,Object>> mapList,String tableName)*
    > 批量插入Map

- *DBHelper.insertBean(Object obj)*
    > 插入JavaBean对象,类名对应数据库表名

- *DBHelper.insertBean(Object obj,tableName)*
    > 插入JavaBean对象,指定表名

- *DBHelper.insertBeanBatch(List<? extends Object> beanList)*
    > 批量插入JavaBean对象

- *DBHelper.insertBeanBatch(List<? extends Object> beanList,String tableName)*
    > 批量插入JavaBean对象,指定表名

- *DBHelper.execute(LString sql,Object... param)*
    > 执行SQL语句

- *DBHelper.query(String sql,Object... params)
    > 查询数据库，返回每天记录用Map封装，再将记录存入List集合中返回

- *DBHelper.queryFirst(String sql,Object... params)
    > 获取第一条记录

- *DBHelper.query(String sql,Class<T> beanClass,Object... params)
    > 查询数据库，返回JavaBean对象的List集合

- *DBHelper.queryFirst(String sql,Class<T> beanClass,Object... params)
    > 查询数据库，第一条记录并用JavaBean封装

#### 3. 持续更新尽情期待



