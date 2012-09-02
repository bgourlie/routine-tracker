package com.fr.client.data;

public interface DataListener {
	public void onDataBind(DataCollection dataCollection);

	public void onDataAddedTemporary(DataObject dataObject);

	public void onDataAddedTemporary(DataObject dataObject, int index);

	public void onDataInsertedPermanent(DataObject dataObject, int index);

	public void onDataReplacedTemporary(DataObject dataObject, int index);

	public void onDataRemovedPermanent(int index);
}
