package examples;

import java.util.*;

import convivadata.*;
import core.*;
import util.*;

public class MainPredictor {
	
	public static void main(String[] args){
		
		long epochLenMs = 1000*60;
		
		int metricId = QualityConsts.METRIC_VIACOM_JOINTIME;
		
		int start = 0; int stride = (int) Math.pow(10, 5);

		String filename = "/Users/junchenjiang/Documents/research/ddn-controller/"
				+ "data/datasets/video-viacom/format-dump1.viacom.fl.txt";
		/* Example line in format-dump1.viacom.fl.txt:
		 * 1417527044750   0.0     1200.0  691.0   20115   95649   229     5       
		 * 166     WIN     eVod    Practically Useful: Scooter_1051673     Fl      
		 * 1700.0  Resource-LEVEL3
		 */
		String schemeFilename = "/Users/junchenjiang/Documents/research/ddn-controller/"
				+ "data/datasets/schema/format-schema-viacom.txt";
		/* Example line in format-schema-viacom.txt:
		 * Time	BufRate	AvgBitrate	JoinTime	Asn	City	Country	ConnType	State	
		 * Os	Liveorvod	ObjectId	PlayerType	InitBitrate	InitCdn
		 */
		
		FastReader r = new FastReader(filename);
		SchemaReader s = new SchemaReader(schemeFilename);
		Map<Integer, List<UnitInstance>> epochToInstances = 
				new HashMap<Integer, List<UnitInstance>>();
		while (true){
			// Reading the next "stride" lines (sessions) from the trace
			Block block = Block.getNextBlock(r, s, stride);
			if (block.numRecords() == 0) break;
			
			for (int i = 0; i < block.numRecords(); i++){
				RecordViacom record = (RecordViacom)block.recordAtIndex(i);
				// Skip if the resource (CDN) is "NULL"
				if (record.getDecision().contains("NULL")) continue;
				double quality = record.getQuality(metricId);
				long time = record.getTime();
				// Extract a list of features from each line in the trace
				List<String> features = Arrays.asList(
						record.getAsn(), 
						record.getConnType(), 
						record.getInitCdn(),
						record.getInitilBitrate(),
						record.getCity(), 
						record.getObjectId(), 
						record.getPlayerType());
				// Creating a new generic instance
				UnitInstance unit = UnitInstance.of(quality, time, features);
				// Instances are grouped into one-minute epochs
				int epoch = (int) ((time-(time%epochLenMs))/epochLenMs);
				List<UnitInstance> list = epochToInstances.containsKey(epoch) ? 
						epochToInstances.get(epoch) : new ArrayList<UnitInstance>();
				list.add(unit); epochToInstances.put(epoch, list);
			}
			System.out.print("Done "+start+" to "+(start+stride)+"\r");
			start += stride;
		}
		System.out.println("\r\nDone reading\r\n");

		for (int epoch : epochToInstances.keySet()){
			// Test set
			List<UnitInstance> testSet = 
					getInstancesInRange(epochToInstances, epoch+1, epoch+2);
			// Long-term train set used for train critical features
			List<UnitInstance> trainSetLong = 
					getInstancesInRange(epochToInstances, epoch-50, epoch+1);
			// Short-term train set used for quality prediction
			List<UnitInstance> trainSetShort = 
					getInstancesInRange(epochToInstances, epoch, epoch+1);
			if (testSet.size() > 100 && trainSetShort.size() > 100 && 
					trainSetLong.size() > 10*trainSetShort.size()) {
				System.out.println("**********************************************");
				System.out.println("Epoch="+epoch+
						"\t"+testSet.size()+
						"\t"+trainSetShort.size()+
						"\t"+trainSetLong.size());
				// CFA prediction
				Map<UnitInstance, Double> unitToPrediction = CfaModule.TrainAndTest(
						trainSetShort, trainSetLong, testSet);
				System.out.println("CFA");
				printAccuracy(unitToPrediction);
				System.out.println("**********************************************");
			}
		}
	}

	private static List<UnitInstance> getInstancesInRange(
			Map<Integer, List<UnitInstance>> epochToInstances, int start, int end) {
		List<UnitInstance> result = new ArrayList<UnitInstance>();
		for (int i = start; i < end; i++)
			if (epochToInstances.containsKey(i))
				result.addAll(epochToInstances.get(i));
		return result;
	}

	private static void printAccuracy(Map<UnitInstance, Double> unitToPrediction) {
		List<Double> errorList = new ArrayList<Double>();
		for (UnitInstance unit : unitToPrediction.keySet()){
			double predict = unitToPrediction.get(unit);
			double actual = unit.quality();
			double error = Math.abs(predict-actual);
			errorList.add(error);
		}
		String f = "#.###";
		System.out.println("Count="+errorList.size()+
				"\tSqrtError="+Util.format(Util.getSqrtMean(errorList),f)+
				"\tMeanError="+Util.format(Util.getAve(errorList),f)+
				"\tStdDevError="+Util.format(Util.getStdDev(errorList),f));
	}

}
