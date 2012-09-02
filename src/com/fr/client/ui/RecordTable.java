package com.fr.client.ui;

import com.google.gwt.user.client.ui.FlexTable;

class RecordTable extends FlexTable {

	private String[] columnHeaders = null;

	private String[] alternatingRowStyles = null;

	void setColumnHeaders(String[] columnHeaders) {
		this.columnHeaders = columnHeaders;
	}

	void setAlternatingRowStyles(String[] alternatingColumnStyles) {
		if (alternatingColumnStyles.length < 2) {
			throw new UnsupportedOperationException(
					"alternativeColumnStyles must contain at least two elements");
		}
		this.alternatingRowStyles = alternatingColumnStyles;
	}

	public RecordTable() {
		super.setStyleName("fr-RecordTable");
	}

	public RecordTable(String style) {
		super.setStyleName(style);
	}

	void init() {
		/*
		 * create table headers
		 */
		super.insertRow(0);

		if (columnHeaders == null || columnHeaders.length == 0) {
			addCell(0);
			super.setHTML(0, 0, "&nbsp;");

		} else {
			for (int i = 0; i < columnHeaders.length; i++) {
				addCell(0);
				setText(0, i, columnHeaders[i]);
			}
			super.getRowFormatter().setStyleName(0, "fr-RecordTableHeader");
		}

	}

	void applyAlternatingRowStyles(int startRow) {
		int styleCount = alternatingRowStyles.length;
		int currentStyleIndex = startRow % styleCount;

		if (startRow == 0) {
			startRow = 1;
		}

		for (int currentRow = startRow; currentRow <= getRowCount() - 1; currentRow++) {
			getRowFormatter().setStyleName(currentRow,
					alternatingRowStyles[currentStyleIndex++]);
			if (currentStyleIndex > styleCount - 1) {
				currentStyleIndex = 0;
			}
		}
	}

	public void removeRow(int row) {

		if (row < 1) {
			throw new IndexOutOfBoundsException();
		}

		super.removeRow(row);

		if (row <= super.getRowCount()) {
			applyAlternatingRowStyles(row);
		}
	}

	public void clearRecords() {
		while (super.getRowCount() > 1) {
			super.removeRow(1);
		}
	}

}
