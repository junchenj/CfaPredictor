package core;

import java.util.*;
import util.*;

public class CfaModule {
	
	/*
	 * Main function of CFA prediction
	 * trainSetShort: Short-term history train set used for quality prediction
	 * trainSetLong: Long-term history train set used for train critical features
	 * testSet: Test set
	 * Output: a map between each record in the test set and its predicted quality
	 */
	public static Map<UnitInstance, Double> TrainAndTest(
			List<UnitInstance> trainSetShort,
			List<UnitInstance> trainSetLong, 
			List<UnitInstance> testSet) {
		Map<UnitInstance, Double> result = new HashMap<UnitInstance, Double>();
		
		int numFeatures = trainSetShort.get(0).features().size();
		// Enumerate all subsets of features 
		List<List<Integer>> subsetIndexes = Util.getAllNonEmptySubsetIndexes(numFeatures);

		// GroupId: a feature value combination
		// Metrics: quality statistics of sessions of the same GroupId
		
		// Group instances in trainSetShort by the finest keys (specified values
		// on all features)
		Map<GroupId, Metrics> finestKeyToMetricsShort = new HashMap<GroupId, Metrics>();
		Map<GroupId, UnitInstance> finestKeyToExample =
				new HashMap<GroupId, UnitInstance>();
		List<Integer> fullIndexList = new ArrayList<Integer>();
		for (int i = 0; i < numFeatures; i++) fullIndexList.add(1);
		for (UnitInstance unit : trainSetShort){
			GroupId finestKey = getKey(unit.features(), fullIndexList);
			Metrics metrics = finestKeyToMetricsShort.containsKey(finestKey) ? 
					finestKeyToMetricsShort.get(finestKey) : new Metrics();
			metrics.addValue(unit.quality());
			finestKeyToMetricsShort.put(finestKey, metrics);
			if (!finestKeyToExample.containsKey(finestKey))
				finestKeyToExample.put(finestKey, unit);
		}
		// Group instances in trainSetShort by all possible keys (feature value 
		// combinations)
		Map<GroupId, Metrics> keyToMetricsShort = new HashMap<GroupId, Metrics>();
		for (GroupId finestKey : finestKeyToMetricsShort.keySet()){
			UnitInstance exampleUnit = finestKeyToExample.get(finestKey);
			Metrics finestMetrics = finestKeyToMetricsShort.get(finestKey);
			for (List<Integer> subsetIndex : subsetIndexes){
				GroupId key = getKey(exampleUnit.features(), subsetIndex);
				Metrics metrics = keyToMetricsShort.containsKey(key) ? 
						keyToMetricsShort.get(key) : new Metrics();
				metrics.addMetrics(finestMetrics);
				keyToMetricsShort.put(key, metrics);
			}
		}
		// Group instances in trainSetLong by the finest keys
		Map<GroupId, Metrics> finestKeyToMetricsLong = new HashMap<GroupId, Metrics>();
		Map<GroupId, UnitInstance> finestKeyToExampleLong = 
				new HashMap<GroupId, UnitInstance>();
		for (UnitInstance unit : trainSetLong){
			GroupId finestKey = getKey(unit.features(), fullIndexList);
			Metrics metrics = finestKeyToMetricsLong.containsKey(finestKey) ? 
					finestKeyToMetricsLong.get(finestKey) : new Metrics();
			metrics.addValue(unit.quality());
			finestKeyToMetricsLong.put(finestKey, metrics);
			if (!finestKeyToExampleLong.containsKey(finestKey))
				finestKeyToExampleLong.put(finestKey, unit);
		}
		// Group instances in trainSetLong by all possible keys
		Map<GroupId, Metrics> keyToMetricsLong = new HashMap<GroupId, Metrics>();
		for (GroupId finestKey : finestKeyToMetricsLong.keySet()){
			UnitInstance exampleUnit = finestKeyToExampleLong.get(finestKey);
			Metrics finestMetricsLong = finestKeyToMetricsLong.get(finestKey);
			for (List<Integer> subsetIndex : subsetIndexes){
				GroupId key = getKey(exampleUnit.features(), subsetIndex);
				Metrics metrics = keyToMetricsLong.containsKey(key) ? 
						keyToMetricsLong.get(key) : new Metrics();
				metrics.addMetrics(finestMetricsLong);
				keyToMetricsLong.put(key, metrics);
			}
		}
		
		// Group instances in testSet by the finest keys 
		Map<GroupId, Metrics> finestKeyToMetricsTest = new HashMap<GroupId, Metrics>();
		Map<GroupId, List<UnitInstance>> finestKeyToUnitsTest = 
				new HashMap<GroupId, List<UnitInstance>>();
		for (UnitInstance unit : testSet){
			GroupId finestKey = getKey(unit.features(), fullIndexList);
			Metrics metrics = finestKeyToMetricsTest.containsKey(finestKey) ? 
					finestKeyToMetricsTest.get(finestKey) : new Metrics();
			metrics.addValue(unit.quality());
			finestKeyToMetricsTest.put(finestKey, metrics);
			List<UnitInstance> units = finestKeyToUnitsTest.containsKey(finestKey) ? 
					finestKeyToUnitsTest.get(finestKey) : new ArrayList<UnitInstance>();
			units.add(unit); finestKeyToUnitsTest.put(finestKey, units);
		}
		
		for (GroupId finestKey : finestKeyToUnitsTest.keySet()){
			UnitInstance exampleUnit = finestKeyToUnitsTest.get(finestKey).get(0);
			GroupId cfaKey = getCfaKey(finestKey, exampleUnit, subsetIndexes,
					keyToMetricsShort, keyToMetricsLong);
			if (cfaKey != null) {
				double prediction = keyToMetricsShort.get(cfaKey).getMean();
				for (UnitInstance unit : finestKeyToUnitsTest.get(finestKey))
					result.put(unit, prediction);
			}
		}
		return result;
	}
	
