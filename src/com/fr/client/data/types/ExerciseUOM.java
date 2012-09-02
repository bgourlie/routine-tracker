package com.fr.client.data.types;

import com.fr.client.data.DataObject;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ExerciseUOM implements IsSerializable, DataObject {

	public static final String TYPE_DISTANCE = "distance";

	public static final String TYPE_DURATION = "duration";

	public static final String TYPE_REPETITION = "repetition";

	private String baseUOM;

	private int conversionFactor;

	private String exerciseType;

	private boolean isDefault;

	private String uom;

	public ExerciseUOM() {
	}

	public String getBaseUOM() {
		return baseUOM;
	}

	public int getConversionFactor() {
		return conversionFactor;
	}

	public String getExerciseType() {
		return exerciseType;
	}

	public boolean getIsDefault() {
		return isDefault;
	}

	public String getUOM() {
		return uom;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public void setBaseUOM(String baseUOM) {
		this.baseUOM = baseUOM;
	}

	public void setConversionFactor(int conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	public void setExerciseType(String exerciseType) {
		this.exerciseType = exerciseType;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

}
