import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    public static Connection getConnection() throws Exception {

        String url = "jdbc:mysql://localhost:3306/inventory_simple";
        String user = "root";
        String password = "your_mysql_password";

        return DriverManager.getConnection(url, user, password);
    }
}
