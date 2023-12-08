package com.github.sylordis.games.codingame.games.medium;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.github.sylordis.commons.graph.d2.Graph2D;
import com.github.sylordis.commons.graph.d2.Line2D;
import com.github.sylordis.commons.graph.d2.Point2D;

public class NetworkCablingMain {

	private Graph2D graph;

	public NetworkCablingMain() {
		graph = new Graph2D();
		collectDatas();
		Line2D correlation = graph.correlation();
		// System.err.println(graph);
		System.err.println("Points: " + Arrays.toString(graph.getPoints().toArray()) + "\n");
		System.err.println("Initial correlation: " + correlation + "\n");
		int minX = (int) graph.getExtrema(Graph2D.Axis.X, false);
		int maxX = (int) graph.getExtrema(Graph2D.Axis.X, true);
		int lastY = (int) Math.floor(correlation.getYforX(minX));
		int currentY = lastY;
		long distance = 0;
		// Travel through each x between two X extrema of the graph
		for (int x = minX; x <= maxX; x++) {
			currentY = (int) Math.floor(correlation.getYforX(x));
			System.err.println("Current (" + x + "," + currentY + ")");
			// Check if we moved on Y axis and cable that distance
			if (currentY != lastY) {
				distance += Math.abs(currentY - lastY);
				System.err.println(" Jumped on Y: " + lastY + "=>" + currentY + " (+" + Math.abs(currentY - lastY) + ")");
			}
			// Get the cabled points
			List<Point2D> column = graph.getPoints(Graph2D.Axis.X, x);
			// Add separation from main line to surrounding points
			for (Point2D p : column) {
				distance += Math.abs(currentY - p.y());
				System.err.println(" Cabling " + p + " (+" + Math.abs(currentY - p.y()) + ")");
				// Remove points from the graph so they will not influence correlation line anymore
				graph.removePoint(p);
			}
			int nMinX = (int) graph.getExtrema(Graph2D.Axis.X, false);
			System.err.println("nMinX=" + nMinX + " x=" + x);
			if (nMinX > x + 1) {
				// Current x is already cabled
				distance += nMinX - x - 1;
				System.err.println(" Jump on X: " + x + "=>" + nMinX + " (+" + (nMinX - x) + ")");
				// New minimum will be inspected on next loop
				x = nMinX;
			}
			// Calculate new correlation if possible
			Line2D corrTemp = graph.correlation();
			if (corrTemp != null) {
				correlation = corrTemp;
				System.err.println(" New correlation: " + correlation);
			}
			if (x != minX)
				distance++;
			System.err.println(" " + distance + "\n");
			lastY = currentY;
		}
		System.out.println(distance);
	}

	public void collectDatas() {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		for (int i = 0; i < N; i++)
			graph.addPoint(new Point2D(in.nextInt(), in.nextInt()));
		in.close();
		graph.sortPoints(Point2D.COMPARATOR_X);
	}

	public static void main(String[] args) {
		new NetworkCablingMain();
	}

}
