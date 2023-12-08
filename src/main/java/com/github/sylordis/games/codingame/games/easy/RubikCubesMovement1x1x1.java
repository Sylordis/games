package com.github.sylordis.games.codingame.games.easy;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

public class RubikCubesMovement1x1x1 {

	static class RubikCubes111 {
		/**
		 * Safe-keeping for the faces.
		 */
		private final char[][] originalFaces;
		/**
		 * Current faces worked on.
		 */
		private char[][] faces;

		/**
		 * Constructs a new rubik's cube of 1x1x1.
		 */
		public RubikCubes111() {
			originalFaces = new char[][] { " U  ".toCharArray(), "LFRB".toCharArray(), " D  ".toCharArray(),
					" B  ".toCharArray() };
			faces = cloneCube(originalFaces);
			faces[1][3] = 'B';
			// System.err.println(MatrixUtils.printMatrixes(this.faces));
		}

		/**
		 * Rotates 4 faces of the cube, programmatively speaking, which will shift 4 faces in the array by one square.
		 *
		 * @param startX
		 *            Starting X coordinates for the rotation.
		 * @param startY
		 *            Starting Y coordinates for the rotation.
		 * @param dx
		 *            f: n -> u which gives the next x for the current rotation.
		 * @param dy
		 *            f: n -> u which gives the next y for the current rotation.
		 */
		private void rotate(int startX, int startY, Function<Integer, Integer> dx, Function<Integer, Integer> dy) {
			char[][] faces = cloneCube(this.faces);
			int x = startX;
			int y = startY;
			char safe = faces[y][x];
			int nx = x;
			int ny = y;
			for (int i = 0; i < 4; i++) {
				nx = (x + dx.apply(i) + 4) % 4;
				ny = (y + dy.apply(i) + 4) % 4;
				faces[y][x] = i != 3 ? faces[ny][nx] : safe;
				x = nx;
				y = ny;
			}
			// Update a bottom if needed
			if (faces[1][3] != this.faces[1][3])
				faces[3][1] = faces[1][3];
			else if (faces[3][1] != this.faces[3][1])
				faces[1][3] = faces[3][1];
			this.faces = faces;
			// System.err.println(MatrixUtils.printMatrixes(this.faces));
		}

		/**
		 * Performs a rotation on a given axis.
		 *
		 * @param rotation
		 *            Axis of rotation and direction: x, x', y, y', z, z'
		 */
		public void rotate(String rotation) {
			char axis = rotation.charAt(0);
			final int direction = rotation.length() == 1 ? 1 : -1;
			switch (axis) {
				case 'x':
					System.err.println(direction == 1 ? "^(x)^" : "v(x')v");
					rotate(1, 0, n -> 0, n -> direction);
					break;
				case 'y':
					System.err.println(direction == 1 ? "<(y)<" : ">(y')>");
					rotate(0, 1, n -> direction, n -> 0);
					break;
				case 'z':
					System.err.println(direction != 1 ? "G(z)G" : "@(z')@");
					rotate(2, 1, n -> n < 2 ? -1 : 1, n -> direction * (n % 3 == 0 ? -1 : 1));
					break;
			}
		}

		/**
		 * Clones the matrix to perform operations on it without changing the current cube.
		 *
		 * @param cube
		 *            Matrix to clone.
		 * @return
		 */
		private char[][] cloneCube(char[][] cube) {
			char[][] faces = new char[4][4];
			for (int y = 0; y < 4; y++)
				faces[y] = Arrays.copyOf(cube[y], 4);
			return faces;
		}

		/**
		 * Gets the new position of given face.
		 *
		 * @param face
		 * @return
		 */
		public char getFace(char face) {
			int fy = -1;
			int fx = -1;
			for (int y = 0; y < 4; y++) {
				for (int x = 0; x < 3; x++) {
					if (faces[y][x] == face) {
						fy = y;
						fx = x;
					}
				}
			}
			return originalFaces[fy][fx];
		}

	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String[] rotations = in.nextLine().split(" ");
		String face1 = in.nextLine();
		String face2 = in.nextLine();
		in.close();
		RubikCubes111 cube = new RubikCubes111();
		System.err.println(Arrays.toString(rotations));
		Arrays.stream(rotations).forEach(cube::rotate);
		System.out.println(cube.getFace(face1.charAt(0)));
		System.out.println(cube.getFace(face2.charAt(0)));
	}
}
