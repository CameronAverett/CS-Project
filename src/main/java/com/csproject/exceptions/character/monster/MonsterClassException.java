package com.csproject.exceptions.character.monster;

public class MonsterClassException extends RuntimeException {
    public MonsterClassException(String monsterClass) {
        super(String.format("Invalid monster class: %s", monsterClass));
    }
}
