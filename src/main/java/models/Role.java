package models;

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

public enum Role {

	// Attributs
	basique(0, 0, 0, 0, 0), elite(1, 1, 1, 5, 1), maitreGobi(2, 2, 2, 10, 2);

	private int force;
	private int dexterite;
	private int resistance;
	private int constitution;
	private int initiative;

	/**
	 *
	 * @Construtor: Role;
	 *
	 * @Function: Créer les caractéristiques de chaque étudiant;
	 *
	 * @ParamètreDentrée: Status;
	 *
	 * @Return: Attribuition des Status;
	 *
	 */
	Role(int force, int dexterite, int resistance, int constitution, int initiative) {
		this.force = force;
		this.dexterite = dexterite;
		this.constitution = constitution;
		this.initiative = initiative;
		this.resistance = resistance;
	}

	@GetSetMethode
	public int getForce() {
		return force;
	}

	@GetSetMethode
	public int getDexterite() {
		return dexterite;
	}

	@GetSetMethode
	public int getResistance() {
		return resistance;
	}

	@GetSetMethode
	public int getConstitution() {
		return constitution;
	}

	@GetSetMethode
	public int getInitiative() {
		return initiative;
	}

	@GetSetMethode
	public int[] getStats() {
		int stats[] = { this.dexterite, this.force, this.resistance, this.constitution, this.initiative };
		return stats;
	}

}
