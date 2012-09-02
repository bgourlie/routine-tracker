package com.fr.client.ui;

public interface RoutineSessionEditorListener extends java.util.EventListener {

	public void onRoutineSessionAdded(String sessionName, int orderIndex);

	public void onRoutineSessionDeleted(int index);

	public void onRoutineSessionSelected(int index);
}
