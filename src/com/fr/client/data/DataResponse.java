package com.fr.client.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DataResponse implements IsSerializable {

	private DataCollection dataCollection = null;

	private DataObject dataObject = null;

	private String responseID = null;

	public DataResponse() {
	}

	public DataResponse(String responseID, DataCollection dataCollection) {
		this.responseID = responseID;
		this.dataCollection = dataCollection;
	}

	public DataResponse(String responseID, DataObject dataObject) {
		this.responseID = responseID;
		this.dataObject = dataObject;
	}

	public DataCollection getDataCollection() {
		return dataCollection;
	}

	public DataObject getDataObject() {
		return dataObject;
	}

	public String getRequestID() {
		return responseID;
	}

	public void setDataRows(DataCollection dataRows) {
		this.dataCollection = dataRows;
	}

	public void setResponseID(String requestID) {
		this.responseID = requestID;
	}

}
