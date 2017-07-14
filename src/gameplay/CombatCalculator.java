package gameplay;

import items.ItemHandler;

import java.util.Random;

import engine.Entity;
import engine.EntityDictionary;
import engine.Main;
import exceptions.PlayerNotFoundException;

public class CombatCalculator {
	
	public static boolean CriticalHit = false;
	
	public static boolean IsCriticalHit() {
		return CriticalHit;
	}
	
	public static int CalculateDamage(Entity attacker, Entity defender) {
		int damage = 0;
		float critical_damage = 0;
		int defence = 0;
		
		if (!DodgeCalculator(defender)) {
			damage = attacker.c_attack;
			critical_damage = attacker.c_critical_damage;
			defence = defender.c_defence;
			
			Entity player = null;
			try {
				player = Main.world.GetEntity(Main.world.FindPlayer());
			} catch (PlayerNotFoundException e) {
			}
			
			if (attacker.type == EntityDictionary.PLAYER || (player != null && attacker.c_creatorID == player.id)) {
				damage += ItemHandler.GetEquipmentAttack();
			} else if (defender.type == EntityDictionary.PLAYER) {
				defence += ItemHandler.GetEquipmentDefence();
			}
			
			if (CriticalHitCalculator(attacker)) {
				damage += Math.round((float)damage * critical_damage);
				CriticalHit = true;
			} else {
				CriticalHit = false;
			}
			
			damage -= defence;
			
			if (damage <= 0) {
				damage = 1;
			}
		}
		
		return damage;
	}
	
	public static int CalculateCooldown(Entity attacker, int base_cooldown) {
		int cooldown = base_cooldown;
		
		cooldown = base_cooldown - (attacker.c_speed * 10);
		
		if (cooldown < base_cooldown / 2) {
			cooldown = base_cooldown / 2;
		}
		
		return cooldown;
	}
	
	public static boolean CriticalHitCalculator(Entity attacker) {
		Random random = new Random();
		int roll = random.nextInt(100) + 1;
		
		if (roll < attacker.c_critical_chance) {
			return true;
		}
		return false;
	}
	
	public static boolean DodgeCalculator(Entity defender) {
		Random random = new Random();
		int roll = random.nextInt(100) + 1;
		
		if (roll < defender.c_dodge_chance) {
			return true;
		}
		return false;
	}
	
}
