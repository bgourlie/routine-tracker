package com.fr.client.ui;

import com.fr.client.data.types.ExerciseUOM;

class UOMOracle {

	public static int getBaseValue(ExerciseUOM uom, double nativeValue) {
		return (int) (uom.getConversionFactor() * nativeValue);
	}

	public static String getFormattedValue(ExerciseUOM uom, double nativeValue) {
		return UOMFormatter.format(uom, nativeValue);
	}

	public static String getFormattedValue(ExerciseUOM uom, int baseValue) {
		return getFormattedValue(uom, getNativeValue(uom, baseValue));
	}

	public static double getNativeValue(ExerciseUOM uom, int baseValue) {
		return (1.0 / (double) uom.getConversionFactor()) * (double) baseValue;
	}

}
