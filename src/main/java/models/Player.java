package models;

import java.util.ArrayList;
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

public class Player {

    // Attributs
    private String pseudo;
    private String filiere;
    private int creditECTS;
    private boolean winner;
    private int score;
    private List<Combattant> combattant = new ArrayList<Combattant>();
    public List<Combattant> reserviste = new ArrayList<Combattant>();

    /**
     *
     * @Construtor: Player;
     *
     * @Function: Créer le joueur et attibui à lui un nom et une filière;
     *
     * @ParamètreDentrée: Prend en entrée son pseudo et sa filière;
     *
     * @Return: Attribuition de pseudo, de filière et des 400 credits ECTS;
     *
     */
    Player(String pseudo, String filiere) {
        this.pseudo = pseudo;
        this.setFiliere(filiere);
        this.creditECTS = 400;
        this.setWinner(false);
    }

    @GetSetMethode
    public int getScore() {
        return this.score;
    }

    @GetSetMethode
    public void updateScore() {
        this.score++;
    }

    @GetSetMethode
    public String getPseudo() {
        return this.pseudo;
    }

    @GetSetMethode
    public int getCreditECTS() {
        return this.creditECTS;
    }

    @GetSetMethode
    public void setCreditECTS(int value) {
        this.creditECTS = value;
    }

    @GetSetMethode
    public List<Combattant> getCombattant() {
        return this.combattant;
    }

    @GetSetMethode
    void setCombattant(List<Combattant> value) {
        this.combattant = value;
    }

    @GetSetMethode
    public List<Combattant> getReserviste() {
        return this.reserviste;
    }

    @GetSetMethode
    public void setReserviste(List<Combattant> value) {
        this.reserviste = value;
    }

    @GetSetMethode
    public String getFiliere() {
        return filiere;
    }

    @GetSetMethode
    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    @GetSetMethode
    public boolean isWinner() {
        return winner;
    }

    @GetSetMethode
    public void setWinner(boolean winner) {
        this.winner = winner;
    }

}
