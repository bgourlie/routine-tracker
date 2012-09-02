package com.fr.client.data.types;

import com.fr.client.data.DataObject;
import com.google.gwt.user.client.rpc.IsSerializable;

public class SessionExercise implements IsSerializable, DataObject {

	private String note;

	private int orderIndex;

	private long routineSessionID;

	private long sessionExerciseID;

	private int target;

	private long userExerciseID;

	public SessionExercise() {
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public void setRoutineSessionID(long routineSessionID) {
		this.routineSessionID = routineSessionID;
	}

	public void setSessionExerciseID(long sessionExerciseID) {
		this.sessionExerciseID = sessionExerciseID;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public void setUserExerciseID(long userExerciseID) {
		this.userExerciseID = userExerciseID;
	}

	public String getNote() {
		return note;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public long getRoutineSessionID() {
		return routineSessionID;
	}

	public long getSessionExerciseID() {
		return sessionExerciseID;
	}

	public int getTarget() {
		return target;
	}

	public long getUserExerciseID() {
		return userExerciseID;
	}
}
