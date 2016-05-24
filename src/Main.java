
import java.sql.ResultSet;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BDD BDD = new BDD();
        ArrayList<String> listeEquipes = new ArrayList<>();
<<<<<<< HEAD
        int nbChampionnats = 0;

        try (ResultSet req = BDD.getStatement().executeQuery("SELECT count(id) FROM championnat;")) {
            while (req.next()) {
                nbChampionnats = req.getInt("count(id)");
            }
        }

        for (int k = 1; k <= nbChampionnats; k++) {
            for (int i = k; i <= k; i++) {
                try (ResultSet req = BDD.getStatement().executeQuery("SELECT nom FROM equipe WHERE idChampionnat = " + i + ";")) {
                    while (req.next()) {
                        listeEquipes.add(req.getString("nom"));
                    }
                }
            }
            
            int nbjour = listeEquipes.size() - 1;
            String[][] matches = new String[((listeEquipes.size()) / 2)][2];
            for (String[] matche : matches) {
                for (int j = 0; j < matche.length; j++) {
                    matche[j] = listeEquipes.get(0);
                    listeEquipes.remove(0);
                }
            }
            System.out.println("\n-------------------- Championnat " + k + " --------------------");
            for (String[] matche : matches) {
                System.out.println(matche[0] + " - " + matche[1]);
            }

            for (int j = 1; j <= nbjour * 2; j++) {
                String temp = "";
                String temp2;
                for (int l = matches.length - 1; l >= 0; l--) {
                    if (l == matches.length - 1) {
                        temp = matches[l][0];
                        matches[l][0] = matches[l][1];
                        matches[l][1] = matches[l - 1][1];
                    } else if (l == 0) {
                        matches[l][1] = temp;
                    } else {
                        temp2 = matches[l][0];
                        matches[l][0] = temp;
                        matches[l][1] = matches[l - 1][1];
                        temp = temp2;
                    }

                }
                System.out.println("\nJournee " + j);
                if (j > nbjour) {
                    for (String[] matche : matches) {
                        System.out.println(matche[1] + " - " + matche[0]);
                    }
                } else {
                    for (String[] matche : matches) {
                        System.out.println(matche[0] + " - " + matche[1]);
                    }
                }
            }

        }
=======
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
>>>>>>> 3944aac810f6f2a3ee2b130137d51b1c151df3f4
        BDD.closeConnection();
    }

}
