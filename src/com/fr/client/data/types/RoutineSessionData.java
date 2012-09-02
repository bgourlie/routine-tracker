package com.fr.client.data.types;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fr.client.data.DataObject;
import com.google.gwt.user.client.rpc.IsSerializable;

public class RoutineSessionData implements IsSerializable, Serializable,
		DataObject {

	private static final long serialVersionUID = 1L;

	private String note = null;

	private long routineSessionDataID;

	private long routineSessionID;

	private String status = null;

	private Timestamp recordedAt;

	public RoutineSessionData() {

	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setRoutineSessionDataID(long routineSessionDataID) {
		this.routineSessionDataID = routineSessionDataID;
	}

	public void setRoutineSessionID(long routineSessionID) {
		this.routineSessionID = routineSessionID;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public long getRoutineSessionDataID() {
		return routineSessionDataID;
	}

	public long getRoutineSessionID() {
		return routineSessionID;
	}

	public String getStatus() {
		return status;
	}

	public Timestamp getRecordedAt() {
		return recordedAt;
	}

	public void setRecordedAt(Timestamp recordedAt) {
		this.recordedAt = recordedAt;
	}

}
