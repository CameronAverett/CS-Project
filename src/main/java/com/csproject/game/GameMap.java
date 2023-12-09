package com.csproject.game;

import com.csproject.exceptions.game.GameMapCreationException;

import java.util.*;

// Class to handle boolean conditions that map configurations can have.
interface GameMapCondition {
	boolean call(int x, int y);
}

// Class to handle the map positions
record Coordinate(int x, int y) {
	public boolean equals(Coordinate other) {
		return x == other.x && y == other.y;
	}
}

public class GameMap {

	private static final int DEFAULT_WIDTH = 13;
	private static final int DEFAULT_HEIGHT = 13;

	private static final String SEPARATOR = " ";
	private static final String NO_ROOM_SYMBOL = " ";
	private static final String BASIC_ROOM_SYMBOL = "#";
	private static final String ENTRANCE_SYMBOL = "-";
	private static final String EXIT_SYMBOL = "+";
	private static final String PLAYER_SYMBOL = "*";

	private static final int NO_ROOM = 0;
	private static final int BASIC_ROOM = 1;
	private static final int ENTRANCE = 2;
	private static final int EXIT = 3;

	private static final Random random = Game.getRandom();

	private int[][] currentMap;
	private HashMap<Coordinate, GameRoom> mapRooms;

	private Coordinate location;
	private Coordinate entrance;
	private Coordinate exit;
	
	public GameMap () {
		createGameMap();
	}

	// Method that is called whenever a new map needs to be created.
	public void createGameMap() {
		double difficulty = Game.getInstance().getDifficulty();

		System.out.printf("%nCreating game map with difficulty %.2f.%n", difficulty);

		int width = (int)(DEFAULT_WIDTH * difficulty);
		int height = (int)(DEFAULT_HEIGHT * difficulty);
		currentMap = createMapLayout(width, height);

		switch(random.nextInt(4)) {
			case (0) -> entrance = new Coordinate(1, 0);
			case (1) -> entrance = new Coordinate(1, height - 1);
			case (2) -> entrance = new Coordinate(width - 2, 0);
			case (3) -> entrance = new Coordinate(width - 2, height - 1);
			default -> throw new GameMapCreationException("Setting entrance location");
		}

		exit = new Coordinate(random.nextInt(width), random.nextInt(height));
		while (getRoomType(exit) != BASIC_ROOM && getDistance(entrance, exit) <= width * 0.6) {
			exit = new Coordinate(exit.x(), random.nextInt(height));
		}

		location = entrance;
		currentMap[entrance.x()][entrance.y()] = ENTRANCE;
		currentMap[exit.x()][exit.y()] = EXIT;

		mapRooms = createRooms();
	}

	// Method to determine the layout of the map.
	private int[][] createMapLayout(int width, int height) {
		List<GameMapCondition> conditions = new ArrayList<>();
		switch (random.nextInt(5)) {
			case (0) -> {
				conditions.add((x, y) -> x % 4 == 0);
				conditions.add((x, y) -> y % 6 == 0);
			}
			case (1) -> {
				conditions.add((x, y) -> x == width / 2);
				conditions.add((x, y) -> y == height / 2);
				conditions.add((x, y) -> x == y);
				conditions.add((x, y) -> x == y - 1);
				conditions.add((x, y) -> x == y + 1);
				conditions.add((x, y) -> x + y == height - 1);
				conditions.add((x, y) -> x + y == height - 2);
				conditions.add((x, y) -> x + y == height);
			}
			case (2) -> {
				conditions.add((x, y) -> x == 0);
				conditions.add((x, y) -> y == 0);
				conditions.add((x, y) -> x == width - 1);
				conditions.add((x, y) -> y == height - 1);
				conditions.add((x, y) -> x == 2);
				conditions.add((x, y) -> y == 2);
				conditions.add((x, y) -> x == width - 3);
				conditions.add((x, y) -> y == height - 3);
			}
			case (3) -> {
				conditions.add((x, y) -> x == 1);
				conditions.add((x, y) -> x == width - 2);
				conditions.add((x, y) -> x == width / 3);
				conditions.add((x, y) -> y == height / 3);
				conditions.add((x, y) -> x == (width / 3) * 2);
				conditions.add((x, y) -> y == (height / 3) * 2);
			}
			case (4) -> {
				conditions.add((x, y) -> x < width / 3 && y <= height / 3);
				conditions.add((x, y) -> x > (width - width / 3 - 1) && x < width && y <= height / 3);
				conditions.add((x, y) -> x < width / 3 && y > (height - height / 3 - 2));
				conditions.add((x, y) -> x > (width - width / 3 - 1) && y > (height - height / 3 - 2));
				conditions.add((x, y) -> x == 2);
				conditions.add((x, y) -> y == 2);
				conditions.add((x, y) -> x == width - 3);
				conditions.add((x, y) -> y == height - 3);
			}
			default -> throw new GameMapCreationException("Creating map layout");
		}
		return createMapLayout(width, height, conditions);
	}

