package com.crossover.trial.weather.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A collected point, including some information about the range of collected
 * values
 *
 * @author code test administrator
 * @version 1.2.0
 * @since 2016-12-24
 */
public class DataPoint {

	private double mean = 0.0;

	private int first = 0;

	private int second = 0;

	private int third = 0;

	private int count = 0;

	/**
	 * private constructor, use the builder to create this object
	 **/
	@SuppressWarnings("unused")
	private DataPoint() {
	}

	/**
	 * protected constructor for builder pattern.
	 * 
	 * @param first
	 * @param second
	 * @param mean
	 * @param third
	 * @param count
	 */
	protected DataPoint(int first, int second, int mean, int third, int count) {
		this.setFirst(first);
		this.setMean(mean);
		this.setSecond(second);
		this.setThird(third);
		this.setCount(count);
	}

	/**
	 * the mean of the observations
	 * 
	 * @return double mean.
	 **/
	public double getMean() {
		return mean;
	}

	/**
	 * Set mean value.
	 * 
	 * @param mean
	 */
	public void setMean(double mean) {
		this.mean = mean;
	}

	/**
	 * 1st quartile -- useful as a lower bound
	 * 
	 * @return int
	 **/
	public int getFirst() {
		return first;
	}

	/**
	 * Set 1st quartile
	 * 
	 * @param first
	 */
	public void setFirst(int first) {
		this.first = first;
	}

	/**
	 * 2nd quartile -- median value
	 * 
	 * @return int
	 **/
	public int getSecond() {
		return second;
	}

	/**
	 * Set 2nd quartile
	 * 
	 * @param second
	 */
	public void setSecond(int second) {
		this.second = second;
	}

	/**
	 * 3rd quartile value -- less noisy upper value
	 * 
	 * @return int
	 * */
	public int getThird() {
		return third;
	}

	/**
	 * Set 3rd quartile
	 * 
	 * @param third
	 */
	public void setThird(int third) {
		this.third = third;
	}

	/**
	 * the total number of measurements
	 * 
	 * @return int
	 **/
	public int getCount() {
		return count;
	}

	/**
	 * Set count
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Override defaults
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.NO_CLASS_NAME_STYLE);
	}

	/**
	 * Override equals
	 */
	public boolean equals(Object that) {
		return this.toString().equals(that.toString());
	}

	/**
	 * Builder class
	 * @author Mohammad
	 *
	 */
	static public class Builder {
		int first;
		int mean;
		int median;
		int last;
		int count;

		public Builder() {
		}

		public Builder withFirst(int first) {
			this.first = first;
			return this;
		}

		public Builder withMean(int mean) {
			this.mean = mean;
			return this;
		}

		public Builder withMedian(int median) {
			this.median = median;
			return this;
		}

		public Builder withCount(int count) {
			this.count = count;
			return this;
		}

		public Builder withLast(int last) {
			this.last = last;
			return this;
		}

		public DataPoint build() {
			return new DataPoint(this.first, this.mean, this.median, this.last,
					this.count);
		}
	}
}
