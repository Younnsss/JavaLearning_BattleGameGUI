package models;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Younes Boutkrida;
 * @author André Correia;
 *
 * @version final;
 *
 * @since 09.2022;
 *
 */

public class Combattant implements Comparable<Combattant> {

	// Attributs
	private int dexterite;
	private int creditECTS;
	private int force;
	private int resistance;
	private int constitution;
	private int initiative;
	private String mode;
	public Strategie strategie;
	private Role role;

	/**
	 *
	 * @Construtor: Combattant;
	 *
	 * @Function: Attribue les status et le role choisi à un combattant;
	 *
	 * @ParamètreDentrée: Les status et le role;
	 *
	 * @Return: rien (void);
	 *
	 */
	Combattant(int dex, int force, int res, int cons, int ini, String stra, Role r) {
		this.dexterite = dex;
		this.force = force;
		this.resistance = res;
		this.constitution = cons;
		this.initiative = ini;
		this.role = r;
		this.creditECTS = 30;
		switch (stra) {
			case "a":
				this.strategie = new Offensive();
				this.mode = "Offensive";
				break;
			case "s":
				this.strategie = new Defensive();
				this.mode = "Defensive";
				break;
			default:
				this.strategie = new Aleatoire();
				this.mode = "Aleatoire";
				break;
		}

	}

	@GetSetMethode
	public String getStrategie() {
		return this.mode;
	}

	@GetSetMethode
	public void setStrategie(String stra) {
		switch (stra) {
			case "a":
				this.strategie = new Offensive();
				this.mode = "Offensive";
				break;
			case "s":
				this.strategie = new Defensive();
				this.mode = "Defensive";
				break;
			default:
				this.strategie = new Aleatoire();
				this.mode = "Aleatoire";
				break;
		}
	}

	@GetSetMethode
	public Role getRole() {
		return this.role;
	}

	@GetSetMethode
	void setRole(Role value) {
		this.role = value;
	}

	@GetSetMethode
	public int getDexterite() {
		return this.dexterite;
	}

	@GetSetMethode
	public void setDexterite(int value) {
		this.dexterite = value;
	}

	@GetSetMethode
	public int getCreditECTS() {
		return this.creditECTS;
	}

	@GetSetMethode
	void setCreditECTS(int value) {
		this.creditECTS = value;
	}

	@GetSetMethode
	public int getForce() {
		return this.force;
	}

	@GetSetMethode
	public void setForce(int value) {
		this.force = value;
	}

	@GetSetMethode
	public int getResistance() {
		return this.resistance;
	}

	@GetSetMethode
	public void setResistance(int value) {
		this.resistance = value;
	}

	@GetSetMethode
	public int getConstitution() {
		return this.constitution;
	}

	@GetSetMethode
	public void setConstitution(int value) {
		this.constitution = value;
	}

	@GetSetMethode
	public int getInitiative() {
		return this.initiative;
	}

	@GetSetMethode
	public void setInitiative(int value) {
		this.initiative = value;
	}

	@GetSetMethode
	public int[] getStats() {
		int stats[] = { this.dexterite, this.force, this.resistance, this.constitution, this.initiative,
				this.creditECTS };
		return stats;
	}

	/**
	 *
	 * @Methode: affComb;
	 *
	 * @Function: Montre tous les combattants d'une zone;
	 *
	 * @ParamètreDentrée: List de combattants;
	 *
	 * @Return: rien (void);
	 *
	 */
	static void affComb(List<Combattant> combattants) {
		for (int i = 0; i < combattants.size(); i++) {
			System.out.print((i + 1) + " : " + Arrays.toString(combattants.get(i).getStats()) + " / ");
			if (i == 4 | i == 9 | i == 14) {
				System.out.println();
			}
		}
		System.out.println();
	}

	/**
	 *
	 * @Methode: recevoirDegats;
	 *
	 * @Function: Monttre tous les status d'une combattant après avoir été attaqué;
	 *
	 * @ParamètreDentrée: Crédits ECTS de degats;
	 *
	 * @Return: rien (void);
	 *
	 */
	public void recevoirDegats(int degats) {
		System.out.println("points : " + degats);
		this.creditECTS = Math.max(0, this.creditECTS - degats);
		System.out.println("Il reste " + this.creditECTS + " ECTS au combattant");
	}

	/**
	 *
	 * @Methode: recevoirSoin;
	 *
	 * @Function: Monttre tous les status d'une combattant après avoir été soigné;
	 *
	 * @ParamètreDentrée: Crédits ECTS de soin;
	 *
	 * @Return: rien (void);
	 *
	 */
	public void recevoirSoin(int soin) {
		System.out.println("points : " + soin);
		this.creditECTS = Math.min(30, this.creditECTS + soin);
		System.out.println("Il reste " + this.creditECTS + " ECTS au combattant");
	}

	/**
	 *
	 * @Methode: generateRandomly;
	 *
	 * @Function: Cette fonction permet à chaque joueur de générer aléatoirement ses
	 *            combattants. Chaque combattant aura des statistiques aléatoires;
	 *
	 * @ParamètreDentrée: Le numéro du combattant et du joueur associé;
	 *
	 * @Return: Cette fonction retourne un combattant généré aléatoirement;
	 *
	 */
	static Combattant generateRandomly(int nbCombattant, Player player) {
		Role r;
		if (nbCombattant == 0) {
			r = Role.maitreGobi;
		} else if (nbCombattant < 5) {
			r = Role.elite;
		} else {
			r = Role.basique;
		}
		String[] s = { "a", "s", "r" };
		int[] stats = r.getStats();
		int random;
		int max;

		for (int i = 0; i < stats.length; i++) {
			max = Math.min(player.getCreditECTS(), 10 - stats[i]);
			random = (int) (Math.random() * (max + 1));
			stats[i] += random;
			player.setCreditECTS(player.getCreditECTS() - random);
		}
		Combattant warrior = new Combattant(stats[0], stats[1], stats[2], stats[3], stats[4],
				s[(int) (Math.random() * 3)], r);
		return warrior;
	}

	@Override
	public int compareTo(Combattant o) {
		return o.getInitiative() - this.initiative;
	}

}
