package com.github.sylordis.games.codingame.games.medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.github.sylordis.games.codingame.games.medium.specific.EdgeSkynet;
import com.github.sylordis.games.codingame.games.medium.specific.VertexSkynet;

public class SkynetRevolutionMain {

	private static Map<Integer, VertexSkynet> nodes = new HashMap<>();
	private static List<EdgeSkynet> edges = new ArrayList<>();

	public static void createNodeSkynets(int n) {
		nodes.clear();
		for (int i = 0; i < n; i++)
			nodes.put(i, new VertexSkynet(i));
	}

	public static EdgeSkynet getEdge(VertexSkynet n1, VertexSkynet n2) {
		EdgeSkynet edge = null;
		for (EdgeSkynet e : edges) {
			if (e.hasVertex(n1) && e.hasVertex(n2))
				return e;
		}
		return edge;
	}

	public static List<EdgeSkynet> getClosestUnlockedEdgesToGateway(VertexSkynet start) {
		List<EdgeSkynet> result = new ArrayList<>();
		List<VertexSkynet> sweeped = new ArrayList<>();
		List<VertexSkynet> current = new ArrayList<>();
		current.add(start);
		while (result.isEmpty()) {
			for (VertexSkynet node : current) {
				List<VertexSkynet> neighbours = node.getNeighbours().stream().map(ns -> (VertexSkynet) ns)
						.collect(Collectors.toList());
				for (VertexSkynet neigh : neighbours) {
					EdgeSkynet edge = getEdge(node, neigh);
					if (neigh.isGateway() && !edge.isLocked()) {
						result.add(edge);
					}
				}
				sweeped.add(node);
			}
			current = current.stream().flatMap(n -> n.getNeighbours().stream().map(node -> (VertexSkynet) node))
					.filter(n -> !sweeped.contains(n)).collect(Collectors.toList());
		}
		return result;
	}

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt(); // the total number of nodes in the level,
								// including the gateways
		int L = in.nextInt(); // the number of links
		int E = in.nextInt(); // the number of exit gateways
		createNodeSkynets(N);
		for (int i = 0; i < L; i++) {
			int N1 = in.nextInt(); // N1 and N2 defines a link between these
									// nodes
			int N2 = in.nextInt();
			VertexSkynet n1 = nodes.get(N1);
			VertexSkynet n2 = nodes.get(N2);
			n1.addNeighbour(n2);
			n2.addNeighbour(n1);
			edges.add(new EdgeSkynet(n1, n2));
		}
		for (int i = 0; i < E; i++) {
			int EI = in.nextInt(); // the index of a gateway node
			nodes.get(EI).setGateway();
		}
		boolean end = false;

		// game loop
		while (!end) {
			int SI = in.nextInt(); // The index of the node on which the Skynet
									// agent is positioned this turn
			// Get all closest unlocked edges to gateway from Skynet agent's
			// position
			List<EdgeSkynet> tempEdges = getClosestUnlockedEdgesToGateway(nodes.get(SI));
			// If there are several, take the first which comes
			EdgeSkynet nlock = tempEdges.get(0);
			// Lock it for data purposes
			nlock.lock();

			// Return string format of the newly locked Edge
			System.out.println(nlock);
		}
		in.close();
	}

}
