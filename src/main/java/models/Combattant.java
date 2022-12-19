package models;
import java.util.Arrays;
import java.util.List;

public class Combattant implements Comparable<Combattant>{
    
	private int dexterite;

    private int creditECTS;

    private int force;

    private int resistance;

    private int constitution;

    private int initiative;

    public Strategie strategie;

    private Role role;
    
    Combattant(int dex,int force, int res, int cons, int ini, String stra, Role r){
    	this.dexterite =dex;
    	this.force = force;
    	this.resistance=res;
    	this.constitution = cons;
    	this.initiative = ini;
    	this.role=r;
    	this.creditECTS=30;
    	switch (stra) {
		case "a":
			this.strategie = new Offensive();
			break;
		case "s":
			this.strategie = new Defensive();
			break;
		default:
			this.strategie = new Aleatoire();
			break;
    	}
    	
    }


    Strategie getStrategie() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.strategie;
    }

    void setStrategie(String stra) {
		switch (stra) {
			case "a":
				System.out.println("oFFENSIVE");
				this.strategie = new Offensive();
				break;
			case "s":
				System.out.println("Defensive");
				this.strategie = new Defensive();
				break;
			default:
				System.out.println("Aleatoire");
				this.strategie = new Aleatoire();
				break;
		}
    }

    public Role getRole() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.role;
    }

    void setRole(Role value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.role = value;
    }
    
    public int getDexterite() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.dexterite;
    }

    public void setDexterite(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.dexterite = value;
    }

    int getCreditECTS() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.creditECTS;
    }

    void setCreditECTS(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.creditECTS = value;
    }

    public int getForce() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.force;
    }

    public void setForce(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.force = value;
    }

    public int getResistance() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.resistance;
    }

    public void setResistance(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.resistance = value;
    }

    public int getConstitution() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.constitution;
    }

    public void setConstitution(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.constitution = value;
    }

    public int getInitiative() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.initiative;
    }

    public void setInitiative(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.initiative = value;
    }
    
    public int[] getStats() {
		int stats[] = {this.dexterite,this.force,this.resistance,this.constitution,this.initiative, this.creditECTS};
		return stats;
	}
    
    static void affComb(List<Combattant> combattants) {
    	for (int i = 0; i < combattants.size(); i++) {
			System.out.print((i+1) + " : " + Arrays.toString(combattants.get(i).getStats()) + " / ");
			if (i==4 | i==9 | i==14) {
				System.out.println();
			}
		}
    	System.out.println();
    }
    
    public void recevoirDegats(int degats) {
		System.out.println("points : " + degats);
        this.creditECTS = Math.max(0, this.creditECTS - degats);
		System.out.println("Il reste " + this.creditECTS + " ECTS au combattant");
    }

    public void recevoirSoin(int soin) {
		System.out.println("points : " + soin);
		this.creditECTS=Math.min(30,this.creditECTS + soin);
		System.out.println("Il reste " + this.creditECTS + " ECTS au combattant");
    }
    
    
    
    
    /*
	 * This function allow to each player to generate their fighters randomly.
	 * Each fighter will have random statistics.
	 * The function takes as an input, the number of the combattant and the player associated.
	 * This function returns one combattant generated randomly.
	 */
     static Combattant generateRandomly(int nbCombattant, Player player) {
    	Role r;
		if(nbCombattant==0) {r=Role.maitreGobi;}
		else if(nbCombattant<5) {r=Role.elite;}
		else {r=Role.basique;}
		String[] s = {"a", "s", "r"};
    	int[] stats = r.getStats();
    	int random;
    	int max;
    	
    	for (int i = 0; i < stats.length; i++) {
    		max = Math.min(player.getCreditECTS(), 10-stats[i]);
			random= (int)(Math.random() * (max + 1));
			stats[i]+=random;
	    	player.setCreditECTS(player.getCreditECTS()-random);
		}
    	Combattant warrior = new Combattant(stats[0], stats[1], stats[2], 
    			stats[3], stats[4], s[(int) (Math.random() * 3)] , r);
    	return warrior;
    }
     
     
     /*
 	 * This function allow to each player to generate their fighters.
 	 * As an input, * The function takes, the number of the combattant and the player associated.
 	 * to each ability and the strategy of the fighter
 	 * Format: Str/Dex/Res/Con/Ini/Strat
 	 * This function returns one combattant.
 	 */
     static Combattant generate(int nbCombattant, Player player) {
    	 
    	 	String[] input;
    	 	Role r;
    	 	if(nbCombattant==0) {r=Role.maitreGobi;}
    	 	else if(nbCombattant<5) {r=Role.elite;}
    	 	else {r=Role.basique;}
    	 	int[] stats = r.getStats();
    	 	
			System.out.println(player.getCreditECTS() + "credit(s)° restant");
			System.out.println("Saisir Dex/For/Res/Cons/Ini/Stra : " + (nbCombattant+1)+"-"+r +" -> "+ Arrays.toString(stats));
			input=Utils.getCombInput("", stats, player.getCreditECTS());
			
			Combattant warrior = new Combattant(Integer.parseInt(input[0])+stats[0], 
					Integer.parseInt(input[1])+stats[1], Integer.parseInt(input[2])+stats[2], 
					Integer.parseInt(input[3])+stats[3], Integer.parseInt(input[4])+stats[4], 
					input[5], r);
			
	    	int sum=0;
	    	for (int i = 0; i < input.length-1; i++) {
				sum +=Integer.parseInt(input[i]);
			}
	    	player.setCreditECTS(player.getCreditECTS()-sum);
			
			
			System.out.println(""+(nbCombattant+1) + " : " + r +" -> "+ Arrays.toString(warrior.getStats()));
			return warrior;
     }


	@Override
	public int compareTo(Combattant o) {
		return o.getInitiative()-this.initiative;
	}

}
