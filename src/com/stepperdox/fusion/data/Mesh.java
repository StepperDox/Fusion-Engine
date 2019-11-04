package com.stepperdox.fusion.data;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
*  @author StepperDox
*  Created on Oct 16, 2019
*/

public class Mesh {
	private Vector3f centerOfGravity;
	private float extremePointDist = 0.0f;
	private List<Vertex> vertices;
	private List<SurfaceNormal> normals;
	private List<Integer> indices;
	private List<Vector2f> textures;
	private Texture ambient = null;
	private Texture diffuse = null;
	private Texture specular = null;
	private Texture normal = null;
	private Texture glow = null;
	private int vao;
	
	public Mesh(List<Vertex> vertices, List<SurfaceNormal> normals, List<Integer> indices, List<Vector2f> textures){
		this.vertices = vertices;
		this.normals = normals;
		this.indices = indices;
		this.textures = textures;
	}
	
	public Vector3f getCenterOfGravity(){
		return centerOfGravity;
	}
	
	public void setCenterOfGravity(Vector3f cog){
		centerOfGravity = cog;
	}
	
	public float getExtremePointDist(){
		return extremePointDist;
	}
	
	public void setExtremePointDist(float epd){
		extremePointDist = epd;
	}
	
	public List<Vertex> getVertices() {
		return vertices;
	}
	
	public void setVertices(List<Vertex> vertices) {
		this.vertices = vertices;
	}
	
	public List<SurfaceNormal> getNormals() {
		return normals;
	}
	
	public void setNormals(List<SurfaceNormal> normals) {
		this.normals = normals;
	}

	public List<Integer> getIndices() {
		return indices;
	}
	
	public void setIndices(List<Integer> indices) {
		this.indices = indices;
	}
	
	public List<Vector2f> getTextures() {
		return textures;
	}
	
	public void setTextures(List<Vector2f> textures) {
		this.textures = textures;
	}
	
	public Texture getAmbient() {
		return ambient;
	}
	
	public void setAmbient(Texture ambient) {
		this.ambient = ambient;
	}
	
	public Texture getDiffuse() {
		return diffuse;
	}
	
	public void setDiffuse(Texture dffuse) {
		this.diffuse = dffuse;
	}
	
	public Texture getSpecular() {
		return specular;
	}
	
	public void setSpecular(Texture specular) {
		this.specular = specular;
	}
	
	public Texture getNormal() {
		return normal;
	}
	
	public void setNormal(Texture normal) {
		this.normal = normal;
	}
	
	public Texture getGlow() {
		return glow;
	}
	
	public void setGlow(Texture glow) {
		this.glow = glow;
	}
	
	public int getVao() {
		return vao;
	}
	
	public void setVao(int vao) {
		this.vao = vao;
	}
}
