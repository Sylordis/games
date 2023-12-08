package com.github.sylordis.games.codingame.games.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SelfDrivingCarTesting {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int nlines = in.nextInt();
		List<StringBuffer> road = new ArrayList<>();
		if (in.hasNextLine())
			in.nextLine();
		String[] xthenCOMMANDS = in.nextLine().split(";");
		int x = Integer.parseInt(xthenCOMMANDS[0]);
		for (int i = 0; i < nlines; i++) {
			String[] rthenROADPATTERN = in.nextLine().split(";");
			for (int j = 0; j < Integer.parseInt(rthenROADPATTERN[0]); j++)
				road.add(new StringBuffer(rthenROADPATTERN[1]));
		}
		in.close();
		xthenCOMMANDS = Arrays.copyOfRange(xthenCOMMANDS, 1, xthenCOMMANDS.length);
		int y = 0;
		for (String command : xthenCOMMANDS) {
			int repeat = Integer.parseInt(command.substring(0, command.length() - 1));
			char direction = command.charAt(command.length() - 1);
			int dx = 0;
			if (direction == 'L')
				dx = -1;
			else if (direction == 'R')
				dx = 1;
			System.err.println("y=" + y + " x=" + x + " dx=" + dx);
			for (int i = 0; i < repeat && road.size() >= y; i++, y++) {
				x += dx;
				road.get(y).replace(x - 1, x, "#");
				System.out.println(road.get(y));
			}
		}
		// road.forEach(b -> System.out.println(b.toString()));
	}

}
