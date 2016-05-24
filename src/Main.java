
import java.sql.ResultSet;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        String[][] res = null;
        initCoupe(3);

    }

    public static void initCoupe(int idCoupe) throws Exception {
        BDD BDD = new BDD();
        int i = 0;
        ArrayList<String> listeCoupeNationale = new ArrayList<>();
        try (ResultSet req = BDD.getStatement().executeQuery("SELECT nom, idPays FROM coupeNationale where id = " + idCoupe + ";")) {
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
                    System.out.println("");
                    nombreMatchQualif = (listeEquipesPays.size() - 32);
                }
                ArrayList<String> listeEquipes2 = new ArrayList<>();
                try (ResultSet req2 = BDD.getStatement().executeQuery("SELECT distinct equipe.nom, equipe.id from equipe,championnat, pays where championnat.idPays =" + i + " and championnat.id = equipe.idChampionnat and championnat.niveau = 2;")) {
                    while (req2.next()) {
                        listeEquipes2.add(req2.getString("equipe.nom"));
                    }
                    String[][] matchQualifs = new String[nombreMatchQualif][2];
                    for (int j = 0; j < nombreMatchQualif; j++) {
                        int rand = (int) (Math.random() * listeEquipes2.size() - 1);
                        matchQualifs[j][0] = listeEquipes2.get(rand);
                        //listeEquipesPays.remove(listeEquipes2.get(rand));
                        listeEquipes2.remove(rand);
                        rand = (int) (Math.random() * listeEquipes2.size() - 1);
                        matchQualifs[j][1] = listeEquipes2.get(rand);
                        listeEquipesPays.remove(listeEquipes2.get(rand));
                        listeEquipes2.remove(rand);
                    }

                    String[] dates = new String[6];
                    dates = Match.simulerDateCoupe(2015, idCoupe);
                    System.out.println("Premier tout qualificatif Le " + dates[0]);
                    for (int j = 0; j < nombreMatchQualif; j++) {
                        System.out.println(matchQualifs[j][0] + " - " + matchQualifs[j][1]);
                    }
                   
                    String[][] matchElim;
                    while (listeEquipesPays.size() > 1) {
                        listeEquipes2.clear();
                        for (String clone : listeEquipesPays) {
                            listeEquipes2.add(clone);
                        }
                        
                       
                        if (listeEquipesPays.size()/2 == 4){
                            System.out.println("\nQuart de finale Le " + dates[3]);
                        }else if (listeEquipesPays.size()/2 == 2){
                            System.out.println("\nDemi finale Le " + dates[4]);
                        }else if (listeEquipesPays.size()/2 == 1){
                            System.out.println("\nFinale Le " + dates[5]);
                        }else  if (listeEquipesPays.size()/2 == 1) {
                            System.out.println("\n16ème de finale " + dates[1]);
                        }else {System.out.println("\n8ème de finale " + dates[2]);}
                       
                        int nbmatch = listeEquipesPays.size()/2;
                        matchElim = new String[listeEquipesPays.size()/2][2];
                            for (int j = 0; j < nbmatch; j++) {
                            int rand = (int) (Math.random() * listeEquipes2.size() - 1);
                            matchElim[j][0] = listeEquipes2.get(rand);
                            listeEquipes2.remove(rand);
                            rand = (int) (Math.random() * listeEquipes2.size() - 1);
                            matchElim[j][1] = listeEquipes2.get(rand);
                            listeEquipesPays.remove(listeEquipes2.get(rand));
                            listeEquipes2.remove(rand);
                        }
                        for (int k = 0; k < matchElim.length; k++) {
                            System.out.println(matchElim[k][0] + " - " + matchElim[k][1]);
                        }
                    }
                }
            }
        }
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
