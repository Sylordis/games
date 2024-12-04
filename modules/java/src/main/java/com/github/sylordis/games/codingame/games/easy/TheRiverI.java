package com.github.sylordis.games.codingame.games.easy;

import java.util.Scanner;

import com.github.sylordis.commons.DigitalRiver;

public class TheRiverI {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		long rs1 = in.nextLong();
		long rs2 = in.nextLong();
		in.close();
		DigitalRiver r1 = new DigitalRiver(rs1), r2 = new DigitalRiver(rs2);
		while (r1.current() != r2.current()) {
			if (r1.current() < r2.current())
				r1.next();
			else
				r2.next();
		}
		System.out.println(r1.current());
	}

}
