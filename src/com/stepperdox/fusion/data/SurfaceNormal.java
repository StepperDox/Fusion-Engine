package com.stepperdox.fusion.data;

import org.lwjgl.util.vector.Vector3f;

/**
*  @author StepperDox
*  Created on Oct 16, 2019
*/

public class SurfaceNormal {
	private Vector3f normal;
	private Vector3f tangent = new Vector3f(0.0f, 1.0f, 0.0f);
	private Vector3f bitangent = new Vector3f(1.0f, 0.0f, 0.0f);
	
	public SurfaceNormal(Vector3f normal, Vector3f tangent, Vector3f bitangent){
		normal.normalise(this.normal);
		if(tangent != null){
			tangent.normalise(this.tangent);
		}
		if(bitangent != null){
			bitangent.normalise(this.bitangent);
		}
	}
	
	public Vector3f getNormal() {
		return normal;
	}
	
	public Vector3f getTangent() {
		return tangent;
	}
	
	public Vector3f getBitangent() {
		return bitangent;
	}
}
