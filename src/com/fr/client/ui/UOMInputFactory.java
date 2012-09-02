package com.fr.client.ui;

class UOMInputFactory {

	public static UOMInput getUOMInput(String exerciseType) {

		if (exerciseType.equals("repetition")) {
			return new RepetitionInput();
		} else if (exerciseType.equals("distance")) {
			return new DistanceInput();
		} else if (exerciseType.equals("duration")) {
			return new DurationInput();
		} else {
			throw new RuntimeException("Invalid ExerciseType: " + exerciseType);
		}

	}

}
