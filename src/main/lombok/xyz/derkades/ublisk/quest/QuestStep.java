package xyz.derkades.ublisk.quest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import lombok.Getter;
import xyz.derkades.ublisk.DataFile;
import xyz.derkades.ublisk.quest.requirement.QuestRequirement;
import xyz.derkades.ublisk.utils.UPlayer;

public abstract class QuestStep {

	@Getter
	private final Quest quest;

	@Getter
	private final NPC npc;

	@Getter
	private final String name;

	private Consumer<UPlayer> onComplete;
	private final List<QuestRequirement> requirements;
	private final List<String> dialogue;
	private final List<String> onCompleteDialogue;
	private final List<String> completeDialogue;

	public QuestStep(final Quest quest, final NPC npc, final String name) {
		this.quest = quest;
		this.npc = npc;
		this.name = name;

		this.onComplete = null;
		this.requirements = new ArrayList<>();
		this.dialogue = new ArrayList<>();
		this.onCompleteDialogue = new ArrayList<>();
		this.completeDialogue = new ArrayList<>();
	}

	public boolean talk(final UPlayer player) {
		if (this.getCompleted(player)) {
			return this.sendDialogue(player, this.completeDialogue);
		}

		boolean completed = true;
		for (final QuestRequirement requirement : this.requirements) {
			if (!requirement.apply(player)) {
				completed = false;
			}
		}

		if (completed) {
			this.setCompleted(player, true);
			if (this.onComplete != null) {
				this.onComplete.accept(player);
			}
			return this.sendDialogue(player, this.onCompleteDialogue);
		} else {
			return this.sendDialogue(player, this.dialogue);
		}
	}

	public void require(final QuestRequirement requirement) {
		this.requirements.add(requirement);
	}

	public void onComplete(final Consumer<UPlayer> onComplete) {
		this.onComplete = onComplete;
	}

	public void addDialogue(final String dialogue) {
		this.dialogue.add(dialogue);
	}

	public void addOnCompleteDialogue(final String dialogue) {
		this.onCompleteDialogue.add(dialogue);
	}

	public void addCompleteDialogue(final String dialogue) {
		this.completeDialogue.add(dialogue);
	}

	private boolean sendDialogue(final UPlayer player, final List<String> dialogue) {
		if (dialogue.size() == 0) {
			return false;
		}

		for (final String message : this.dialogue) {
			player.sendMessage(message); // TODO Formatting
		}
		return true;
	}

	private boolean getCompleted(final UPlayer player) {
		return DataFile.QUESTS_2.getConfig().getBoolean("completed." + this.quest.getName() + ".step." + this.getName());
	}

	private void setCompleted(final UPlayer player, final boolean completed) {
		DataFile.QUESTS_2.getConfig().set("completed." + this.quest.getName() + ".step." + this.getName() + "." + player.getUniqueId(), completed);
	}

}
