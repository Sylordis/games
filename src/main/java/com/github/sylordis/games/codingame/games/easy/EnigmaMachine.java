package com.github.sylordis.games.codingame.games.easy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.IntUnaryOperator;

public class EnigmaMachine {

	enum Operation {
		ENCODE, DECODE;
	}

	static class Rotor {
		private String wheel;

		public Rotor(String letters) {
			wheel = letters;
		}

		private String replace(String message, IntUnaryOperator mapper) {
			return message.codePoints().map(mapper)
					.collect(StringBuffer::new, StringBuffer::appendCodePoint, StringBuffer::append).toString();
		}

		public String encode(String message) {
			return replace(message, c -> wheel.charAt(c - 'A'));
		}

		public String decode(String cypher) {
			return replace(cypher, c -> wheel.indexOf(c) + 'A');
		}
	}

	public static String incrementalShift(String message, int shift, int increment) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < message.length(); i++) {
			int newchar = message.codePointAt(i) - 'A';
			// Make sure the given is positive through modulo
			newchar = (((newchar + shift + increment * i) % 26) + 26) % 26;
			buffer.append((char) (newchar + 'A'));
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Operation operation = Operation.valueOf(in.nextLine());
		int pseudoRandomNumber = in.nextInt();
		if (in.hasNextLine())
			in.nextLine();
		List<Rotor> rotors = new ArrayList<>();
		for (int i = 0; i < 3; i++)
			rotors.add(new Rotor(in.nextLine()));
		String message = in.nextLine();
		in.close();
		if (operation == Operation.ENCODE) {
			// Apply Caesar incremental shift
			message = incrementalShift(message, pseudoRandomNumber, 1);
			// Go through each rotor
			for (Rotor rotor : rotors)
				message = rotor.encode(message);
		} else {
			Collections.reverse(rotors);
			// Go through each rotor in reverse
			for (Rotor rotor : rotors)
				message = rotor.decode(message);
			// Apply inverse Caeser incremental shift
			message = incrementalShift(message, -pseudoRandomNumber, -1);
		}
		System.out.println(message);
	}
}
