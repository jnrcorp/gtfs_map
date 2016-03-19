package com.jnrcorp.gtfs.util.model;

public class Range<T> {

	private T min;
	private T max;

	public Range() {
		super();
	}

	public Range(T min, T max) {
		super();
		this.min = min;
		this.max = max;
	}

	public boolean bothExist() {
		return min != null && max != null;
	}

	public boolean onlyMinExists() {
		return min != null && max == null;
	}

	public boolean onlyMaxExists() {
		return min == null && max != null;
	}

	public T getMin() {
		return min;
	}

	public void setMin(T min) {
		this.min = min;
	}

	public T getMax() {
		return max;
	}

	public void setMax(T max) {
		this.max = max;
	}

}
