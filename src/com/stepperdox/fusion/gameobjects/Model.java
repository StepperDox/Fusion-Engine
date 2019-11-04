package com.stepperdox.fusion.gameobjects;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import com.stepperdox.fusion.component.Component;
import com.stepperdox.fusion.data.Mesh;
import com.stepperdox.fusion.data.Transform;

/**
*  @author StepperDox
*  Created on Oct 16, 2019
*/

public class Model {
	//Components for this model
	private List<Component> coms = null;
	//Transform data for this model
	private Transform local = new Transform(new Vector3f(), new Vector3f(), new Vector3f(), 0.01f);
	private Transform parent = null;
	//Determines if the model should take up update time
	private boolean awake = false;
	//Determines if the model should be rendered
	private boolean renderPass = true;
	//Required Level Of Detail mesh for this model
	private Mesh LOD_ONE = null;
	//Optional Level Of Detail meshes for this model
	private Mesh LOD_TWO = null;
	private Mesh LOD_THREE = null;
	private Mesh LOD_FOUR = null;
	private Mesh LOD_FIVE = null;
	
	
	public Model(Mesh LOD_ONE, Transform startPos, Transform parent){
		this.LOD_ONE = LOD_ONE;
		if(startPos != null){
			local = startPos;
		}
		if(parent != null){
			this.parent = parent;
		}
	}
	
	public void callUpdate(){
		if(awake){
			if(parent != null){
				local.transformAsChild(parent.getTransform());
			}
			local.callUpdate();
		}
	}
	
	public void setTransform(Transform t){
		local = t;
	}
	
	public Transform getTransform(){
		return local;
	}
	
	public void setParentTransform(Transform t){
		parent = t;
	}
	
	public Transform getParentTransform(){
		return parent;
	}
	
	public boolean getRenderPass(){
		return renderPass;
	}
	
	public void setRenderPass(boolean render){
		renderPass = render;
	}
	
	public boolean getAwake(){
		return awake;
	}
	
	public void setAwake(boolean awake){
		this.awake = awake;
	}
	
	public void setLOD(int LOD, Mesh mesh){
		if(LOD == 0){
			LOD_ONE = mesh;
		}else if(LOD == 1){
			LOD_TWO = mesh;
		}else if(LOD == 2){
			LOD_THREE = mesh;
		}else if(LOD == 3){
			LOD_FOUR = mesh;
		}else if(LOD == 4){
			LOD_FIVE = mesh;
		}else{
			System.err.println("Cannot set LOD:" + LOD);
		}
	}
	
	public Mesh getLOD(int LOD){
		switch(LOD){
			case(0):
				return LOD_ONE;
			case(1):
				return LOD_TWO;
			case(2):
				return LOD_THREE;
			case(3):
				return LOD_FOUR;
			case(4):
				return LOD_FIVE;
			default:
				System.err.println("Cannot get LOD:" + LOD + " because it does not exist!");
				return null;
		}
	}
	
	//Returns the index of the added component
	public int addComponent(Component c){
		coms.add(c);
		return coms.indexOf(c);
	}
	
	public void removeComponent(int index){
		coms.remove(index);
	}
}
