package com.github.sylordis.games.codingame.games.medium.specific;

import com.github.sylordis.commons.graph.IdentifiableVertex;

public class VertexSkynet extends IdentifiableVertex {

	private boolean gateway;

	public VertexSkynet(int id) {
		super(id);
		gateway = false;
	}

	public boolean isGateway() {
		return gateway;
	}

	public void setGateway() {
		gateway = true;
	}

}
