import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlConnection {
    public static Statement connection() throws SQLException {
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/reservation","root","Andrei21");
        Statement statement=connection.createStatement();
        return statement;
    }
}
