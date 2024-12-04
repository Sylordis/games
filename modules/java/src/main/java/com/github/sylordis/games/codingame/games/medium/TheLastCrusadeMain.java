package com.github.sylordis.games.codingame.games.medium;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.github.sylordis.commons.Direction;
import com.github.sylordis.games.codingame.games.medium.specific.LastCrusadeRoom;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class TheLastCrusadeMain {

	static Map<Integer, LastCrusadeRoom> roomsTypes;

	private static void setLastCrusadeRooms() {
		roomsTypes = new HashMap<>();
		// LastCrusadeRoom 1
		LastCrusadeRoom r = new LastCrusadeRoom();
		r.addInOut(Direction.NORTH, Direction.SOUTH);
		r.addInOut(Direction.WEST, Direction.SOUTH);
		r.addInOut(Direction.EAST, Direction.SOUTH);
		roomsTypes.put(1, r);
		// LastCrusadeRoom 2
		r = new LastCrusadeRoom();
		r.addInOut(Direction.WEST, Direction.EAST);
		r.addInOut(Direction.EAST, Direction.WEST);
		roomsTypes.put(2, r);
		// LastCrusadeRoom 3
		r = new LastCrusadeRoom();
		r.addInOut(Direction.NORTH, Direction.SOUTH);
		roomsTypes.put(3, r);
		// LastCrusadeRoom 4
		r = new LastCrusadeRoom();
		r.addInOut(Direction.NORTH, Direction.WEST);
		r.addInOut(Direction.EAST, Direction.SOUTH);
		roomsTypes.put(4, r);
		// LastCrusadeRoom 5
		r = new LastCrusadeRoom();
		r.addInOut(Direction.NORTH, Direction.EAST);
		r.addInOut(Direction.WEST, Direction.SOUTH);
		roomsTypes.put(5, r);
		// LastCrusadeRoom 6
		r = new LastCrusadeRoom();
		r.addInOut(Direction.WEST, Direction.EAST);
		r.addInOut(Direction.EAST, Direction.WEST);
		roomsTypes.put(6, r);
		// LastCrusadeRoom 7
		r = new LastCrusadeRoom();
		r.addInOut(Direction.NORTH, Direction.SOUTH);
		r.addInOut(Direction.EAST, Direction.SOUTH);
		roomsTypes.put(7, r);
		// LastCrusadeRoom 8
		r = new LastCrusadeRoom();
		r.addInOut(Direction.WEST, Direction.SOUTH);
		r.addInOut(Direction.EAST, Direction.SOUTH);
		roomsTypes.put(8, r);
		// LastCrusadeRoom 9
		r = new LastCrusadeRoom();
		r.addInOut(Direction.NORTH, Direction.SOUTH);
		r.addInOut(Direction.WEST, Direction.SOUTH);
		roomsTypes.put(9, r);
		// LastCrusadeRoom 10
		r = new LastCrusadeRoom();
		r.addInOut(Direction.NORTH, Direction.WEST);
		roomsTypes.put(10, r);
		// LastCrusadeRoom 11
		r = new LastCrusadeRoom();
		r.addInOut(Direction.NORTH, Direction.EAST);
		roomsTypes.put(11, r);
		// LastCrusadeRoom 12
		r = new LastCrusadeRoom();
		r.addInOut(Direction.EAST, Direction.SOUTH);
		roomsTypes.put(12, r);
		// LastCrusadeRoom 13
		r = new LastCrusadeRoom();
		r.addInOut(Direction.WEST, Direction.SOUTH);
		roomsTypes.put(13, r);
	}

	public static void main(String args[]) {
		setLastCrusadeRooms();
		try (Scanner in = new Scanner(System.in)) {
			int W = in.nextInt(); // number of columns.
			int H = in.nextInt(); // number of rows.
			int[][] map = new int[H][W];
			in.nextLine();
			for (int h = 0; h < H; h++) {
				for (int w = 0; w < W; w++) {
					map[h][w] = in.nextInt();
					System.err.print(map[h][w] + " ");
					// String LINE = in.nextLine(); // represents a line in the grid and contains W integers. Each
					// integer represents one room of a given type.
				}
				System.err.println();
			}
			int EX = in.nextInt(); // the coordinate along the X axis of the exit (not useful for this first mission,
									 // but must be read).

			// game loop
			while (true) {
				int XI = in.nextInt();
				int YI = in.nextInt();
				String POS = in.next();
				if ("TOP".equals(POS))
					POS = "UP";
				Direction next = roomsTypes.get(map[YI][XI]).getExit(Direction.valueOf(POS));

				// One line containing the X Y coordinates of the room in which you believe Indy will be on the next
				// turn.
				System.out.println((XI + next.dx()) + " " + (YI + next.dy()));
			}
		}
	}
}