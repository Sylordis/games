package com.github.sylordis.games.aoc.aoc2022;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.List;
import java.util.Scanner;

import com.github.sylordis.commons.computer.AddToIntRegisterOperation;
import com.github.sylordis.commons.computer.CPU;
import com.github.sylordis.commons.computer.CPUOperation;
import com.github.sylordis.commons.computer.DoNothingOperation;
import com.github.sylordis.commons.computer.Register;

public class Day10 {

	private final List<Integer> interestingCycles = List.of(20, 60, 100, 140, 180, 220);

	public static void main(String[] args) {
		new Day10().day1();
	}

	private void day1() {
		CPU<String, Integer> cpu = new CPU<>(1, 1);
		Register<Integer> regX = cpu.createRegister("x");
		loadInput(cpu);
		long signalStrengthsSum = 0L;
		int cycle = 0;
		while (cpu.hasOperations()) {
			cpu.cycle();
			cycle++;
			print("cycle {}, {}", cycle, cpu);
			if (interestingCycles.contains(cycle)) {
				long signalStrength = cycle * regX.getValue();
				signalStrengthsSum += signalStrength;
				print("strength={} (total: {})", signalStrength, signalStrengthsSum);
			}
		}
		print("{}", signalStrengthsSum);
	}

	private void day2() {
		CPU<String, Integer> cpu = new CPU<>(1, 1);
		cpu.createRegister("x");
		loadInput(cpu);
		// TODO
		print("{}", "Hello world");
	}

	private CPUOperation<String,Integer> strToOperation(String instruction) {
		CPUOperation<String,Integer> op = null;
		if (instruction.equals("noop"))
			op = new DoNothingOperation<>(1);
		else if (instruction.startsWith("add")) {
			String[] parts = instruction.split(" ");
			final String registerName = parts[0].substring("add".length());
			final int value = Integer.parseInt(parts[1]);
			op = new AddToIntRegisterOperation<String>(2, registerName, value);
		}
		return op;
	}

	private void loadInput(CPU<String, Integer> cpu) {
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				cpu.addOperation(strToOperation(in.nextLine()));
			}
		}
		print("{}", cpu);
	}

}
