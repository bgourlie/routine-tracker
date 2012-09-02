package com.fr.client.ui;

import java.util.Iterator;

import com.fr.client.data.DataCollection;
import com.fr.client.data.DataListener;
import com.fr.client.data.DataObject;
import com.fr.client.data.types.RoutineSession;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RoutineSessionEditor extends Composite implements TabListener,
		KeyboardListener, ChangeListener, DataListener {

	private static final String STRING_LOADING = "Loading...";

	private static final String STRING_NO_SESSIONS_DEFINED = "There are no workout sessions defined for this routine.";

	private final ListBox lstInsertLocation = new ListBox();

	private final VerticalPanel mainPanel = new VerticalPanel();

	private final MenuBar mnuEditSelectedSession = new MenuBar(true);

	private final MenuBar mnuRoot = new MenuBar();

	private RoutineSessionEditorListenerCollection routineEditorListeners = null;

	private final SessionExerciseEditor sessionExerciseEditor = new SessionExerciseEditor();

	private final TabPanel sessionTabPanel = new TabPanel();

	private TabBar sessionTabBar = null;

	private final TextBox txtSessionName = new TextBox();

	private DataCollection routineSessions = null;

	public RoutineSessionEditor() {

		initWidget(mainPanel);

		// for convenience...
		sessionTabBar = sessionTabPanel.getTabBar();

		/* set custom styles */
		this.setStyleName("fr-RoutineEditor");

		// add our listeners
		txtSessionName.addKeyboardListener(this);
		lstInsertLocation.addChangeListener(this);
		sessionTabPanel.addTabListener(this);

		// format our menu
		mnuRoot.addItem("Edit Selected Workout", false, mnuEditSelectedSession);
		mnuEditSelectedSession.addItem("Delete Workout", false,
				cmdDeleteSession());
		mnuEditSelectedSession.addItem("Rename Workout", false,
				cmdRenameSession());

		// add them to the function panel
		HorizontalPanel createSessionPanel = new HorizontalPanel();
		createSessionPanel.add(new HTML("Create new workout&nbsp;"));
		createSessionPanel.add(lstInsertLocation);
		createSessionPanel.add(new HTML("&nbsp;named&nbsp;"));
		createSessionPanel.add(txtSessionName);

		// add our panels to the main panel
		mainPanel.add(mnuRoot);
		mainPanel.add(createSessionPanel);
		mainPanel.add(new HTML("Loading..."));
		// set the list items in the lstInsertLocation listbox
		populateInsertLocationList();
	}

	public void addRoutineEditorListener(RoutineSessionEditorListener listener) {
		if (routineEditorListeners == null) {
			routineEditorListeners = new RoutineSessionEditorListenerCollection();
		}
		routineEditorListeners.add(listener);

	}

	private void addSession(String sessionName) {

		sessionTabPanel.add(new HTML(STRING_LOADING), sessionName);

	}

	private void addSession(String sessionName, int displayIndex) {
		// when display index is -1, we just add it onto the end of the routine

		sessionTabPanel.insert(new HTML(STRING_LOADING), sessionName,
				displayIndex);

	}

	private Command cmdDeleteSession() {
		return new Command() {
			public void execute() {
				deleteSelectedSession();
			}
		};
	}

	private Command cmdRenameSession() {
		return new Command() {
			public void execute() {
				Window.alert("Rename Workout not yet implemented");
			}
		};
	}

	private void deleteSelectedSession() {
		if (sessionTabBar.getSelectedTab() > -1) {
			routineEditorListeners.fireOnSessionDeleted(sessionTabBar
					.getSelectedTab());
		}
	}

	public SessionExerciseEditor getSessionExerciseEditor() {
		return sessionExerciseEditor;
	}

	private void setRoutineSessions(DataCollection routineSessions) {

		sessionTabPanel.clear();

		this.routineSessions = routineSessions;

		Iterator i = routineSessions.iterator();
		while (i.hasNext()) {
			RoutineSession session = (RoutineSession) i.next();
			addSession(session.getRoutineSessionName(), session.getOrderIndex());

		}

		int tabCount = sessionTabBar.getTabCount();

		if (tabCount > 0) {
			// repopulate the insert at list
			populateInsertLocationList();

			// remove the "Loading..." html so we can insert the tabpanel
			mainPanel.remove(2);
			mainPanel.add(sessionTabPanel);
			// select the last tab and show the tab panel
			sessionTabPanel.selectTab(tabCount - 1);
		} else {
			// if there are no sessions, then let them know
			mainPanel.remove(2);
			mainPanel.add(new HTML(STRING_NO_SESSIONS_DEFINED));
		}

	}

	// return false to dissallow the selection of the tab
	public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
		return true;
	}

	public void onChange(Widget sender) {
		// purely convenience, when a list item
		// is selected in the insert listbox,
		// set the focus on txtSessionName
		if (sender == lstInsertLocation) {
			txtSessionName.setFocus(true);
		}
	}

	public void onDataAddedTemporary(DataObject dataObject) {

		if (mainPanel.getWidget(2) != sessionTabPanel) {
			mainPanel.remove(2);
			mainPanel.add(sessionTabPanel);
		}
		addSession(((RoutineSession) dataObject).getRoutineSessionName());

	}

	public void onDataAddedTemporary(DataObject dataObject, int index) {

		addSession(((RoutineSession) dataObject).getRoutineSessionName(), index);

	}

	public void onDataBind(DataCollection dataCollection) {
		setRoutineSessions(dataCollection);
	}

	public void onDataInsertedPermanent(DataObject dataObject, int index) {

		if (!((RoutineSession) dataObject).getRoutineSessionName()
				.equalsIgnoreCase(sessionTabBar.getTabHTML(index))) {
			/*
			 * TODO Once GWT supports the renaming of a tab, we will set it to
			 * equal the session name returned from the server in the meantime,
			 * we just make sure it matches
			 */
			System.out.println("!!New Data does not equal old data!!");
		}

		sessionTabPanel
				.selectTab(((RoutineSession) dataObject).getOrderIndex());

		// repopulate the insert at list
		populateInsertLocationList();

	}

	public void onDataRemovedPermanent(int index) {
		sessionTabPanel.remove(index);

		if (sessionTabBar.getTabCount() > 0) {
			sessionTabPanel.selectTab(sessionTabBar.getTabCount() - 1);
		} else {
			// if there are no sessions, then let them know
			mainPanel.remove(2);
			mainPanel.add(new HTML(STRING_NO_SESSIONS_DEFINED));
		}
	}

	public void onDataReplacedTemporary(DataObject dataObject, int index) {
		// TODO Auto-generated method stub

	}

	public void onKeyDown(Widget sender, char keyCode, int modifiers) {
		// do nothing
	}

	public void onKeyPress(Widget sender, char keyCode, int modifiers) {

		if (sender == txtSessionName && keyCode == KEY_ENTER) {
			userAddSession();
		}
	}

	public void onKeyUp(Widget sender, char keyCode, int modifiers) {
		// DO NOTHING
	}

	public void onTabSelected(SourcesTabEvents sender, int tabIndex) {

		DeckPanel dp = sessionTabPanel.getDeckPanel();

		// remove the session editor from its old tab, and insert at the newly
		// selected tab
		if (dp.getWidget(dp.getVisibleWidget()) != sessionExerciseEditor) {

			dp.remove(sessionExerciseEditor);
			dp.insert(sessionExerciseEditor, tabIndex);
			dp.showWidget(dp.getWidgetIndex(sessionExerciseEditor));

			// routineSessionEditor.setVisible(false);
			sessionExerciseEditor
					.setRoutineSession((RoutineSession) routineSessions
							.getDataObject(tabIndex));

			routineEditorListeners.fireOnSessionSelected(tabIndex);

		}

	}

	// populates the list based on the existing session
	private void populateInsertLocationList() {

		lstInsertLocation.clear();

		if (sessionTabBar.getTabCount() == 0) {
			lstInsertLocation.addItem("At beginning of routine", "0");
		} else {
			for (int i = 0; i < sessionTabBar.getTabCount(); i++) {
				lstInsertLocation.addItem("Before "
						+ sessionTabBar.getTabHTML(i), String.valueOf(i));
			}

			lstInsertLocation.addItem("At end of routine", "-1");
			lstInsertLocation
					.setSelectedIndex(lstInsertLocation.getItemCount() - 1);
		}

	}

	private boolean tabNameExists(String name) {

		for (int i = 0; i < sessionTabBar.getTabCount(); i++) {
			if (sessionTabBar.getTabHTML(i).toLowerCase().equals(
					name.toLowerCase())) {
				return true;
			}
		}

		return false;
	}

	private void userAddSession() {

		String sessionName = txtSessionName.getText();

		int insertIndex = Integer.parseInt(lstInsertLocation
				.getValue(lstInsertLocation.getSelectedIndex()));

		if (validateSessionName(sessionName)) {
			routineEditorListeners.fireOnSessionAdded(sessionName, insertIndex);

			// clear the sessionName text box
			txtSessionName.setText("");

		}
	}

	private boolean validateSessionName(String sessionName) {
		if (txtSessionName.getText().trim().equals("")) {
			return false;
		} else if (tabNameExists(sessionName)) {
			Window
					.alert("'"
							+ sessionName
							+ "' already exists.  Please choose a different session name.");
			return false;
		} else {
			return true;
		}
	}

}
