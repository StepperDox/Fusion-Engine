package com.stepperdox.fusion.component;

/**
*  @author StepperDox
*  Created on Oct 23, 2019
*/

public abstract class Component{

	//Initialize Everything (Recallable)
	public abstract void callInit();
	
	//Update Everything
	public abstract void callUpdate();
	
	//Un-Initialize Everything (Recallable)
	public abstract void callCleanup();
	
	//Shut Everything Down
	public abstract void callExit();
}
