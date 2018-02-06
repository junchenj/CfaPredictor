package util;

import java.io.*;

public class FastWriter {

	public String filename = "";
	public BufferedWriter out;
	
	public FastWriter(String target){
		filename = target;
//		if (!new File(filename).exists()) {
            File file = new File(filename);
            file.getParentFile().mkdirs();
            Util.writeStringToFile(filename, "");
//        }
		try{
			FileWriter fstream = new FileWriter(filename, true);
            out = new BufferedWriter(fstream);
		} catch (Exception e){e.printStackTrace();}
	}
	
	public FastWriter(String target, boolean rewrite){
		filename = target;
        File file = new File(filename);
        file.getParentFile().mkdirs();
        if (rewrite) Util.writeStringToFile(filename, "");
		try{
			FileWriter fstream = new FileWriter(filename, true);
            out = new BufferedWriter(fstream);
		} catch (Exception e){e.printStackTrace();}
	}
	
	
	public void appendStr(String str){
		try {out.write(str);} catch (IOException e) {
			e.printStackTrace(); throw new IllegalStateException();}
	}
	
	public void appendLine(String line){
		appendStr(line+"\r\n");
	}

	public void close() {
		try {out.close();} catch (IOException e) {
			e.printStackTrace(); throw new IllegalStateException();}
	}
	
	public void tempcheck() {
		try {
			out.close();
			FileWriter fstream = new FileWriter(filename, true);
			BufferedWriter newout = new BufferedWriter(fstream);
			out = newout;
			out.write("");
		} catch (Exception e){
			e.printStackTrace(); throw new IllegalStateException();}
	}
}
