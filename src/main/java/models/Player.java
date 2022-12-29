package models;
import java.util.ArrayList;
import java.util.List;

public class Player {
	
    private String pseudo,filiere;
    private int creditECTS;
    private boolean winner;
    private int score;
    private List<Combattant> combattant = new ArrayList<Combattant> ();
    public List<Combattant> reserviste = new ArrayList<Combattant> ();

    public int getScore() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.score;
    }

    public void updateScore() {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.score++;
    }
    
    Player(String pseudo, String filiere){
    	this.pseudo = pseudo;
    	this.filiere = filiere;
    	this.creditECTS = 400;
        this.winner = false;
    }

    public String getPseudo() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.pseudo;
    }

    public int getCreditECTS() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.creditECTS;
    }

    public void setCreditECTS(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.creditECTS = value;
    }

    public List<Combattant> getCombattant() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.combattant;
    }

    void setCombattant(List<Combattant> value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.combattant = value;
    }

    public List<Combattant> getReserviste() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.reserviste;
    }

    public void setReserviste(List<Combattant> value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.reserviste = value;
    }
    

}
