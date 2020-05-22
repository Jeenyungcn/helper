package online.jeenyung.helper.db;

import online.jeenyung.helper.utils.BeanHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 操作数据库的工具类
 * @author Jeen
 * @since 2020-2-19
 * @version 1.0
 */
public class DBHelper
{

    private DBHelper(){}
    /**
     * 插入记录
     * @param params 字段与值
     * @param tableName 表名
     * @return 插入成功的行数
     */
    public static int insertMap(Map<String,Object> params, String tableName)
    {
        int result = 0;

        if(params==null || params.size()==0||tableName==null)
        {
            return result;
        }


        //拼接SQL
        StringBuilder insertSql = new StringBuilder("insert into").append(" ").append(tableName).append(" ");

        //拼接列
        StringBuilder column = new StringBuilder("(");

        //存放值
        List<Object> columnValue = new ArrayList<Object>();

        Set<Map.Entry<String,Object>> entrySet = params.entrySet();
        for(Map.Entry<String,Object> entry: entrySet)
        {
            column.append(entry.getKey()).append(",");
            columnValue.add(entry.getValue());
        }
        column.replace(column.length()-1, column.length(),")").append(" ");

        //设置占位符
        StringBuilder subs = new StringBuilder("values (");
        int size = columnValue.size();
        for(int i=0;i<size;i++)
        {
            subs.append("?").append(",");
        }
        subs.replace(subs.length() - 1, subs.length() , ")");

        insertSql.append(column).append(subs);

        return new DBExecutor().update(insertSql.toString(),columnValue.toArray());
    }


    /**
     * 批量插入
     * @param mapList Map的列表
     * @param tableName 表名
     * @return 影响行数
     */
    public static int insertMapBatch(List<Map<String,Object>> mapList,String tableName)
    {
        int result = 0;
        for(Map<String,Object> map: mapList)
        {
            result=result+insertMap(map,tableName);
        }

        return result;
    }

    /**
     * 插入JavaBean
     * @param obj JavaBean对象
     * @return 影响结果
     */
    public static int insertBean(Object obj)
    {
        return insertBean(obj,null);
    }

    /**
     * 插入JavaBean，指定表名
     * @param obj JavaBean对象
     * @param obj 表名
     * @return 影响结果
     */
    public static int insertBean(Object obj,String tableName)
    {
        if(obj==null)
        {
            return 0;
        }

        if(tableName==null)
        {
            String classFullName = obj.getClass().getName();
            tableName = classFullName.substring(classFullName.lastIndexOf(".")+1, classFullName.length());
        }

        return insertMap(BeanHelper.toMap(obj),tableName);
    }

    /**
     * 批量插入对象
     * @param beanList JavaBean对象的列表
     * @return 插入成功条数
     */
    public static int insertBeanBatch(List<? extends Object> beanList)
    {
        if(beanList==null||beanList.size()<1)
        {
            return 0;
        }
        int result = 0;
        for(Object bean:beanList)
        {
            result = result + insertBean(bean);
        }
        return result;
    }

    /**
     * 批量插入对象
     * @param beanList JavaBean对象的列表
     * @param tableName 表名
     * @return 插入成功条数
     */
    public static int insertBeanBatch(List<? extends Object> beanList,String tableName)
    {
        if(beanList==null||beanList.size()<1)
        {
            return 0;
        }

        int result = 0;

        if(tableName!=null)
        {
            for(Object bean:beanList)
            {
                result = result + insertBean(bean, tableName);
            }
        }else
        {
            for(Object bean:beanList)
            {
                result = result + insertBean(bean);
            }
        }
        return result;
    }

    /**
     * 直接执行SQL语句
     * @param sql SQL语句
     * @param param 参数
     * @return 影响的记录数
     */
    public static int execute(String sql,Object... param)
    {
        return new DBExecutor().update(sql, param);
    }


    /**
     * 批量执行SQL
     * @param sql SQL语句
     * @param params 参数
     * @return 影响的记录数
     */
    public static int[] executeBatch(String sql,Object[][] params)
    {
        return new DBExecutor().updateBatch(sql, params);
    }

    /**
     * 查询记录
     * @param sql SQL语句
     * @param params 参数
     * @return 返回多条记录
     */
    public static List<Map<String, Object>> query(String sql,Object... params)
    {
        List<Map<String,Object>> results = null;

        return new DBExecutor().query(sql,params);
    }

    /**
     * 查询记录
     * @param sql SQL语句
     * @param params 参数
     * @return 返回一条记录
     */
    public static Map<String, Object> queryFirst(String sql,Object... params)
    {
        List<Map<String,Object>> results = null;

        results = query(sql,params);

        if(results!=null&&results.size()>0)
        {
            return results.get(0);
        }

        return null;
    }

    /**
     * 查询记录
     * @param sql SQL语句
     * @param beanClass 字节码对象
     * @param params 参数
     * @return JavaBean的集合
     */
    public static <T> List<T> query(String sql,Class<T> beanClass,Object... params)
    {
        List<Map<String, Object>> rows = query(sql, params);

        return BeanHelper.toBeanList(rows,beanClass);
    }

    /**
     * 查询记录
     * @param sql SQL语句
     * @param beanClass 字节码对象
     * @param params 参数
     * @return JavaBean对象
     */
    public static <T> T queryFirst(String sql,Class<T> beanClass,Object... params)
    {
        List<Map<String, Object>> rows = new DBExecutor().query(sql, params);

        if(rows.size()==0)
        {
            return null;
        }

        Map<String, Object> firstRow = rows.get(0);

        return BeanHelper.toBean(firstRow,beanClass);
    }

}
