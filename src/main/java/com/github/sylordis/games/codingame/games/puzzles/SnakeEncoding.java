package com.github.sylordis.games.codingame.games.puzzles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class SnakeEncoding {

	private List<Character> queue;
	private int size;

	public SnakeEncoding() {
		queue = new ArrayList<>();
	}

	public void decode(String[] lines) {
		System.err.println(Arrays.toString(lines));
		size = lines.length;
		int h = size - 1;
		int w = 0;
		int hMod = -1;
		for (int i = 0; i < size * size; i++) {
			queue.add(lines[h].charAt(w));
			if ((h == 0 && w % 2 == 0) || (h == size - 1 && w % 2 == 1)) {
				w++;
			} else {
				hMod = w % 2 == 0 ? -1 : 1;
				h += hMod;
			}
			System.err.println("h=" + h + " w=" + w + " hMod=" + hMod + " queue=" + queue);
		}
	}

	public void encode(int times) {
		Collections.rotate(queue, times);
	}

	@Override
	public String toString() {
		StringBuilder[] lines = new StringBuilder[size];
		for (int i = 0; i < size; i++)
			lines[i] = new StringBuilder();
		int h = size - 1;
		int w = 0;
		int hMod = -1;
		Iterator<Character> it = queue.iterator();
		while (it.hasNext()) {
			lines[h].append(it.next());
			if ((h == 0 && w % 2 == 0) || (h == lines.length - 1 && w % 2 == 1)) {
				w++;
			} else {
				hMod = w % 2 == 0 ? -1 : 1;
				h += hMod;
			}
			System.err.println(Arrays.toString(lines));
		}
		StringBuilder rame = new StringBuilder();
		for (StringBuilder line : lines)
			rame.append(line.toString() + "\n");
		return rame.toString().substring(0, rame.length() - 1);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		int X = in.nextInt();
		String[] lines = new String[N];
		for (int i = 0; i < N; i++) {
			lines[i] = in.next();
		}
		in.close();
		SnakeEncoding snake = new SnakeEncoding();
		snake.decode(lines);
		snake.encode(X);
		System.out.println(snake);
	}
}
