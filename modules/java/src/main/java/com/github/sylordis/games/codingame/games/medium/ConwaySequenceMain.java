package com.github.sylordis.games.codingame.games.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConwaySequenceMain {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int R = in.nextInt(); // Starting number
		int L = in.nextInt(); // Line to print
		in.close();
		String now = "" + R;
		for (int n = 1; n < L; n++) {
			// Cut the string into ints
			int[] cuts = Arrays.stream(now.split(" ")).mapToInt(s -> Integer.parseInt(s)).toArray();
			System.err.println("Cuts: "+Arrays.toString(cuts));
			// Convert
			int lastIndex = 0;
			List<Integer> convert = new ArrayList<>();
			for (int i = 1; i < cuts.length; i++) {
				if (cuts[i] != cuts[lastIndex]) {
					System.err.println("cut! " + i + "-" + lastIndex + "=" + (i - lastIndex) + " ["+cuts[lastIndex]+"]");
					convert.add(i - lastIndex);
					convert.add(cuts[lastIndex]);
					lastIndex = i;
				}
			}
			// Add the last bit
			convert.add(cuts.length-lastIndex);
			convert.add(cuts[lastIndex]);
			// Assemble the string
			now = String.join(" ", convert.stream().map(a -> Integer.toString(a)).collect(Collectors.toList()));
			System.err.println("["+now+"]");
		}

		System.out.println(now);
	}

}

