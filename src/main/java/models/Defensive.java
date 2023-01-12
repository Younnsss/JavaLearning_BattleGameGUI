package models;

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

public class Defensive implements Strategie {

    @Override
    public void action(Combattant comb, List<Combattant> allies, List<Combattant> enemies) {
        Utils.heal(comb, allies, enemies);
    }

}
