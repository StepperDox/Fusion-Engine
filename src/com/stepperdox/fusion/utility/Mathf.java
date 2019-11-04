package com.stepperdox.fusion.utility;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import com.stepperdox.fusion.data.Mesh;
import com.stepperdox.fusion.data.Vertex;

/**
*  @author StepperDox
*  Created on Oct 16, 2019
*/

public class Mathf {
	
	public static void calculateCOG(Mesh m){
		Vector3f newCOG = new Vector3f();
		int i = 0;
		for(Vertex v:m.getVertices()){
			newCOG.x = v.getPosition().x;
			newCOG.y = v.getPosition().y;
			newCOG.z = v.getPosition().z;
			i++;
		}
		newCOG.x = newCOG.x / i;
		newCOG.y = newCOG.y / i;
		newCOG.z = newCOG.z / i;
		m.setCenterOfGravity(newCOG);
	}
	
	public static void calculateExtremeDist(Mesh m){
		float farthestDist = 0.0f;
		for(Vertex v:m.getVertices()){
			float dist = findVec3Distance(v.getPosition(), m.getCenterOfGravity());
			if(dist > farthestDist){
				farthestDist = dist;
			}
		}
		m.setExtremePointDist(farthestDist);
	}
	
	public static Matrix4f createTransformationMatrix(Vector3f position, Vector3f rotation, Vector3f scale){
		Matrix4f mat = new Matrix4f();
		mat.setIdentity();
		mat.translate(position);
		Matrix4f.rotate((float) Mathf.toRadians(rotation.x), new Vector3f(1, 0, 0), mat, mat);
		Matrix4f.rotate((float) Mathf.toRadians(rotation.y), new Vector3f(0, 1, 0), mat, mat);
		Matrix4f.rotate((float) Mathf.toRadians(rotation.z), new Vector3f(0, 0, 1), mat, mat);
		Matrix4f.scale(scale, mat, mat);
		return mat;
	}
	
	public static double toRadians(float degrees){
		return degrees * (Math.PI / 180);
	}

	public static float[] convertVec3ToFloatArray(List<Vector3f> data){
		float[] out = new float[data.size() * 3];
		int index = 0;
		for(int i = 0; i < out.length; i++){
			out[i] = data.get(index).x;
			out[i++] = data.get(index).y;
			out[i++] = data.get(index).z;
			index++;
		}
		return out;
	}
	
	public static float[] convertVec2ToFloatArray(List<Vector2f> data){
		float[] out = new float[data.size() * 2];
		int index = 0;
		for(int i = 0; i < out.length; i++){
			out[i] = data.get(index).x;
			out[i++] = data.get(index).y;
			index++;
		}
		return out;
	}
	
	public static FloatBuffer createFloatBuffer(float[] data){
		FloatBuffer out = BufferUtils.createFloatBuffer(data.length);
		out.put(data);
		out.flip();
		return out;
	}
	
	public static IntBuffer createIntBuffer(int[] data){
		IntBuffer out = BufferUtils.createIntBuffer(data.length);
		out.put(data);
		out.flip();
		return out;
	}
	
	public static Vector3f scaleVector(Vector3f v, float s){
		return new Vector3f(v.x * s, v.y * s, v.z * s);
	}
	
	public static float findVec3Distance(Vector3f point1, Vector3f point2){
		float x = (point2.x - point1.x) * (point2.x - point1.x);
		float y = (point2.y - point1.y) * (point2.y - point1.y);
		float z = (point2.z - point1.z) * (point2.z - point1.z);
		return (float) Math.sqrt(x + y + z);
	}
}
