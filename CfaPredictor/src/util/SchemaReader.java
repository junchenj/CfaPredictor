package util;

import java.util.*;

public class SchemaReader {

	public static final String FormatSep = "\t";
	private String mSchemaFile = "";
	private Map<String, Integer> mTagToIndex;
	
	public SchemaReader(String schemaFile) {
		mTagToIndex = Util.readSchema(schemaFile, FormatSep);
		mSchemaFile = schemaFile;
	}

	public Map<String, Integer> tagToIndex() {
		return mTagToIndex;
	}

}
