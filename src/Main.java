
import java.sql.ResultSet;

public class Main {

    public static void main(String[] args) throws Exception {
        BDD BDD = new BDD();

        Match test = new Match(BDD, 1);
        System.out.println(test.toString());
        System.out.println();

        try (ResultSet req = BDD.getStatement().executeQuery("SELECT arbitre.nom "
                + "FROM arbitre, pays "
                + "WHERE pays.id = arbitre.pays AND pays.nom = 'France' "
                + "ORDER BY arbitre.id asc;")) {
            while (req.next()) {
                System.out.println(req.getString("nom"));
            }
        }

        BDD.closeConnection();
    }

}
