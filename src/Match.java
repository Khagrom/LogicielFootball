

import java.sql.ResultSet;
import java.util.Calendar;

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

    public static int[] simulerResultat() {
        int[] res = new int[2];
        double i = Math.random() * 100;
        if (i < 30) {
            res[0] = 0;
        } else if (i < 65) {
            res[0] = 1;
        } else if (i < 85) {
            res[0] = 2;
        } else if (i < 92) {
            res[0] = 3;
        } else if (i < 97) {
            res[0] = 4;
        } else if (i < 98.2) {
            res[0] = 5;
        } else if (i < 99) {
            res[0] = 6;
        } else if (i < 99.6) {
            res[0] = 7;
        } else {
            res[0] = 8;
        }

        i = Math.random() * 100;

        if (i < 35) {
            res[1] = 0;
        } else if (i < 65) {
            res[1] = 1;
        } else if (i < 85) {
            res[1] = 2;
        } else if (i < 92) {
            res[1] = 3;
        } else if (i < 97) {
            res[1] = 4;
        } else if (i < 98.2) {
            res[1] = 5;
        } else if (i < 99) {
            res[1] = 6;
        } else if (i < 99.6) {
            res[1] = 7;
        } else {
            res[1] = 8;
        }
        
        System.out.println(res[0] + " " + res[1]);

        return res;
    }
    public static int[] simulerProlongations(int[] resultatAvantPronlongations) {
    int[] resultatApresPronlongations = new int[2];
    double i = Math.random() * 100;
        if (i < 70) {
            resultatApresPronlongations[0] = resultatAvantPronlongations[0] + 0;
        } else if (i < 90) {
            resultatApresPronlongations[0] = resultatAvantPronlongations[0] + 1;
        } else if (i < 98) {
            resultatApresPronlongations[0] = resultatAvantPronlongations[0] + 2;
        }
        else resultatApresPronlongations[0] = resultatAvantPronlongations[0] + 3;

        i = Math.random() * 100;

        if (i < 75) {
            resultatApresPronlongations[1] = resultatAvantPronlongations[1] + 0;
        } else if (i < 95) {
            resultatApresPronlongations[1] = resultatAvantPronlongations[1] + 1;
        } else if (i < 99) {
            resultatApresPronlongations[1] = resultatAvantPronlongations[1] + 2;
        } else resultatApresPronlongations[1] = resultatAvantPronlongations[1] + 3;

        return resultatApresPronlongations;
    }
    
    public static int[] simulerTAB(){
        int[] nbTAB = new int[2];
        nbTAB[0] = 0;
        nbTAB[1] = 0;
        for (int i = 5; i > 0; i--){
            if (Math.random()*2>0.5) nbTAB[0]++;
            if (Math.random()*2>0.5) nbTAB[1]++;
            if (i<(Math.abs(nbTAB[0]-nbTAB[1]))){
                return nbTAB;
            }
        }
        if (nbTAB[0] == nbTAB[1]){
            if (Math.random()*2>1) nbTAB[0]++;
            else nbTAB[1]++;
        }
        
        return nbTAB; 
    }

    public static String[] simulerDate(int annee) {
        String[] date = new String[46];
        Calendar calendar = Calendar.getInstance();
        calendar.set(annee, 7, 0);
        while (calendar.get(Calendar.DAY_OF_WEEK) != 7) {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        for (int i = 1; i <= 46; i++) {
            String dateFormater = calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
            date[i - 1] = dateFormater;
            calendar.add(Calendar.DATE, 7);
        }
        return date;
    }

    public static String[] simulerDateCoupe(int annee, int numCoupe) {
        String[] date = new String[6];
        Calendar calendar = Calendar.getInstance();
        if (numCoupe == 2) {
            calendar.set(annee, 9, 1);
            while (calendar.get(Calendar.DAY_OF_WEEK) != 4) {
                calendar.add(Calendar.DAY_OF_WEEK, 1);
            }
        } else {
            calendar.set(annee+1, 2, 0);
            while (calendar.get(Calendar.DAY_OF_WEEK) != 4) {
                calendar.add(Calendar.DAY_OF_WEEK, 1);
            }
        }
        for (int i = 1; i <= 6; i++) {
            String dateFormater = calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
            date[i - 1] = dateFormater;
            calendar.add(Calendar.DATE, 7);
        }
        return date;
    }
}


