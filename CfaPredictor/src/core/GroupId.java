package core;

import java.io.Serializable;
import java.util.*;

public class GroupId implements Serializable {
	
	private static final long serialVersionUID = 8959494864925822846L;

	private GroupType mType = null;
	private String mSpatial = null;
	// multiple features
	private List<String> mSpatialList = null;
	public boolean isMultipleTypes(){return mSpatialList != null;}
	
	public static GroupId of(GroupType type, String spatial){
		GroupId result = new GroupId(); 
		result.mSpatial = spatial; result.mType = type;
		return result;}
	public String spatial(){return mSpatial;}
	public GroupType type(){return mType;}
	
	public static GroupId of(GroupType type, List<String> spatialList){
		GroupId result1 = new GroupId();
		result1.mSpatialList = spatialList; result1.mType = type;
		return result1;}
	public List<String> spatialList(){return mSpatialList;}
	
	public String toString(){
		if (!this.isMultipleTypes())
			return mSpatial;
		else return mSpatialList.toString();}
	public boolean equals(Object o){
		if (o instanceof GroupId){
			GroupId other = (GroupId)o;
			if (!this.isMultipleTypes())
				return other.mSpatial.equals(this.mSpatial)
						&& other.mType.equals(this.mType);
			else
				return other.mSpatialList.toString().equals(this.mSpatialList.toString())
						&& other.mType.equals(this.mType);
		} return false;
	}
	public int hashCode(){ 
		if (!this.isMultipleTypes()) return (mSpatial).hashCode();
		else return (mSpatialList).hashCode();
	}

	public Map<Integer, String> getFeatureToValueMap() {
		List<Integer> featureList = mType.featureTypes();
		Map<Integer, String> result = new HashMap<Integer, String>();
		for (int i = 0; i < featureList.size(); i++)
			result.put(featureList.get(i), mSpatialList.get(i));
		return result;
	}

}
