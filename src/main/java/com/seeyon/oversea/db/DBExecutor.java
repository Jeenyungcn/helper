package com.seeyon.oversea.db;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * SQL语句执行器
 * @author Jeen
 * @since 2020-2-19
 * @version 1.0
 */
public class DBExecutor
{
    private DataSource dataSource = null;
    public DBExecutor()
    {
        this.dataSource = DBPoolInitializer.getDataSource();
    }

    /**
     * 执行SQL语句
     * @param sql SQL语句
     * @param params 参数
     * @return 更新的记录的条数
     */
    public int update(String sql,Object... params)
    {
        int result = 0;
        QueryRunner qr = this.getQueryRunner();
        try
        {
            result = qr.update(sql, params);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public int[] updateBatch(String sql,Object[][] params)
    {
        int[] result = null;
        QueryRunner qr = this.getQueryRunner();
        try
        {
            result = qr.batch(sql, params);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 查询记录
     * @param sql SQL语句
     * @param params 参数
     * @return 每一条记录用Map封装，再将所有记录存入List中返回
     */
    public List<Map<String,Object>> query(String sql, Object... params)
    {
        List<Map<String,Object>> results = null;
        QueryRunner qr = this.getQueryRunner();
        try
        {
            results = qr.query(sql, new MapListHandler(),params);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return results;
    }

    /**
     * 返回第一条记录
     * @param sql SQL语句
     * @param params 参数
     * @return 第一条记录的Map封装
     */
    public Map<String,Object> queryFirst(String sql,Object... params)
    {
        List<Map<String,Object>> results = this.query(sql, params);
        if(results.size()==0)
        {
            return null;
        }
        return this.query(sql, params).get(0);
    }

    /**
     * 获取QueryRunner对象
     * @return QueryRunner对象
     */
    private QueryRunner getQueryRunner()
    {
        QueryRunner qr = new QueryRunner(this.dataSource);
        return qr;
    }
}
