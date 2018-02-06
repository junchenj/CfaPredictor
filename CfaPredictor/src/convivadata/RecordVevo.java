package convivadata;

import java.util.*;

import core.*;
import util.*;

public class RecordVevo extends Record {

	private static final long serialVersionUID = 4455672629226750331L;

	public static RecordVevo initialize(String[] fields, Map<String, Integer> tagToIndex) {
		/*Deprecated*/
//		RecordVevo record = new RecordVevo();
//		record.setBufRate(Double.valueOf(Util.getField(fields, "BufRate", tagToIndex)));
//		record.setAvgBitrate(Double.valueOf(Util.getField(fields, "AvgBitrate", tagToIndex)));
//		record.setJoinTime(Double.valueOf(Util.getField(fields, "JoinTime", tagToIndex)));
//		
//		record.setTime(Double.valueOf(Util.getField(fields, "Time", tagToIndex)));
//		record.setAsn(Util.getField(fields, "Asn", tagToIndex));
//		record.setCity(Util.getField(fields, "City", tagToIndex));
//		record.setCountry(Util.getField(fields, "Country", tagToIndex));
//		record.setConnType(Util.getField(fields, "ConnType", tagToIndex));
//		
//		record.setInitilBitrate(Util.getField(fields, "InitBitrate", tagToIndex));
//		record.setInitCdn(Util.getField(fields, "InitCdn", tagToIndex));
//		return record;
		
		return initializeByFields(
				Util.getField(fields, "BufRate", tagToIndex),
				Util.getField(fields, "AvgBitrate", tagToIndex),
				Util.getField(fields, "JoinTime", tagToIndex),
				Util.getField(fields, "Time", tagToIndex),
				Util.getField(fields, "Asn", tagToIndex),
				Util.getField(fields, "City", tagToIndex),
				Util.getField(fields, "Country", tagToIndex),
				Util.getField(fields, "ConnType", tagToIndex),
				Util.getField(fields, "InitBitrate", tagToIndex),
				Util.getField(fields, "InitCdn", tagToIndex)
				);
	}

	public static RecordVevo initializeByFields(
			String bufRate, String avgBitrate, String joinTime, 
			String time, String asn, String city, String country, String connType,
			String initBitrate, String initCdn){
		RecordVevo record = new RecordVevo();
		record.setBufRate(Double.valueOf(bufRate));
		record.setAvgBitrate(Double.valueOf(avgBitrate));
		record.setJoinTime(Double.valueOf(joinTime));
		
		record.setTime(Long.valueOf(time));
		record.setAsn(asn);
		record.setCity(city);
		record.setCountry(country);
		record.setConnType(connType);
		
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
	
	public String getInitilBitrate(){return GetDecision("InitBitrate");}
	public String getInitCdn(){return GetDecision("InitCdn");}
	
	@Override
	public GroupId getGroupId(GroupType groupType){
		if (groupType.isMultipleTypes()){
			List<String> spatialList = new ArrayList<String>();
			for (int i = 0; i < groupType.featureTypes().size(); i++){
				int type = groupType.featureTypes().get(i);
				String spatial = null;
				switch(type){
				case TYPE_ASN:
					spatial = "AS"+getAsn(); break;
				case TYPE_City:
					spatial = "City"+getCity(); break;
				default: throw new RuntimeException("Unknown type: "+type);
				}
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

	@Override
	public String getDecision() {return getInitilBitrate()+"#"+getInitCdn();}
	@Override
	public void setDecision(String decision) {
		String[] fields = decision.split("#"); setInitilBitrate(fields[0]); setInitCdn(fields[1]);}

	@Override
	public double getQuality(int metricId) {
		switch (metricId){
		case QualityConsts.METRIC_VEVO_BUFRATIO: return getBufRate();
		case QualityConsts.METRIC_VEVO_AVGBITRATE: return getAvgBitrate();
		case QualityConsts.METRIC_VEVO_JOINTIME: return getJoinTime();
		default: throw new RuntimeException();
		}
	}
	
	@Override
	public void setQuality(double quality, int metricId) {
		switch (metricId){
		case QualityConsts.METRIC_VEVO_BUFRATIO: setBufRate(quality); break;
		case QualityConsts.METRIC_VEVO_AVGBITRATE: setAvgBitrate(quality); break;
		case QualityConsts.METRIC_VEVO_JOINTIME: setJoinTime(quality); break;
		default: throw new RuntimeException();
		}
	}

	@Override
	public boolean isValid() {
		return (getBufRate() >= 0 && getAvgBitrate() > 0 && getJoinTime() > 0);}

	@Override
	public int getEpoch(int epochLenSeconds) {
		long temp = (long)getTime();
		long epochLenMilliseconds = epochLenSeconds == Integer.MAX_VALUE ? 
				Long.MAX_VALUE : epochLenSeconds*1000;
		return (int) ((temp-temp%epochLenMilliseconds)/epochLenMilliseconds);
	}

	@Override
	public Record deepCopy() {
		RecordVevo copy = new RecordVevo(); return getDeepCopy(copy);
	}

}
