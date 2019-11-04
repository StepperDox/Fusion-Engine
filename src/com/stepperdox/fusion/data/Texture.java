package com.stepperdox.fusion.data;

import java.nio.ByteBuffer;

/**
*  @author StepperDox
*  Created on Oct 22, 2019
*/

public class Texture {
	private ByteBuffer BUFFER;
	private int WIDTH;
	private int HEIGHT;
	private int texID;
	private int rows;
	private float shine = 0;
	private float diffuse = 1;
	private boolean transparent = false;
	private boolean forcedLight = false;
	
	public Texture(ByteBuffer buffer, int width, int height){
		BUFFER = buffer;
		WIDTH = width;
		HEIGHT = height;
	}
	
	public ByteBuffer getBUFFER() {
		return BUFFER;
	}
	
	public int getWIDTH() {
		return WIDTH;
	}
	
	public int getHEIGHT() {
		return HEIGHT;
	}
	
	public int getTexID() {
		return texID;
	}
	
	public void setTexID(int texID) {
		this.texID = texID;
	}
	
	public int getRows() {
		return rows;
	}
	
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public float getShine() {
		return shine;
	}
	
	public void setShine(float shine) {
		this.shine = shine;
	}
	
	public float getDiffuse() {
		return diffuse;
	}
	
	public void setDiffuse(float diffuse) {
		this.diffuse = diffuse;
	}
	
	public boolean isTransparent() {
		return transparent;
	}
	
	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}
	
	public boolean isForcedLight() {
		return forcedLight;
	}
	
	public void setForcedLight(boolean forcedLight) {
		this.forcedLight = forcedLight;
	}
}
