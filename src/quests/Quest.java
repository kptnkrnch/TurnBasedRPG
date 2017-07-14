package quests;

import java.util.ArrayList;

public class Quest {
	public int id;
	public int giverNPCID;
	public ArrayList<Integer> requiredItems;
	public ArrayList<Integer> requiredItemCount;
	public ArrayList<Integer> currentItems; //each time the players inventory is changed, if requiredItems or requiredItemCount are not empty, this is updated.
	public ArrayList<Integer> requiredConversations; //NPC IDs
	public ArrayList<Integer> requiredLocationDiscoveries; //map IDs
	public ArrayList<Integer> requiredEnemyTypes;
	public ArrayList<Integer> requiredKillCount;
	public ArrayList<Integer> currentKillCount;
	public int rewardEXP;
	public int rewardMoney;
	public ArrayList<Integer> rewardItems;
	public int subsequentQuestID; //-1 is none
	public int requiredPriorQuestID; //-1 is none
	public int turnInNPCID; //-1 is no NPC
	public boolean isTurnInConversationComplete;
	public String recommendedLevel; 
	public int minimumLevel; //-1 is none
	public int maximumLevel; //-1 is none
	public String questName;
	public String questDescription;
	public String acquiredLocation; //optional
	public String turnInLocation;
	public boolean isActive;
	public ArrayList<Integer> innerQuestIDs;
	public ArrayList<Quest> innerQuests;
	public boolean isPrimaryQuest;
	public boolean isInnerQuest;
	public boolean isComplete;
	
	public Quest() {
		id = -1;
		giverNPCID = -1;
		requiredItems = new ArrayList<Integer>();
		requiredItemCount = new ArrayList<Integer>();
		currentItems = new ArrayList<Integer>();
		requiredConversations = new ArrayList<Integer>();
		requiredLocationDiscoveries = new ArrayList<Integer>();
		requiredEnemyTypes = new ArrayList<Integer>();
		requiredKillCount = new ArrayList<Integer>();
		currentKillCount = new ArrayList<Integer>();
		rewardEXP = 0;
		rewardMoney = 0;
		rewardItems = new ArrayList<Integer>();
		subsequentQuestID = -1;
		requiredPriorQuestID = -1;
		turnInNPCID = -1;
		recommendedLevel = ""; 
		minimumLevel = -1;
		maximumLevel = -1;
		questName = "";
		questDescription = "";
		acquiredLocation = "";
		turnInLocation = "";
		isActive = false;
		innerQuestIDs = new ArrayList<Integer>();
		innerQuests = new ArrayList<Quest>();
		isPrimaryQuest = false;
		isInnerQuest = false;
		isComplete = false;
		isTurnInConversationComplete = false;
	}
	
	public boolean Activate() {
		if (isComplete == false) {
			isActive = true;
			return true;
		}
		return false;
	}
}
