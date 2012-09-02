package com.fr.client.data;

import java.util.Iterator;
import java.util.Vector;

public class DataListenerCollection extends Vector {

	static final long serialVersionUID = 3L;

	public void fireOnDataBind(DataCollection dataCollection) {
		System.out.println("firing onDataBind()");
		for (Iterator it = iterator(); it.hasNext();) {
			DataListener listener = (DataListener) it.next();
			listener.onDataBind(dataCollection);
		}
	}

	public void fireOnDataAddedTemporary(DataObject dataObject) {
		System.out.println("firing onTemporaryDataAdded(dataObject)");
		for (Iterator it = iterator(); it.hasNext();) {
			DataListener listener = (DataListener) it.next();
			listener.onDataAddedTemporary(dataObject);
		}
	}

	public void fireOnDataAddedTemporary(DataObject dataObject, int index) {
		System.out.println("firing onTemporaryDataAdded(dataObject, int)");
		for (Iterator it = iterator(); it.hasNext();) {
			DataListener listener = (DataListener) it.next();
			listener.onDataAddedTemporary(dataObject, index);
		}
	}

	public void fireOnDataInsertedPermanent(DataObject dataObject, int index) {
		System.out.println("firing onPermanentDataInserted()");
		for (Iterator it = iterator(); it.hasNext();) {
			DataListener listener = (DataListener) it.next();
			listener.onDataInsertedPermanent(dataObject, index);
		}
	}

	public void fireOnDataRemovedPermanent(int index) {
		System.out.println("firing onDataRemoved()");
		for (Iterator it = iterator(); it.hasNext();) {
			DataListener listener = (DataListener) it.next();
			listener.onDataRemovedPermanent(index);
		}
	}

	public void fireOnDataReplacedTemporary(DataObject dataObject, int index) {
		System.out.println("firing onDataReplacedTemporary()");
		for (Iterator it = iterator(); it.hasNext();) {
			DataListener listener = (DataListener) it.next();
			listener.onDataReplacedTemporary(dataObject, index);
		}
	}

}
