package com.csproject.game;

import com.csproject.character.monster.Monster;
import com.csproject.character.monster.MonsterFactory;

import java.util.Random;

public class GameRoom {

	private static final String SLIME = "Slime";

	private static final Random random = Game.getRandom();

	private final Monster monster;

	private GameRoom(boolean empty) {
		double difficulty = empty ? 0.0 : Game.getInstance().getDifficulty();
		this.monster = createMonster(difficulty);
	}
	
	public GameRoom() {
		this(false);
	}

	private Monster createMonster(double difficulty) {
		double roll = random.nextDouble(100) * difficulty;
		if (roll < 70) return null;

		String[] monsterTypes = {SLIME};
		String monsterClass = monsterTypes[random.nextInt(monsterTypes.length)];
		return MonsterFactory.get(monsterClass, (int) Math.ceil(difficulty));
	}

	public Monster getMonster() {
		return monster;
	}

	public static GameRoom createEmptyRoom() {
		return new GameRoom(true);
	}
}
