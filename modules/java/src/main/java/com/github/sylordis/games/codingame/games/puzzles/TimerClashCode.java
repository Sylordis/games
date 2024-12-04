package com.github.sylordis.games.codingame.games.puzzles;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TimerClashCode {

	private int nplayers;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("m:SS");
	private Date currentTime;
	private List<String> times;

	public TimerClashCode() {
		nplayers = 1;
		currentTime = new Date();
		times = new ArrayList<>();
	}

	public int calculateNewTime() {
		return 0; // TODO
	}

	public static void main(String[] args) {
		try (Scanner in = new Scanner(System.in)) {
			int N = in.nextInt();
			String[] entries = new String[N];
			for (int i = 0; i < N; i++)
				entries[i] = in.nextLine();
			TimerClashCode timer = new TimerClashCode();
		}
	}

}
