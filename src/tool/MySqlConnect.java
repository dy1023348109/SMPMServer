package tool;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
public class MySqlConnect {
    public static final String DRIVER="com.mysql.jdbc.Driver";
    public static final String URL="jdbc:mysql://localhost:3306/rm";
    public static final String NAME="root";
    public static final String PWD="duyan123";
    public static Connection conn=null;
    public MySqlConnect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn= DriverManager.getConnection(URL,NAME,PWD);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public  Connection getConn()
    {
        return conn;
    }
}
