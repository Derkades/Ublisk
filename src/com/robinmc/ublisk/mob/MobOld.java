package com.robinmc.ublisk.mob;

@Deprecated
public enum MobOld {
	
	/*
	 * Fields marked with [something] are optional
	 * 
	 * Mob area
	 *   List of Radius
	 *     Spawn location x (integer!)
	 *     [Max spawn y (integer!)]
	 *     Spawn location z (integer!)
	 *     Diameter for the previously mentioned x and y
	 * Entity type
	 * Level
	 * Health (1.5 is one and a half heart)
	 * Minimum XP given when killed
	 * Maximum XP given when killed
	 * Max spawns (mobs outside of area will also count!)
	 * Name
	 * Spawn rate (4 means 1 every 4 seconds. NOTE: If multiple areas are specified, 4 will mean [#areas] zombies every 4 second, one in each area)
	 * Gold drop level
	 * Mob drops
	 *   Item
	 *   Chance
	 *	OR
	 *	 Item
	 *	 Minimum
	 *	 Maximum
	 *
	 * NOTE: Do not create multiple mobs with the same level and name
	 */
	
	/*SHEEP_LEVEL_1(new MobArea(
			new Radius(27, -50, 5), // Introduction
			new Radius(29, -45, 2), // Introduction
			new Radius(135, 85, 10)), // Route from Glaenor to Rhocus
			new Sheep(), 1, 1.5, 0, 4, 10, "Sheep", 10, GoldDrop.LEVEL1,
			new MobDrop(new ItemStack(Material.WOOL), 70)),
	CHICKEN(new MobArea(
			new Radius(66, -40, 60)), //Introduction
			new Chicken(), 1, 0.5, 0, 2, 20, "Chicken", 5, GoldDrop.LEVEL1,
			new MobDrop(new ItemStack(Material.FEATHER), 30)),
	RUINS_DWELLER(new MobArea(
			new Radius(221, -23, 20)), //Ruins near Glaenor forest
			new ZombieVillager().withProfession(Profession.FARMER), 4, 10, 2, 7, 7, "Ruins Dweller", 10, GoldDrop.LEVEL3,
			new MobDrop(new ItemStack(Material.ROTTEN_FLESH), 0, 2)),
	ZOMBIE_LEVEL_3(new MobArea(
			new Radius(160, 89, 100), //TODO Merge into one big radius
			new Radius(214, -66, 60),
			new Radius(111, 19, 50)),
			new Zombie(), 3, 7, 0, 5, 50, "Zombie", 5, GoldDrop.LEVEL2),
	RHOCUS_ZOMBIE(new MobArea(
			new Radius(280, 90, 318, 60)),
			new Zombie(), 15, 15, 5, 15, 50, "Rhocus Zombie", 5, GoldDrop.LEVEL3,
			new MobDrop(new ItemStack(Material.ROTTEN_FLESH), 0, 2),
			new MobDrop(new ItemStack(Material.ROTTEN_FLESH), 2, 3)),
	RHOCUS_PIG(new MobArea(
			new Radius(148, 90, 261, 4)),
			new Pig(), 3, 3.5, 2, 4, 6, "Rhocus Pig", 5, GoldDrop.LEVEL1,
			new MobDrop(new ItemStack(Material.PORK), 0, 2),
			new MobDrop(new ItemStack(Material.BONE), 1, 2));
	
	private Radius[] area;
	private MobType type;
	private int level;
	private double health;
	private int minExp;
	private int maxExp;
	private int maxSpawn;
	private String name;
	private double rate;
	private GoldDrop gold;
	private MobDrop[] drops;

	Mob(MobArea area, MobType type, int level, double health, int minExp, int maxExp, int maxSpawn, String name, double rate, GoldDrop gold, MobDrop... drops){
		this.area = area.getRadius();
		this.type = type;
		this.level = level;
		this.health = health;
		this.minExp = minExp;
		this.maxExp = maxExp;
		this.maxSpawn = maxSpawn;
		this.name = name;
		this.rate = rate;
		this.gold = gold;
		this.drops = drops;
	}
	
	public Radius[] getAreas(){
		return area;
	}
	
	public MobType getMobType(){
		return type;
	}
	
	public EntityType getEntityType(){
		return type.getEntityType();
	}
	
	public int getLevel(){
		return level;
	}
	
	public double getHealth(){
		return health;
	}
	
	public int getXP(){
		return Random.getRandomInteger(minExp, maxExp);
	}
	
	public int getMaxSpawns(){
		return maxSpawn;
	}
	
	public String getName(){
		return name;
	}
	
	public double getSpawnRate(){
		return rate;
	}
	
	public GoldDrop getGoldDrop(){
		return gold;
	}
	
	public MobDrop[] getDrops(){
		return drops;
	}
	
	public String getDisplayName(){
		return DARK_AQUA + name + DARK_GRAY + " [" + DARK_GREEN + level + DARK_GRAY + "]";
	}
	
	public boolean hasReachedMaxSpawning(){
		int count = 0;
		
		for (LivingEntity entity : Var.WORLD.getLivingEntities()){
			try {
				Mob mob = getMob(entity);
				if (mob.getName() == this.getName() &&
						mob.getLevel() == this.getLevel()){
					count++;
				}
			} catch (MobNotFoundException e) {}
		}
		
		return count >= this.getMaxSpawns();
	}

	public static void removeMobs(){
		for (Item item: Var.WORLD.getEntitiesByClass(Item.class))
			item.remove();
		List<String> mobNames = new ArrayList<String>();
		for (Mob mob : Mob.values()) mobNames.add(mob.getDisplayName());
		for (Entity entity : Var.WORLD.getEntities()){
			if (mobNames.contains(entity.getName())){
				entity.remove();
			}
		}
	}
	
	
	public static Mob getMob(Entity entity) throws MobNotFoundException {
		for (Mob mob : Mob.values()){
			String name = DARK_AQUA + mob.getName() + DARK_GRAY + " [" + DARK_GREEN + mob.getLevel() + DARK_GRAY + "]";
			if (entity.getName().equals(name)){
				return mob;
			}
		}
		throw new MobNotFoundException();
	}
	
	public static boolean containsEntityType(EntityType type){
		for (Mob mob : Mob.values()){
			if (mob.getEntityType() == type){
				return true;
			}
		}
		return false;
	}
	
	public static void startMobSpawning(){
		SpawnMob.spawnMobs();
	}*/

}
