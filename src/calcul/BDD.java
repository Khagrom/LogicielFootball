package calcul;


import java.sql.*;

public class BDD {

    private final Connection connection;
    private final Statement statement;

    public BDD() throws Exception {
        String driverName = "com.mysql.jdbc.Driver";
        Class.forName(driverName);

        String url = "jdbc:mysql://iutdoua-webetu.univ-lyon1.fr/p1513906";
        String utilisateur = "p1513906";
        String motDePasse = "249281";

        connection = DriverManager.getConnection(url, utilisateur, motDePasse);
        statement = connection.createStatement();
    }

    public Statement getStatement() throws Exception {
        return statement;
    }

    public Connection getConnection() {
        return connection;
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
