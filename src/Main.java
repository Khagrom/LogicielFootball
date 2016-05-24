
import java.sql.ResultSet;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        String[][] res = null;
        res = initCoupe(98);
        initChampionnat(2015);
        
       }
    
    
    
public static String[][] initCoupe(int idCoupe) throws Exception{
            BDD BDD = new BDD();
        int i = 0;
        ArrayList<String> listeCoupeNationale = new ArrayList<>();
        try (ResultSet req = BDD.getStatement().executeQuery("SELECT nom, idPays FROM coupeNationale where id = " + idCoupe + ";")){
                while (req.next()) {
                    listeCoupeNationale.add(req.getString("nom"));
                    i = req.getInt("idPays");
                }
                for (String coupeNationale : listeCoupeNationale) {
                    ArrayList<String> listeEquipesPays = new ArrayList<>();
                    int nombreMatchQualif;
                    try (ResultSet req2 = BDD.getStatement().executeQuery("SELECT distinct equipe.nom, equipe.id from equipe,championnat, pays where championnat.idPays =" + i + " and championnat.id = equipe.idChampionnat;")) {
                        while (req2.next()) {
                            listeEquipesPays.add(req2.getString("equipe.nom"));
                        }
                        System.out.println("\n" + coupeNationale);
                        System.out.println(listeEquipesPays.size() - 32);
                        nombreMatchQualif = (listeEquipesPays.size() - 32);
                    }
                    listeEquipesPays.clear();
                    try (ResultSet req2 = BDD.getStatement().executeQuery("SELECT distinct equipe.nom, equipe.id from equipe,championnat, pays where championnat.idPays =" + i + " and championnat.id = equipe.idChampionnat and championnat.niveau = 2;")) {
                        while (req2.next()) {
                            listeEquipesPays.add(req2.getString("equipe.nom"));
                        }
                        String[][] matchQualifs = new String[nombreMatchQualif][2];
                        for (int j = 0; j < nombreMatchQualif; j++) {
                            int rand = (int) (Math.random() * listeEquipesPays.size() - 1);
                            matchQualifs[j][0] = listeEquipesPays.get(rand);
                            listeEquipesPays.remove(rand);
                            rand = (int) (Math.random() * listeEquipesPays.size() - 1);
                            matchQualifs[j][1] = listeEquipesPays.get(rand);
                            listeEquipesPays.remove(rand);

                        }
                        for (int j = 0; j < nombreMatchQualif; j++) {
                            System.out.println(matchQualifs[j][0] + " - " + matchQualifs[j][1]);
                        }
                        
                        return matchQualifs;
                    }

                }
            }
return null;
}
    public static void initChampionnat(int annee) throws Exception {
        BDD BDD = new BDD();
        String[] date = Match.simulerDate(annee);
        ArrayList<String> listeEquipes = new ArrayList<>();
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
                System.out.println("\nJournee " + j + "\t\tLe " + date[j - 1]);
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

        BDD.closeConnection();
    }

 }