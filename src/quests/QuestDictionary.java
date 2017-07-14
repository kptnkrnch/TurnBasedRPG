package quests;

import java.util.ArrayList;

public class QuestDictionary {
	public static ArrayList<Quest> quests = new ArrayList<Quest>();
	
	public static void AddQuest(Quest quest) {
		quests.add(quest);
	}
	
	public static Quest GetQuestByIndex(int index) {
		return quests.get(index);
	}
	
	public static Quest GetQuestByID(int questID) {
		Quest quest = null;
		for (int i = 0; i < quests.size(); i++) {
			if (quests.get(i).id == questID) {
				quest = quests.get(i);
			}
		}
		return quest;
	}
	
	public static void ActivateQuest(int questID) {
		if (questID >= 0) {
			Quest temp = quests.get(questID - 1);
			if (temp.id == questID) {
				temp.Activate();
				quests.set(questID - 1, temp);
			} else {
				for (int i = 0; i < quests.size(); i++) {
					temp = quests.get(i);
					if (temp.id == questID) {
						temp.Activate();
						quests.set(i, temp);
					}
				}
			}
		}
	}
}
