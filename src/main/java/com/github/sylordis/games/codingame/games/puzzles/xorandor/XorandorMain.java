package com.github.sylordis.games.codingame.games.puzzles.xorandor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.github.sylordis.commons.logical.LogicalComponent;
import com.github.sylordis.commons.logical.Output;
import com.github.sylordis.commons.logical.resolution.ResolutionList;

public class XorandorMain {

	private List<LogicalComponent> circuit;

	private Output getLED() {
		Output led = (Output) circuit.stream().filter(c -> c instanceof Output).findFirst().get();
		return led;
	}

	/**
	 * Resolves the problem
	 *
	 * @return a list of switches to turn on
	 */
	private String resolve() {
		Output led = getLED();
		List<LogicalComponent> current = new ArrayList<>();
		List<LogicalComponent> switches = new ArrayList<>();
		current.add(led);
		List<ResolutionList> resolutions = new ArrayList<>();
		resolutions.add(led.resolve(true));
		System.err.println(Arrays.toString(resolutions.toArray()));
		while (!current.isEmpty()) {
			break;
		}
		// Resolve the current components
		// Get previous components until there's no more
		StringBuilder rame = new StringBuilder();
		for (LogicalComponent elt : current) {
			rame.append(elt);// .toString().replaceAll("[A-Za-z]+-", "I") + "\n");
		}
		return rame.toString();
	}

	/**
	 * Transforms a textual circuit representation into a logical circuit.
	 *
	 * @param lines
	 *            Textual representation of the circuit
	 * @throws Exception
	 */
	private void setCircuitFromStringRepresentation(String[] lines) throws Exception, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		XorandorTranslator translator = new XorandorTranslator();
		circuit = translator.translate(lines);
	}

	/**
	 * Main method.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Scanner in = new Scanner(System.in);
			int height = in.nextInt();
			in.nextLine(); // width is useless
			String[] lines = new String[height + 1];
			for (int i = 0; i <= height; i++) {
				lines[i] = in.nextLine();
			}
			in.close();
			XorandorMain xorandor = new XorandorMain();
			xorandor.setCircuitFromStringRepresentation(lines);
			System.out.println(xorandor.resolve());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
