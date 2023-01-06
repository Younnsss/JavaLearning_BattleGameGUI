package models;

import GUIController.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Zone {
	
    private String name;
    private Boolean isFinish;
    public List<Combattant> combattantP1 = new ArrayList<Combattant> ();
    public List<Combattant> combattantP2 = new ArrayList<Combattant> ();

	private Player Pwinner;

    Zone(String n) {
		this.name = n;
		this.isFinish = false;
	}
	public Player getPWinner() { return this.Pwinner; }
	public void setWinner(Player player) { this.Pwinner=player; }
	public String getName() { return this.name; }
	public Boolean getIsFinish() { return this.isFinish; }

    public List<Combattant> getCombattantP1() { return this.combattantP1;}

    public void setCombattantP1(List<Combattant> value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.combattantP1 = value;
    }
    
    public List<Combattant> getCombattantP2() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.combattantP2;
    }

    public void setCombattantP2(List<Combattant> value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.combattantP2 = value;
    }
	public void battle() {
		System.out.println(this.combattantP1.size());
		System.out.println(this.combattantP2.size());
		Combattant.affComb(this.combattantP1);
		System.out.println();
		Combattant.affComb(this.combattantP2);
		Collections.sort(this.combattantP1);
		Collections.sort(this.combattantP2);
		System.out.println();
		System.out.println();
		int j1=0,j2=0;
		while(this.combattantP1.size()>0 && this.combattantP2.size()>0) {
			j1=0;
			j2=0;
			while (j1 < this.combattantP1.size() && j2 < this.combattantP2.size()) {

				System.out.println();
				System.out.println();
				System.out.println("Action " + (j1 + j2 + 1));
				System.out.println();
				System.out.println();
				if (this.combattantP1.get(j1).getInitiative() > this.combattantP2.get(j2).getInitiative()) {
					System.out.println("Strategie :" + this.combattantP1.get(j1).strategie);
					this.combattantP1.get(j1).strategie.action(this.combattantP1.get(j1), this.combattantP1, this.combattantP2);
					System.out.println();
					Combattant.affComb(this.combattantP1);
					System.out.println();
					Combattant.affComb(this.combattantP2);
					j1++;
				} else {
					System.out.println("Strategie :" + this.combattantP2.get(j2).strategie);
					this.combattantP2.get(j2).strategie.action(this.combattantP2.get(j2), this.combattantP2, this.combattantP1);
					System.out.println();
					Combattant.affComb(this.combattantP1);
					System.out.println();
					Combattant.affComb(this.combattantP2);
					j2++;
				}
			}
			while (j1 < this.combattantP1.size()) {
				System.out.println();
				System.out.println();
				System.out.println("Action " + (j1 + j2 + 1));
				System.out.println();
				System.out.println();
				System.out.println("Strategie :" + this.combattantP1.get(j1).strategie);
				this.combattantP1.get(j1).strategie.action(this.combattantP1.get(j1), this.combattantP1, this.combattantP2);
				System.out.println();
				Combattant.affComb(this.combattantP1);
				System.out.println();
				Combattant.affComb(this.combattantP2);
				j1++;
			}
			while (j2 < this.combattantP2.size()) {
				System.out.println();
				System.out.println();
				System.out.println("Action " + (j1 + j2 + 1));
				System.out.println();
				System.out.println();
				System.out.println("Strategie :" + this.combattantP2.get(j2).strategie);
				this.combattantP2.get(j2).strategie.action(this.combattantP2.get(j2), this.combattantP2, this.combattantP1);
				System.out.println();
				Combattant.affComb(this.combattantP1);
				System.out.println();
				Combattant.affComb(this.combattantP2);
				j2++;
			}
		}
		this.isFinish = true;
	}

	public void results(){
		if(this.combattantP1.size() == 0) {
			this.setWinner(Main.game.getPlayers()[1]);
			Main.game.getPlayers()[1].updateScore();
			System.out.println(Main.game.getPlayers()[1].getPseudo() +" a gagné" + this.name);
		} else {
			this.setWinner(Main.game.getPlayers()[0]);
			Main.game.getPlayers()[0].updateScore();
			System.out.println(Main.game.getPlayers()[0].getPseudo() +" a gagné" + this.name);
		}
	}

}
