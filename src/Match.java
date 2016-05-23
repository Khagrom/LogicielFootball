
import java.sql.ResultSet;

public class Match {

    private String equipe1;
    private String equipe2;
    private int butEquipe1;
    private int butEquipe2;

    public Match(BDD BDD, int idMatch) throws Exception {
        try (ResultSet req = BDD.getStatement().executeQuery("SELECT idEquipe1, idEquipe2, butEquipe1, butEquipe2 from rencontre where id=" + idMatch + ";")) {
            while (req.next()) {
                butEquipe1 = req.getInt("butEquipe1");
                butEquipe2 = req.getInt("butEquipe2");

                if (butEquipe1 > butEquipe2) {
                    equipe1 = req.getString("idEquipe1");
                    equipe2 = req.getString("idEquipe2");
                } else {
                    equipe2 = req.getString("idEquipe1");
                    equipe1 = req.getString("idEquipe2");
                }
            }
        }
    }

    @Override
    public String toString() {
        if (butEquipe1 == butEquipe2) {
            return "Égalité entre les équipes " + equipe1 + " et " + equipe2 + ",\n"
                    + "Score : " + butEquipe1 + "-" + butEquipe2;
        } else {
            return "Équipe gagnante : " + equipe1 + ",\n"
                    + "Équipe perdante : " + equipe2 + ",\n"
                    + "Score : " + butEquipe1 + "-" + butEquipe2;
        }
    }

    public String getEquipe1() {
        return equipe1;
    }

    public String getEquipe2() {
        return equipe2;
    }

    public boolean egalite() {
        return butEquipe1 == butEquipe2;
    }
}
