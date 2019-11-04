package com.stepperdox.fusion.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
*  @author StepperDox
*  Created on Oct 14, 2019
*/

public abstract class ShaderEngine {
	private int shader;
	private int vertID;
	private int fragID;
	private FloatBuffer matBuf = BufferUtils.createFloatBuffer(16);
	
	public ShaderEngine(String vert, String frag){
		vertID = loadShaderFile(vert, GL20.GL_VERTEX_SHADER);
		fragID = loadShaderFile(vert, GL20.GL_FRAGMENT_SHADER);
		shader = GL20.glCreateProgram();
		GL20.glAttachShader(shader, vertID);
		GL20.glAttachShader(shader, fragID);
		sendToPipeline();
		GL20.glLinkProgram(shader);
		GL20.glValidateProgram(shader);
		locateUniforms();
	}
	
	protected int locateUniform(String name){
		return GL20.glGetUniformLocation(shader, name);
	}
	
	public void callStart(){
		GL20.glUseProgram(shader);
	}
	
	public void callStop(){
		GL20.glUseProgram(0);
	}
	
	public void callExit(){
		callStop();
		GL20.glDetachShader(shader, vertID);
		GL20.glDetachShader(shader, fragID);
		GL20.glDeleteShader(vertID);
		GL20.glDeleteShader(fragID);
		GL20.glDeleteProgram(shader);
	}
	
	protected void pipelineSender(int attrib, String variable){
		GL20.glBindAttribLocation(shader, attrib, variable);
	}
	
	protected void loadFloat(int var, float data){
		GL20.glUniform1f(var, data);
	}
	
	protected void loadInt(int var, int data){
		GL20.glUniform1i(var, data);
	}
	
	protected void loadVector3f(int var, Vector3f data){
		GL20.glUniform3f(var, data.x, data.y, data.z);
	}
	
	protected void loadVector2f(int var, Vector2f data){
		GL20.glUniform2f(var, data.x, data.y);
	}
	
	protected void loadBoolean(int var, boolean data){
		int dat = 0;
		if(data){
			dat = 1;
		}
		GL20.glUniform1i(var, dat);
	}
	
	protected void loadMatrix4f(int var, Matrix4f data){
		data.store(matBuf);
		matBuf.flip();
		GL20.glUniformMatrix4(var, false, matBuf);
	}
	
	@SuppressWarnings("deprecation")
	private int loadShaderFile(String file, int type){
		file = "src/com/stepperdox/fusion/shaderfiles/" + file;
		StringBuilder src = new StringBuilder(file);
		try{
			InputStream in = Class.class.getResourceAsStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = reader.readLine()) != null){
				src.append(line).append("\n");
			}
		}catch(IOException e){
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		int id = GL20.glCreateShader(type);
		GL20.glShaderSource(id, src);
		GL20.glCompileShader(id);
		if(GL20.glGetShader(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
			System.err.println("Failed to Compile Shader: " + file);
			System.err.println(GL20.glGetShaderInfoLog(id, 750));
			System.exit(-1);
		}
		return id;
	}
	
	public abstract void sendToPipeline();
	
	public abstract void locateUniforms();
}
