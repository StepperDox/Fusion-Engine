package com.stepperdox.fusion.game;

import com.stepperdox.fusion.engine.ComponentEngine;
import com.stepperdox.fusion.engine.WindowEngine;

public class Game {
	
	//Game constructor, creates a window and a game loop;
	public Game(){
		//startThis();
		WindowEngine.callStart();
		ComponentEngine.callStart();
		
		
		while(!WindowEngine.exitCalled()){
			//doThis();
			ComponentEngine.callUpdate();
			
			WindowEngine.callUpdate();
		}
		//stopThis();
		WindowEngine.callExit();
		ComponentEngine.callExit();
		
	}
}
