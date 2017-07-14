package npc;

import java.util.ArrayList;

public class Dialog {
	public int requiredQuest;
	public int givesQuest;
	public boolean isDefault;
	public boolean isFirst;
	public String dialogText;
	public int textPosition;
	public boolean hasBeenSaid;
	public ArrayList<Integer> initiatesPath;
	public ArrayList<Integer> entityPathIsFor;
	public DialogChoice choice;
	
	public Dialog() {
		requiredQuest = -1;
		givesQuest = -1;
		isDefault = false;
		isFirst = false;
		dialogText = "";
		textPosition = 0;
		hasBeenSaid = false;
		initiatesPath = new ArrayList<Integer>();
		entityPathIsFor = new ArrayList<Integer>();
		choice = new DialogChoice();
	}
}
