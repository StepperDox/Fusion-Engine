package com.stepperdox.fusion.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

/**
*  @author StepperDox
*  Created on Oct 16, 2019
*/

public class WindowEngine {
	private static String TITLE = "Fusion Engine BETA";
	private static DisplayMode DEFAULT = new DisplayMode(1024, 720);
	private static int MAX_FPS = 120;
	private static boolean VSYNC = false;
	private static boolean RESIZABLE = false;
	private static boolean FULLSCREEN = true;
	private static boolean MULTISAMPLING = true;
	
	private static long lastFrameTime;
	private static float delta;
	private static boolean isFull;
	
	public static void callStart(){
		ContextAttribs attrib = new ContextAttribs(3, 2).withProfileCore(true).withForwardCompatible(true);
		try{
			Display.setDisplayMode(DEFAULT);
			Display.setTitle(TITLE);
			Display.create(new PixelFormat().withSamples(4).withDepthBits(24), attrib);
			Display.setResizable(RESIZABLE);
			Display.setVSyncEnabled(VSYNC);
			if(MULTISAMPLING){
				GL11.glEnable(GL13.GL_MULTISAMPLE);
			}
		}catch(LWJGLException e){
			e.printStackTrace();
		}
	}
	
	public static void callUpdate(){
		if(Display.wasResized()){
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		}
		try {
			if(Keyboard.isKeyDown(Keyboard.KEY_F11) && FULLSCREEN && !isFull){
				Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
				isFull = true;
			}else if(Keyboard.isKeyDown(Keyboard.KEY_F11) && isFull){
				Display.setDisplayMode(DEFAULT);
				isFull = false;
			}
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Display.setTitle(TITLE + " | Frame Time: " + getUpdateTime());
		Display.sync(MAX_FPS);
		Display.update();
		long cTime = getCurrentTime();
		delta = (float) (cTime - lastFrameTime / 1000.0);
		lastFrameTime = cTime;
	}
	
	public static void callExit(){
		Display.destroy();
	}
	
	public static float getUpdateTime(){
		return delta;
	}
	
	public static boolean exitCalled(){
		return Display.isCloseRequested();
	}
	
	private static long getCurrentTime(){
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}
}
