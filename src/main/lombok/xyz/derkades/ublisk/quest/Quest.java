package xyz.derkades.ublisk.quest;

public abstract class Quest {

	public static final Quest[] QUESTS = {

	};

	public abstract String getName();

	public abstract int getLevel();

	public abstract int getRewardExp();

	public int getLifeCrystalReward() {
		return 0;
	}

	public abstract QuestStep[] getQuestSteps();

	public static Quest fromString(final String questName){
		for (final Quest quest : QUESTS){
			if (quest.getName().equals(questName)){
				return quest;
			}
		}
		return null;
	}

}