	static private int minThreshold = 5;
	/*
	 * Find the critical feature combination
	 */
	private static GroupId getCfaKey(GroupId finestKey, UnitInstance exampleUnit,
			List<List<Integer>> subsetIndexes, Map<GroupId, Metrics> keyToMetricsShort,
			Map<GroupId, Metrics> keyToMetricsLong) {
		GroupId cfaKey = null;
		if (keyToMetricsLong.containsKey(finestKey)){
			// If the key has been profiled in the long-term history, 
			// pick the feature subset whose instances have the most similar mean quality
			// to the mean quality of the instances of the finest-grained key
			Metrics finestMetricsLong = keyToMetricsLong.get(finestKey);
			double minDiff = Double.MAX_VALUE;
			for (List<Integer> subsetIndex : subsetIndexes){
				GroupId key = getKey(exampleUnit.features(), subsetIndex);
				if (!keyToMetricsShort.containsKey(key) || 
						keyToMetricsShort.get(key).getCount() < minThreshold) continue;
				Metrics metricsLong = keyToMetricsLong.get(key);
				double diff = Math.abs(metricsLong.getMean()-
						finestMetricsLong.getMean());
				if (minDiff > diff) {minDiff = diff; cfaKey = key;}
			}
		}
		if (cfaKey == null){
			// If the key has not been profiled in the long-term history
			// Pick the feature subset that has the least features (Occam's razor)
			// and has enough instances in the short-term history
			int maxDepth = -1;
			for (List<Integer> subsetIndex : subsetIndexes){
				GroupId key = getKey(exampleUnit.features(), subsetIndex);
				if (!keyToMetricsShort.containsKey(key) || 
						keyToMetricsShort.get(key).getCount() < minThreshold) continue;
				int depth = 0;
				for (int i : subsetIndex) if (i == 1) depth++;
				if (maxDepth < depth) {maxDepth = depth; cfaKey = key;}
			}
		}
		return cfaKey;
	}

	private static GroupId getKey(List<String> features, List<Integer> subsetIndex){
		List<Integer> indexList = new ArrayList<Integer>();
		List<String> spatialList = new ArrayList<String>();
		for (int i = 0; i < subsetIndex.size(); i++) 
			if (subsetIndex.get(i) == 1){
				indexList.add(i); spatialList.add(features.get(i));
			}
		GroupType type = GroupType.of(indexList, -1);
		GroupId groupId = GroupId.of(type, spatialList);
		return groupId;
	}

}
