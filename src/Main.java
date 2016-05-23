
import java.sql.ResultSet;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BDD BDD = new BDD();
        ArrayList<String> listeEquipes = new ArrayList<>();
        ArrayList<String> listeMatchesNonTries = new ArrayList<>();
        ArrayList<String> listeMatchesTries = new ArrayList<>();
        int nbChampionnats = 0;
        Random randomizer = new Random();
        String eqDom;
        String eqExt;
        String match;

        try (ResultSet req = BDD.getStatement().executeQuery("SELECT id FROM championnat;")) {
            while (req.next()) {
                nbChampionnats = req.getInt("id");
            }
        }

        System.out.println("Liste en cours de création...");
        for (int i = 1; i <= nbChampionnats; i++) {

            try (ResultSet req = BDD.getStatement().executeQuery("SELECT DISTINCT e1.nom, e2.nom"
                    + " FROM equipe e1, equipe e2"
                    + " WHERE e1.idChampionnat = " + i
                    + " AND e2.idChampionnat = " + i
                    + " AND e1.nom != e2.nom;")) {
                while (req.next()) {
                    listeMatchesNonTries.add(req.getString("e1.nom") + " - " + req.getString("e2.nom"));
                }
            }

            do {
                try (ResultSet req = BDD.getStatement().executeQuery("SELECT nom FROM equipe WHERE idChampionnat = " + i + ";")) {
                    while (req.next()) {
                        listeEquipes.add(req.getString("nom"));
                    }
                }

                do {

                    do {
                        eqDom = listeEquipes.get(randomizer.nextInt(listeEquipes.size()));
                        eqExt = listeEquipes.get(randomizer.nextInt(listeEquipes.size()));
                    } while (eqDom.equals(eqExt));
                    match = eqDom + " - " + eqExt;

                    if (listeEquipes.remove(eqDom) && listeEquipes.remove(eqExt) && listeMatchesNonTries.remove(match)) {
                        listeMatchesTries.add(match);
                    }

                } while (!listeEquipes.isEmpty());
            } while (!listeMatchesNonTries.isEmpty());

            System.out.println(((double) i / nbChampionnats) * 100 + "%...");
        }
        System.out.println();
        System.out.println("Taille liste : " + listeMatchesTries.size());
        BDD.closeConnection();
    }

}
