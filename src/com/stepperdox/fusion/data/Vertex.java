package com.stepperdox.fusion.data;

import org.lwjgl.util.vector.Vector3f;

/**
*  @author StepperDox
*  Created on Oct 22, 2019
*/

public class Vertex {
	private Vector3f position;
	private Material mtl;
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public Material getMtl() {
		return mtl;
	}
	
	public void setMtl(Material mtl) {
		this.mtl = mtl;
	}
}
