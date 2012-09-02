package com.fr.client.ui;

import java.util.Iterator;

import com.fr.client.data.DataCollection;
import com.fr.client.data.DataListener;
import com.fr.client.data.DataObject;
import com.fr.client.data.types.ExerciseUOM;
import com.fr.client.data.types.UserExercise;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class UserExerciseEditor extends Composite implements TableListener,
		ClickListener, KeyboardListener, ChangeListener, DataListener {

	private static final int COLUMN_NAME = 0;

	private static final int COLUMN_NOTE = 3;

	private static final int COLUMN_TYPE = 1;

	private static final int COLUMN_UOM = 2;

	private static final String HTML_CANCEL_BUTTON = "<button>Cancel</button>";

	private static final String HTML_DELETE_BUTTON = "<button>Delete</button>";

	private static final String HTML_EDIT_BUTTON = "<button>Edit</button>";

	private static final String HTML_SAVE_BUTTON = "<button>Save</button>";

	private static final int NO_ACTIVE_RECORD = -1;

	private static final int OPTION_DELETE_CANCEL = 1;

	private static final int OPTION_EDIT_SAVE = 0;

	private static final int STATE_EDIT_ROW = 1;

	private static final int STATE_NEW_ROW = 2;

	private static final int STATE_PENDING = 3;

	private static final int STATE_VIEWING = 0;

	private int activeRecord = NO_ACTIVE_RECORD;

	private final Button btnNewExercise = new Button("Add Exercise");

	private final HorizontalPanel commandPanel = new HorizontalPanel();

	private DataCollection exerciseUOMs = null;

	private final ListBox lstType = new ListBox();

	private final ListBox lstUOM = new ListBox();

	private final RecordTable optionTable = new RecordTable("optionTable");

	private final DockPanel panel = new DockPanel();

	private final RecordTable recordTable = new RecordTable();

	private int state = STATE_VIEWING;

	private String tmpName = null;

	private String tmpNote = null;

	private int tmpType = 0;

	private int tmpUOM = 0;

	private final TextBox txtName = new TextBox();

	private final TextBox txtNote = new TextBox();

	private UserExerciseEditorListenerCollection userExerciseEditorListeners;

	private DataCollection userExercises = null;

	public UserExerciseEditor() {
		String[] columnHeaders = { "Name", "Type", "Unit of Measure",
				"Note (optional)" };
		String[] optionHeaders = { "Options" };
		String[] alternatingRowStyles = { "alternatingRow1", "alternatingRow2" };

		recordTable.setColumnHeaders(columnHeaders);
		optionTable.setColumnHeaders(optionHeaders);
		recordTable.setAlternatingRowStyles(alternatingRowStyles);
		optionTable.setAlternatingRowStyles(alternatingRowStyles);

		optionTable.addTableListener(this);
		btnNewExercise.addClickListener(this);
		lstType.addChangeListener(this);
		txtNote.addKeyboardListener(this);

		commandPanel.add(btnNewExercise);
		panel.add(commandPanel, DockPanel.SOUTH);
		panel.add(recordTable, DockPanel.CENTER);
		panel.add(optionTable, DockPanel.EAST);

		recordTable.init();
		optionTable.init();

		recordTable.getFlexCellFormatter().setWidth(0, COLUMN_NAME, "180px");
		recordTable.getFlexCellFormatter().setWidth(0, COLUMN_TYPE, "120px");
		recordTable.getFlexCellFormatter().setWidth(0, COLUMN_UOM, "170px");
		recordTable.getFlexCellFormatter().setWidth(0, COLUMN_NOTE, "170px");
		optionTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		optionTable.getFlexCellFormatter().setWidth(0, 0, "150px");

		lstType.setWidth("100px");
		txtNote.setWidth("175px");
		txtNote.setMaxLength(32);

		lstType.addItem("Select One...");
		lstType.addItem("repetition");
		lstType.addItem("distance");
		lstType.addItem("duration");

		initWidget(panel);

	}

	public void addUserExerciseEditorListener(
			UserExerciseEditorListener listener) {
		if (userExerciseEditorListeners == null) {
			userExerciseEditorListeners = new UserExerciseEditorListenerCollection();
		}
		userExerciseEditorListeners.add(listener);

	}

	private void cancelEditAdd() {
		switch (state) {
		case STATE_NEW_ROW:
			recordTable.removeRow(activeRecord);
			optionTable.removeRow(activeRecord);
			break;

		case STATE_EDIT_ROW:
			recordTable.setText(activeRecord, COLUMN_NAME, tmpName);
			recordTable.setText(activeRecord, COLUMN_TYPE, lstType
					.getItemText(tmpType));

			populateUOMList(lstType.getItemText(tmpType));

			recordTable.setText(activeRecord, COLUMN_UOM, lstUOM
					.getItemText(tmpUOM));
			recordTable.setText(activeRecord, COLUMN_NOTE, tmpNote);
			optionTable.setHTML(activeRecord, OPTION_EDIT_SAVE,
					HTML_EDIT_BUTTON);
			optionTable.setHTML(activeRecord, OPTION_DELETE_CANCEL,
					HTML_DELETE_BUTTON);
			break;

		}

		activeRecord = NO_ACTIVE_RECORD;
		state = STATE_VIEWING;
	}

	private void commitRecord(int row) {

		recordTable.setText(row, COLUMN_NAME, txtName.getText());

		recordTable.setText(row, COLUMN_TYPE, lstType.getItemText(lstType
				.getSelectedIndex()));

		recordTable.setText(row, COLUMN_UOM, lstUOM.getItemText(lstUOM
				.getSelectedIndex()));

		recordTable.setText(row, COLUMN_NOTE, txtNote.getText());

		optionTable.setHTML(row, OPTION_EDIT_SAVE, HTML_EDIT_BUTTON);

		optionTable.setHTML(row, OPTION_DELETE_CANCEL, HTML_DELETE_BUTTON);

		UserExercise userExercise = new UserExercise();
		userExercise.setExerciseName(txtName.getText());
		userExercise.setExerciseType(lstType.getItemText(lstType
				.getSelectedIndex()));
		userExercise.setDefaultUOM(lstUOM
				.getItemText(lstUOM.getSelectedIndex()));
		userExercise.setNote(txtNote.getText());
		userExercise.setDisplay(true);

		switch (state) {
		case STATE_NEW_ROW:
			userExerciseEditorListeners.fireOnUserExerciseAdded(userExercise);
			break;
		case STATE_EDIT_ROW:
			long ueid = ((UserExercise) userExercises.getDataObject(row - 1))
					.getUserExerciseID();

			userExercise.setUserExerciseID(ueid);
			// the order index isn't maintained at all on the database
			// so it is managed by the client here in the UserExerciseEditor
			userExercise.setOrderIndex(row - 1);

			userExerciseEditorListeners.fireOnUserExerciseChanged(userExercise);
			break;
		default:
			System.out.println("ERROR IN UserExerciseEditor.commitRecord()");
		}

		txtName.setText("");
		lstType.setSelectedIndex(0);
		lstUOM.setSelectedIndex(0);
		txtNote.setText("");

		activeRecord = NO_ACTIVE_RECORD;
		state = STATE_VIEWING;

	}

	private void deleteRecord(int row) {
		UserExercise userExercise = (UserExercise) userExercises
				.getDataObject(row - 1);

		userExercise.setOrderIndex(row - 1);

		userExerciseEditorListeners.fireOnUserExerciseDeleted(userExercise);
	}

	private void editRecord(int row) {
		tmpName = recordTable.getText(row, COLUMN_NAME);
		tmpType = typeIndexFromName(recordTable.getText(row, COLUMN_TYPE));
		populateUOMList(recordTable.getText(row, COLUMN_TYPE));
		tmpUOM = uomIndexFromName(recordTable.getText(row, COLUMN_UOM));
		tmpNote = recordTable.getText(row, COLUMN_NOTE);

		lstType.setSelectedIndex(tmpType);
		lstUOM.setSelectedIndex(tmpUOM);
		txtName.setText(tmpName);
		txtNote.setText(tmpNote);

		recordTable.setWidget(row, COLUMN_NAME, txtName);
		recordTable.setWidget(row, COLUMN_TYPE, lstType);
		recordTable.setWidget(row, COLUMN_UOM, lstUOM);
		recordTable.setWidget(row, COLUMN_NOTE, txtNote);

		optionTable.setHTML(row, OPTION_EDIT_SAVE, HTML_SAVE_BUTTON);

		optionTable.setHTML(row, OPTION_DELETE_CANCEL, HTML_CANCEL_BUTTON);

		state = STATE_EDIT_ROW;
		activeRecord = row;

	}

	private void insertDataWidgets(int recordRow) {
		resetDataWidgets();
		recordTable.setWidget(recordRow, COLUMN_NAME, txtName);
		recordTable.setWidget(recordRow, COLUMN_TYPE, lstType);
		recordTable.setWidget(recordRow, COLUMN_UOM, lstUOM);
		recordTable.setWidget(recordRow, COLUMN_NOTE, txtNote);

		optionTable.setHTML(recordRow, OPTION_EDIT_SAVE, HTML_SAVE_BUTTON);
		optionTable
				.setHTML(recordRow, OPTION_DELETE_CANCEL, HTML_CANCEL_BUTTON);
	}

	private void insertRecord(int beforeRow) {

		recordTable.insertRow(beforeRow);
		optionTable.insertRow(beforeRow);

		// make sure the row height is uniform since they are two separate
		// tables
		recordTable.getFlexCellFormatter().setHeight(beforeRow, 0, "30px");
		optionTable.getFlexCellFormatter().setHeight(beforeRow, 0, "30px");

	}

	private void insertRecord(int beforeRow, boolean applyAlternatingStyles) {
		this.insertRecord(beforeRow);
		if (applyAlternatingStyles) {
			recordTable.applyAlternatingRowStyles(beforeRow);
			optionTable.applyAlternatingRowStyles(beforeRow);
		}

	}

	public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
		// this is where the magic happens for the option table.
		// we have faux buttons, and actually decide what to do
		// based on which cell and row is clicked
		if (row != 0 && state != STATE_PENDING) {
			// there is no active record
			if (activeRecord == NO_ACTIVE_RECORD) {
				switch (cell) {
				case OPTION_DELETE_CANCEL:
					deleteRecord(row);
					break;
				case OPTION_EDIT_SAVE:
					editRecord(row);
					break;
				}
				// there is an active record
			} else {
				if (row == activeRecord) {
					switch (cell) {
					case OPTION_EDIT_SAVE:
						if (validateRecord()) {
							commitRecord(row);
						}
						break;
					case OPTION_DELETE_CANCEL:
						cancelEditAdd();
						break;
					}
				} else {
					Window
							.alert("You must save or cancel the currently active record.");
				}
			}

		}
	}

	public void onChange(Widget sender) {
		if (sender == lstType) {
			if (lstType.getSelectedIndex() == 0) {
				lstUOM.clear();
				lstUOM.addItem("---");
			} else {
				populateUOMList(lstType.getItemText(lstType.getSelectedIndex()));
			}
		}
	}

	public void onClick(Widget sender) {
		if (sender == btnNewExercise) {
			if (state == STATE_VIEWING) {
				int newRecord = recordTable.getRowCount();
				insertRecord(newRecord, true);

				insertDataWidgets(newRecord);
				activeRecord = newRecord;
				state = STATE_NEW_ROW;
			} else {
				System.out
						.println("user trying to add a row when they're not supposed to");
			}
		}
	}

	public void onDataAddedTemporary(DataObject dataObject) {
		// TODO Auto-generated method stub

	}

	public void onDataAddedTemporary(DataObject dataObject, int index) {
		// TODO Auto-generated method stub

	}

	public void onDataBind(DataCollection dataCollection) {

		userExercises = dataCollection;

		System.out.println("on data bind called in UserExerciseEditor");
		recordTable.clearRecords();
		optionTable.clearRecords();
		int curRow;
		UserExercise s;
		for (Iterator i = dataCollection.iterator(); i.hasNext();) {

			curRow = recordTable.getRowCount();
			insertRecord(curRow);
			s = (UserExercise) i.next();

			recordTable.setText(curRow, COLUMN_NAME, s.getExerciseName());
			recordTable.setText(curRow, COLUMN_TYPE, String.valueOf(s
					.getExerciseType()));
			recordTable.setText(curRow, COLUMN_UOM, s.getDefaultUOM());
			recordTable.setText(curRow, COLUMN_NOTE, String
					.valueOf(s.getNote()));

			optionTable.setHTML(curRow, OPTION_DELETE_CANCEL,
					HTML_DELETE_BUTTON);
			optionTable.setHTML(curRow, OPTION_EDIT_SAVE, HTML_EDIT_BUTTON);
		}

		recordTable.applyAlternatingRowStyles(1);
		optionTable.applyAlternatingRowStyles(1);

		state = STATE_VIEWING;
		activeRecord = NO_ACTIVE_RECORD;
	}

	public void onDataInsertedPermanent(DataObject dataObject, int index) {
		System.out.println("The data was permanently inserted");

	}

	public void onDataRemovedPermanent(int index) {
		recordTable.removeRow(index + 1);
		optionTable.removeRow(index + 1);

	}

	public void onDataReplacedTemporary(DataObject dataObject, int index) {
		System.out.println("Data was replaced permanent");

	}

	public void onKeyDown(Widget sender, char keyCode, int modifiers) {

	}

	public void onKeyPress(Widget sender, char keyCode, int modifiers) {
		if (keyCode == 13 && (sender == lstType || sender == txtNote)) {
			this.onCellClicked(optionTable, activeRecord, OPTION_EDIT_SAVE);
		}

	}

	public void onKeyUp(Widget sender, char keyCode, int modifiers) {

	}

	private void populateUOMList(String exerciseType) {
		System.out.println("populating UOM list exerciseType is "
				+ exerciseType);
		lstUOM.clear();
		lstUOM.addItem("Select one...");
		ExerciseUOM uom;
		int defaultUOM = -1;
		for (Iterator i = exerciseUOMs.iterator(); i.hasNext();) {
			uom = (ExerciseUOM) i.next();
			if (uom.getExerciseType().equalsIgnoreCase(exerciseType)) {
				lstUOM.addItem(uom.getUOM());
				if (uom.getIsDefault()) {
					defaultUOM = lstUOM.getItemCount() - 1;
				}
			}
		}

		// if there is a default UOM or only one UOM available for that exercise
		// type,
		// automatically select it
		if (lstUOM.getItemCount() == 2) {
			lstUOM.setSelectedIndex(1);
		} else if (defaultUOM > -1) {
			lstUOM.setSelectedIndex(defaultUOM);
		} else {
			lstUOM.setSelectedIndex(0);
		}
	}

	private void resetDataWidgets() {
		txtName.setText("");
		lstType.setSelectedIndex(0);
		lstUOM.clear();
		lstUOM.addItem("---");
		txtNote.setText("");
	}

	public void setExerciseUOMs(DataCollection exerciseUOMs) {
		this.exerciseUOMs = exerciseUOMs;
	}

	private int typeIndexFromName(String type) {
		for (int i = 1; i < lstType.getItemCount(); i++) {
			if (type.equalsIgnoreCase(lstType.getItemText(i))) {
				return i;
			}
		}

		return -1;
	}

	private int uomIndexFromName(String uom) {

		for (int i = 1; i < lstUOM.getItemCount(); i++) {
			if (uom.equalsIgnoreCase(lstUOM.getItemText(i))) {
				return i;
			}
		}

		return -1;
	}

	private boolean validateRecord() {
		try {

			if (txtName.getText().trim().equals("")) {
				Window.alert("Invalid exercise name");
				return false;
			} else if (lstType.getSelectedIndex() < 1) {
				Window.alert("Please select an exercise type");
				return false;
			} else if (lstUOM.getSelectedIndex() < 1) {
				Window.alert("Please select a default unit of measure");
				return false;
			}
		} catch (Exception e) {
			Window.alert("Validation Exception Caught");
			return false;
		}

		return true;
	}
}
