package com.fr.client.rpc;

import com.fr.client.data.DataCollection;
import com.fr.client.data.DataResponse;
import com.fr.client.data.types.UserExercise;
import com.google.gwt.user.client.rpc.RemoteService;

public interface ExerciseService extends RemoteService {

	public DataCollection getUOMS();

	public DataResponse addUserExercise(String token, String requestID,
			UserExercise userExercise);

	public DataResponse changeUserExercise(String token, String requestID,
			UserExercise userExercise);

	public DataCollection getUserExercises(String token);

	public DataResponse deleteUserExercise(String token, String requestID,
			long userExerciseID);

	public DataCollection getBaseConversions();
}
