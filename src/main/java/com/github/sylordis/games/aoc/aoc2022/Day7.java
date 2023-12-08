package com.github.sylordis.games.aoc.aoc2022;

import static com.github.sylordis.commons.utils.PrintUtils.print;

import java.util.List;
import java.util.Scanner;

import com.github.sylordis.commons.filesystem.File;

public class Day7 {

	private final long FILESYSTEM_SIZE = 70000000;
	private final long UPDATE_SIZE = 30000000;
	
	public static void main(String[] args) {
		new Day7().part2();
	}

	private File setUpFileSystem() {
		File root = new File("");
		File currentFile = root;
		try (Scanner in = new Scanner(System.in)) {
			while (in.hasNext()) {
				// Build the tree
				String terminalLine = in.nextLine();
				currentFile = interpretTerminalLine(terminalLine, currentFile);
			}
		}
//		print("{}", root.tree());
		return root;
	}

	public void part1() {
		File root = setUpFileSystem();
		// Use the tree
		List<File> smallDirs = root.findAll(f -> f.isDirectory() && f.getSize() <= 100000);
		print("small dirs={}", smallDirs);
		long total = smallDirs.stream().mapToLong(File::getSize).sum();
		print("{}", total);
	}

	public void part2() {
		File root = setUpFileSystem();
		long du = root.getSize();
		long df = FILESYSTEM_SIZE - du;
		long sizeToFree = UPDATE_SIZE - df;
		print("du={} df={} needed={}", du, df, sizeToFree);
		List<File> dirsToDelete = root.findAll(f -> f.isDirectory() && f.getSize() >= sizeToFree);
		print("candidates={}", dirsToDelete);
		File toDelete = dirsToDelete.stream().min((f1,f2) -> (int) (f1.getSize() - f2.getSize())).get();
		print("{}", toDelete);
	}

	private File interpretTerminalLine(String terminalLine, File currentFile) {
		File nextFile = currentFile;
		if (terminalLine.startsWith("$ ")) {
			// Command
			String command = terminalLine.substring(2);
			nextFile = interpretCommandLine(command, currentFile);
		} else {
			// File parsing
			createIfNotExists(terminalLine, currentFile);
		}
		return nextFile;
	}

	private void createIfNotExists(String line, File currentFile) {
//		print("file={}", line);
		String[] parts = line.split(" ");
		String fileName = parts[1];
		if (File.TYPE_DIR.equals(parts[0])) {
			if (currentFile.getDirectChild(fileName) == null)
				currentFile.addChild(new File(fileName, currentFile));
//			print("current={} {}", currentFile, currentFile.getChildren());
		} else {
			long size = Long.valueOf(parts[0]);
			File child = currentFile.getDirectChild(fileName);
			if (null == child)
				currentFile.addChild(new File(fileName, currentFile, size));
			else
				child.setSize(size);
//			print("current={} {}", currentFile, currentFile.getChildren());
		}
	}

	private File interpretCommandLine(String line, File currentFile) {
//		print("command={}", line);
		String[] parts = line.split(" ");
		File nextFile = currentFile;
		switch (parts[0]) {
			case "cd":
				if ("..".equals(parts[1]))
					nextFile = currentFile.getParent();
				else if (File.SEPARATOR.equals(parts[1]))
					nextFile = currentFile.getRoot();
				else
					nextFile = currentFile.getDirectChild(parts[1]);
//				print("pwd={}", nextFile);
				break;
		}
		return nextFile;
	}

}
