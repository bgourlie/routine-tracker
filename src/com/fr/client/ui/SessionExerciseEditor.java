package com.fr.client.ui;

import java.util.Iterator;

import com.fr.client.data.DataCollection;
import com.fr.client.data.DataListener;
import com.fr.client.data.DataObject;
import com.fr.client.data.DataRelation;
import com.fr.client.data.types.RoutineSession;
import com.fr.client.data.types.SessionExercise;
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

public class SessionExerciseEditor extends Composite implements TableListener,
		ClickListener, KeyboardListener, ChangeListener, DataListener {

	private static final int COLUMN_EXERCISE = 0;

	private static final int COLUMN_NOTE = 2;

	private static final int COLUMN_TARGET = 1;

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

	private final Button btnNewSessionExercise = new Button("Add Exercise");

	private final HorizontalPanel commandPanel = new HorizontalPanel();

	private final ListBox lstUserExercise = new ListBox();

	private final RecordTable optionTable = new RecordTable(
			"RoutineSessionEditor-optionTable");

	private final DockPanel panel = new DockPanel();

	private final RecordTable recordTable = new RecordTable();

	private RoutineSession routineSession = null;

	private SessionExerciseEditorListenerCollection routineSessionEditorListeners;

	private DataCollection sessionExercises = null;

	private int state = STATE_VIEWING;

	private String tmpNote = null;

	private int tmpTarget = -1;

	private int tmpUserExerciseIndex = 0;

	private final TextBox txtNote = new TextBox();

	private UOMInput uomInputTarget = null;

	private DataCollection userExercises = null;

	private DataCollection exerciseUOMS = null;

	public SessionExerciseEditor() {
		String[] columnHeaders = { "Exercise", "Target", "Note (optional)" };
		String[] optionHeaders = { "Options" };
		String[] alternatingRowStyles = { "alternatingRow1", "alternatingRow2" };

		recordTable.setColumnHeaders(columnHeaders);
		optionTable.setColumnHeaders(optionHeaders);
		recordTable.setAlternatingRowStyles(alternatingRowStyles);
		optionTable.setAlternatingRowStyles(alternatingRowStyles);

		optionTable.addTableListener(this);
		btnNewSessionExercise.addClickListener(this);
		txtNote.addKeyboardListener(this);
		lstUserExercise.addChangeListener(this);
		commandPanel.add(btnNewSessionExercise);
		panel.add(commandPanel, DockPanel.SOUTH);
		panel.add(recordTable, DockPanel.CENTER);
		panel.add(optionTable, DockPanel.EAST);

		recordTable.init();
		optionTable.init();

		recordTable.getFlexCellFormatter()
				.setWidth(0, COLUMN_EXERCISE, "180px");
		recordTable.getFlexCellFormatter().setWidth(0, COLUMN_TARGET, "120px");
		recordTable.getFlexCellFormatter().setWidth(0, COLUMN_NOTE, "170px");
		optionTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		optionTable.getFlexCellFormatter().setWidth(0, 0, "150px");

		txtNote.setWidth("175px");
		txtNote.setMaxLength(32);

		initWidget(panel);

	}

	public void addRoutineSessionEditorListener(
			SessionExerciseEditorListener listener) {
		if (routineSessionEditorListeners == null) {
			routineSessionEditorListeners = new SessionExerciseEditorListenerCollection();
		}
		routineSessionEditorListeners.add(listener);
	}

	private void cancelEditAdd() {
		switch (state) {
		case STATE_NEW_ROW:
			recordTable.removeRow(activeRecord);
			optionTable.removeRow(activeRecord);
			break;

		case STATE_EDIT_ROW:
			recordTable.setText(activeRecord, COLUMN_EXERCISE, lstUserExercise
					.getItemText(tmpUserExerciseIndex));
			recordTable.setText(activeRecord, COLUMN_TARGET, String
					.valueOf(tmpTarget));
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

		SessionExercise se;
		UserExercise ue = (UserExercise) userExercises
				.getDataObject(lstUserExercise.getSelectedIndex() - 1);

		recordTable.setText(row, COLUMN_EXERCISE, lstUserExercise
				.getItemText(lstUserExercise.getSelectedIndex()));

		recordTable.setText(row, COLUMN_TARGET, UOMOracle.getFormattedValue(
				DataRelation
						.exerciseUOMByName(exerciseUOMS, ue.getDefaultUOM()),
				uomInputTarget.getValue()));

		recordTable.setText(row, COLUMN_NOTE, txtNote.getText());

		optionTable.setHTML(row, OPTION_EDIT_SAVE, HTML_EDIT_BUTTON);

		optionTable.setHTML(row, OPTION_DELETE_CANCEL, HTML_DELETE_BUTTON);

		se = new SessionExercise();
		se.setRoutineSessionID(routineSession.getRoutineSessionID());
		se.setUserExerciseID(ue.getUserExerciseID());
		se.setTarget(UOMOracle.getBaseValue(DataRelation.exerciseUOMByName(
				exerciseUOMS, ue.getDefaultUOM()), uomInputTarget.getValue()));
		se.setNote(txtNote.getText());
		se.setOrderIndex(row - 1);

		switch (state) {
		case STATE_NEW_ROW:
			routineSessionEditorListeners.fireOnSessionExerciseAdded(se);
			break;
		case STATE_EDIT_ROW:
			se.setSessionExerciseID(((SessionExercise) sessionExercises
					.getDataObject(row - 1)).getSessionExerciseID());
			routineSessionEditorListeners.fireOnSessionExerciseChanged(se);
			break;
		default:
			System.out.println("ERROR IN RoutineSessionEditor.commitRecord()");
		}

		lstUserExercise.setSelectedIndex(0);
		uomInputTarget.resetWidget();
		txtNote.setText("");

		activeRecord = NO_ACTIVE_RECORD;
		state = STATE_VIEWING;

	}

	private void deleteRecord(int row) {

		routineSessionEditorListeners
				.fireOnSessionExerciseDeleted((SessionExercise) sessionExercises
						.getDataObject(row - 1));
	}

	private void editRecord(int row) {
		SessionExercise se = (SessionExercise) sessionExercises
				.getDataObject(row - 1);
		UserExercise ue = DataRelation.userExerciseByID(userExercises, se
				.getUserExerciseID());

		initUOMInputTarget(ue);

		tmpUserExerciseIndex = exerciseIndexFromName(ue.getExerciseName());
		tmpTarget = se.getTarget();
		tmpNote = se.getNote();

		lstUserExercise.setSelectedIndex(tmpUserExerciseIndex);
		uomInputTarget.setValue(UOMOracle
				.getNativeValue(DataRelation.exerciseUOMByName(exerciseUOMS, ue
						.getDefaultUOM()), tmpTarget));
		txtNote.setText(tmpNote);

		recordTable.setWidget(row, COLUMN_EXERCISE, lstUserExercise);
		recordTable.setWidget(row, COLUMN_TARGET, uomInputTarget.getWidget());
		recordTable.setWidget(row, COLUMN_NOTE, txtNote);

		optionTable.setHTML(row, OPTION_EDIT_SAVE, HTML_SAVE_BUTTON);

		optionTable.setHTML(row, OPTION_DELETE_CANCEL, HTML_CANCEL_BUTTON);

		state = STATE_EDIT_ROW;
		activeRecord = row;

	}

	private int exerciseIndexFromName(String exerciseName) {
		for (int i = 1; i < lstUserExercise.getItemCount(); i++) {
			if (exerciseName.equals(lstUserExercise.getItemText(i))) {
				return i;
			}
		}

		return -1;
	}

	private void initUOMInputTarget(UserExercise ue) {
		uomInputTarget = UOMInputFactory.getUOMInput(ue.getExerciseType());
		uomInputTarget.setUOM(DataRelation.exerciseUOMByName(exerciseUOMS, ue
				.getDefaultUOM()));
	}

	private void insertDataWidgets(int row) {
		recordTable.setWidget(row, COLUMN_EXERCISE, lstUserExercise);
		recordTable.setHTML(row, COLUMN_TARGET, "");
		recordTable.setWidget(row, COLUMN_NOTE, txtNote);

		optionTable.setHTML(row, OPTION_EDIT_SAVE, HTML_SAVE_BUTTON);
		optionTable.setHTML(row, OPTION_DELETE_CANCEL, HTML_CANCEL_BUTTON);
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
		if (sender == lstUserExercise) {
			if (lstUserExercise.getSelectedIndex() == 0) {
				uomInputTarget = null;
				recordTable.setHTML(activeRecord, COLUMN_TARGET, "");
			} else {
				initUOMInputTarget((UserExercise) userExercises
						.getDataObject(lstUserExercise.getSelectedIndex() - 1));
				recordTable.setWidget(activeRecord, COLUMN_TARGET,
						uomInputTarget.getWidget());
			}
		}

	}

	public void onClick(Widget sender) {
		if (sender == btnNewSessionExercise) {
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
		System.out.println("on data bind called in RoutineSessionEditor");
		recordTable.clearRecords();
		optionTable.clearRecords();

		this.sessionExercises = dataCollection;

		int curRow;
		SessionExercise se;
		UserExercise ue;
		for (Iterator i = sessionExercises.iterator(); i.hasNext();) {
			se = (SessionExercise) i.next();

			ue = DataRelation.userExerciseByID(userExercises, se
					.getUserExerciseID());

			curRow = recordTable.getRowCount();
			insertRecord(curRow);

			recordTable.setText(curRow, COLUMN_EXERCISE, ue.getExerciseName());
			recordTable.setText(curRow, COLUMN_TARGET, UOMOracle
					.getFormattedValue(DataRelation.exerciseUOMByName(
							exerciseUOMS, ue.getDefaultUOM()), se.getTarget()));
			recordTable.setText(curRow, COLUMN_NOTE, String.valueOf(se
					.getNote()));

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
		if (keyCode == 13
				&& (sender == uomInputTarget.getWidget() || sender == txtNote)) {
			this.onCellClicked(optionTable, activeRecord, OPTION_EDIT_SAVE);
		}

	}

	public void onKeyUp(Widget sender, char keyCode, int modifiers) {

	}

	public void setRoutineSession(RoutineSession routineSession) {
		this.routineSession = routineSession;
	}

	public void setUserExercises(DataCollection userExercises) {

		this.userExercises = userExercises;

		lstUserExercise.clear();
		lstUserExercise.addItem("Select One...");
		for (Iterator i = userExercises.iterator(); i.hasNext();) {
			lstUserExercise
					.addItem(((UserExercise) i.next()).getExerciseName());
		}
	}

	private boolean validateRecord() {
		try {
			if (lstUserExercise.getSelectedIndex() < 1) {
				Window.alert("Please select an exercise");
				return false;
			} else if (uomInputTarget.getValue() <= 0) {
				Window.alert("Invalid target specified.");
				return false;
			}
		} catch (Exception e) {
			Window.alert("Validation Exception Caught: " + e.getMessage());
			return false;
		}

		return true;
	}

	public void setExerciseUOMS(DataCollection exerciseUOMS) {
		this.exerciseUOMS = exerciseUOMS;
	}
}
