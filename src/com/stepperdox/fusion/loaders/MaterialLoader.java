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
import org.lwjgl.util.vector.Vector3f;
import com.stepperdox.fusion.data.Material;

/**
*  @author StepperDox
*  Created on Oct 23, 2019
*/

public class MaterialLoader {
	private static List<Material> materials = new ArrayList<Material>();
	
	public static void loadMTL(String file){
		System.out.println("Parsing Material File (" + file + ")...");
		FileReader fr = null;
		InputStream in = Class.class.getResourceAsStream("res/test/objects/" + file);
		BufferedReader reader = null;
		if(in == null){
			try {
				fr = new FileReader(new File("res/test/objects/" + file));
			} catch (FileNotFoundException e) {
				System.err.println("Cannot find: " + file + ", returning null");
				System.err.println(e.getLocalizedMessage());
				return;
			}
			reader = new BufferedReader(fr);
		}else{
			reader = new  BufferedReader(new InputStreamReader(in));
		}
		String line;
		Material currentmtl = new Material();
		try {
			while(true){
					line = reader.readLine();
					String[] cLine = null;
					if(line != null){
						cLine = line.split(" ");
					}else{
						materials.add(currentmtl);
						System.out.println("Added Material: " + currentmtl.getName());
						break;
					}
					if(line.startsWith("newmtl ")){
						currentmtl = new Material();
						currentmtl.setName(cLine[1]);
					}else if(line.startsWith("Ka ")){
						currentmtl.setAmbient(new Vector3f(Float.parseFloat(cLine[1]), Float.parseFloat(cLine[2]), Float.parseFloat(cLine[3])));
					}else if(line.startsWith("Kd ")){
						currentmtl.setDiffuse(new Vector3f(Float.parseFloat(cLine[1]), Float.parseFloat(cLine[2]), Float.parseFloat(cLine[3])));
					}else if(line.startsWith("Ks ")){
						currentmtl.setSpecular(new Vector3f(Float.parseFloat(cLine[1]), Float.parseFloat(cLine[2]), Float.parseFloat(cLine[3])));
					}else if(line.startsWith("d ")){
						currentmtl.setTransparency(1f - Float.parseFloat(cLine[1]));
					}else if(line.startsWith("Tr ")){
						currentmtl.setTransparency(Float.parseFloat(cLine[1]));
					}else if(line.startsWith("illum ")){
						currentmtl.setIllumination(Integer.parseInt(cLine[1]));
					}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static Material getMTL(String name){
		for(Material m:materials){
			if(m.getName().equals(name)){
				return m;
			}
		}
		System.out.println("Material array has: " + materials.size() + " element(s).");
		System.err.println("Cannot find material: " + name + ", returning " + materials.get(0).getName());
		return materials.get(0);
	}
}
