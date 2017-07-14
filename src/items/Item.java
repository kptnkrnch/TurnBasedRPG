package items;

public class Item {
	public int id;
	public String name;
	public int type;
	public int rarity;
	public int armor;
	public int damage;
	public float criticalChance;
	public float criticalDamage;
	public int freezingDamage;
	public float freezingChance;
	public int poisonDamage;
	public float poisonChance;
	public int fireDamage;
	public float fireChance;
	public float stunChance;
	public int stunTime;
	public float slowChance;
	public int slowTime;
	public float dodgeChance;
	public int attackSpeed;
	public int healingAmount;
	public float healingChance;
	public int maxUses;
	public int useCount;
	public int fireResist;
	public int freezingResist;
	public int poisonResist;
	public float cureBurningChance;
	public float cureFreezingChance;
	public float curePoisonChance;
	public int abilityCooldownTime; //milliseconds
	public int currentCooldownTime; //milliseconds
	public int inventoryPosition;
	public boolean isDroppable;
	public boolean isSellable;
	public boolean isQuestItem;
	public boolean isBanked;
	public boolean isSold;
	public boolean isEquipped;
	public int value;
	
	public Item() {
		id = -1;
		name = "";
		type = -1;
		rarity = 1;
		armor = 0;
		damage = 0;
		criticalChance = 0;
		criticalDamage = 0;
		freezingDamage = 0;
		freezingChance = 0;
		poisonDamage = 0;
		poisonChance = 0;
		fireDamage = 0;
		fireChance = 0;
		stunChance = 0;
		stunTime = 0;
		slowChance = 0;
		slowTime = 0;
		dodgeChance = 0;
		attackSpeed = 0;
		healingAmount = 0;
		healingChance = 0;
		maxUses = 0;
		useCount = 0;
		fireResist = 0;
		freezingResist = 0;
		poisonResist = 0;
		cureBurningChance = 0;
		cureFreezingChance = 0;
		curePoisonChance = 0;
		abilityCooldownTime = 0;
		currentCooldownTime = 0;
		inventoryPosition = -1;
		isDroppable = false;
		isQuestItem = false;
		isBanked = false;
		isSold = false;
		isEquipped = false;
		value = 0;
	}
	
	public Item(Item source) {
		id = source.id;
		name = source.name;
		type = source.type;
		rarity = source.rarity;
		armor = source.armor;
		damage = source.damage;
		criticalChance = source.criticalChance;
		criticalDamage = source.criticalDamage;
		freezingDamage = source.freezingDamage;
		freezingChance = source.freezingChance;
		poisonDamage = source.poisonDamage;
		poisonChance = source.poisonChance;
		fireDamage = source.fireDamage;
		fireChance = source.fireChance;
		stunChance = source.stunChance;
		stunTime = source.stunTime;
		slowChance = source.slowChance;
		slowTime = source.slowTime;
		dodgeChance = source.dodgeChance;
		attackSpeed = source.attackSpeed;
		healingAmount = source.healingAmount;
		healingChance = source.healingChance;
		maxUses = source.maxUses;
		useCount = source.useCount;
		fireResist = source.fireResist;
		freezingResist = source.freezingResist;
		poisonResist = source.poisonResist;
		cureBurningChance = source.cureBurningChance;
		cureFreezingChance = source.cureFreezingChance;
		curePoisonChance = source.curePoisonChance;
		abilityCooldownTime = source.abilityCooldownTime;
		currentCooldownTime = source.currentCooldownTime;
		inventoryPosition = source.inventoryPosition;
		isDroppable = source.isDroppable;
		isQuestItem = source.isQuestItem;
		isBanked = source.isBanked;
		isSold = source.isSold;
		isEquipped = source.isEquipped;
		value = source.value;
	}
	
	public void IncreaseUseCount() {
		if (maxUses > 0) {
			useCount++;
		}
	}
}
