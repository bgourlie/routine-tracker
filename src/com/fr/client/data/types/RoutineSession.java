package com.fr.client.data.types;

import java.io.Serializable;

import com.fr.client.data.DataObject;
import com.google.gwt.user.client.rpc.IsSerializable;

public class RoutineSession implements IsSerializable, Serializable, DataObject {

	private static final long serialVersionUID = 1L;

	private int orderIndex;

	private long routineSessionID;

	private String routineSessionName;

	private long userRoutineID;

	public RoutineSession() {
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public void setRoutineSessionID(long routineSessionID) {
		this.routineSessionID = routineSessionID;
	}

	public void setRoutineSessionName(String routineSessionName) {
		this.routineSessionName = routineSessionName;
	}

	public void setUserRoutineID(long userRoutineID) {
		this.userRoutineID = userRoutineID;
	}

	public void debugPrint() {
		System.out.println("RoutineSessionID:	" + getRoutineSessionID());
		System.out.println("UserRoutineID:	" + getUserRoutineID());
		System.out.println("RoutineSessionName:	" + getRoutineSessionName());
		System.out.println("OrderIndex:	" + getOrderIndex());
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public long getRoutineSessionID() {
		return routineSessionID;
	}

	public String getRoutineSessionName() {
		return routineSessionName;
	}

	public long getUserRoutineID() {
		return userRoutineID;
	}
}
