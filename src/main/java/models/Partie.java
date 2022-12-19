package models;

import java.util.*;


public class Partie {

    private Player[] players;
    private Zone[] zones = new Zone[5];

    private HashMap<Player,Integer> results;

    public HashMap<Player,Integer> getResults() {
        return results;
    }

    public void setResults() {
        results = new HashMap<Player,Integer>();
        int score;
        for (Player p : players) {
            score = 0;
            for (Zone z : zones) {
                if (z.getPWinner().getPseudo() == p.getPseudo()) {
                    score++;
                }
            }
            results.put(p,score);
        }
        if(results.get(players[0]) > results.get(players[1])) {
            players[0].setWinner(true);
        } else{
            players[1].setWinner(true);
        }
    }

    public Zone[] getZones() {
        return this.zones;
    }

    public Zone getZone(int i) {
        return this.zones[i];
    }

    public Player[] getPlayers() {
        return this.players;
    }

    public List<Zone> getDestZones() {
        List<Zone> destZones = new ArrayList<Zone>();
        int cpt = 1;
        for (Zone z : this.zones) {
            if (z.getIsFinish() == null) {
                Utils.broadcast("----- Zone de combat " + cpt + " :" + z.getName() + " -----");
                Combattant.affComb(z.combattantP1);
                System.out.println();
                Combattant.affComb(z.combattantP2);
                destZones.add(z);
                cpt++;
            }
        }
        return destZones;
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

    ;

    /*
     * This function allow the game to initialize randomly the players of the game.
     * The default players are : {"Joueur 1", "ISI"} and {"Joueur 2", "RT"}
     * The functions takes nothing as an input.
     * This function does not return anything.
     */
    public void initAutoPlayer(int nbPlayer) {
        String pseudo = "";
        String filiere = "";
        if (nbPlayer == 0) {
            pseudo = "Joueur 1";
            filiere = "ISI";
        } else if (nbPlayer == 1) {
            pseudo = "Joueur 2";
            filiere = "RT";
        }
        Player player = new Player(pseudo, filiere);
        this.players[nbPlayer] = player;
        System.out.println("Pseudo : " + pseudo + "     Filiere : " + filiere);
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

    public void autoDep(int nbPlayer) {

        for (int i = 0; i < 5; i++) {

            int[] intInput = {1, 2, 3};

            List<Combattant> combattants = new ArrayList<Combattant>();
            System.out.println("---------- " + this.zones[i].getName() + " ----------");
            for (int j = 0; j < intInput.length; j++) {
                System.out.println("-> " + Arrays.toString(this.players[nbPlayer].getCombattant().get(intInput[j] - 1 - j).getStats()));
                combattants.add(this.players[nbPlayer].getCombattant().get(intInput[j] - 1 - j));
                this.players[nbPlayer].getCombattant().remove(intInput[j] - 1 - j);
            }
            if (nbPlayer == 1) {
                this.zones[i].setCombattantP2(combattants);
            } else {
                this.zones[i].setCombattantP1(combattants);
            }

        }
    }

    public void depComb(int nbPlayer) {

        for (int i = 0; i < 5; i++) {

            int[] intInput;

            System.out.println("Zone :" + this.zones[i].getName());
            System.out.println("Choisir parmi ces combattants :");
            Combattant.affComb(this.players[nbPlayer].getCombattant());
            intInput = Utils.getZoneInput(5 - i - 1, this.players[nbPlayer].getCombattant().size());

            List<Combattant> combattants = new ArrayList<Combattant>();
            for (int j = 0; j < intInput.length; j++) {
                combattants.add(this.players[nbPlayer].getCombattant().get(intInput[j] - 1 - j));
                this.players[nbPlayer].getCombattant().remove(intInput[j] - 1 - j);
            }
            if (nbPlayer == 1) {
                this.zones[i].setCombattantP2(combattants);
            } else {
                this.zones[i].setCombattantP1(combattants);
            }

        }
    }

    public void depRes(int nbPlayer, List<Zone> destZones) {

        for (Zone zone : destZones) {

            int[] intInput;


            System.out.println("Zone :" + zone.getName());
            if (this.players[nbPlayer - 1].getReserviste().size() == 0) {
                System.out.println("Vous n'avez plus de réservistes");
            } else {
                System.out.println("Choisir parmi ces combattants :");
                Combattant.affComb(this.players[nbPlayer - 1].getReserviste());
                System.out.println("Deployer ? (n/ [1/2/...])");
                String[] input = Utils.scan.nextLine().split("/");
                if (!input[0].equals("n")) {
                    intInput = Arrays.asList(input).stream().mapToInt(Integer::parseInt).toArray();
                    Arrays.sort(intInput);
                    List<Combattant> combattants = new ArrayList<Combattant>();
                    for (int j = 0; j < intInput.length; j++) {
                        combattants.add(this.players[nbPlayer - 1].getReserviste().get(intInput[j] - 1 - j));
                        this.players[nbPlayer - 1].getReserviste().remove(intInput[j] - 1 - j);
                    }
                    if (nbPlayer == 1) {
                        zone.combattantP1.addAll(combattants);
                    } else {
                        zone.combattantP2.addAll(combattants);
                    }
                }

            }
        }
    }


    public void redeploy(Zone zone, List<Zone> destZones, int NbPlayer) {
        List<Combattant> survivors;

        if (NbPlayer == 1 && zone.getCombattantP1().size() > 0 || NbPlayer == 2 && zone.getCombattantP2().size() > 0) {

            if (NbPlayer == 1) {
                survivors = zone.combattantP1;
            } else {
                survivors = zone.combattantP2;
            }

            int cpt2 = 0;
            int cpt1 = 0;
            for (int j = 0; j < survivors.size() + cpt2; j++) {
                System.out.println("Voici les survivants :");
                Combattant.affComb(survivors);
                System.out.println("Redeployer " + Arrays.toString(survivors.get(cpt1).getStats()) + " ? (o/n)");
                String t = Utils.scan.nextLine();
                if (t.equals("o")) {
                    System.out.println("Saisir la zone et la strategie pour le combattant :");
                    String[] input = Utils.scan.nextLine().split("/");
                    System.out.println("Strategie : " + survivors.get(cpt1).getStrategie());
                    survivors.get(cpt1).setStrategie(input[1]);
                    System.out.println("Strategie : " + survivors.get(cpt1).getStrategie());
                    if (NbPlayer == 1) {
                        destZones.get(Integer.parseInt(input[0]) - 1).combattantP1.add(survivors.get(cpt1));
                    } else {
                        destZones.get(Integer.parseInt(input[0]) - 1).combattantP2.add(survivors.get(cpt1));
                    }
                    survivors.remove(0);
                    cpt2++;
                } else {
                    cpt1++;
                }
            }
        } else {
            Utils.broadcast(this.players[NbPlayer - 1].getPseudo() + " n'a plus de combattant en vie sur " + zone.getName());
        }


    }


    public void autoRes(int nbPlayer) {

        int[] intInput = {1, 2, 3, 4, 5};
        List<Combattant> combattants = new ArrayList<Combattant>();
        for (int j = 0; j < intInput.length; j++) {
            combattants.add(this.players[nbPlayer].getCombattant().get(intInput[j] - 1 - j));
            this.players[nbPlayer].getCombattant().remove(intInput[j] - 1 - j);
        }
        this.players[nbPlayer].setReserviste(combattants);
    }

    public void choixRes(int nbPlayer) {

        int[] intInput;
        System.out.println("Veuillez choisir 5 réservistes parmi ces combattants :");
        Combattant.affComb(this.players[nbPlayer].getCombattant());
        intInput = Utils.getResInput();
        List<Combattant> combattants = new ArrayList<Combattant>();
        for (int j = 0; j < intInput.length; j++) {
            combattants.add(this.players[nbPlayer].getCombattant().get(intInput[j] - 1 - j));
            this.players[nbPlayer].getCombattant().remove(intInput[j] - 1 - j);
        }
        this.players[nbPlayer].setReserviste(combattants);
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
        } while (this.zones[resp].getIsFinish() != null);
        return resp;
    }

}


