package models;

import java.util.List;

public class Aleatoire implements Strategie {


    @Override
    public void action(Combattant comb, List<Combattant> allies, List<Combattant> enemies) {
        
        if (Math.round(Math.random() * 1000) >= 500) {
            Utils.attack(comb,allies,enemies);
        } else {
            Utils.heal(comb,allies,enemies);
        }
    }

}
