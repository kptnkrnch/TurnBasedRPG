package npc;

import java.util.ArrayList;

import engine.Entity;
import engine.Main;

public class NPCData {
	
	public int id;
	public ArrayList<npc.Dialog> npc_dialog;
	
	public NPCData() {
		id = -1;
	}
	
	public static NPCData FindNPCDataByID(int id) {
		NPCData data = null;
		
		for (int i = 0; i < Main.world.entities.size(); i++) {
			Entity temp = Main.world.GetEntity(i);
			if (temp != null && temp.npc_data != null && temp.npc_data.id == id) {
				data = temp.npc_data;
				return data;
			}
		}
		
		return data;
	}
}
