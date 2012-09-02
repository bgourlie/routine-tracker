package com.fr.client.data.types;

import java.io.Serializable;

import com.fr.client.data.DataObject;
import com.google.gwt.user.client.rpc.IsSerializable;

public class UserRoutine implements IsSerializable, Serializable, DataObject {

	private static final long serialVersionUID = 1L;

	private String routineName = null;

	private long userRoutineID;

	private long userID;

	private boolean locked;

	public UserRoutine() {

	}

	public String getRoutineName() {
		return routineName;
	}

	public void setRoutineName(String routineName) {
		this.routineName = routineName;
	}

	public long getUserRoutineID() {
		return userRoutineID;
	}

	public void setUserRoutineID(long userRoutineID) {
		this.userRoutineID = userRoutineID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public long getUserID() {
		return userID;
	}

	public boolean getLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}
