package com.github.sylordis.games.codingame.games.easy;

import java.util.Scanner;
import java.util.function.Function;

public class DisorderedFirstContact {

	public static String encode(String message) {
		int side = 1; // 0 for start, 1 for end
		int index = 0;
		int count = 1;
		StringBuffer buffer = new StringBuffer();
		while (index < message.length()) {
			final String sub = message.substring(index, Math.min(message.length(), index + count));
			buffer.insert(buffer.length() * side, sub);
			side = Math.abs(side - 1);
			index += count;
			count++;
		}
		System.err.println("encode::out:[" + buffer.toString() + "]");
		return buffer.toString();
	}

	public static String decode(String message) {
		int count = 0;
		while (sumTo(count) < message.length())
			count++;
		// side = if count even, left side
		StringBuffer buffer = new StringBuffer();
		StringBuffer orig = new StringBuffer(message);
		while (count > 0) {
			int start = count % 2 == 0 ? 0 : orig.length() - count;
			int end = count % 2 == 0 ? count - (sumTo(count) - orig.length()) : orig.length();
			String part = orig.substring(start, end);
			System.err.println(count + "[" + start + "," + end + "] [" + part + "]");
			buffer.insert(0, part);
			orig.delete(start, end);
			System.err.println("[" + buffer.toString() + "] [" + orig.toString() + "]");
			count--;
		}
		buffer.insert(0, orig);
		System.err.println("decode::out:[" + buffer.toString() + "]");
		return buffer.toString();
	}

	private static int sumTo(int n) {
		return n * (n + 1) / 2;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		if (in.hasNextLine()) {
			in.nextLine();
		}
		String message = in.nextLine();
		in.close();
		Function<String, String> func = n > 0 ? DisorderedFirstContact::decode : DisorderedFirstContact::encode;
		for (int i = 0; i < Math.abs(n); i++)
			message = func.apply(message);
		System.out.println(message);
	}
}
