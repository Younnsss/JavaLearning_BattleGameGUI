package models;
import java.util.Arrays;
//import java.util.HashSet;
import java.util.List;
import java.util.Scanner;


public class Utils {
	
	public final static String checkFiliere[] = {"ISI", "RT"};
	public final static String checkStrategie[] = {"a", "s", "r"};
	public final static String checkPoints[] = {"0", "1", "2", "3", "4", "5","6", "7", "8", "9","10"};
	public final static String checkComb[] = {"0", "1", "2", "3", "4", "5","6", "7", "8", "9","10","11", "12", "13", "14", "15", "16","17", "18", "19", "20"};

	public final static Scanner scan = new Scanner(System.in);
	
	private final static int REFERENCE_DEGAT = 10;
	
	public static void attack(Combattant comb, List<Combattant> allies, List<Combattant> enemies) {
		if(enemies.size()==0){
			System.out.println("Il n'y a plus d'ennemis");
		}
		else {
			Combattant target = enemies.get(getTarget(enemies));
			System.out.println(Arrays.toString(comb.getStats()) + " attaque" + Arrays.toString(target.getStats()));
			if (Math.round(Math.random() * 100) <= 40 + 3 * comb.getDexterite()) {
				// * Attaque reussie
				double coefficientDegat = Math.max(0, Math.min(100, 10 * comb.getForce() - 5 * target.getResistance())) / 100;
				target.recevoirDegats((int) Math.ceil(Math.random() * (1 + coefficientDegat) * REFERENCE_DEGAT));
				if (target.getCreditECTS() == 0) {
					enemies.remove(target);
				}
			} else {
				// * attaque rate
				System.out.println("L'attaque rate");
			}
		}
    }

    public static void heal(Combattant comb, List<Combattant> allies, List<Combattant> enemies) {
		Combattant target = allies.get(getTarget(allies));
		System.out.println(Arrays.toString(comb.getStats()) + " soigne" + Arrays.toString(target.getStats()));
        if (Math.round(Math.random() * 100) <= 20 + 6 * comb.getDexterite()) {
            // * soin réussi
            target.recevoirSoin((int) Math.min(30+target.getConstitution(),Math.ceil(Math.random() * 0.6 * (10 + target.getConstitution()))));
        } else {
            // * soin rater
        	System.out.println("Le soin rate");
        }
    }

	public static int getTarget(List<Combattant> combattants) {
		int min = combattants.get(0).getCreditECTS();
		int index = 0;
		for (int i = 1; i < combattants.size(); i++) {
			if (combattants.get(i).getCreditECTS() < min) {
				min = combattants.get(i).getCreditECTS();
				index = i;
			}
		}
		return index;
	}

    
	
}
