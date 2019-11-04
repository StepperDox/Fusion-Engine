package com.stepperdox.fusion.engine;

import java.util.ArrayList;
import java.util.List;

import com.stepperdox.fusion.component.Component;

/**
*  @author StepperDox
*  Created on Oct 23, 2019
*/

public class ComponentEngine {
	private static List<Component> components = new ArrayList<Component>();
	
	public static void callStart(){
		for(Component c:components){
			c.callInit();
		}
	}
	
	public static void callUpdate(){
		for(Component c:components){
			c.callUpdate();
		}
	}
	
	public static void callExit(){
		for(Component c:components){
			c.callExit();
		}
	}
	
	public static void registerComponent(Component c){
		components.add(c);
		c.callInit();
	}

	public static void removeComponent(int index){
		components.remove(index);
	}
	
	public static void removeComponent(Component index){
		components.remove(index);
	}
}
