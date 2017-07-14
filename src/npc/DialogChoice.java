package npc;

/**
 * Used for things such as stores or choosing a reward from a quest.
 * @author Joshua
 *
 */

public class DialogChoice {
	public String text;
	public int cost;
	public int givesItem;
	
	public DialogChoice() {
		text = "";
		cost = 0;
		givesItem = -1;
	}
}
