package util;

import java.io.*;
import java.util.*;

public class FastReader {

	public String filename = "";
	public BufferedReader in;
	
	public FastReader(String target){
		try{
            in = new BufferedReader(
            		new InputStreamReader(
            				new DataInputStream(
            						new FileInputStream(target))));
		} catch (Exception e){e.printStackTrace();}
	}
	
	public String nextLine(){
		try{
			return in.readLine();
		} catch (IOException e){e.printStackTrace();}
		return null;
	}
	
	public Set<String> nextLineSet(int n){
		Set<String> result = new HashSet<String>();
		try{
			String strLine;
			while ((strLine = in.readLine()) != null){
				result.add(strLine);
				if (result.size() >= n) break;
			}
		} catch (IOException e){e.printStackTrace();}
		return result;
	}
	
	public List<String> nextLineList(int n){
		List<String> result = new ArrayList<String>();
		try{
			String strLine;
			while ((strLine = in.readLine()) != null){
				result.add(strLine);
				if (result.size() >= n) break;
			}
		} catch (IOException e){e.printStackTrace();}
		return result;
	}
	
	public void close() {
		try {in.close();} catch (IOException e) {
			e.printStackTrace(); throw new IllegalStateException();}
	}
	
}
