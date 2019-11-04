package com.stepperdox.fusion.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import com.stepperdox.fusion.data.Material;
import com.stepperdox.fusion.data.Mesh;
import com.stepperdox.fusion.data.SurfaceNormal;
import com.stepperdox.fusion.data.Vertex;
import com.stepperdox.fusion.utility.Mathf;

/**
 * @author StepperDox Created on Oct 24, 2019
 */

public class ObjectLoader {

	public static Mesh loadOBJ(String file) {
		FileReader fr = null;
		InputStream in = Class.class.getResourceAsStream("res/test/objects/" + file);
		BufferedReader reader = null;
		if (in == null) {
			try {
				fr = new FileReader(new File("res/test/objects/" + file));
			} catch (FileNotFoundException e) {
				System.err.println("Cannot find: " + file + ", returning null");
				System.err.println(e.getLocalizedMessage());
				return null;
			}
			reader = new BufferedReader(fr);
		} else {
			reader = new BufferedReader(new InputStreamReader(in));
		}
		Material currentmtl = null;
		List<Vertex> vertices = new ArrayList<Vertex>();
		List<SurfaceNormal> normals = new ArrayList<SurfaceNormal>();
		List<Integer> indices = new ArrayList<Integer>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<SurfaceNormal> finalNormals = new ArrayList<SurfaceNormal>();
		List<Vector2f> finalTextures = new ArrayList<Vector2f>();
		String line;
		try {
			while (true) {
				line = reader.readLine();
				String[] cLine = line.split(" ");
				if (line.startsWith("mtllib ")) {
					MaterialLoader.loadMTL(cLine[1]);
				} else if (line.startsWith("v ")) {
					Vertex v = new Vertex();
					v.setPosition(new Vector3f(Float.parseFloat(cLine[1]), Float.parseFloat(cLine[2]), Float.parseFloat(cLine[3])));
					vertices.add(v);
				} else if (line.startsWith("vt ")) {
					textures.add(new Vector2f(Float.parseFloat(cLine[1]), Float.parseFloat(cLine[2])));
				} else if (line.startsWith("vn ")) {
					SurfaceNormal n = new SurfaceNormal(new Vector3f(Float.parseFloat(cLine[1]), Float.parseFloat(cLine[2]), Float.parseFloat(cLine[3])), new Vector3f(), new Vector3f());
					normals.add(n);
				} else if (line.startsWith("usemtl ")) {
					break;
				}
			}
			boolean canTest = true;
			while (line != null) {
				String[] cLine = line.split(" ");
				if (!line.startsWith("f ")) {
					if (line.startsWith("usemtl ")) {
						currentmtl = MaterialLoader.getMTL(cLine[1]);
						if (currentmtl == null) {
							System.err.println("Failed to fetch material: " + cLine[1]);
						} else {
							System.out.println("Now using material: " + cLine[1]);
						}
					}
					line = reader.readLine();
					continue;
				}
				if(canTest){
					try {
						String test = cLine[4];
						System.err.println("Mesh is not triangular, cannot parse it! | " + test);
						return null;
					} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
						System.out.println("Mesh is triangular, good!");
						canTest = false;
					}
				}
				String[] v1 = cLine[1].split("/");
				String[] v2 = cLine[2].split("/");
				String[] v3 = cLine[3].split("/");
								
				configureMesh(v1, vertices, normals, indices, textures, finalNormals, finalTextures, currentmtl);
				configureMesh(v2, vertices, normals, indices, textures, finalNormals, finalTextures, currentmtl);
				configureMesh(v3, vertices, normals, indices, textures, finalNormals, finalTextures, currentmtl);
				line = reader.readLine();
			}
			System.out.println("Closing reader...");
			reader.close();
		} catch (IOException e) {
			System.err.println("Error loading: " + file + ", returning null. Hope it wasn't important!");
			System.err.println(e.getLocalizedMessage());
			return null;
		}
		Mesh m = new Mesh(vertices, finalNormals, indices, finalTextures);
		System.out.println("Process complete, mesh loaded.");
		Mathf.calculateCOG(m);
		Mathf.calculateExtremeDist(m);
		System.out.println("Found physics variables... ");
		System.out.println("Extreme point distance: " + m.getExtremePointDist());
		System.out.println("Center of gravity: " + m.getCenterOfGravity().x + " " + m.getCenterOfGravity().y + " " + m.getCenterOfGravity().z);
		calculateNormalVectors(m);
		System.out.println("Calculated new Per-Surface normal vectors. Discarded Per-Vertex normals.");
		return m;
	}

	private static void configureMesh(String[] v, List<Vertex> verts, List<SurfaceNormal> norms, List<Integer> inds, List<Vector2f> texts, List<SurfaceNormal> Fnorms, List<Vector2f> Ftexts, Material currentmtl) {
		int vert = Integer.parseInt(v[0]) - 1;
		Vertex holder = verts.get(vert);
		holder.setMtl(currentmtl);
		verts.set(vert, holder);
		inds.add(vert);
		Ftexts.add(texts.get(Integer.parseInt(v[1]) - 1));
		Fnorms.add(norms.get(Integer.parseInt(v[2]) - 1));
	}
	
	private static void calculateNormalVectors(Mesh m){
		List<SurfaceNormal> newNorms = m.getNormals();
		Vector3f pos1 = null;
		Vector3f pos2 = null;
		Vector3f pos3 = null;
		Vector3f normal = new Vector3f();
		Vector3f tangent = new Vector3f();
		Vector3f bitangent = new Vector3f();
		Vector2f uv1 = null;
		Vector2f uv2 = null;
		Vector2f uv3 = null;
		int pointer = 0;
		int i = 0;
		for(Integer index:m.getIndices()){
			if(i == 0 || pos1 == null){
				if(pos1 == null){
					pos1 = m.getVertices().get(index).getPosition();
					uv1 = m.getTextures().get(index);
					normal = m.getNormals().get(index).getNormal();
					pointer = index;
					continue;
				}
				pos1 = m.getVertices().get(index).getPosition();
				uv1 = m.getTextures().get(index);
				normal = m.getNormals().get(index).getNormal();
				pointer = index;
				i++;
			}
			if(i == 1 || pos2 == null){
				if(pos2 == null){
					pos2 = m.getVertices().get(index).getPosition();
					uv2 = m.getTextures().get(index);
					continue;
				}
				pos2 = m.getVertices().get(index).getPosition();
				uv2 = m.getTextures().get(index);
				i++;
			}
			if(i == 2 || pos3 == null){
				if(pos3 == null){
					pos3 = m.getVertices().get(index).getPosition();
					uv3 = m.getTextures().get(index);
					continue;
				}
				pos3 = m.getVertices().get(index).getPosition();
				uv3 = m.getTextures().get(index);
				i = 0;
			}
			Vector3f e1 = new Vector3f();
			Vector3f e2 = new Vector3f();
			Vector2f duv1 = new Vector2f();
			Vector2f duv2 = new Vector2f();
			Vector3f.sub(pos2, pos1, e1);
			Vector3f.sub(pos3, pos1, e2);
			Vector2f.sub(uv2, uv1, duv1);
			Vector2f.sub(uv3, uv1, duv2);
			float f = 1.0f / (duv1.x * duv2.y - duv2.x * duv1.y);
			tangent.setX(f * (duv2.y * e1.x - duv1.y * e2.x));
			tangent.setY(f * (duv2.y * e1.y - duv1.y * e2.y));
			tangent.setZ(f * (duv2.y * e1.z - duv1.y * e2.z));
			bitangent.setX(f * (-duv2.x * e1.x + duv1.x * e2.x));
			bitangent.setY(f * (-duv2.x * e1.y + duv1.x * e2.y));
			bitangent.setZ(f * (-duv2.x * e1.z + duv1.x * e2.z));
			newNorms.set(pointer, new SurfaceNormal(normal, tangent, bitangent));
		}
		m.setNormals(newNorms);
	}
}
