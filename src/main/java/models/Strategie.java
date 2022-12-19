package models;

import java.util.List;

public interface Strategie {
	public void action(Combattant comb, List<Combattant> allies, List<Combattant> enemies);
}
