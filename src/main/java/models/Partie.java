package models;

import java.util.*;


public class Partie {

    private Player[] players;
    private Zone[] zones = new Zone[5];

    private HashMap<Player,Integer> results;


    public int getZoneIndex(Zone zone){
        for(int i = 0; i < zones.length; i++){
            if(zones[i] == zone){
                return i;
            }
        }
        return -1;
    }

    public Zone[] getZones() {
        return this.zones;
    }

    public Player[] getPlayers() {
        return this.players;
    }

    /*
     * This function initialize the fives zones found in the game.
     * The function takes as an input a list of names of the different zones.
     * this function return nothing.
     */
    public void initZones(String[] n) {
        for (int i = 0; i < 5; i++) {
            this.zones[i] = new Zone(n[i]);
        }
    }



    /*
     * This function allow the game to initialize the players of the game.
     * To do so, each player must give as an input in the console their name and their speciality.
     * The functions takes nothing as an input.
     * This function does not return anything.
     */
    public void initPlayers(String pseudoP1, String filiereP1, String pseudoP2, String filiereP2) {
        this.players = new Player[]{new Player(pseudoP1, filiereP1), new Player(pseudoP2, filiereP2)};
    }


    public void initCombs() {
        for (Player p : players) {
            List<Combattant> combattants = new ArrayList<Combattant>();
            for (int j = 0; j < 20; j++) {
                Role r;
                if (j == 0) {
                    r = Role.maitreGobi;
                } else if (j < 5) {
                    r = Role.elite;
                } else {
                    r = Role.basique;
                }
                int[] stats = r.getStats();
                combattants.add(new Combattant(stats[0],
                        stats[1], stats[2],
                        stats[3], stats[4],
                        "r", r));
            }
            p.setCombattant(combattants);
        }
    }


    /*
     * This function allow to each player to generate their fighters randomly.
     * Each fighter will have random statistics.
     * The function takes as an input, the number of the combattant and the player associated.
     * This function returns a list of 20 combattants.
     */
    public void initAutoComb(int nbPlayer) {
        List<Combattant> combattants = new ArrayList<Combattant>();
        for (int j = 0; j < 20; j++) {
            Combattant combattant = Combattant.generateRandomly(j, this.players[nbPlayer]);
            combattants.add(combattant);
            System.out.println(j + " -> " + combattant.getRole() + " : " + Arrays.toString(combattant.getStats()));
        }
        this.players[nbPlayer].setCombattant(combattants);
    }

    /*
     * This function allows the user to pick a random zone of the game.
     * The zone must be a zone that has for the moment no winner.
     * The function takes nothing as an input.
     * This function return the index of the zone.
     */
    public int chooseZone() {
        int resp;
        do {
            resp = (int) (Math.random() * 5);
        } while (this.zones[resp].getIsFinish() ==true);
        return resp;
    }

}


