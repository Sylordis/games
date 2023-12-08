package com.github.sylordis.games.codingame.games.puzzles;

import static com.github.sylordis.commons.utils.PrintUtils.debug;

import java.util.Scanner;

import com.github.sylordis.games.codingame.games.puzzles.onedspreadsheet.Cell;
import com.github.sylordis.games.codingame.games.puzzles.onedspreadsheet.CellType;

public class OneDSpreadsheet {

	private Cell[] cells;

	public OneDSpreadsheet(String[] cells) {
		this.cells = new Cell[cells.length];
		for (int i = 0; i < cells.length; i++)
			this.cells[i] = convertToCell(cells[i]);
	}

	private Cell convertToCell(String string) {
		String[] parts = string.split(" ");
		debug(parts);
		return new Cell(CellType.valueOf(parts[0]), parts[1], parts[2]);
	}

	@Override
	public String toString() {
		StringBuffer rame = new StringBuffer();
		for (int i = 0; i < cells.length; i++) {
			rame.append(getCellValue(i));// .append(" ");
			if (i < cells.length - 1)
				rame.append("\n");
		}
		return rame.toString();
	}

	/**
	 * Gets the value of a cell.
	 * 
	 * @param i Index of the cell.
	 * @return a value
	 */
	private int getCellValue(int i) {
		if (cells[i].getType() == CellType.VALUE)
			return getValue(cells[i].first());
		else
			return cells[i].getType().getOperator().applyAsInt(getValue(cells[i].first()), getValue(cells[i].second()));
	}

	private int getValue(String value) {
		int ret;
		try {
			ret = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			ret = getCellValue(getValue(value.substring(1)));
		}
		return ret;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int lines = in.nextInt();
		in.nextLine();
		String[] cells = new String[lines];
		for (int i = 0; i < lines; i++)
			cells[i] = in.nextLine();
		OneDSpreadsheet sheet = new OneDSpreadsheet(cells);
		System.out.print(sheet);
		in.close();
	}

}
