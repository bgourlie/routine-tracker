package com.fr.client.rpc;

import com.fr.client.data.DataCollection;
import com.fr.client.data.types.RoutineSession;
import com.fr.client.data.types.RoutineSessionData;
import com.fr.client.data.types.SessionExercise;
import com.fr.client.data.types.UserRoutine;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RoutineServiceAsync {

	public void addRoutineSession(String token, String requestID,
			RoutineSession routineSession, AsyncCallback async);

	public void addSessionExercise(String token, String requestID,
			SessionExercise sessionExercise, AsyncCallback async);

	public void addUserRoutine(String token, String requestID,
			UserRoutine userRoutine, AsyncCallback async);

	public void changeSessionExercise(String token, String requestID,
			SessionExercise sessionExercise, AsyncCallback async);

	public void deleteRoutineSession(String token, String requestID,
			long routineSessionID, AsyncCallback async);

	public void deleteSessionExercise(String token, String requestID,
			long sessionExerciseID, AsyncCallback async);

	public void getRoutineSessions(String token, long routineID,
			AsyncCallback async);

	public void getSessionExercises(String token, long routineSessionID,
			AsyncCallback async);

	public void getUserRoutines(String token, AsyncCallback async);

	public void saveRoutineSession(String token,
			RoutineSessionData routineSessionData,
			DataCollection sessionExerciseData, AsyncCallback async);

	public void getRoutineSessionData(String token, long routineSessionID,
			AsyncCallback async);

	public void getNextRoutineSession(String token, AsyncCallback async);
}
