
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Léo
 */
public class test {

    public static void main(String args[]) {
        int[] tab = new int[5];
        tab = Match.simulerResultat();
        System.out.println("Match :\t" + tab[0] + " - " + tab[1]);
        if (tab[0] == tab[1]) {
            tab = Match.simulerProlongations(tab);
            System.out.println("Pronlongation :\t" + tab[0] + " - " + tab[1]);
            if (tab[0] == tab[1]) {

                tab = Match.simulerTAB();
                System.out.println("TAB :\t" + tab[0] + " - " + tab[1]);
            }
        }

        System.out.println();
    }
}

