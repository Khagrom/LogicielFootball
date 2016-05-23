
import java.sql.*;

public class BDD {

    private final Connection connection;
    private final Statement statement;

    public BDD() throws Exception {
        String driverName = "com.mysql.jdbc.Driver";
        Class.forName(driverName);

        String url = "jdbc:mysql://134.214.113.155/p1513906";
        String utilisateur = "p1513906";
        String motDePasse = "249281";

        connection = DriverManager.getConnection(url, utilisateur, motDePasse);
        statement = connection.createStatement();
    }

    public Statement getStatement() throws Exception {
        return statement;
    }

    public void closeConnection() throws Exception {
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
