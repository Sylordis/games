package com.github.sylordis.games.codingame.games.easy;

import java.util.Scanner;

public class RectangularBlockSpinner {

	private final char[][] chars;

	public RectangularBlockSpinner(char[][] chars) {
		this.chars = chars;
	}

	public String rotate(int rotation) {
		int angle = rotation % 360;
		int dStartX = 0, dStartY = 0, startX = 0, startY = 0, dx = 0, dy = 0;
		switch (angle) {
			case 45:
				startX = chars.length - 1;
				startY = 0;
				dStartX = -1;
				dStartY = 0;
				dx = 1;
				dy = 1;
				break;
			case 135:
				startX = chars.length - 1;
				startY = chars.length - 1;
				dStartX = 0;
				dStartY = -1;
				dx = -1;
				dy = 1;
				break;
			case 225:
				startX = 0;
				startY = chars.length - 1;
				dStartX = 1;
				dStartY = 0;
				dx = 1;
				dy = -1;
				break;
			case 315:
				startX = 0;
				startY = 0;
				dStartX = 0;
				dStartY = 1;
				dx = 1;
				dy = -1;
				break;
		}
		StringBuffer buffer = new StringBuffer();
		for (int currLine = 1; currLine < chars.length * 2; currLine++) {
			int x = startX;
			int y = startY;
			int nchars = currLine <= chars.length ? currLine : chars.length * 2 - currLine;
			String padding = pad((chars.length * 2 - (nchars * 2 - 1)) / 2);
			buffer.append(padding);
			for (int c = 0; c < nchars; c++) {
				if (c != 0)
					buffer.append(" ");
				buffer.append(chars[y][x]);
				x += dx;
				y += dy;
			}
			buffer.append(padding).append("\n");
			if (currLine == chars.length) {
				dStartX += dx;
				dStartY += dy;
			}
			startX += dStartX;
			startY += dStartY;
		}
		return buffer.toString();
	}

	private String pad(int n) {
		return new String(new char[n]).replace("\0", " ");
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int size = in.nextInt();
		int angle = in.nextInt();
		if (in.hasNextLine())
			in.nextLine();
		char[][] chars = new char[size][];
		for (int i = 0; i < size; i++)
			chars[i] = in.nextLine().replaceAll(" ", "").toCharArray();
		in.close();
		RectangularBlockSpinner spinner = new RectangularBlockSpinner(chars);
		final String diamond = spinner.rotate(angle);
		System.out.println(diamond.substring(0, diamond.length() - 1));
	}
}
