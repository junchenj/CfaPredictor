package core;

import java.util.*;

public class UnitInstance {
	private double mQuality = -1;
	private long mTime = -1;
	private List<String> mFeatures = new ArrayList<String>();
	
	private UnitInstance(double quality, long time, List<String> features) {
		mQuality = quality;
		mTime = time;
		for (int i = 0; i < features.size(); i++)
			mFeatures.add(features.get(i));
	}

	public static UnitInstance of(double quality, long time, List<String> features){
		return new UnitInstance(quality, time, features);
	}

	public double quality(){return mQuality;}
	
	public long time(){return mTime;}
	
	public List<String> features() {
		return mFeatures;
	}
}
