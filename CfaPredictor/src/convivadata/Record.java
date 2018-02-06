package convivadata;

import java.util.*;
import core.*;

public abstract class Record {

	public final static int TYPE_FINE = 0;
	public final static int TYPE_MEDIUM = 10;
	public final static int TYPE_MEDIUM1 = 11;
	public final static int TYPE_COARSE = 2;
	public final static int TYPE_GLOBAL = 3;
	
	public final static int TYPE_ASN = 21;
	public final static int TYPE_City = 22;
	public final static int TYPE_ConnectionType = 23;
	public final static int TYPE_ObjectId = 24;
	public final static int TYPE_PlayerType = 25;
	
	private Map<Integer, Double> mQualityMap = new HashMap<Integer, Double>();
	protected Map<Integer, String> mFeatureStrMap = new HashMap<Integer, String>();
	private Map<Integer, Double> mFeatureNumMap = new HashMap<Integer, Double>();
	private Map<Integer, Long> mFeatureNumLongMap = new HashMap<Integer, Long>();
	private Map<Integer, String> mDecisionMap = new HashMap<Integer, String>();
	
	protected static final Map<String, Integer> KeyIdMap;
	static {
    	Map<String, Integer> temp = new HashMap<String, Integer>();
    	// Vevo fields
    	temp.put(Vevo("Time"), temp.size());
    	temp.put(Vevo("BufRate"), temp.size()); temp.put(Vevo("AvgBitrate"), temp.size());
    	temp.put(Vevo("JoinTime"), temp.size());
    	temp.put(Vevo("Asn"), temp.size()); temp.put(Vevo("City"), temp.size());
    	temp.put(Vevo("Country"), temp.size()); temp.put(Vevo("ConnType"), temp.size());
    	temp.put(Vevo("InitBitrate"), temp.size()); temp.put(Vevo("InitCdn"), temp.size());
     	// NGC fields
     	temp.put(Ngc("Time"), temp.size());
     	temp.put(Ngc("Rtt"), temp.size()); temp.put(Ngc("Loss"), temp.size());
     	temp.put(Ngc("Jitter"), temp.size());
     	temp.put(Ngc("Mos"), temp.size()); temp.put(Ngc("Dmos"), temp.size());
     	temp.put(Ngc("Ip1"), temp.size()); temp.put(Ngc("Ip2"), temp.size());
     	temp.put(Ngc("Asn1"), temp.size()); temp.put(Ngc("Asn2"), temp.size());
     	temp.put(Ngc("Country1"), temp.size()); temp.put(Ngc("Country2"), temp.size());
     	temp.put(Ngc("Connection1"), temp.size()); temp.put(Ngc("Connection2"), temp.size());
     	 temp.put(Ngc("RelayOption"), temp.size());
    	// Bing fields
    	temp.put(Bing("Time"), temp.size());
    	temp.put(Bing("RTT"), temp.size());
    	temp.put(Bing("Ip"), temp.size()); temp.put(Bing("Asn"), temp.size());
    	temp.put(Bing("Country"), temp.size());
    	temp.put(Bing("State"), temp.size());
    	temp.put(Bing("Edge"), temp.size());
    	// Synthetic fields
    	temp.put(Syn("Quality"), temp.size());
    	temp.put(Syn("Time"), temp.size());
    	for (int i = 0; i < 10; i++) temp.put(Syn("F"+i), temp.size());
    	temp.put(Syn("Decision"), temp.size());
    	// Viacom fields
    	temp.put(Viacom("Time"), temp.size());
    	temp.put(Viacom("BufRate"), temp.size()); temp.put(Viacom("AvgBitrate"), temp.size());
    	temp.put(Viacom("JoinTime"), temp.size());
    	temp.put(Viacom("Asn"), temp.size()); temp.put(Viacom("City"), temp.size());
    	temp.put(Viacom("Country"), temp.size()); temp.put(Viacom("ConnType"), temp.size());
    	temp.put(Viacom("State"), temp.size()); 
    	temp.put(Viacom("Os"), temp.size()); temp.put(Viacom("Liveorvod"), temp.size());
    	temp.put(Viacom("ObjectId"), temp.size()); temp.put(Viacom("PlayerType"), temp.size());
    	temp.put(Viacom("InitBitrate"), temp.size()); temp.put(Viacom("InitCdn"), temp.size());
    	KeyIdMap = Collections.unmodifiableMap(temp);
    }
	private static int keyToId(String k){
		if (KeyIdMap.containsKey(k)) return KeyIdMap.get(k);
		else throw new RuntimeException();
	}
	
	private static String Vevo(String string) {return "vevo_"+string;}
	private static String Ngc(String string) {return "ngc_"+string;}
	private static String Bing(String string) {return "bing_"+string;}
	private static String Syn(String string) {return "syn_"+string;}
	private static String Viacom(String string) {return "viacom_"+string;}

	protected double GetQuality(String k){
		return mQualityMap.get(keyToId(getTypedKey(k)));}
	
	private String getTypedKey(String k) {
		if (this instanceof RecordViacom) return Viacom(k);
		if (this instanceof RecordVevo) return Vevo(k);
		throw new RuntimeException();
	}

	protected String  GetFeatureStr(String k){
		return mFeatureStrMap.get(keyToId(getTypedKey(k)));}

	protected double GetFeatureNum(String k){
		return mFeatureNumMap.get(keyToId(getTypedKey(k)));}
	
	protected long GetFeatureNumLong(String k){
		return mFeatureNumLongMap.get(keyToId(getTypedKey(k)));}

	protected String GetDecision(String k){
		return mDecisionMap.get(keyToId(getTypedKey(k)));}
	
	protected void InsertQuality(String k, double v){
		mQualityMap.put(keyToId(getTypedKey(k)), v);}
	
	protected void InsertFeatureStr(String k, String v){
		mFeatureStrMap.put(keyToId(getTypedKey(k)), v);}
	
	protected void InsertFeatureNum(String k, double v){
		mFeatureNumMap.put(keyToId(getTypedKey(k)), v);}
	
	protected void InsertFeatureNumLong(String k, long v){
		mFeatureNumLongMap.put(keyToId(getTypedKey(k)), v);}
	
	protected void InsertDecision(String k, String v){
		mDecisionMap.put(keyToId(getTypedKey(k)), v);}

	protected Record getDeepCopy(Record copy){
		copy.mQualityMap = new HashMap<Integer, Double>(this.mQualityMap);
		copy.mFeatureStrMap = new HashMap<Integer, String>(this.mFeatureStrMap);
		copy.mFeatureNumMap = new HashMap<Integer, Double>(this.mFeatureNumMap);
		copy.mFeatureNumLongMap = new HashMap<Integer, Long>(this.mFeatureNumLongMap);
		copy.mDecisionMap = new HashMap<Integer, String>(this.mDecisionMap);
		return copy;
	}

	public abstract GroupId getGroupId(GroupType type);
	
//	public abstract String getGroupSpatial(int type);

	public abstract String getDecision();
	
	public abstract void setDecision(String decision);
	
	public abstract double getQuality(int metricId);
	
	public abstract void setQuality(double quality, int metricId);
	
	public abstract boolean isValid();

	public abstract int getEpoch(int epochLenSeconds);
	
	public abstract Record deepCopy();

	public abstract long getTime();

}
