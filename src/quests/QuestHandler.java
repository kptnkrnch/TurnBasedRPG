package quests;

import java.util.ArrayList;

import locations.Location;
import locations.LocationDictionary;
import npc.NPCData;

public class QuestHandler {
	
	public static ArrayList<Integer> activeQuests = new ArrayList<Integer>();
	
	public static void UpdateQuestStatuses() {
		for (int i = 0; i < QuestDictionary.quests.size(); i++) {
			Quest temp = QuestDictionary.GetQuestByIndex(i);
			
			if (temp.isActive) {
				
				//Requirements section
				boolean itemRequirement = false;
				boolean conversationRequirement = false;
				boolean locationRequirement = false;
				boolean killRequirement = false;
				//ITEMS
				if (temp.requiredItems.size() > 0) {
					int itemrequirementsmet = 0;
					for (int n = 0; n < temp.requiredItemCount.size(); n++) {
						int currentCount = temp.currentItems.get(n);
						int requiredCount = temp.requiredItemCount.get(n);
						if (currentCount >=  requiredCount) {
							itemrequirementsmet++;
						}
					}
					if (itemrequirementsmet == temp.requiredItems.size()) {
						itemRequirement = true;
					} else {
						break;
					}
				}
				//CONVERSATIONS
				if (temp.requiredConversations.size() > 0) {
					int conversationRequirementsMet = 0;
					for (int n = 0; n < temp.requiredConversations.size(); n++) {
						int NPC_ID = temp.requiredConversations.get(n);
						NPCData data = NPCData.FindNPCDataByID(NPC_ID);
						for (int p = 0; p < data.npc_dialog.size(); p++) {
							npc.Dialog tempDialog = data.npc_dialog.get(p);
							if (tempDialog.requiredQuest == temp.id && tempDialog.hasBeenSaid) {
								conversationRequirementsMet++;
							}
						}
					}
					if (conversationRequirementsMet == temp.requiredConversations.size()) {
						conversationRequirement = true;
					}
				}
				//LOCATIONS
				if (temp.requiredLocationDiscoveries.size() > 0) {
					int locationRequirementsMet = 0;
					for (int n = 0; n < temp.requiredLocationDiscoveries.size(); n++) {
						int locationID = temp.requiredLocationDiscoveries.get(n);
						Location data = LocationDictionary.GetLocationByID(locationID);
						if (data != null) {
							if (data.hasBeenVisited == true) {
								locationRequirementsMet++;
							}
						}
					}
					if (locationRequirementsMet == temp.requiredLocationDiscoveries.size()) {
						locationRequirement = true;
					}
				}
				//KILLS
				if (temp.requiredEnemyTypes.size() > 0) {
					int killRequirementsMet = 0;
					for (int n = 0; n < temp.requiredKillCount.size(); n++) {
						int requiredKills = temp.requiredKillCount.get(n);
						int currentKills = temp.currentKillCount.get(n);
						
						if (currentKills == requiredKills) {
							killRequirementsMet++;
						}
					}
					if (killRequirementsMet == temp.requiredKillCount.size()) {
						killRequirement = true;
					}
				}
				
				//HANDLING SECTION
				if (itemRequirement && 
						conversationRequirement && 
						locationRequirement &&
						killRequirement) {

					if (temp.turnInNPCID == -1) {
						temp.isComplete = true;
						temp.isActive = false;
						QuestDictionary.ActivateQuest(temp.subsequentQuestID);
					} else if (temp.isTurnInConversationComplete) {
						temp.isComplete = true;
						temp.isActive = false;
						QuestDictionary.ActivateQuest(temp.subsequentQuestID);
					} 
				}
			}
		}
	}
	
}
