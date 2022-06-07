import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    static String username = "root";
    static String password = "2604";
    static String url = "jdbc:mysql://localhost:3306/users";

    public Connection getConnection() throws SQLException{
        return (Connection) DriverManager.getConnection(url,username,password);
    }
    public void ShowError(SQLException e){
        JOptionPane.showMessageDialog(null,"Hata : " + e.getMessage() );
        JOptionPane.showMessageDialog(null,"Hata Kodu : " + e.getErrorCode());
    }
}
