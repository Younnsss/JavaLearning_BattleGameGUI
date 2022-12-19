package models;

import java.util.List;

public class Offensive implements Strategie {

    @Override
    public void action(Combattant comb, List<Combattant> allies, List<Combattant> enemies) {
        Utils.attack(comb,allies,enemies);
    }


}
