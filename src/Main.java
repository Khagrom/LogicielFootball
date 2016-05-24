
import java.sql.ResultSet;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BDD BDD = new BDD();
        ArrayList<String> listeEquipes = new ArrayList<>();
        ArrayList<String> listeMatches = new ArrayList<>();
        for (int k =1; k<11;k++){
        for (int i = k; i <= k; i++) {
            try (ResultSet req = BDD.getStatement().executeQuery("SELECT nom FROM equipe WHERE idChampionnat = " + i + ";")) {
                while (req.next()) {
                    listeEquipes.add(req.getString("nom"));
                }
            }
        }

        for (int i = k; i <= k; i++) {
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
        int nbjour = listeEquipes.size()-1;
        System.out.println(nbjour);
        String[][] matches = new String[((listeEquipes.size())/2)][2];
        System.out.println(matches.length);
        for (int i = 0; i<matches.length ; i++){
            for (int j = 0; j< matches[i].length; j++){
                matches[i][j] = new String(listeEquipes.get(0).toString());
                listeEquipes.remove(0);
            }
        }
       System.out.println("\n-------------------- Championnat " +k);
      for(int i =0; i<matches.length;i++)System.out.println(matches[i][0] + " - " + matches[i][1]);
            
        for (int j = 1; j<=nbjour*2;j++){
        ArrayList<String> listeJour1 = new ArrayList<>();   
            String temp = "";
            String temp2 = "";
            for (int l = matches.length-1; l>=0;l--){
                if (l == matches.length-1){
                    temp = matches[l][0];
                    matches[l][0] = matches[l][1];
                    matches[l][1] = matches[l-1][1];
                }
                else if (l == 0) {
                    matches[l][1] = temp; 
                }

                else {
                    temp2 = matches[l][0];
                    matches[l][0] = temp;
                    matches[l][1] = matches[l-1][1];
                    temp =temp2;
                }
                
            }
            System.out.println("\nJournee "+ j);
            if (j > nbjour) for(int i =0; i<matches.length;i++)System.out.println(matches[i][1] + " - " + matches[i][0]);
            else for(int i =0; i<matches.length;i++)System.out.println(matches[i][0] + " - " + matches[i][1]);
            /*listeMatches.remove(match);
            listeJour1.add(match);
        listeJour1.stream().forEach((i) -> {
            System.out.println(i);
        });*/
       listeJour1.clear();
    }
         
    }BDD.closeConnection();
    }

}
