
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        initGroupeCoupeEurope("EL");
    }

    public static void initGroupeCoupeEurope(String nomCoupe) throws Exception {
        BDD BDD = new BDD();
        ArrayList<Integer[]> participants = new ArrayList<>();
        try (ResultSet req = BDD.getStatement().executeQuery("SELECT id, idChampionnat FROM equipe where coupeEurope = \"" + nomCoupe + "\" and idChampionnat < 11;")) {
            while (req.next()) {
                Integer[] equipe = { req.getInt("id"), req.getInt("idChampionnat")};
                participants.add(equipe);
            }
        }
        try (ResultSet req = BDD.getStatement().executeQuery("SELECT id, idChampionnat FROM equipe where idChampionnat = 11;")) {
            ArrayList<Integer[]> autresParticipant = new ArrayList<>();
            while (req.next()) {
                Integer[]  equipe = { req.getInt("id"), req.getInt("idChampionnat")};
                autresParticipant.add(equipe);
            }
            if (nomCoupe == "LC") {
                while (participants.size() != 32) {
                    int temp = (int) (Math.random() * autresParticipant.size());
                    participants.add(autresParticipant.get(temp));
                    autresParticipant.remove(temp);
                }
            } else {
                while (participants.size() != 48) {
                    int temp = (int) (Math.random() * autresParticipant.size());
                    participants.add(autresParticipant.get(temp));
                    autresParticipant.remove(temp);
                }
            }
            int[][] groupes= new int[participants.size()/4][4];
            for (int i = 1; i <= groupes.length;i++){
                System.out.println("\nGROUPE "+i);
                
                for (int j = 0;j<groupes[i-1].length;j++){
                    int temp = (int) (Math.random() * participants.size());
                    groupes[i-1][j]=(participants.get(temp))[0];
                    participants.remove(temp);
                }
                
                
                
            }
        }
        BDD.closeConnection();
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

                    java.sql.Date[] dates = new java.sql.Date[6];
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

                        if (listeEquipesPays.size() / 2 == 4) {
                            System.out.println("\nQuart de finale Le " + dates[3]);
                        } else if (listeEquipesPays.size() / 2 == 2) {
                            System.out.println("\nDemi finale Le " + dates[4]);
                        } else if (listeEquipesPays.size() / 2 == 1) {
                            System.out.println("\nFinale Le " + dates[5]);
                        } else if (listeEquipesPays.size() / 2 == 1) {
                            System.out.println("\n16ème de finale " + dates[1]);
                        } else {
                            System.out.println("\n8ème de finale " + dates[2]);
                        }

                        int nbmatch = listeEquipesPays.size() / 2;
                        matchElim = new String[listeEquipesPays.size() / 2][2];
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
        java.sql.Date[] date = Match.simulerDate(annee);
        ArrayList<Integer> listeEquipes = new ArrayList<>();
        int nbChampionnats = 0;

        try (ResultSet req = BDD.getStatement().executeQuery("SELECT count(id) FROM championnat;")) {
            while (req.next()) {
                nbChampionnats = req.getInt("count(id)");
            }
        }

        for (int k = 1; k <= nbChampionnats; k++) {
            for (int i = k; i <= k; i++) {
                try (ResultSet req = BDD.getStatement().executeQuery("SELECT id FROM equipe WHERE idChampionnat = " + i + ";")) {
                    while (req.next()) {
                        listeEquipes.add(req.getInt("id"));
                    }
                }
            }

            int nbjour = listeEquipes.size() - 1;
            int[][] matches = new int[((listeEquipes.size()) / 2)][2];
            for (int[] matche : matches) {
                for (int j = 0; j < matche.length; j++) {
                    matche[j] = listeEquipes.get(0);
                    listeEquipes.remove(0);
                }
            }
            System.out.println("\n" + (k - 1) * 100 / nbChampionnats + "%");
            for (int j = 1; j <= nbjour * 2; j++) {

                int temp = 0;
                int temp2;
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
                if (j % 2 == 0) {
                    System.out.print("   " + (int) (((double) (j - 1) / (nbjour * 2)) * 100) + "%");
                }
                if (j > nbjour) {

                    for (int[] match : matches) {
                        try {
                            String query = "INSERT INTO `rencontre`(`idEquipe1`, `idEquipe2`, `idCompetition`, `idGroupeArbitre`, `journee`, `date`) values (?,?,?,?,?,?);";
                            PreparedStatement ps = BDD.getConnection().prepareStatement(query);
                            ps.setInt(1, match[1]);
                            ps.setInt(2, match[0]);
                            ps.setInt(3, k);
                            ps.setInt(4, 1);
                            ps.setInt(5, j);
                            ps.setDate(6, date[j - 1]);
                            ps.execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    for (int[] match : matches) {
                        try {
                            String query = "INSERT INTO rencontre(idEquipe1, idEquipe2, idCompetition, idGroupeArbitre, journee, date) values (?, ?, ?, ?, ?, ?)";
                            PreparedStatement ps = BDD.getConnection().prepareStatement(query);
                            ps.setInt(1, match[0]);
                            ps.setInt(2, match[1]);
                            ps.setInt(3, k);
                            ps.setInt(4, 1);
                            ps.setInt(5, j);
                            ps.setDate(6, date[j - 1]);
                            ps.execute();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                }
            }
        }

        BDD.closeConnection();
    }

}
