package core;

import java.io.Serializable;
import java.util.List;

public class GroupType implements Serializable {

	private static final long serialVersionUID = -7584678970644493108L;
	
	private int mFeatueType = -1;
	// multiple features
	private List<Integer> mFeatureTypeList = null;
	public int featureType(){return mFeatueType;}
	public List<Integer> featureTypes(){return mFeatureTypeList;}
	public boolean isMultipleTypes(){return mFeatureTypeList != null;}

	private int mEpochLenSeconds;
	public int epochLenSeconds(){return mEpochLenSeconds;}
	
	public static GroupType of(List<Integer> featureTypeList, int epochLenSeconds){
		GroupType result = new GroupType();
		result.mFeatureTypeList = featureTypeList;
		result.mEpochLenSeconds = epochLenSeconds;
		return result;
	}
	public static GroupType of(int featureType, int epochLenSeconds){
		GroupType result = new GroupType();
		result.mFeatueType = featureType;
		result.mEpochLenSeconds = epochLenSeconds;
		return result;
	}
	
	public String toString(){
		if (!this.isMultipleTypes())
			return "Type-"+mFeatueType+"%Epoch-"+mEpochLenSeconds;
		else return "Types-"+mFeatureTypeList+"%Epoch-"+mEpochLenSeconds;}
	public boolean equals(Object o){
		if (o instanceof GroupType){
			GroupType other = (GroupType)o;
			if (!this.isMultipleTypes())
				return (other.mEpochLenSeconds == this.mEpochLenSeconds && 
					other.mFeatueType == this.mFeatueType);
			else 
				return (other.mEpochLenSeconds == this.mEpochLenSeconds && 
				other.mFeatureTypeList.toString().equals(this.mFeatureTypeList.toString()));
		} 
		return false;}
	public int hashCode(){if (!this.isMultipleTypes())
		return ("Type-"+mFeatueType+"%Epoch-"+mEpochLenSeconds).hashCode();
	else return ("Types-"+mFeatureTypeList+"%Epoch-"+mEpochLenSeconds).hashCode();}
	
}
