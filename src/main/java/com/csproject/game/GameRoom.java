package com.csproject.game;

import com.csproject.character.Character;
import com.csproject.character.monster.Monster;
import com.csproject.character.monster.MonsterFactory;

import java.util.Random;

public class GameRoom {

	private static final String SLIME = "Slime";

	private static final Random random = Game.getRandom();

	private final Monster monster;
	
	public GameRoom() {
		double difficulty = Game.getInstance().getDifficulty();
		this.monster = createMonster(difficulty);
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
}
