package com.fr.client.data;

import java.util.Iterator;

import com.fr.client.data.types.ExerciseUOM;
import com.fr.client.data.types.RoutineSession;
import com.fr.client.data.types.SessionExercise;
import com.fr.client.data.types.UserExercise;

public final class DataRelation {
	public static UserExercise userExerciseByID(DataCollection userExercises,
			double userExerciseID) {
		UserExercise ue;
		for (Iterator i = userExercises.iterator(); i.hasNext();) {
			ue = (UserExercise) i.next();
			if (ue.getUserExerciseID() == userExerciseID) {
				return ue;
			}
		}

		throw new RuntimeException("UserExercise Doesn't exist");
	}

	public static ExerciseUOM exerciseUOMByName(DataCollection exerciseUOMS,
			String uomName) {
		ExerciseUOM euom;
		for (Iterator i = exerciseUOMS.iterator(); i.hasNext();) {
			euom = (ExerciseUOM) i.next();
			if (euom.getUOM().equals(uomName)) {
				return euom;
			}
		}

		throw new RuntimeException("UOM Doesn't exist");
	}

	public static RoutineSession routineSessionByID(
			DataCollection routineSessions, double routineSessionID) {
		RoutineSession rs;
		for (Iterator i = routineSessions.iterator(); i.hasNext();) {
			rs = (RoutineSession) i.next();
			if (rs.getRoutineSessionID() == routineSessionID) {
				return rs;
			}
		}

		throw new RuntimeException("RoutineSession Doesn't exist");
	}

	public static SessionExercise sessionExerciseByID(
			DataCollection sessionExercises, double sessionExerciseID) {
		SessionExercise se;
		for (Iterator i = sessionExercises.iterator(); i.hasNext();) {
			se = (SessionExercise) i.next();
			if (se.getSessionExerciseID() == sessionExerciseID) {
				return se;
			}
		}

		throw new RuntimeException("SessionExercise Doesn't exist");
	}

}
