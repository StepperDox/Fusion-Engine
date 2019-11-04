package com.stepperdox.fusion.gameobjects;

import java.util.List;
import java.util.Map;
import com.stepperdox.fusion.data.Mesh;
import com.stepperdox.fusion.loaders.ObjectLoader;
import com.stepperdox.fusion.loaders.TextureLoader;

/**
*  @author StepperDox
*  Created on Oct 16, 2019
*/

public class ModelBuilder {
	
	public static Model buildModel(Map<String, List<String>> meshes){
		Model ret = new Model(null, null, null);
		int ind = 0;
		for(String s:meshes.keySet()){
			Mesh m = ObjectLoader.loadOBJ(s);
			int index = 0;
			for(String i:meshes.get(s)){
				switch(index){
					default:
						System.err.println("Wait wha- HOW DID THIS HAPPEN!?!");
					case(0):
						m.setAmbient(TextureLoader.loadTexture(i));
					case(1):
						m.setDiffuse(TextureLoader.loadTexture(i));
					case(2):
						m.setSpecular(TextureLoader.loadTexture(i));
					case(3):
						m.setNormal(TextureLoader.loadTexture(i));
					case(4):
						m.setGlow(TextureLoader.loadTexture(i));
				}
			}
			ret.setLOD(ind, m);
			ind++;
		}
		return ret;
	}
}
