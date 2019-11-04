package com.stepperdox.fusion.data;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import com.stepperdox.fusion.engine.WindowEngine;
import com.stepperdox.fusion.utility.Mathf;

/**
*  @author StepperDox
*  Created on Oct 22, 2019
*/

public class Transform {
	private Vector3f position = new Vector3f();
	private Vector3f rotation = new Vector3f();
	private Vector3f scale = new Vector3f();
	private Vector3f velocity = new Vector3f();
	private Vector3f angularVelocity = new Vector3f();
	private float friction = 0.01f;
	private Matrix4f transform;
	
	public Transform(Vector3f position, Vector3f rotation, Vector3f scale, float friction){
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.friction = friction;
	}
	
	public void callUpdate(){
		velocity.setX(velocity.x - friction);
		velocity.setY(velocity.y - friction);
		velocity.setZ(velocity.z - friction);
		angularVelocity.setX(angularVelocity.x - friction);
		angularVelocity.setY(angularVelocity.y - friction);
		angularVelocity.setZ(angularVelocity.z - friction);
		checkPhys();
		Mathf.scaleVector(velocity, friction);
		Mathf.scaleVector(angularVelocity, friction);
		position.set(-velocity.x, -velocity.y, -velocity.z);
		rotation.set(-angularVelocity.x, -angularVelocity.y, -angularVelocity.z);
	}
	
	public void applyWorldForce(Vector3f direction, float force){
		position.setX(direction.x * force);
		position.setY(direction.y * force);
		position.setZ(direction.z * force);
	}
	
	public void applyRelativeForce(Vector3f direction, float force){
		direction.x = direction.x * force;
		direction.y = direction.y * force;
		direction.z = direction.z * force;
		Vector3f dist = new Vector3f();
		dist.x = (float) (direction.x * Math.sin(Math.toRadians(rotation.y)));
		dist.y = (float) (direction.y * Math.sin(Math.toRadians(rotation.x)));
		dist.y += (float) (direction.y * Math.sin(Math.toRadians(rotation.z)));
		dist.z = (float) (direction.z * Math.cos(Math.toRadians(rotation.y)));
		if(dist.x > force){
			dist.x = force;
		}
		if(dist.y > force){
			dist.y = force;
		}
		if(dist.z > force){
			dist.x = force;
		}
		velocity.setX(velocity.getX() + dist.x);
		velocity.setY(velocity.getY() + dist.y);
		velocity.setZ(velocity.getZ() + dist.z);
	}
	
	public void applyTorque(Vector3f direction, float torque){
		angularVelocity.setX(direction.x * torque);
		angularVelocity.setY(direction.y * torque);
		angularVelocity.setZ(direction.z * torque);
	}
	
	public void scale(Vector3f scale){
		this.scale = scale;
	}
	
	public void scaleWithTime(Vector3f scale){
		scale.x = scale.x * WindowEngine.getUpdateTime();
		scale.y = scale.y * WindowEngine.getUpdateTime();
		scale.z = scale.z * WindowEngine.getUpdateTime();
		Vector3f.add(scale, this.scale, this.scale);
	}
	
	public Vector3f getVelocity(){
		return velocity;
	}
	
	public Vector3f getAngularVelocity(){
		return angularVelocity;
	}
	
	public void transformAsChild(Matrix4f transform){
		Matrix4f.mul(transform, this.transform, this.transform);
	}
	
	public Matrix4f getTransform(){
		transform = Mathf.createTransformationMatrix(position, rotation, scale);
		return transform;
	}

	public void rotateLocal(Vector3f direction, float speed){
		rotation.setX(rotation.x + direction.x);
		rotation.setY(rotation.y + direction.y);
		rotation.setZ(rotation.z + direction.z);
	}
	
	private void checkPhys(){
		if(velocity.x <= 0){
			velocity.setX(0);
		}
		if(velocity.y <= 0){
			velocity.setY(0);
		}
		if(velocity.z <= 0){
			velocity.setZ(0);
		}
		if(angularVelocity.x <= 0){
			angularVelocity.setX(0);
		}
		if(angularVelocity.y <= 0){
			angularVelocity.setY(0);
		}
		if(angularVelocity.z <= 0){
			angularVelocity.setZ(0);
		}
	}
}
