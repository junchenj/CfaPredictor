package convivadata;

import java.util.*;

import core.*;
import util.*;

public class RecordViacom extends Record {

	public static RecordViacom initialize(String[] fields, Map<String, Integer> tagToIndex) {
		
		return initializeByFields(
				Util.getField(fields, "BufRate", tagToIndex),
				Util.getField(fields, "AvgBitrate", tagToIndex),
				Util.getField(fields, "JoinTime", tagToIndex),
				Util.getField(fields, "Time", tagToIndex),
				Util.getField(fields, "Asn", tagToIndex),
				Util.getField(fields, "City", tagToIndex),
				Util.getField(fields, "Country", tagToIndex),
				Util.getField(fields, "ConnType", tagToIndex),
				Util.getField(fields, "State", tagToIndex),
				Util.getField(fields, "Os", tagToIndex),
				Util.getField(fields, "Liveorvod", tagToIndex),
				Util.getField(fields, "ObjectId", tagToIndex),
				Util.getField(fields, "PlayerType", tagToIndex),
				Util.getField(fields, "InitBitrate", tagToIndex),
				Util.getField(fields, "InitCdn", tagToIndex)
				);
	}

	public static RecordViacom initializeByFields(
			String bufRate, String avgBitrate, String joinTime, 
			String time, String asn, String city, String country, String connType,
			String state, String os, String liveorvod, String objectId, String playerType,
			String initBitrate, String initCdn){
		RecordViacom record = new RecordViacom();
		record.setBufRate(Double.valueOf(bufRate));
		record.setAvgBitrate(Double.valueOf(avgBitrate));
		record.setJoinTime(Double.valueOf(joinTime));
		
		record.setTime(Long.valueOf(time));
		record.setAsn(asn);
		record.setCity(city);
		record.setCountry(country);
		record.setConnType(connType);
		record.setState(state);
		record.setOs(os);
		record.setLiveorvod(liveorvod);
//		record.setObjectId(String.valueOf(objectId.hashCode()));
		record.setObjectId(objectId);
		record.setPlayerType(playerType);
		
		record.setInitilBitrate(initBitrate);
		record.setInitCdn(initCdn);
		return record;
	}

	

	public void setBufRate(double v) {InsertQuality("BufRate", v);}
	public void setAvgBitrate(double v) {InsertQuality("AvgBitrate", v);}
	public void setJoinTime(double v) {InsertQuality("JoinTime", v);}
	
	public void setTime(long v) {InsertFeatureNumLong("Time", v);}
	public void setAsn(String v) {
		InsertFeatureStr("Asn", v);}
	public void setCity(String v) {InsertFeatureStr("City", v);}
	public void setCountry(String v) {InsertFeatureStr("Country", v);}
	public void setConnType(String v) {InsertFeatureStr("ConnType", v);}
	public void setState(String v) {InsertFeatureStr("State", v);}
	public void setOs(String v) {InsertFeatureStr("Os", v);}
	public void setLiveorvod(String v) {InsertFeatureStr("Liveorvod", v);}
	public void setObjectId(String v) {InsertFeatureStr("ObjectId", v);}
	public void setPlayerType(String v) {InsertFeatureStr("PlayerType", v);}
	
	public void setInitilBitrate(String v) {InsertDecision("InitBitrate", v);}
	public void setInitCdn(String v) {InsertDecision("InitCdn", v);}

	
	public double getBufRate(){return GetQuality("BufRate");}
	public double getAvgBitrate(){return GetQuality("AvgBitrate");}
	public double getJoinTime(){return GetQuality("JoinTime");}
	
	public long getTime(){return GetFeatureNumLong("Time");}
	public String getAsn(){
//		if (GetFeatureStr("Asn").equals("229"))
//			System.out.println("DEBUG1: "+mFeatureStrMap+"\t"+KeyIdMap);
		return GetFeatureStr("Asn");}
	public String getCity(){return GetFeatureStr("City");}
	public String getCountry(){return GetFeatureStr("Country");}
	public String getConnType(){return GetFeatureStr("ConnType");}
	public String getState(){return GetFeatureStr("State");}
	public String getOs(){return GetFeatureStr("Os");}
	public String getLiveorvod(){return GetFeatureStr("Liveorvod");}
	public String getObjectId(){return GetFeatureStr("ObjectId");}
	public String getPlayerType(){return GetFeatureStr("PlayerType");}
	
	public String getInitilBitrate(){return GetDecision("InitBitrate");}
	public String getInitCdn(){return GetDecision("InitCdn");}
	
