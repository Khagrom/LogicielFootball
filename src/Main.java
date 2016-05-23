
import java.sql.ResultSet;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BDD BDD = new BDD();
        ArrayList<String> listeEquipes = new ArrayList<>();
        ArrayList<String> listeMatches = new ArrayList<>();

        for (int i = 1; i <= 1; i++) {
            try (ResultSet req = BDD.getStatement().executeQuery("SELECT nom FROM equipe WHERE idChampionnat = " + i + ";")) {
                while (req.next()) {
                    listeEquipes.add(req.getString("nom"));
                }
            }
        }

        for (int i = 1; i <= 1; i++) {
            try (ResultSet req = BDD.getStatement().executeQuery("SELECT DISTINCT e1.nom, e2.nom"
                    + " FROM equipe e1, equipe e2"
                    + " WHERE e1.idChampionnat = " + i
                    + " AND e2.idChampionnat = " + i
                    + " AND e1.nom != e2.nom;")) {
                while (req.next()) {
                    listeMatches.add(req.getString("e1.nom") + " - " + req.getString("e2.nom"));
                }
            }
        }

        ArrayList<String> listeJour1 = new ArrayList<>();
        while (!listeEquipes.isEmpty()) {
            Random randomizer = new Random();
            String eqDom;
            String eqExt;
            String match;
            do {
                do {
                    eqDom = listeEquipes.get(randomizer.nextInt(listeEquipes.size()));
                    eqExt = listeEquipes.get(randomizer.nextInt(listeEquipes.size()));
                } while (eqDom.equals(eqExt));
                match = eqDom + " - " + eqExt;
            } while (listeMatches.indexOf(match) == -1);
            listeEquipes.remove(eqDom);
            listeEquipes.remove(eqExt);
            listeMatches.remove(match);

            listeJour1.add(match);
        }

        listeJour1.stream().forEach((i) -> {
            System.out.println(i);
        });
        BDD.closeConnection();
    }

}
