package quests;

import items.Item;

import java.io.File;
import java.util.Scanner;

public class QuestLoader {
	
	public static void LoadQuests() {
		try {
			Scanner scan = new Scanner(new File("res/dictionaries/QuestDictionary.dict"));
			long linenumber = 0;
			while (scan.hasNextLine()) {
				String line = scan.nextLine().toLowerCase();
				linenumber++;
				if (line.equals("beginquest:")) {
					Quest quest = new Quest();
					while (!line.equals("endquest:")) {
						line = scan.nextLine();
						linenumber++;
						if (line.equalsIgnoreCase("endquest:")) {
							break;
						} else {
							line = line.replace(':', ' ');
							Scanner parameter = new Scanner(line);
							String pname = parameter.next();
							pname = pname.toLowerCase();
							
							float fvalue = -1;
							int ivalue = -1;
							String svalue = "";
							
							switch (pname) {
							case "id":
								ivalue = parameter.nextInt();
								quest.id = ivalue;
								break;
							case "givernpcid":
								ivalue = parameter.nextInt();
								quest.giverNPCID = ivalue;
								break;
							case "requireditems":
								svalue = parameter.next();
								svalue = svalue.replace(" ", "");
								svalue = svalue.replace(",", " ");
								parameter.close();
								parameter = new Scanner(svalue);
								
								while (parameter.hasNextInt()) {
									ivalue = parameter.nextInt();
									if (ivalue == -1) {
										break;
									}
									quest.requiredItems.add(ivalue);
								}
								break;
							case "requireditemcount":
								svalue = parameter.next();
								svalue = svalue.replace(" ", "");
								svalue = svalue.replace(",", " ");
								parameter.close();
								parameter = new Scanner(svalue);
								
								while (parameter.hasNextInt()) {
									ivalue = parameter.nextInt();
									if (ivalue == -1) {
										break;
									}
									quest.requiredItemCount.add(ivalue);
								}
								break;
							case "currentitems":
								svalue = parameter.next();
								svalue = svalue.replace(" ", "");
								svalue = svalue.replace(",", " ");
								parameter.close();
								parameter = new Scanner(svalue);
								
								while (parameter.hasNextInt()) {
									ivalue = parameter.nextInt();
									if (ivalue == -1) {
										break;
									}
									quest.currentItems.add(ivalue);
								}
								break;
							case "requiredconversations":
								svalue = parameter.next();
								svalue = svalue.replace(" ", "");
								svalue = svalue.replace(",", " ");
								parameter.close();
								parameter = new Scanner(svalue);
								
								while (parameter.hasNextInt()) {
									ivalue = parameter.nextInt();
									if (ivalue == -1) {
										break;
									}
									quest.requiredConversations.add(ivalue);
								}
								break;
							case "requiredlocationdiscoveries":
								svalue = parameter.next();
								svalue = svalue.replace(" ", "");
								svalue = svalue.replace(",", " ");
								parameter.close();
								parameter = new Scanner(svalue);
								
								while (parameter.hasNextInt()) {
									ivalue = parameter.nextInt();
									if (ivalue == -1) {
										break;
									}
									quest.requiredLocationDiscoveries.add(ivalue);
								}
								break;
							case "requiredenemytypes":
								svalue = parameter.next();
								svalue = svalue.replace(" ", "");
								svalue = svalue.replace(",", " ");
								parameter.close();
								parameter = new Scanner(svalue);
								
								while (parameter.hasNextInt()) {
									ivalue = parameter.nextInt();
									if (ivalue == -1) {
										break;
									}
									quest.requiredEnemyTypes.add(ivalue);
								}
								break;
							case "requiredkillcount":
								svalue = parameter.next();
								svalue = svalue.replace(" ", "");
								svalue = svalue.replace(",", " ");
								parameter.close();
								parameter = new Scanner(svalue);
								
								while (parameter.hasNextInt()) {
									ivalue = parameter.nextInt();
									if (ivalue == -1) {
										break;
									}
									quest.requiredKillCount.add(ivalue);
								}
								break;
							case "currentkillcount":
								svalue = parameter.next();
								svalue = svalue.replace(" ", "");
								svalue = svalue.replace(",", " ");
								parameter.close();
								parameter = new Scanner(svalue);
								
								while (parameter.hasNextInt()) {
									ivalue = parameter.nextInt();
									if (ivalue == -1) {
										break;
									}
									quest.currentKillCount.add(ivalue);
								}
								break;
							case "rewardexp":
								ivalue = parameter.nextInt();
								quest.rewardEXP = ivalue;
								break;
							case "rewardmoney":
								ivalue = parameter.nextInt();
								quest.rewardMoney = ivalue;
								break;
							case "rewarditems":
								svalue = parameter.next();
								svalue = svalue.replace(" ", "");
								svalue = svalue.replace(",", " ");
								parameter.close();
								parameter = new Scanner(svalue);
								
								while (parameter.hasNextInt()) {
									ivalue = parameter.nextInt();
									if (ivalue == -1) {
										break;
									}
									quest.rewardItems.add(ivalue);
								}
								break;
							case "subsequentquestid":
								ivalue = parameter.nextInt();
								quest.subsequentQuestID = ivalue;
								break;
							case "requiredpriorquestid":
								ivalue = parameter.nextInt();
								quest.requiredPriorQuestID = ivalue;
								break;
							case "turninnpcid":
								ivalue = parameter.nextInt();
								quest.turnInNPCID = ivalue;
								break;
							case "recommendedlevel":
								while (parameter.hasNext()) {
									svalue += parameter.next();
									svalue += " ";
								}
								svalue = svalue.trim();
								quest.recommendedLevel = svalue;
								break;
							case "minimumlevel":
								ivalue = parameter.nextInt();
								quest.minimumLevel = ivalue;
								break;
							case "maximumlevel":
								ivalue = parameter.nextInt();
								quest.maximumLevel = ivalue;
								break;
							case "questname":
								while (parameter.hasNext()) {
									svalue += parameter.next();
									svalue += " ";
								}
								svalue = svalue.trim();
								quest.questName = svalue;
								break;
							case "questdescription":
								while (parameter.hasNext()) {
									svalue += parameter.next();
									svalue += " ";
								}
								svalue = svalue.trim();
								quest.questDescription = svalue;
								break;
							case "acquiredlocation":
								while (parameter.hasNext()) {
									svalue += parameter.next();
									svalue += " ";
								}
								svalue = svalue.trim();
								quest.acquiredLocation = svalue;
								break;
							case "turninlocation":
								while (parameter.hasNext()) {
									svalue += parameter.next();
									svalue += " ";
								}
								svalue = svalue.trim();
								if (!svalue.contains("-1")) {
									quest.turnInLocation = svalue;
								}
								break;
							case "isactive":
								svalue = parameter.next().toLowerCase();
								if (svalue.equals("true") || svalue.equals("yes")) {
									quest.isActive = true;
								} else if (svalue.equals("false") || svalue.equals("no")) {
									quest.isActive = false;
								} else {
									parameter.close();
									System.err.println("QuestLoader: An error has occurred pulling boolean value from quest dictionary file.");
									throw new Exception();
								}
								break;
							case "innerquests":
								svalue = parameter.next();
								svalue = svalue.replace(" ", "");
								svalue = svalue.replace(",", " ");
								parameter.close();
								parameter = new Scanner(svalue);
								
								while (parameter.hasNextInt()) {
									ivalue = parameter.nextInt();
									if (ivalue == -1) {
										break;
									}
									quest.innerQuestIDs.add(ivalue);
								}
								break;
							case "isprimaryquest":
								svalue = parameter.next().toLowerCase();
								if (svalue.equals("true") || svalue.equals("yes")) {
									quest.isPrimaryQuest = true;
								} else if (svalue.equals("false") || svalue.equals("no")) {
									quest.isPrimaryQuest = false;
								} else {
									parameter.close();
									System.err.println("QuestLoader: An error has occurred pulling boolean value from quest dictionary file.");
									throw new Exception();
								}
								break;
							case "isinnerquest":
								svalue = parameter.next().toLowerCase();
								if (svalue.equals("true") || svalue.equals("yes")) {
									quest.isInnerQuest = true;
								} else if (svalue.equals("false") || svalue.equals("no")) {
									quest.isInnerQuest = false;
								} else {
									parameter.close();
									System.err.println("QuestLoader: An error has occurred pulling boolean value from quest dictionary file.");
									throw new Exception();
								}
								break;
							case "iscomplete":
								svalue = parameter.next().toLowerCase();
								if (svalue.equals("true") || svalue.equals("yes")) {
									quest.isComplete = true;
								} else if (svalue.equals("false") || svalue.equals("no")) {
									quest.isComplete = false;
								} else {
									parameter.close();
									System.err.println("QuestLoader: An error has occurred pulling boolean value from quest dictionary file.");
									throw new Exception();
								}
								break;
							default:
								break;
							}
							parameter.close();
						}
					}
					//System.out.println("Quest Name: " + quest.questName);
					//System.out.println("Quest Description: " + quest.questDescription);
					//System.out.println("Acquired Location: " + quest.acquiredLocation);
					QuestDictionary.AddQuest(quest);
				} else if (line.equals("endquest:")) {
					System.err.println("QuestLoader: A misplaced endquest: has occurred on line: " + linenumber);
					scan.close();
					throw new Exception();
				} else {
					scan.close();
					throw new Exception();
				}
			}
			scan.close();
			
			for (int i = 0; i < QuestDictionary.quests.size(); i++) {
				Quest current = QuestDictionary.GetQuestByIndex(i);
				for (int n = 0; n < current.innerQuestIDs.size(); n++) {
					int currentQuestID = current.innerQuestIDs.get(n);
					current.innerQuests.add(QuestDictionary.GetQuestByID(currentQuestID));
				}
			}
			
		} catch (Exception e) {
			System.err.println("QuestLoader: An error has occurred while loading the item dictionary.");
			e.printStackTrace();
		}
	}
}
