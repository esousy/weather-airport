package com.crossover.trial.weather.dao.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A collected point, including some information about the range of collected
 * values
 *
 * @author code test administrator
 */
public class DataPoint implements Serializable {

	private static final long serialVersionUID = -2533695717816373790L;

	private double mean = 0.0;

	private int first = 0;

	private int second = 0;

	private int third = 0;

	private int count = 0;

	/** private constructor, use the builder to create this object */
	public DataPoint() {

	}

	/**
	 * @param mean
	 * @param first
	 * @param second
	 * @param third
	 * @param count
	 */
	public DataPoint(double mean, int first, int second, int third, int count) {
		this.mean = mean;
		this.first = first;
		this.second = second;
		this.third = third;
		this.count = count;
	}
	
	
	

	/**
	 * @return the mean
	 */
	public final double getMean() {
		return mean;
	}

	/**
	 * @param mean the mean to set
	 */
	public final void setMean(double mean) {
		this.mean = mean;
	}

	/**
	 * @return the first
	 */
	public final int getFirst() {
		return first;
	}

	/**
	 * @param first the first to set
	 */
	public final void setFirst(int first) {
		this.first = first;
	}

	/**
	 * @return the second
	 */
	public final int getSecond() {
		return second;
	}

	/**
	 * @param second the second to set
	 */
	public final void setSecond(int second) {
		this.second = second;
	}

	/**
	 * @return the third
	 */
	public final int getThird() {
		return third;
	}

	/**
	 * @param third the third to set
	 */
	public final void setThird(int third) {
		this.third = third;
	}

	/**
	 * @return the count
	 */
	public final int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public final void setCount(int count) {
		this.count = count;
	}




	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + first;
		long temp;
		temp = Double.doubleToLongBits(mean);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + second;
		result = prime * result + third;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataPoint other = (DataPoint) obj;
		if (count != other.count)
			return false;
		if (first != other.first)
			return false;
		if (Double.doubleToLongBits(mean) != Double.doubleToLongBits(other.mean))
			return false;
		if (second != other.second)
			return false;
		if (third != other.third)
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DataPoint [mean=" + mean + ", first=" + first + ", second=" + second + ", third=" + third + ", count="
				+ count + "]";
	}




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
			return new DataPoint(this.first, this.mean, this.median, this.last, this.count);
		}
	}
}
