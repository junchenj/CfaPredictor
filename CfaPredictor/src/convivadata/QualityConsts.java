package convivadata;

public class QualityConsts {
	
	public static final int METRIC_VEVO_BUFRATIO = 10;
	public static final int METRIC_VEVO_AVGBITRATE = 11;
	public static final int METRIC_VEVO_JOINTIME = 12;

	public static final int METRIC_NGC_RTT = 20;
	public static final int METRIC_NGC_LOSS = 21;
	public static final int METRIC_NGC_JITTER = 22;
	public static final int METRIC_NGC_DMOS = 23;

	public static final int METRIC_BING_RTT = 30;
	
	public static final int METRIC_VIACOM_BUFRATIO = 40;
	public static final int METRIC_VIACOM_AVGBITRATE = 41;
	public static final int METRIC_VIACOM_JOINTIME = 42;
	public static final int METRIC_VIACOM_BUFRATIO_BAD = 43;
	public static final int METRIC_VIACOM_AVGBITRATE_BAD = 44;
	public static final int METRIC_VIACOM_JOINTIME_BAD = 45;
	
	public static final int METRIC_SYNTHETIC = 100;
	
	public static String GetMetricName(int metricId){
		switch (metricId){
		case METRIC_VEVO_BUFRATIO:		return "METRIC_VEVO_BUFRATIO";
		case METRIC_VEVO_AVGBITRATE:	return "METRIC_VEVO_AVGBITRATE";
		case METRIC_VEVO_JOINTIME:		return "METRIC_VEVO_JOINTIME";
		
		case METRIC_NGC_RTT:					return "METRIC_NGC_RTT";
		case METRIC_NGC_LOSS:					return "METRIC_NGC_LOSS";
		case METRIC_NGC_JITTER:				return "METRIC_NGC_JITTER";
		case METRIC_NGC_DMOS:				return "METRIC_NGC_DMOS";
		
		case METRIC_BING_RTT:					return "METRIC_BING_RTT";
		
		case METRIC_SYNTHETIC:					return "METRIC_SYNTHETIC";

		case METRIC_VIACOM_BUFRATIO:		return "METRIC_VIACOM_BUFRATIO";
		case METRIC_VIACOM_AVGBITRATE:	return "METRIC_VIACOM_AVGBITRATE";
		case METRIC_VIACOM_JOINTIME:		return "METRIC_VIACOM_JOINTIME";
		case METRIC_VIACOM_BUFRATIO_BAD:		return "METRIC_VIACOM_BUFRATIO_BAD";
		case METRIC_VIACOM_AVGBITRATE_BAD:	return "METRIC_VIACOM_AVGBITRATE_BAD";
		case METRIC_VIACOM_JOINTIME_BAD:		return "METRIC_VIACOM_JOINTIME_BAD";
		default: throw new RuntimeException();
		}
	}

}
