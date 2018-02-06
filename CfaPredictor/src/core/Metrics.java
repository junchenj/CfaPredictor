package core;

import java.util.*;


public class Metrics {

	public static boolean UseSampleOrGaussian = true;
	
	private double mCount = 0;
	private double mSum = 0;
	private double mSqrSum = 0;
	private double mWeightSum = 0;
	private double mSqrWeightSum = 0;
	private double scale = 0.001;
	
	// Could be removed for scalability
	private List<Double> mValues = new ArrayList<Double>();
	
	public String toString(){return "Metrics%Count="+mCount;}
	
	public Metrics addValue(double v){return addValue(v, 1.0);}
	public Metrics addValue(double v, double weight){
		mCount++;
		double scaled = v*scale;
		mSum += scaled*weight;
		mSqrSum += scaled*scaled*weight*weight;
		mWeightSum += weight;
		mSqrWeightSum += weight*weight;
		// Could be removed for scalability
		mValues.add(scaled);
		return this;}
	public Metrics addMetrics(Metrics other){return addMetrics(other, 1.0);}
	public Metrics addMetrics(Metrics other, double weight){
		this.mCount += other.mCount;
		this.mSum += other.mSum*weight;
		this.mSqrSum += other.mSqrSum*weight*weight;
		this.mWeightSum += other.mWeightSum*weight;
		this.mSqrWeightSum += other.mSqrWeightSum*weight*weight;
		// Could be removed for scalability
		this.mValues.addAll(other.mValues);
		return this;}
	
	public Metrics addValues(List<Double> list) {
		for (double v : list) addValue(v); return this;}

	public double getNext(){
		if (UseSampleOrGaussian)
			return (1.0/scale)*mValues.get(new Random().nextInt(mValues.size()));
//			throw new RuntimeException("No mValues set!");
		else {
			double mean = getMean();
			double stdev = getStdev();
			double rand = new Random().nextGaussian();
			return Math.min(Math.max(rand*stdev+mean, 0), 2*mean);
		}
	}
	
	public double getNextKSum(int k){
		double mean = getMean();
		double stdev = getStdev();
		Random rand = new Random();
		double result = 0;
//		for (int i = 0; i < k; i++)
//			result += rand.nextGaussian()*stdev+mean;
		// simulate sum of k random samples. 
		// this assumes the average has standard deviation of mean (seom). 
		result += k*(rand.nextGaussian()*stdev/Math.pow(k, 0.5)+mean);
		return result;
		
//		double result = 0;
//		Random rand = new Random();
//		int count = mValues.size();
//		for (int i = 0; i < k; i++)
//			result += (1.0/scale)*mValues.get(rand.nextInt(count));
//		return result;
	}
	
	public double getCount(){return mCount;}
	
	public double getMean(){return (mSum/scale)/mWeightSum;}
	
	public double getStdev(){
		double part1 = mSqrSum/mSqrWeightSum;
		double part2 = Math.pow(mSum/mWeightSum, 2.0);
		double temp = part1-part2;
		double temp2 = Math.pow(temp, 0.5);
		if (Double.isNaN(temp2)) return 0;
		else return (1.0/scale)*temp2;}
	
//	public double getPerc(double v){return (1.0/scale)*Util.getPerc(mValues, v);}
	
}
