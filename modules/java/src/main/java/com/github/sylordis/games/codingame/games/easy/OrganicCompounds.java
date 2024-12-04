package com.github.sylordis.games.codingame.games.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class OrganicCompounds {

	static class Bond {
		private final int bonds;

		public Bond(int bonds) {
			this.bonds = bonds;
		}

		public int getBonds() {
			return bonds;
		}

		@Override
		public String toString() {
			return "(" + bonds + ")";
		}

	}

	static class HydrogenatedCarbon {
		private final int hydrogens;

		public HydrogenatedCarbon(int h) {
			this.hydrogens = h;
		}

		public int getHydrogens() {
			return hydrogens;
		}

		public int requiredLinks() {
			return 4 - hydrogens;
		}

		@Override
		public String toString() {
			return "CH" + hydrogens;
		}
	}

	static class BondsAnalyser {

		private Object[][] formula;

		public void load(String[] lines) {
			int biggest = Arrays.stream(lines).mapToInt(s -> s.length() / 3).max().getAsInt();
			formula = new Object[lines.length][];
			for (int y = 0; y < lines.length; y++) {
				List<Object> objs = new ArrayList<>();
				for (int x = 0; x < lines[y].length(); x += 3) {
					String repr = lines[y].substring(x, x + 3);
					Object obj = null;
					if (repr.matches("CH[0-9]"))
						obj = new HydrogenatedCarbon(Integer.parseInt("" + repr.charAt(2)));
					else if (repr.matches(Pattern.quote("(") + "[0-9]" + Pattern.quote(")")))
						obj = new Bond(Integer.parseInt("" + repr.charAt(1)));
					objs.add(obj);
				}
				formula[y] = Arrays.copyOf(objs.toArray(), biggest);
			}
		}

		public boolean isValid() {
			boolean flag = true;
			for (int y = 0; y < formula.length && flag; y++) {
				for (int x = 0; x < formula[y].length && flag; x++) {
					if (formula[y][x] instanceof HydrogenatedCarbon) {
						System.err.println(x + "," + y + " Molecule: ");
						int bonds = ((HydrogenatedCarbon) formula[y][x]).requiredLinks();
						for (int r = 0; r < 4; r++) {
							int dx = x + (int) Math.cos(Math.PI * r / 2);
							int dy = y + (int) Math.sin(Math.PI * r / 2);
							if (dx < formula[y].length //
									&& dx >= 0 //
									&& dy < formula.length //
									&& dy >= 0 //
									&& formula[dy][dx] != null) {
								final int mbonds = ((Bond) formula[dy][dx]).getBonds();
								bonds -= mbonds;
								System.err.println(
										"bonds: " + mbonds + " at " + dx + "," + dy + "; " + bonds + " missing");
							}
						}
						if (bonds != 0)
							flag = false;
					}
				}
			}
			return flag;
		}

		public Object[][] getFormula() {
			return formula;
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		if (in.hasNextLine()) {
			in.nextLine();
		}
		String[] lines = new String[N];
		for (int i = 0; i < N; i++) {
			lines[i] = in.nextLine();
		}
		in.close();
		BondsAnalyser analyser = new BondsAnalyser();
		analyser.load(lines);
		System.out.println(analyser.isValid() ? "VALID" : "INVALID");
	}
}
