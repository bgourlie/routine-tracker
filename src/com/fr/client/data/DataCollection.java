package com.fr.client.data;

import java.util.Iterator;
import java.util.Vector;

import com.google.gwt.user.client.rpc.IsSerializable;

public final class DataCollection implements IsSerializable {

	private static final String REQUEST_PREFIX = "rq";

	private Vector dataCollection = new Vector();

	private Vector temporaryDataMap = null;

	public DataCollection() {

	}

	public void dataAddPermanent(DataObject dataObject) {

		if (temporaryDataMap == null) {
			initializeTemporaryDataMap();
		}

		dataCollection.add(dataObject);
		// keep em synced
		temporaryDataMap.setSize(dataCollection.size());
	}

	public int dataInsertPermanent(DataObject dataObject, String requestID) {
		int index = indexFromRequestID(requestID);
		dataCollection.setElementAt(dataObject, index);
		temporaryDataMap.setElementAt(null, index);
		return index;
	}

	public String dataAddTemporary(DataObject dataObject) {

		if (temporaryDataMap == null) {
			initializeTemporaryDataMap();
		}

		String tempDataID = generateRequestID();

		dataCollection.add(dataObject);
		temporaryDataMap.add(tempDataID);

		return tempDataID;
	}

	public String dataAddTemporary(DataObject dataObject, int index) {

		if (temporaryDataMap == null) {
			initializeTemporaryDataMap();
		}

		String tempDataID = generateRequestID();
		dataCollection.insertElementAt(dataObject, index);
		temporaryDataMap.insertElementAt(tempDataID, index);
		return tempDataID;
	}

	public int dataRemovePermanent(String requestID) {

		if (temporaryDataMap == null) {
			initializeTemporaryDataMap();
		}

		int index = indexFromRequestID(requestID);
		dataCollection.remove(index);
		temporaryDataMap.remove(index);
		return index;
	}

	public String dataRemoveTemporary(int index) {
		if (temporaryDataMap == null) {
			initializeTemporaryDataMap();
		}

		String deleteRequestID = generateRequestID();
		temporaryDataMap.setElementAt(deleteRequestID, index);

		return deleteRequestID;
	}

	public String dataReplaceTemporary(DataObject dataObject, int index) {

		if (temporaryDataMap == null) {
			initializeTemporaryDataMap();
		}

		String tempDataID = generateRequestID();

		dataCollection.setElementAt(dataObject, index);
		temporaryDataMap.setElementAt(tempDataID, index);

		return tempDataID;
	}

	private String generateRequestID() {
		return REQUEST_PREFIX + String.valueOf(Math.random());
	}

	public int getDataCount() {
		return dataCollection.size();
	}

	public DataObject getDataObject(int index) {
		return (DataObject) dataCollection.get(index);
	}

	private int indexFromRequestID(String requestID) {

		int index = 0;

		for (Iterator i = temporaryDataMap.iterator(); i.hasNext();) {

			String s = (String) i.next();
			// if the strings are equal
			if (s != null && s.equals(requestID)) {

				return index;
			}
			index++;
		}
		// if we get here it was not found and we should throw an exception, but
		// negative int for now
		return -1;
	}

	private void initializeTemporaryDataMap() {

		temporaryDataMap = new Vector();
		temporaryDataMap.setSize(dataCollection.size());
	}

	public Iterator iterator() {
		return dataCollection.iterator();
	}

}
