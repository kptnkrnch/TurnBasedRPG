package entities;

import java.util.ArrayList;

import engine.Entity;
import engine.World;

public class NPC {
	
	public static boolean Speak(World world, Entity NPC) {
		if (NPC.dialog != null) {
			if (Dialog.NPC_ID != NPC.id) {
				Dialog.CreateDialog(world, NPC.dialog, NPC.id, NPC.x - 235, NPC.y - 140);
				return true;
			} else {
				return Dialog.UpdateDialog(world);
			}
		}
		return false;
	}
	
	public static Entity SetDialog(ArrayList<String> dialog, Entity NPC) {
		NPC.dialog = new ArrayList<String>(dialog);
		return NPC;
	}

}
