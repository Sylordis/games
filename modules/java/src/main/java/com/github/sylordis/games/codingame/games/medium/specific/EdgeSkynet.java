package com.github.sylordis.games.codingame.games.medium.specific;

import com.github.sylordis.commons.graph.Edge;
import com.github.sylordis.commons.graph.Vertex;

public class EdgeSkynet extends Edge {

	boolean locked;

	public EdgeSkynet(Vertex n1, Vertex n2) {
		super(n1, n2);
		locked = false;
	}

	public boolean isLocked() {
		return locked;
	}

	public void lock() {
		locked = true;
	}
	
}
