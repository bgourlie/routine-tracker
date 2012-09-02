package com.fr.client.data.types;

import java.io.Serializable;

import com.fr.client.data.DataObject;
import com.google.gwt.user.client.rpc.IsSerializable;

public class SessionExerciseData implements Serializable, IsSerializable,
		DataObject {

	private static final long serialVersionUID = 1L;

	private int actual = -1;

	private String note = null;

	private long routineSessionDataID;

	private long sessionExerciseID;

	private long sessionExerciseDataID;

	private String status = null;

	public SessionExerciseData() {

	}

	public void setActual(int actual) {
		this.actual = actual;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getActual() {
		return actual;
	}

	public String getNote() {
		return note;
	}

	public long getRoutineSessionDataID() {
		return routineSessionDataID;
	}

	public long getSessionExerciseID() {
		return sessionExerciseID;
	}

	public void setSessionExerciseID(long sessionExerciseID) {
		this.sessionExerciseID = sessionExerciseID;
	}

	public void setRoutineSessionDataID(long routineSessionDataID) {
		this.routineSessionDataID = routineSessionDataID;
	}

	public String getStatus() {
		return status;
	}

	public long getSessionExerciseDataID() {
		return sessionExerciseDataID;
	}

	public void setSessionExerciseDataID(long sessionExerciseDataID) {
		this.sessionExerciseDataID = sessionExerciseDataID;
	}

}
