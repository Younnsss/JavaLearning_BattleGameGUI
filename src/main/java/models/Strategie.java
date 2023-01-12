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
 *
 * @Interface: Strategie;
 *
 * @Function: Cela consiste comme interface pour la classe Offensive - ATTAQUER
 *            (@Methode: attack), Defensive - SOIGNER (@Methode: heal) et
 *            Aleatoire - ATTAQUER OU SOIGNER;
 *
 * @ParamètreDentrée: Le liste des combattant. le combattant qui fait l'action
 *                    et le combattant qui reçoit l'aciton;
 *
 * @Return: Renvoie rien (void);
 *
 */
public interface Strategie {
	public void action(Combattant comb, List<Combattant> allies, List<Combattant> enemies);
}
