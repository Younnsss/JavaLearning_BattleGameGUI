package models;
public enum Role {
	basique(0,0,0,0,0), 
	elite(1,1,1,5,1),
	maitreGobi(2,2,2,10,2);

	private int force;
	private int dexterite;
	private int resistance;
	private int constitution;
	private int initiative;
	
	Role(int force, int dexterite, int resistance, int constitution, int initiative){
		this.force = force;
		this.dexterite = dexterite;
		this.constitution = constitution;
		this.initiative = initiative;
		this.resistance = resistance;
	}

	public int getForce() {
		return force;
	}

	public int getDexterite() {
		return dexterite;
	}

	public int getResistance() {
		return resistance;
	}

	public int getConstitution() {
		return constitution;
	}

	public int getInitiative() {
		return initiative;
	}
	
	public int[] getStats() {
		int stats[] = {this.dexterite,this.force,this.resistance,this.constitution,this.initiative};
		return stats;
	}
	
	
}
