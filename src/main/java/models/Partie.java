package models;

import java.util.*;

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

public class Partie {

    // Attributs
    private Player[] players;
    private Zone[] zones = new Zone[5];
    private HashMap<Player, Integer> results;

    public int getZoneIndex(Zone zone) {
        for (int i = 0; i < zones.length; i++) {
            if (zones[i] == zone) {
                return i;
            }
        }
        return -1;
    }

    @GetSetMethode
    public Zone[] getZones() {
        return this.zones;
    }

    @GetSetMethode
    public Player[] getPlayers() {
        return this.players;
    }

    /**
     *
     * @Methode: initZones;
     *
     * @Function: Cette fonction initialise les cinq zones présentes dans le jeu;
     *
     * @ParamètreDentrée: Liste des noms des différentes zones;
     *
     * @Return: rien (void);
     *
     */
    public void initZones(String[] n) {
        for (int i = 0; i < 5; i++) {
            this.zones[i] = new Zone(n[i]);
        }
    }

    /**
     *
     * @Methode: initPlayers;
     *
     * @Function: Cette fonction permet au jeu d'initialiser les joueurs du jeu;
     *
     * @ParamètreDentrée: Entrer dans la console leur nom et leur spécialité;
     *
     * @Return: rien (void);
     *
     */
    public void initPlayers(String pseudoP1, String filiereP1, String pseudoP2, String filiereP2) {
        this.players = new Player[] { new Player(pseudoP1, filiereP1), new Player(pseudoP2, filiereP2) };
    }

    /**
     *
     * @Methode: initCombs;
     *
     * @Function: Cette fonction permet au jeu de trier ses combattant comme
     *            maitreGobi, elite ou basique et les atribuir les status;
     *
     * @ParamètreDentrée: rien (void);
     *
     * @Return: rien (void);
     *
     */
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
                combattants.add(new Combattant(stats[0], stats[1], stats[2], stats[3], stats[4], "r", r));
            }
            p.setCombattant(combattants);
        }
    }

    /**
     *
     * @Methode: initAutoComb;
     *
     * @Function: Cette fonction permet à chaque joueur de générer aléatoirement ses
     *            combattants. Chaque combattant aura des statistiques aléatoires;
     *
     * @ParamètreDentrée: Le numéro du combattant et du joueur associé;
     *
     * @Return: Une liste de 20 combattants;
     *
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

    /**
     *
     * @Methode: chooseZone;
     *
     * @Function: Cette fonction permet à l'utilisateur de choisir une zone
     *            aléatoire du jeu. La zone doit être une zone qui n'a pour
     *            l'instant aucun vainqueur;
     *
     *
     *
     * @ParamètreDentrée: rien (void);
     *
     * @Return: L'indice de la zone;
     *
     */
    public int chooseZone() {
        int resp;
        do {
            resp = (int) (Math.random() * 5);
        } while (this.zones[resp].getIsFinish() == true);
        return resp;
    }

    @GetSetMethode
    public HashMap<Player, Integer> getResults() {
        return results;
    }

    @GetSetMethode
    public void setResults(HashMap<Player, Integer> results) {
        this.results = results;
    }

}
