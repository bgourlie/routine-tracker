package com.fr.client.common;

import com.google.gwt.user.client.Window;

public final class Module {

	public static final String MODULE_FRHOME = "/com.fr.FRHome/FRHome.html";

	public static final String MODULE_ROUTINE = "/com.fr.Routine/Routine.html";

	public static final String MODULE_USERHOME = "/com.fr.UserHome/UserHome.html";

	public static final String MODULE_WORKOUT = "/com.fr.Workout/Workout.html";

	public static void redirect(String moduleLocation) {
		Window.open(moduleLocation, "_self", null);
	}

}
