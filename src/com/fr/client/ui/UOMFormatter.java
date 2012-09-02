package com.fr.client.ui;

import com.fr.client.data.types.ExerciseUOM;

class UOMFormatter {

	public static String format(ExerciseUOM uom, double nativeValue) {

		String retval;

		if (uom.getExerciseType().equals("repetition")) {
			retval = String.valueOf((int) nativeValue) + " " + uom.getUOM();
		} else if (uom.getExerciseType().equals("distance")) {
			retval = String.valueOf(nativeValue) + " " + uom.getUOM();
		} else if (uom.getExerciseType().equals("duration")) {
			retval = String.valueOf(((int) nativeValue / 3600) + "h"
					+ String.valueOf((int) (nativeValue % 3600) / 60) + "m"
					+ String.valueOf((int) nativeValue % 60) + "s");
		} else {
			throw new RuntimeException("Invalid exercise type ("
					+ uom.getExerciseType() + ") in UOMFormatter.format()");
		}
		return retval;
	}
}
