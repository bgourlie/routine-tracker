package com.fr.client.data;

public class DataBroker {
	private DataCollection dataCollection = null;

	private DataListenerCollection listeners = null;

	public void addDataListener(DataListener listener) {
		if (listeners == null) {
			listeners = new DataListenerCollection();
		}
		listeners.add(listener);

	}

	public String dataAddTemporary(DataObject dataObject) {
		String retval = dataCollection.dataAddTemporary(dataObject);
		if (listeners != null) {
			listeners.fireOnDataAddedTemporary(dataObject);
		}
		return retval;
	}

	public String dataAddTemporary(DataObject dataObject, int index) {
		String retval = dataCollection.dataAddTemporary(dataObject, index);

		if (listeners != null) {
			listeners.fireOnDataAddedTemporary(dataObject, index);
		}

		return retval;
	}

	public void dataInsertPermanent(DataResponse response) {

		int index = dataCollection.dataInsertPermanent(
				response.getDataObject(), response.getRequestID());

		if (listeners != null) {
			listeners.fireOnDataInsertedPermanent(response.getDataObject(),
					index);
		}
	}

	public void dataRemovePermanent(DataResponse response) {

		int index = dataCollection.dataRemovePermanent(response.getRequestID());

		if (listeners != null) {
			listeners.fireOnDataRemovedPermanent(index);
		}
	}

	public String dataRemoveTemporary(int index) {
		return dataCollection.dataRemoveTemporary(index);
	}

	public String dataReplaceTemporary(DataObject dataObject, int index) {
		String retval = dataCollection.dataReplaceTemporary(dataObject, index);
		if (listeners != null) {
			listeners.fireOnDataReplacedTemporary(dataObject, index);
		}
		return retval;
	}

	public int getDataCount() {
		return dataCollection.getDataCount();
	}

	public Object getDataObject(int index) {
		return dataCollection.getDataObject(index);
	}

	public void removeDataListener(DataListener listener) {
		if (listeners != null) {
			listeners.remove(listener);
		}
	}

	public void setDataCollection(DataCollection dataCollection) {
		this.dataCollection = dataCollection;

		if (listeners != null) {
			listeners.fireOnDataBind(dataCollection);
		}
	}

}
