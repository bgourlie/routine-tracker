package com.fr.client.rpc;

import com.fr.client.data.types.UserExercise;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ExerciseServiceAsync {
	public void getUOMS(AsyncCallback callback);

	public void addUserExercise(String token, String requestID,
			UserExercise userExercise, AsyncCallback callback);

	public void getUserExercises(String token, AsyncCallback callback);

	public void changeUserExercise(String token, String requestID,
			UserExercise userExercise, AsyncCallback callback);

	public void deleteUserExercise(String token, String requestID,
			long userExerciseID, AsyncCallback callback);

	public void getBaseConversions(AsyncCallback callback);
}
