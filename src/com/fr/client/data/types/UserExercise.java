package com.fr.client.data.types;

import java.io.Serializable;

import com.fr.client.data.DataObject;
import com.google.gwt.user.client.rpc.IsSerializable;

public class UserExercise implements IsSerializable, Serializable, DataObject {

	private static final long serialVersionUID = 1L;

	private String defaultUOM = null;

	private boolean display = true;

	private String exerciseName = null;

	private String exerciseType = null;

	private long userID;

	private String note = null;

	private int orderIndex = -1;

	private long userExerciseID = -1;

	public UserExercise() {
	}

	public String getDefaultUOM() {
		return defaultUOM;
	}

	public boolean getDisplay() {
		return display;
	}

	public String getExerciseName() {
		return exerciseName;
	}

	public String getExerciseType() {
		return exerciseType;
	}

	public String getNote() {
		return note;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public long getUserExerciseID() {
		return userExerciseID;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public void setDefaultUOM(String defaultUOM) {
		this.defaultUOM = defaultUOM;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public void setExerciseType(String exerciseType) {
		this.exerciseType = exerciseType;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setUserExerciseID(long userExerciseID) {
		this.userExerciseID = userExerciseID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

}
