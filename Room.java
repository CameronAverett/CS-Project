package com.csproject.game;

import java.awt.Point;
import java.util.HashMap;
import java.util.Random;

public class Room {
	private HashMap<Point, String> rooms = new HashMap<Point, String>();
	private String[] monsters = {"slime"}; // more monsters can be added later on 
	
	
	public Room() {}
	
	public HashMap<Point, String> createRooms (int[][] map, double difficulty) {
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				
				switch(map[x][y]) {
					// case(0) room is empty
					case(1) -> rooms.put(new Point(x, y), encounter(difficulty));
					case(2) -> rooms.put(new Point(x, y),"entrance");
					case(9) -> rooms.put(new Point(x, y),"exit");
				}
			}
		}
		return rooms;
	}
	
	public String encounter(double difficulty) {
		Random random = new Random();
		String roomName;
		double num = random.nextInt(100) * difficulty; // difficulty will become a multiplier on the chance a monster is in the room
		
		if (num >= 70) {   // 3/10 chance a monster is in the room
			roomName = monsters[random.nextInt(monsters.length)];
		}
		else {
			roomName = "room";
		}
		return roomName;
	}
	
}
