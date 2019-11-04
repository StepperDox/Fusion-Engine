package com.stepperdox.fusion.data;

import org.lwjgl.util.vector.Vector3f;

/**
*  @author StepperDox
*  Created on Oct 22, 2019
*/

public class Material {
	private String name = "";
	private Vector3f ambient = new Vector3f();
	private Vector3f diffuse = new Vector3f();
	private Vector3f specular = new Vector3f();
	private float transparency = 0;
	private int illumination = 1;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Vector3f getAmbient() {
		return ambient;
	}
	
	public void setAmbient(Vector3f ambient) {
		this.ambient = ambient;
	}
	
	public Vector3f getDiffuse() {
		return diffuse;
	}
	
	public void setDiffuse(Vector3f diffuse) {
		this.diffuse = diffuse;
	}
	
	public Vector3f getSpecular() {
		return specular;
	}
	
	public void setSpecular(Vector3f specular) {
		this.specular = specular;
	}
	
	public float getTransparency() {
		return transparency;
	}
	
	public void setTransparency(float transparency) {
		this.transparency = transparency;
	}
	
	public int getIllumination() {
		return illumination;
	}
	
	public void setIllumination(int illumination) {
		this.illumination = illumination;
	}
}
