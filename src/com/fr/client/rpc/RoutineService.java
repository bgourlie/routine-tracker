package com.fr.client.rpc;

import com.fr.client.data.DataCollection;
import com.fr.client.data.DataResponse;
import com.fr.client.data.types.RoutineSession;
import com.fr.client.data.types.RoutineSessionData;
import com.fr.client.data.types.SessionExercise;
import com.fr.client.data.types.UserRoutine;
import com.google.gwt.user.client.rpc.RemoteService;

public interface RoutineService extends RemoteService {

	public DataResponse addRoutineSession(String token, String requestID,
			RoutineSession routineSession) throws DatabaseException,
			AuthenticationException;

	public DataResponse addSessionExercise(String token, String requestID,
			SessionExercise sessionExercise) throws DatabaseException,
			AuthenticationException;

	public DataResponse addUserRoutine(String token, String requestID,
			UserRoutine userRoutine) throws DatabaseException,
			AuthenticationException;

	public DataResponse changeSessionExercise(String token, String requestID,
			SessionExercise sessionExercise) throws DatabaseException,
			AuthenticationException;

	public DataResponse deleteRoutineSession(String token, String requestID,
			long routineSessionID) throws DatabaseException,
			AuthenticationException;

	public DataResponse deleteSessionExercise(String token, String requestID,
			long sessionExerciseID) throws DatabaseException,
			AuthenticationException;

	public DataCollection getRoutineSessions(String token, long routineID)
			throws DatabaseException, AuthenticationException;

	public DataCollection getSessionExercises(String token,
			long routineSessionID) throws DatabaseException,
			AuthenticationException;

	public DataCollection getUserRoutines(String token)
			throws DatabaseException, AuthenticationException;

	public void saveRoutineSession(String token,
			RoutineSessionData routineSessionData,
			DataCollection sessionExerciseData) throws DatabaseException,
			AuthenticationException;

	public RoutineSessionData getRoutineSessionData(String token,
			long routineSessionID) throws DatabaseException,
			AuthenticationException;

	public RoutineSession getNextRoutineSession(String token)
			throws DatabaseException, AuthenticationException;

}
