package convivadata;

import java.util.*;
import core.*;
import util.FastReader;
import util.SchemaReader;

public class Block {
	
	private List<Record> mRecords = new ArrayList<Record>();
	
	public void insert(Record record){mRecords.add(record);}
	public void insertBlock(Block block){mRecords.addAll(block.mRecords);}
	
	public int numRecords() {return mRecords.size();}
	public Record recordAtIndex(int index) {return mRecords.get(index);}
	
	public static Block getNextBlock(FastReader r, SchemaReader s, int stride) {
		Block result = new Block();
		Map<String, Integer> tagToIndex = s.tagToIndex();
		List<String> lines = r.nextLineList(stride);
		for (int i = 0; i < lines.size(); i++){
			String line = lines.get(i);
			String[] fields = line.split(SchemaReader.FormatSep);
			RecordViacom record = RecordViacom.initialize(fields, tagToIndex);
			if (!record.isValid()) continue;
			result.insert(record);
		}
		return result;
	}
	
	public Map<GroupId, Block> partitionByGroupType(GroupType type){
		Map<GroupId, Block> idToBlock = new HashMap<GroupId, Block>();
		for (int index = 0; index < this.numRecords(); index++){
			Record record = this.recordAtIndex(index);
			GroupId id = record.getGroupId(type);
			Block block = idToBlock.containsKey(id) ? idToBlock.get(id) : 
				new Block(); block.insert(record); idToBlock.put(id, block);
		}
		return idToBlock;
	}
}