	// Method that create the map given the desired width and height and a list of map conditions.
	private int[][] createMapLayout(int width, int height, List<GameMapCondition> conditions) {
		int[][] map = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				boolean meetsCondition = false;
				for (GameMapCondition condition : conditions) {
					if (condition.call(x, y)) {
						meetsCondition = true;
						break;
					}
				}
				map[x][y] = !meetsCondition ? NO_ROOM : BASIC_ROOM;
			}
		}
		return map;
	}

	// Method that creates the room hashmap from the created map layout.
	private HashMap<Coordinate, GameRoom> createRooms() {
		HashMap<Coordinate, GameRoom> rooms = new HashMap<>();
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				Coordinate coordinate = new Coordinate(x, y);
				int roomType = getRoomType(coordinate);
				if (roomType == NO_ROOM) continue;
				rooms.put(coordinate, roomType == BASIC_ROOM ?
						new GameRoom() : GameRoom.createEmptyRoom());
			}
		}
		return rooms;
	}

	// Calculates the distance between 2 points on the map.
	private double getDistance(Coordinate a, Coordinate b) {
		double left = Math.pow(b.x() - a.x(), 2);
		double right = Math.pow(b.y() - a.y(), 2);
		return Math.sqrt(left + right);
	}

	private int getWidth() {
		return currentMap.length;
	}

	private int getHeight() {
		return currentMap[0].length;
	}

	public Coordinate getLocation() {
		return location;
	}

	public void setLocation(Coordinate location) {
		this.location = location;
	}

	public Coordinate getEntrance() {
		return entrance;
	}

	public Coordinate getExit() {
		return exit;
	}

	// Determines if the coordinate is not in an empty room or off of the map
	public boolean isRoom(Coordinate coordinate) {
		if (coordinate.x() < 0 || coordinate.x() >= getWidth()) return false;
		if (coordinate.y() < 0 || coordinate.y() >= getHeight()) return false;
		return getRoomType(coordinate) != NO_ROOM;
	}

	private int getRoomType(Coordinate coordinate) {
		return currentMap[coordinate.x()][coordinate.y()];
	}

	public boolean inEntrance() {
		return location.equals(entrance);
	}

	public boolean inExit() {
		return location.equals(exit);
	}

	public GameRoom getCurrentRoom() {
		return mapRooms.get(location);
	}

	// Method to display the map. Is called before all player navigation actions.
	public void displayMap() {
		System.out.printf(
				"""
    
				---------------
				| Map Legend  |
				---------------
				| Entrance: %s |
				| Room:     %s |
				| Exit:     %s |
				| Player:   %s |
				---------------
				
				""",
				ENTRANCE_SYMBOL,
				BASIC_ROOM_SYMBOL,
				EXIT_SYMBOL,
				PLAYER_SYMBOL
		);

		StringBuilder mapDisplay = new StringBuilder();
		mapDisplay.append("--".repeat(getWidth() + 1)).append("-");
		mapDisplay.append(System.lineSeparator());
		for (int y = 0; y < getHeight(); y++) {
			StringBuilder mapRow = new StringBuilder("| ");
			for (int x = 0; x < getWidth(); x++) {
				Coordinate coordinate = new Coordinate(x, y);

				String symbol;
				switch (getRoomType(new Coordinate(x, y))) {
					case (NO_ROOM) -> symbol = NO_ROOM_SYMBOL;
					case (BASIC_ROOM) -> symbol = BASIC_ROOM_SYMBOL;
					case (ENTRANCE) -> symbol = ENTRANCE_SYMBOL;
					case (EXIT) -> symbol = EXIT_SYMBOL;
					default -> symbol = PLAYER_SYMBOL;
				}
				if (coordinate.equals(location)) {
					symbol = PLAYER_SYMBOL;
				}

				mapRow.append(symbol);
				mapRow.append(SEPARATOR);
			}
			mapRow.append("|");
			mapDisplay.append(mapRow);
			mapDisplay.append(System.lineSeparator());
		}
		mapDisplay.append("--".repeat(getWidth() + 1)).append("-");
		mapDisplay.append(System.lineSeparator());
		System.out.println(mapDisplay);
	}
}
