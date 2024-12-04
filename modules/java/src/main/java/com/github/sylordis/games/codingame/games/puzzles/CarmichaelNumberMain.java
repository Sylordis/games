package com.github.sylordis.games.codingame.games.puzzles;

import java.math.BigInteger;
import java.util.Scanner;

public class CarmichaelNumberMain {

	public CarmichaelNumberMain() {
	}

	public boolean matches(int n) {
		boolean isCarmichael = false;
		if (!isPrime(n)) {
			BigInteger i = BigInteger.valueOf(2);
			isCarmichael = i.pow(n).equals(BigInteger.valueOf(2 % n));
		}
		return isCarmichael;
	}

	public static boolean isPrime(int n) {
		// check if n is a multiple of 2
		if (n % 2 == 0)
			return false;
		// if not, then just check the odds
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		in.close();
		CarmichaelNumberMain number = new CarmichaelNumberMain();
		System.out.println(number.matches(n) ? "YES" : "NO");
	}

}
