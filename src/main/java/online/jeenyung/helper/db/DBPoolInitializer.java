package online.jeenyung.helper.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

public class DBPoolInitializer
{
    private static DataSource dataSource = null;
    private DBPoolInitializer(){}
    static
    {
        dataSource = new ComboPooledDataSource();
    }

    public static DataSource getDataSource()
    {
        return dataSource;
    }
}
