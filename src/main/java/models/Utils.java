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

	
	
	/*
	 * This function allows the user to broadcast messages.
	 * The broadcasted message will be shown between two empty lines.
	 * The function takes as an input the message.
	 * This function returns nothing.
	 */
	public static void broadcast(String m) {
		System.out.println();
		System.out.println(m);
		System.out.println();
	}
	
	
	
	/*
	 * This function allows the player to get an input from the player in the console.
	 * The function takes as an input the message that will be shown to the player.
	 * This function return the input of the player.
	 */
	public static String input(String m) {
		String resp;
		System.out.println(m);
		resp = scan.nextLine();
		return resp;
	}
	
	/*
	 *This function allows the user to see if an object o is in a list of objects.
	 *The functions takes as an input the object and the list of objects.
	 *This function return a boolean value. 
	 */
	public static boolean in(Object o, Object[] os) {
		boolean resp = false;
		for (Object obj: os) {
			if (obj != null) {
				if (obj.equals(o)) {
					resp = true;
				}
			}
		}
		return resp;
	}
	
	/*
	 * The function allows the user to know if a string is an integer.
	 * It takes as an input the string.
	 * This function return a boolean value.
	 */
	public static boolean isInt(String s) {
		if (s == null) return false;
		try {
			Integer.parseInt(s);
		} catch(NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	/*
	 * This function allows the user to calculate the sum of a list of integers.
	 * The function takes as an input a list of integers.
	 * This function returns the sum of the list.
	 */
	public static int sum(int[] i) {
		int sum = 0;
		for (int nb: i) {
			sum += nb;
		}
		return sum;
	}
	
	
    static String[] getCombInput(String texte, int[] stats, int credit) {
    	
    	String[] input;
    	do {
    		System.out.println(texte);
        	input = scan.nextLine().split("/");
		} while (checkCombInput(input, stats, credit));
    	
    	return input;
    	
    }
    
    static boolean checkCombInput(String[] input, int[] stats, int credit) {
    	
   		if(!Arrays.asList(checkStrategie).contains(input[input.length-1])) {
   			return true;
   		}else {
   			int sum = 0;
   			for (int i = 0; i < stats.length; i++) {
				if(!Arrays.asList(checkPoints).contains(input[i])) {
					return true;
				}else {
					if(Integer.parseInt(input[i])+stats[i] >10) {
						return true;
					}
				}
				sum +=Integer.parseInt(input[i]);
			}
   			if(credit - sum<0) {
   	   			return true;
   	   		}
   		}
   		return false;
    }
    
    static int[] getResInput() {
    	
    	String[] input;
    	int[] intInput;
    	do {
        	input = scan.nextLine().split("/");
		} while (checkResInput(input));
    	intInput = Arrays.asList(input).stream().mapToInt(Integer::parseInt).toArray();
    	Arrays.sort(intInput);
    	return intInput;
    }
    static boolean checkResInput(String[] input) {
//    	Set<String> set = new HashSet<String>(Arrays.asList(input));
   		if(input.length !=5 ) {
   			System.out.println("Attention : Il faut saisir 5 réservistes");
   			return true;
   		}else {
   			for (int i = 0; i < input.length; i++) {
				if(!Arrays.asList(checkComb).contains(input[i])) {
					System.out.println("Attention : Veuillez saisir des chiffres/nombres compris entre 1 et 20");
					return true;
				}
			}

   		}
   		System.out.println("c'est good !");
   		return false;
    }
   
    
    static int[] getZoneInput(int nbZone, int nbComb) {
    	
    	String[] input;
    	int[] intInput;
    	do {
        	input = scan.nextLine().split("/");
		} while (checkZoneInput(input, nbZone, nbComb));
    	intInput = Arrays.asList(input).stream().mapToInt(Integer::parseInt).toArray();
    	Arrays.sort(intInput);
    	return intInput;
    }
    
    static boolean checkZoneInput(String[] input, int nbZone, int nbComb) {
    	int nbCombLeft = nbComb - input.length;
   		if(input.length == 0 | nbZone>nbCombLeft) {
   			System.out.println("Attention : Il faut au minimum un combattant dans chaque Zone");
   			return true;
   		}else {
   			for (int i = 0; i < input.length; i++) {
				if(!Arrays.asList(checkComb).contains(input[i])) {
					System.out.println("Attention : Veuillez saisir des chiffres/nombres compris entre 1 et "+ (nbComb));
					return true;
				}
				if(Integer.parseInt(input[i])-1>nbComb) {
					return true;
				}
			}

   		}
   		if(nbZone==0 && input.length!=nbComb) {
   			System.out.println("Attention : Il faut déployer l'ensemble des combattants !");
   			return true;
   		}
   		System.out.println("c'est good !");
   		return false;
    }

	static int[] getInput(int nbZone, int nbComb) {

		String[] input;
		int[] intInput;
		do {
			input = scan.nextLine().split("/");
		} while (checkInput(input, nbZone, nbComb));
		intInput = Arrays.asList(input).stream().mapToInt(Integer::parseInt).toArray();
		Arrays.sort(intInput);
		return intInput;
	}

	static boolean checkInput(String[] input, int nbZone, int nbComb) {
		int nbCombLeft = nbComb - input.length;
		if(input.length == 0 | nbZone>nbCombLeft) {
			System.out.println("Attention : Il faut au minimum un combattant dans chaque Zone");
			return true;
		}else {
			for (int i = 0; i < input.length; i++) {
				if(!Arrays.asList(checkComb).contains(input[i])) {
					System.out.println("Attention : Veuillez saisir des chiffres/nombres compris entre 1 et "+ (nbComb));
					return true;
				}
				if(Integer.parseInt(input[i])-1>nbComb) {
					return true;
				}
			}

		}
		if(nbZone==0 && input.length!=nbComb) {
			System.out.println("Attention : Il faut déployer l'ensemble des combattants !");
			return true;
		}
		System.out.println("c'est good !");
		return false;
	}
    
	
}
