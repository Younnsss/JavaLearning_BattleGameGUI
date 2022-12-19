package models;

import java.util.List;

public class Defensive implements Strategie {

    @Override
    public void action(Combattant comb, List<Combattant> allies, List<Combattant> enemies) {
        Utils.heal(comb,allies,enemies);
    }


}