	@Override
	public GroupId getGroupId(GroupType groupType){
		if (groupType.isMultipleTypes()){
			List<String> spatialList = new ArrayList<String>();
			for (int i = 0; i < groupType.featureTypes().size(); i++){
				int type = groupType.featureTypes().get(i);
//				String spatial = null;
//				switch(type){
//				case TYPE_ASN:
//					spatial = "AS"+getAsn(); break;
//				case TYPE_City:
//					spatial = "City"+getCity(); break;
//				case TYPE_ConnectionType:
//					spatial = "ConnectionType"+getConnType(); break;
//				case TYPE_ObjectId:
//					spatial = "ObjectId"+getObjectId(); break;
//				case TYPE_PlayerType:
//					spatial = "PlayerType"+getPlayerType(); break;
//				default: throw new RuntimeException("Unknown type: "+type);
//				}
				String spatial = getValueOfFeature(type);
				spatialList.add(spatial);
			}
			return GroupId.of(groupType, spatialList);
		} else {
			String spatialString = null;
			switch(groupType.featureType()){
			case TYPE_FINE: 
				spatialString = "AS"+getAsn()+"#"+"City"+getCity()+"#"+"Conn"+getConnType();
				break;
			case TYPE_MEDIUM: 
				spatialString = "AS"+getAsn()+"#"+"City"+getCity();
				break;
			case TYPE_MEDIUM1: 
				spatialString = "AS"+getAsn()+"#"+"Conn"+getConnType();
				break;
			case TYPE_COARSE: 
				spatialString = "AS"+getAsn();
				break;
			case TYPE_GLOBAL: 
				spatialString = "Global";
				break;
			default: throw new RuntimeException("Unknown group type: "+groupType);
			}
			return GroupId.of(groupType, spatialString);
		}
	}
	
	public String getValueOfFeature(int type){
		String spatial = "";
		switch(type){
		case TYPE_ASN:
			spatial = "AS"+getAsn(); break;
		case TYPE_City:
			spatial = "City"+getCity(); break;
		case TYPE_ConnectionType:
			spatial = "ConnectionType"+getConnType(); break;
		case TYPE_ObjectId:
			spatial = "ObjectId"+getObjectId(); break;
		case TYPE_PlayerType:
			spatial = "PlayerType"+getPlayerType(); break;
		default: throw new RuntimeException("Unknown type: "+type);
		}
		return spatial;
	}

	@Override
	public String getDecision() {
//		return getInitilBitrate()+"#"+getInitCdn();
		return getInitCdn();
	}
	@Override
	public void setDecision(String decision) {
//		String[] fields = decision.split("#"); setInitilBitrate(fields[0]); setInitCdn(fields[1]);
		setInitCdn(decision);
	}

	@Override
	public double getQuality(int metricId) {
		switch (metricId){
		case QualityConsts.METRIC_VIACOM_BUFRATIO: return getBufRate();
		case QualityConsts.METRIC_VIACOM_AVGBITRATE: return getAvgBitrate();
		case QualityConsts.METRIC_VIACOM_JOINTIME: return getJoinTime();
		case QualityConsts.METRIC_VIACOM_BUFRATIO_BAD: 
			return getBufRate() > 0.05 ? 1 : 0;
		case QualityConsts.METRIC_VIACOM_AVGBITRATE_BAD: 
			return getAvgBitrate() < 1000 ? 1 : 0;
		case QualityConsts.METRIC_VIACOM_JOINTIME_BAD: 
			return getJoinTime() > 5000 ? 1 : 0;
		default: throw new RuntimeException();
		}
	}
	
	@Override
	public void setQuality(double quality, int metricId) {
		switch (metricId){
		case QualityConsts.METRIC_VIACOM_BUFRATIO: setBufRate(quality); break;
		case QualityConsts.METRIC_VIACOM_AVGBITRATE: setAvgBitrate(quality); break;
		case QualityConsts.METRIC_VIACOM_JOINTIME: setJoinTime(quality); break;
		default: throw new RuntimeException();
		}
	}

	@Override
	public boolean isValid() {
		return (getBufRate() >= 0 && getAvgBitrate() > 0 && getJoinTime() > 0);}

	@Override
	public int getEpoch(int epochLenSeconds) {
//		long temp = (long)getTime();
//		long epochLenMilliseconds = epochLenSeconds == Integer.MAX_VALUE ? 
//				Long.MAX_VALUE : epochLenSeconds*1000;
//		return (int) ((temp-temp%epochLenMilliseconds)/epochLenMilliseconds);
		return RecordViacom.timeMsToEpochInSec(getTime(), epochLenSeconds);
	}

	@Override
	public Record deepCopy() {
		RecordViacom copy = new RecordViacom(); return getDeepCopy(copy);
	}

	public static int timeMsToEpochInSec(long time, int epochLenSeconds) {
		long temp = (long)time;
		long epochLenMilliseconds = epochLenSeconds == Integer.MAX_VALUE ? 
				Long.MAX_VALUE : epochLenSeconds*1000;
		return (int) ((temp-temp%epochLenMilliseconds)/epochLenMilliseconds);
	}

}
