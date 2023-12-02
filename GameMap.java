package com.csproject.game;

import java.util.Random;

public class GameMap {
	
	public GameMap () {}
	
	public int[][] createGameMap() {
		return createGameMap(13, 13, 1.0);
	}
	
	public int[][] createGameMap(double difficulty) {
		return createGameMap(13, 13, difficulty);
	}

	public int[][] createGameMap(int width, int length, double difficulty) {
		width = (int)(width * difficulty);
		length = (int)(width * difficulty);
		int[][] map = new int[width][length];
		
		Random random = new Random();
		
		int entrX = 0, entrY = 0, exitX, exitY;
		
		switch(random.nextInt(5)) { // randomly choose between 5 maps
			case(0) -> map = gridPath(width,length);
			case(1) -> map = diamondPath(width,length);
			case(2) -> map = concentricPath(width,length);
			case(3) -> map = branchingPath(width,length);
			case(4) -> map = branchingPath(width,length);
		}
		switch(random.nextInt(4)) { // random generates entrance between 4 spots
			case(0):
				map[1][0] = 2;
				entrX = 1;
				entrY = 0;
				break;
			case(1):
				map[1][length-1] = 2;
				entrX = 1;
				entrY = length-1;
				break;
			case(2):
				map[width-2][0] = 2;
				entrX = width-2;
				entrY = 0;
				break;
			case(3):
				map[width-2][length-1] = 2;
				entrX = width-2;
				entrY = length-1;
				break;
		}
		
		exitX = random.nextInt(width);
		exitY = getCoord(entrX, entrY, exitX, map);
		
		map[exitX][exitY] = 9;
		
		// 0 = no room
		// 1 = room
		// 2 = entrance
		// 9 = exit
		
		return map;
	}
	
	int[][] gridPath(int width, int length) {
		int[][] map = new int[width][length];
		
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if (x%4==0 || y%6==0) {    // creates a a square shape with path every 4 and 6 spaces apart
					map[x][y] = 1;
				}
			}
		}
		return map;
		
	}
	
	int[][] diamondPath(int width, int length) {
		int[][] map = new int[width][length];
		
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if(x == width/2 || y == length/2) {  // creates a cross 
					map[x][y] = 1;
				}
				if(x == y || x == y-1|| x == y+1){ // left diagonal
					map[x][y] = 1;
				}
				if(x+y==length-1 || x+y==length-2 || x+y==length) { // right diagonal
					map[x][y] = 1;
				}
			}
		}
		return map;
	}
	
	int[][] concentricPath(int width, int length) {
		int[][] map = new int[width][length];

		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if (x==0||y==0||x==width-1||y==length-1) { // outer square
					map[x][y] = 1;
				}
				if (x==2||y==2||x==width-3||y==length-3) { // inner square
					map[x][y] = 1;
				}
			}
		}
		
		return map;
	}
	
	int[][] branchingPath(int width, int length) {
		int[][] map = new int[width][length];
		
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if (x==1|| x==width-2 || x==width/3||y==length/3||x==(width/3)*2||y==(length/3)*2) { // outer square
					map[x][y] = 1;
				}
			}
		}
		
		return map;
	}
	
	int[][] cornerPath(int width, int length) {
		int[][] map = new int[width][length];
		
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if (x<width/3 && y<=length/3) {						 // top left corner
					map[x][y] = 1;
				}
				if (x>(width-width/3-1) && x<width && y<=length/3) { // bottom left corner
					map[x][y] = 1;
				}
				if (x<width/3 && y>(length-length/3-2)) {  		    // top right corner
					map[x][y] = 1;
				}
				if (x>(width-width/3-1) && y>(length-length/3-2)) { // bottom left corner
					map[x][y] = 1;
				}
				if (x==2||y==2||x==width-3||y==length-3) { // inner square
					map[x][y] = 1;
				}
			}
		}
		
		return map;
	}	
	
	
	public int getCoord(int a, int b, int c, int[][] arr) {
		Random r = new Random();
		int d = r.nextInt(arr[0].length);
		
		if (arr[c][d]==1 && getDist(a,b,c,d)>arr.length*.6) {
			return d;
		}
		else {
			return getCoord(a, b, c, arr);
		}
		
	}
	
	public double getDist(int a, int b, int c, int d) {
		// sqrt ((x2-x1)^2 + (y2-y1)^2)  
		
		double leftSide = Math.pow(c-a,2);
		double rightSide = Math.pow(d-b,2);
		double dist = Math.sqrt(leftSide + rightSide);
		
		return dist;
	}

}
