package com.fr.server.rpc;

import com.fr.client.data.DataCollection;
import com.fr.client.data.DataResponse;
import com.fr.client.data.types.RoutineSession;
import com.fr.client.data.types.RoutineSessionData;
import com.fr.client.data.types.SessionExercise;
import com.fr.client.data.types.UserRoutine;
import com.fr.client.rpc.AuthenticationException;
import com.fr.client.rpc.DatabaseException;
import com.fr.client.rpc.RoutineService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RoutineServiceImpl extends RemoteServiceServlet implements
		RoutineService {

	private static final long serialVersionUID = 1L;

	public DataResponse addRoutineSession(String token, String requestID,
			RoutineSession routineSession) throws DatabaseException,
			AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	public DataResponse addSessionExercise(String token, String requestID,
			SessionExercise sessionExercise) throws DatabaseException,
			AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	public DataResponse addUserRoutine(String token, String requestID,
			UserRoutine userRoutine) throws DatabaseException,
			AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	public DataResponse changeSessionExercise(String token, String requestID,
			SessionExercise sessionExercise) throws DatabaseException,
			AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	public DataResponse deleteRoutineSession(String token, String requestID,
			long routineSessionID) throws DatabaseException,
			AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	public DataResponse deleteSessionExercise(String token, String requestID,
			long sessionExerciseID) throws DatabaseException,
			AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	public RoutineSession getNextRoutineSession(String token)
			throws DatabaseException, AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	public RoutineSessionData getRoutineSessionData(String token,
			long routineSessionID) throws DatabaseException,
			AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	public DataCollection getRoutineSessions(String token, long routineID)
			throws DatabaseException, AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	public DataCollection getSessionExercises(String token,
			long routineSessionID) throws DatabaseException,
			AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	public DataCollection getUserRoutines(String token)
			throws DatabaseException, AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveRoutineSession(String token,
			RoutineSessionData routineSessionData,
			DataCollection sessionExerciseData) throws DatabaseException,
			AuthenticationException {
		// TODO Auto-generated method stub

	}
}
