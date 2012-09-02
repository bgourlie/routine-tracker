package com.fr.server.rpc;

import com.fr.client.data.DataCollection;
import com.fr.client.data.DataResponse;
import com.fr.client.data.types.UserExercise;
import com.fr.client.rpc.ExerciseService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ExerciseServiceImpl extends RemoteServiceServlet implements
		ExerciseService {

	private static final long serialVersionUID = 1L;

	public DataResponse addUserExercise(String token, String requestID,
			UserExercise userExercise) {
		// TODO Auto-generated method stub
		return null;
	}

	public DataResponse changeUserExercise(String token, String requestID,
			UserExercise userExercise) {
		// TODO Auto-generated method stub
		return null;
	}

	public DataResponse deleteUserExercise(String token, String requestID,
			long userExerciseID) {
		// TODO Auto-generated method stub
		return null;
	}

	public DataCollection getBaseConversions() {
		// TODO Auto-generated method stub
		return null;
	}

	public DataCollection getUOMS() {
		// TODO Auto-generated method stub
		return null;
	}

	public DataCollection getUserExercises(String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
