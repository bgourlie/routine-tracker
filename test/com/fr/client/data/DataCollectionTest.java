package com.fr.client.data;

import com.google.gwt.junit.client.GWTTestCase;

public class DataCollectionTest extends GWTTestCase {

	private DataCollection dataCollection = new DataCollection();

	private DataObject temporaryData;

	private DataObject permanentData;

	private String requestID = null;

	public String getModuleName() {
		return "com.fr.FRTest";
	}

	public void testDataAdd() {
		requestID = dataCollection.dataAddTemporary(temporaryData);
		assertNotNull(requestID);
		assertEquals(dataCollection.getDataObject(0), temporaryData);
		assertEquals(dataCollection.getDataCount(), 1);
		dataCollection.dataInsertPermanent(permanentData, requestID);
		assertEquals(dataCollection.getDataObject(0), permanentData);
		assertEquals(dataCollection.getDataCount(), 1);
	}

	public void testDataReplace() {
		dataCollection.dataAddPermanent(permanentData);
		requestID = dataCollection.dataReplaceTemporary(temporaryData, 0);
		assertNotNull(requestID);
		assertEquals(dataCollection.getDataObject(0), temporaryData);
		assertEquals(dataCollection.getDataCount(), 1);
		dataCollection.dataInsertPermanent(permanentData, requestID);
		assertEquals(dataCollection.getDataObject(0), permanentData);
		assertEquals(dataCollection.getDataCount(), 1);
	}

	public void testDataRemove() {
		dataCollection.dataAddPermanent(permanentData);
		requestID = dataCollection.dataRemoveTemporary(0);
		assertNotNull(requestID);
		/* make sure data isn't removed until its been permanently removed */
		assertEquals(dataCollection.getDataCount(), 1);
		dataCollection.dataRemovePermanent(requestID);
		assertEquals(dataCollection.getDataCount(), 0);
	}

}
