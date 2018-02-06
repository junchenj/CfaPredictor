package util;


import java.io.*;
import java.text.DecimalFormat;
import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: junchenjiang
 * Date: 5/10/15
 * Time: 9:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util  implements Serializable{
    public static double getAve(List<Double> list){
        if (list.size() == 0){
//			System.out.println("util.Analysis.getAve: empty list");
            return -1;
        }
        double sum = 0.0;
        for (double v : list){
            sum += v;
        }
        return (double)sum/(double)list.size();
    }
    public static double getHarmonicAve(List<Double> list){
        if (list.size() == 0){
//			System.out.println("util.Analysis.getAve: empty list");
            return -1;
        }
        double sum = 0.0;
        for (double v : list){
            sum += 1.0/Math.max(v, 0.0001);
        }
        double mean = (double)sum/(double)list.size();
        return 1.0/mean;
    }

    public static double getWeightedAve(List<Double> list, List<Double> weightList){
        if (list.size() == 0){
//			System.out.println("util.Analysis.getAve: empty list");
            return -1;
        }
        double sum = 0.0;
        double denom = 0.0;
        for (int i = 0; i < list.size(); i++){
            sum += list.get(i)*weightList.get(i);
            denom += weightList.get(i);
        }
        return (double)sum/denom;
    }

    public static float getAveFloat(List<Float> list){
        if (list.size() == 0){
//			System.out.println("util.Analysis.getAve: empty list");
            return -1;
        }
        float sum = (float) 0.0;
        for (float v : list){
            sum += v;
        }
        return (float)sum/(float)list.size();
    }

    public static double getMedian(List<Double> list) {
        if (list.size() == 0){
            System.out.println("util.Analysis.getMean: empty list");
            return -1;
        }
        return getPerc(list, 0.5);
    }

    public static double getSqrtMean(List<Double> list) {
        if (list.size() == 0){
            System.out.println("util.Analysis.getSqrtMean: empty list");
            return -1;
        }
        double sqSum = getSqSum(list);
        return Math.sqrt(sqSum/(double)list.size());
    }

    public static double getStdDev(List<Double> list) {
        return Math.pow(getVar(list), 0.5);
    }
    
    public static double getVar(List<Double> list){
    	if (list.size() == 0){
            System.out.println("util.Analysis.getStdDev: empty list");
            return -1;
        }
        double ave = getAve(list);
        double sqrSum = 0;
        for (double v : list){
            sqrSum += (v-ave)*(v-ave);
        }
        return sqrSum/(double)list.size();
    }

    public static double getStdErrOfMean(List<Double> list) {
        if (list.size() == 0){
            System.out.println("util.Analysis.getStdErrOfMean: empty list");
            return -1;
        }
        double ave = getAve(list);
        double sqrSum = 0;
        for (double v : list){
            sqrSum += (v-ave)*(v-ave);
        }
        double stdDev = Math.pow(sqrSum/(double)list.size(), 0.5);
        return stdDev/Math.pow((double)list.size(), 0.5);
    }

    public static double getRelativeStdDev(List<Double> list) {
        if (list.size() == 0){
            System.out.println("util.Analysis.getRelativeStdDev: empty list");
            return -1;
        }
        return getStdDev(list)/getAve(list);
    }

    public static double getSum(List<Double> list) {
        if (list.size() == 0){
            System.out.println("util.Analysis.getSum: empty list");
            return -1;
        }
        double sum = 0.0;
        for (double v : list){
            sum += v;
        }
        return sum;
    }

    public static double getSqSum(List<Double> list) {
        if (list.size() == 0){
            System.out.println("util.Analysis.getSqSum: empty list");
            return -1;
        }
        double sum = 0.0;
        for (double v : list){
            sum += v*v;
        }
        return sum;
    }

    public static double getAveInt(List<Integer> list){
        if (list.size() == 0){
            System.out.println("util.Analysis.getIntAve: empty list");
            return -1;
        }
        double sum = 0.0;
        for (int v : list){
            sum += v;
        }
        return (double)sum/(double)list.size();
    }

    public static long getAveLong(List<Long> list){
        if (list.size() == 0){
            return 0;
        }
        long sum = 0;
        for (long v : list){
            sum += v;
        }
        return (long)sum/(long)list.size();
    }

    public static void increamentKey(
            Map<String, Integer> map, String key, int n){
        if (!map.containsKey(key)){
            map.put(key, 0);
            map.put(key, map.get(key)+n);
        } else {
            map.put(key, map.get(key)+n);
        }
    }

    public static Map<String, Double> increamentKeyDouble(
            Map<String, Double> map, String key, double n){
        if (!map.containsKey(key)){
            map.put(key, 0.0);
        }
        map.put(key, map.get(key)+n);
        return map;
    }

    public static void insertKey(
            Map<String, Set<String>> map, String key, String value){
        if (!map.containsKey(key)){
            map.put(key, new HashSet<String>());
        }
        Set<String> values = map.get(key);
        values.add(value);
        map.put(key, values);
    }
    public static void insertKeyValueDouble(
            Map<String, List<Double>> map, String key, double value){
        if (!map.containsKey(key)){
            map.put(key, new ArrayList<Double>());
        }
        List<Double> values = map.get(key);
        values.add(value);
        map.put(key, values);
    }
    public static void insertKeyValueObjectDouble(
            Map<Object, List<Double>> map, Object key, double value){
        if (!map.containsKey(key)){
            map.put(key, new ArrayList<Double>());
        }
        List<Double> values = map.get(key);
        values.add(value);
        map.put(key, values);
    }
    public static void insertKeyAllValuesDouble(
            Map<String, List<Double>> map, String key, List<Double> values){
        if (!map.containsKey(key)){
            map.put(key, new ArrayList<Double>());
        }
        List<Double> list = map.get(key);
        list.addAll(values);
        map.put(key, list);
    }

    public static Set<String> getIntersect(Set<String> set1, Set<String> set2){
        Set<String> result = new HashSet<String>();
        for (String v : set1){
            if (set2.contains(v)){
                result.add(v);
            }
        }
        return result;
    }
    
    public static Set<String> getUnionSet(Set<String> set1, Set<String> set2) {
    	Set<String> result = new HashSet<String>();
    	result.addAll(set1); result.addAll(set2);
    	return result;
	}

    public static Map<String, String> intializeMapByString(String input){
        Map<String, String> result = new HashMap<String, String>();
        String shortInput = input.substring(1, input.length()-1);
        String[] contents = shortInput.split(", ");
        for (int i = 0; i < contents.length; i++){
            String[] pair = contents[i].split("=");
            if (pair.length == 2){
                result.put(pair[0], pair[1]);
            }
        }
        return result;
    }

    public static List<String> intializeListByString(String input){
        List<String> result = new ArrayList<String>();
        String shortInput = input.substring(1, input.length()-1);
        if (shortInput.equals("")) return result;
        String[] contents = shortInput.split(", ");
        for (int i = 0; i < contents.length; i++){
            result.add(contents[i]);
        }
        return result;
    }

    public static List<String> intializeListByCompactString(String input){
        List<String> result = new ArrayList<String>();
        String shortInput = input.substring(1, input.length()-1);
        String[] contents = shortInput.split("%");
        for (int i = 0; i < contents.length; i++){
            result.add(contents[i]);
        }
        return result;
    }

    public static List<Integer> intializeIntegerListByString(String input){
        List<Integer> result = new ArrayList<Integer>();
        if (input.equals("[]"))
            return result;
        String shortInput = input.substring(1, input.length()-1);
        String[] contents = shortInput.split(", ");
        for (int i = 0; i < contents.length; i++){
            result.add(Integer.valueOf(contents[i]));
        }
        return result;
    }

    public static List<Double> intializeDoubleListByString(String input){
        List<Double> result = new ArrayList<Double>();
        if (input.equals("[]"))
            return result;
        String shortInput = input.substring(1, input.length()-1);
        String[] contents = shortInput.split(", ");
        for (int i = 0; i < contents.length; i++){
            result.add(Double.valueOf(contents[i]));
        }
        return result;
    }

    public static Set<String> intializeSetByString(String input){
        Set<String> result = new HashSet<String>();
        String shortInput = input.substring(1, input.length()-1);
        String[] contents = shortInput.split(", ");
        for (int i = 0; i < contents.length; i++){
            result.add(contents[i]);
        }
        return result;
    }

    public static int getSetSizeByString(String input){
        String shortInput = input.substring(1, input.length()-1);
        String[] contents = shortInput.split(", ");
        return contents.length;
    }

    public static void printToCdfCsv(List<Double> list, String filename){
    	try{
            if (!new File(filename).exists()) {
                File file = new File(filename);
                file.getParentFile().mkdirs();
                writeStringToFile(filename, "");
            }
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            List<Double> copy = new ArrayList<Double>();
            for (double v : list){
                copy.add(v);
            }
            Collections.sort(copy);
            for (int i = 0; i < copy.size(); i++){
                out.write(copy.get(i)+","+(double)(i+1)/(double)(copy.size())+"\r\n");
            }
            out.close();
        } catch (Exception e) {e.printStackTrace();}
    }
    
    public static void printToCdfCompactCsv(List<Double> list, int limit, String filename){
        try{
            if (!new File(filename).exists()) {
                File file = new File(filename);
                file.getParentFile().mkdirs();
                writeStringToFile(filename, "");
            }
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            List<Double> copy = new ArrayList<Double>();
            double rate = (double)limit/(double)(list.size());
            Random rand = new Random();
            for (double v : list){
                if (rand.nextDouble() < rate)
                    copy.add(v);
            }
            Collections.sort(copy);
            for (int i = 0; i < copy.size(); i++){
                out.write(copy.get(i)+","+(double)(i+1)/(double)(copy.size())+"\r\n");
            }
            out.close();
        } catch (Exception e) {e.printStackTrace();}
    }
    public static void printToCdfWithKeyCsv(Map<String, Double> map, String filename){
        try{
            if (!new File(filename).exists()) {
                File file = new File(filename);
                file.getParentFile().mkdirs();
                writeStringToFile(filename, "");
            }
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            List<String> rank = Util.orderKeyByValueAscentDouble(map); 
            for (int i = 0; i < rank.size(); i++){
            	String key = rank.get(i);
            	double fraction = (double)(i+1)/(double)(rank.size());
                out.write(map.get(key)+","+fraction+","+key+"\r\n");
            }
            out.close();
        } catch (Exception e) {e.printStackTrace();}
    }
    
    public static void printToCdfCompactCsvMultipleList(List<List<Double>> listOfList,
    		List<String> nameList, String filename){
        try{
            if (!new File(filename).exists()) {
                File file = new File(filename);
                file.getParentFile().mkdirs();
                writeStringToFile(filename, "");
            }
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            List<List<Double>> copies = new ArrayList<List<Double>>();
            for (int i = 0; i < listOfList.size(); i++){
            	List<Double> copy = new ArrayList<Double>();
            	for (double v : listOfList.get(i)) copy.add(v);
            	Collections.sort(copy);
            	copies.add(copy);
            }
//            double limit = 10000;
//            String head = "CDF";
//            for (int i = 0; i < copies.size(); i++)
//            	head = head+","+nameList.get(i);
//            head = head+"\r\n";
//            out.write(head);
//            
//            for (double j = 0; j < limit; j++){
//            	String str = "";
//            	double frac = j/limit;
//            	str = str+""+frac;
//            	for (int i = 0; i < copies.size(); i++) {
//            		List<Double> copy = copies.get(i);
//            		double size = (double)(copy.size()-1);
//            		str = str+","+copy.get((int)(frac*size));
//            	}
//            	str = str+"\r\n";
//            	out.write(str);
//            }
            double limit = 10000;
            List<Double> anchorList = new ArrayList<Double>();
            for (int i = 0; i < listOfList.size(); i++){
            	List<Double> copy = copies.get(i);
            	for (double j = 0; j < limit; j++){
            		double frac = (j+1)/limit;
            		double anchor = copy.get((int)((double)(copy.size()-1)*frac));
            		anchorList.add(anchor);
            	}
            }
            Collections.sort(anchorList);
            String head = "Value";
            for (int i = 0; i < listOfList.size(); i++)
            	head = head+","+nameList.get(i);
            out.write(head+"\r\n");
            List<Integer> currentIndexList = new ArrayList<Integer>();
            for (int i = 0; i < listOfList.size(); i++)
            	currentIndexList.add(0);
            for (int j = 0; j < anchorList.size(); j++){
            	double anchor = anchorList.get(j);
            	String str = anchor+"";
            	for (int i = 0; i < listOfList.size(); i++){
            		List<Double> copy = copies.get(i);
            		boolean found = false;
            		for (int index = currentIndexList.get(i); index < copy.size(); index++){
            			if (copy.get(index) >= anchor){ 
            				str = str+","+(double)(index+1)/(double)copy.size();
            				currentIndexList.set(i, index); found = true;
            				break;
            			}
            		}
            		if (!found) {
            			int index = copy.size()-1;
            			str = str+","+(double)(index+1)/(double)copy.size();
        				currentIndexList.set(i, index); found = true;
            		}
            	}
            	out.write(str+"\r\n");
            }
            out.close();
        } catch (Exception e) {e.printStackTrace();}
    }
    
    public static void printToCdf(List<Double> list, String filename){
        try{
            if (!new File(filename).exists()) {
                File file = new File(filename);
                file.getParentFile().mkdirs();
                writeStringToFile(filename, "");
            }
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            List<Double> copy = new ArrayList<Double>();
            for (double v : list){
                copy.add(v);
            }
            Collections.sort(copy);
            for (int i = 0; i < copy.size(); i++){
                out.write(copy.get(i)+"\t"+(double)(i+1)/(double)(copy.size())+"\r\n");
            }
            out.close();
        } catch (Exception e) {e.printStackTrace();}
    }

    public static void printToCdfCompact(List<Double> list, String filename){
        printToCdfCompact(list, 1000, filename);
    }

    public static void printToCdfCompact(List<Double> list, int limit, String filename){
        try{
            if (!new File(filename).exists()) {
                File file = new File(filename);
                file.getParentFile().mkdirs();
                writeStringToFile(filename, "");
            }
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            List<Double> copy = new ArrayList<Double>();
            double rate = (double)limit/(double)(list.size());
            Random rand = new Random();
            for (double v : list){
                if (rand.nextDouble() < rate)
                    copy.add(v);
            }
            Collections.sort(copy);
            for (int i = 0; i < copy.size(); i++){
                out.write(copy.get(i)+"\t"+(double)(i+1)/(double)(copy.size())+"\r\n");
            }
            out.close();
        } catch (Exception e) {e.printStackTrace();}
    }

    public static void printToCdfWithInfo(List<Double> valueList, List<String> infoList, String filename){
        Set<Double> valueSet = new HashSet<Double>();
        Map<Double, List<String>> valueToInfos = new HashMap<Double, List<String>>();
        for (int i = 0; i < valueList.size(); i++){
            double value = valueList.get(i);
            String info = infoList.get(i);
            valueSet.add(value);
            List<String> infos = valueToInfos.containsKey(value) ?
                    valueToInfos.get(value) : new ArrayList<String>();
            infos.add(info);
            valueToInfos.put(value, infos);
        }
        try{
            if (!new File(filename).exists()) {
                File file = new File(filename);
                file.getParentFile().mkdirs();
                writeStringToFile(filename, "");
            }
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            List<Double> copy = new ArrayList<Double>();
            for (double v : valueSet){
                copy.add(v);
            }
            Collections.sort(copy);
            int index = 0;
            for (int i = 0; i < copy.size(); i++){
                double value = copy.get(i);
                List<String> infos = valueToInfos.get(value);
                for (int j = 0; j < infos.size(); j++){
                    String info = infos.get(j);
                    out.write(value+"\t"+(double)(index)/(double)(valueList.size())+"\t"+info+"\r\n");
                    index++;
                }
            }
            out.close();
        } catch (Exception e) {e.printStackTrace();}
    }

    public static void printToCdfInteger(List<Integer> list, String filename) throws IOException{
        if (!new File(filename).exists()) {
            File file = new File(filename);
            file.getParentFile().mkdirs();
            writeStringToFile(filename, "");
        }
        FileWriter fstream = new FileWriter(filename);
        BufferedWriter out = new BufferedWriter(fstream);
        List<Double> copy = new ArrayList<Double>();
        for (double v : list){
            copy.add(v);
        }
        Collections.sort(copy);
        for (int i = 0; i < copy.size(); i++){
            out.write(copy.get(i)+"\t"+(double)(i+1)/(double)(copy.size())+"\r\n");
        }
        out.close();
    }

    public static double getPerc(List<Double> list, double percent) {
        if (list.size() == 0){
            System.out.println("util.Analysis.getPercent: empty list");
            Thread.dumpStack();
            return -1;
        }
        List<Double> copy = new ArrayList<Double>();
        for (double v : list){
            copy.add(v);
        }
        Collections.sort(copy);
        return copy.get((int)((copy.size()-1)*percent));
    }

    public static double getMax(List<Double> list){
        if (list.size() == 0){
            System.out.println("util.Analysis.getMax: empty list");
            return -1;
        }
        List<Double> copy = new ArrayList<Double>();
        for (double v : list){
            copy.add(v);
        }
        Collections.sort(copy);
        return copy.get(copy.size()-1);
    }

    public static double getMin(List<Double> list){
        if (list.size() == 0){
            System.out.println("util.Analysis.getMin: empty list");
            return -1;
        }
        List<Double> copy = new ArrayList<Double>();
        for (double v : list){
            copy.add(v);
        }
        Collections.sort(copy);
        return copy.get(0);
    }
    
    public static int getMaxInt(List<Integer> list){
        if (list.size() == 0){
            System.out.println("util.Analysis.getMaxInt: empty list");
            return -1;
        }
        List<Integer> copy = new ArrayList<Integer>();
        for (int v : list){
            copy.add(v);
        }
        Collections.sort(copy);
        return copy.get(copy.size()-1);
    }

    public static int getMinInt(List<Integer> list){
        if (list.size() == 0){
            System.out.println("util.Analysis.getMinInt: empty list");
            return -1;
        }
        List<Integer> copy = new ArrayList<Integer>();
        for (int v : list){
            copy.add(v);
        }
        Collections.sort(copy);
        return copy.get(0);
    }

    public static double getPercentOrder(List<Double> list, double percent,
                                         boolean ascend) {
        if (list.size() == 0){
            System.out.println("util.Analysis.getPercent: empty list");
            return -1;
        }
        List<Double> copy = new ArrayList<Double>();
        for (double v : list){
            if (ascend)
                v = 1-v;
            copy.add(v);
        }
        Collections.sort(copy);
        double result = copy.get((int)((copy.size()-1)*percent));
        if (ascend) return 1-result;
        else return result;
    }

    public static long getPercentLong(List<Long> list, double percent) {
        if (list.size() == 0){
            System.out.println("util.Analysis.getPercentLong: empty list");
            return -1;
        }
        List<Long> copy = new ArrayList<Long>();
        for (long v : list){
            copy.add(v);
        }
        Collections.sort(copy);
        return copy.get((int)((copy.size()-1)*percent));
    }

    public static int getValueSum(
            Map<String, Integer> map) {
        int sum = 0;
        for (String key : map.keySet())
            sum += map.get(key);
        return sum;
    }

    public static void writeStringToFile(
            String filename, String content) {
        try{
            if (!new File(filename).exists()) {
                File file = new File(filename);
                file.getParentFile().mkdirs();
            }
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(content);
            out.close();
        } catch (Exception e){e.printStackTrace();}
    }

    public static void appendStringToFile(
            String filename, String content) {
        try{
            if (!new File(filename).exists()) {
                File file = new File(filename);
                file.getParentFile().mkdirs();
                writeStringToFile(filename, "");
            }
            FileWriter fstream = new FileWriter(filename, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(content);
            out.close();
        } catch (Exception e){e.printStackTrace();}
    }

    public static void appendNewlineToFile(
            String filename, String content) {
        try{
            if (!new File(filename).exists()) {
                File file = new File(filename);
                file.getParentFile().mkdirs();
                writeStringToFile(filename, "");
            }
            FileWriter fstream = new FileWriter(filename, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(content+"\r\n");
            out.close();
        } catch (Exception e){e.printStackTrace();}
    }


    public static void appendBigSetToFile(
            String filename, Set<String> content) throws IOException{
    	try{
        if (!new File(filename).exists()) {
            File file = new File(filename);
            file.getParentFile().mkdirs();
            writeStringToFile(filename, "");
        }
        FileWriter fstream = new FileWriter(filename, true);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(content.toString());
        out.close();
    	} catch (Exception e){e.printStackTrace();}
    }

    public static void appendBigListOfLinesToFile(
            String filename, List<String> lines){
    	try{
        if (!new File(filename).exists()) {
            File file = new File(filename);
            file.getParentFile().mkdirs();
            writeStringToFile(filename, "");
        }
        FileWriter fstream = new FileWriter(filename, true);
        BufferedWriter out = new BufferedWriter(fstream);
        for (String line : lines)
        	out.write(line+"\n");
        out.close();
    	} catch (Exception e){e.printStackTrace();}
    }

    public static double problemGoodRateToProblemRate(double problemGoodRate){
        return problemGoodRate/(1+problemGoodRate);
    }

    public static boolean isSubsetOfContents(String[] contents1, String[] contents2){
        List<String> list2 = new ArrayList<String>();
        for (String att : contents2)
            list2.add(att);
        for (String att : contents1){
            if (!list2.contains(att))
                return false;
        }
        if (contents2.length == contents1.length)
            return false;
        return true;
    }

    public static Set<String> getOverlapSet(Set<String> set1,
                                            Set<String> set2) {
        Set<String> result = new HashSet<String>();
        for (String v1 : set1){
            if (set2.contains(v1))
            	result.add(v1);
        }
        return result;
    }
    
    public static Set<Integer> getOverlapSetInt(Set<Integer> set1,
            Set<Integer> set2) {
		Set<Integer> result = new HashSet<Integer>();
		for (Integer v1 : set1){
		if (set2.contains(v1))
			result.add(v1);
		}
		return result;
	}

    public static Set<String> getAllSet(Set<String> set1,
                                        Set<String> set2) {
        Set<String> result = new HashSet<String>();
        for (String v1 : set1){
            result.add(v1);
        }
        for (String v2 : set2){
            result.add(v2);
        }
        return result;
    }

    public static Set<Integer> getOverlapSetInteger(Set<Integer> set1,
                                                    Set<Integer> set2) {
        Set<Integer> result = new HashSet<Integer>();
        for (int v1 : set1){
            for (int v2 : set2){
                if (v1 == v2)
                    result.add(v1);
            }
        }
        return result;
    }

    public static Set<Integer> getAllSetInteger(Set<Integer> set1,
                                                Set<Integer> set2) {
        Set<Integer> result = new HashSet<Integer>();
        for (int v1 : set1){
            result.add(v1);
        }
        for (int v2 : set2){
            result.add(v2);
        }
        return result;
    }
    public static Set<String> getSetSubtract(Set<String> set1, Set<String> set2) {
    	Set<String> result = new HashSet<String>();
    	for (String v : set1) if (!set2.contains(v)) result.add(v);
    	return result;
	}

    public static List<String> orderKeyByValueAscent(Map<String, Integer> map){
        List<String> result = new ArrayList<String>();
        Set<String> set = new HashSet<String>();
        List<Integer> valueList = new ArrayList<Integer>();
        Map<Integer, Set<String>> reversedMap = new HashMap<Integer, Set<String>>();
        for (String key : map.keySet()){
            int value = map.get(key);
            if (!reversedMap.containsKey(value)){
                reversedMap.put(value, new HashSet<String>());
                valueList.add(value);
            }
            Set<String> reversedValueSet = reversedMap.get(value);
            reversedValueSet.add(key);
            reversedMap.put(value, reversedValueSet);
        }
        Collections.sort(valueList);
        for (int i = 0; i < valueList.size(); i++){
            Set<String> reversedValueSet = reversedMap.get(valueList.get(i));
            for (String reversedValue : reversedValueSet){
                if (!set.contains(reversedValue)){
                    result.add(reversedValue);
                    set.add(reversedValue);
                }
            }
        }
        return result;
    }

    public static List<String> orderKeyByValueAscentLong(Map<String, Long> map){
        List<String> result = new ArrayList<String>();
        Set<String> set = new HashSet<String>();
        List<Long> valueList = new ArrayList<Long>();
        Map<Long, Set<String>> reversedMap = new HashMap<Long, Set<String>>();
        for (String key : map.keySet()){
            long value = map.get(key);
            if (!reversedMap.containsKey(value)){
                reversedMap.put(value, new HashSet<String>());
                valueList.add(value);
            }
            Set<String> reversedValueSet = reversedMap.get(value);
            reversedValueSet.add(key);
            reversedMap.put(value, reversedValueSet);
        }
        Collections.sort(valueList);
        for (int i = 0; i < valueList.size(); i++){
            Set<String> reversedValueSet = reversedMap.get(valueList.get(i));
            for (String reversedValue : reversedValueSet){
                if (!set.contains(reversedValue)){
                    result.add(reversedValue);
                    set.add(reversedValue);
                }
            }
        }
        return result;
    }

//	public static List<Node> orderKeyByValueAscentNode(Map<Node, Double> map){
//		List<Node> result = new ArrayList<Node>();
//		List<Double> valueList = new ArrayList<Double>();
//		Map<Double, Set<Node>> reversedMap = new HashMap<Double, Set<Node>>();
//		for (Node key : map.keySet()){
//			double value = map.get(key);
//			if (!reversedMap.containsKey(value)){
//				reversedMap.put(value, new HashSet<Node>());
//				valueList.add(value);
//			}
//			Set<Node> reversedValueSet = reversedMap.get(value);
//			reversedValueSet.add(key);
//			reversedMap.put(value, reversedValueSet);
//		}
//		Collections.sort(valueList);
//		for (int i = 0; i < valueList.size(); i++){
//			Set<Node> reversedValueSet = reversedMap.get(valueList.get(i));
//			for (Node reversedValue : reversedValueSet){
//				if (!result.contains(reversedValue))
//					result.add(reversedValue);
//			}
//		}
//		return result;
//	}

    public static List<String> orderKeyByValueAscentDouble(Map<String, Double> map){
        List<String> result = new ArrayList<String>();
        Set<String> set = new HashSet<String>();
        List<Double> valueList = new ArrayList<Double>();
        Map<Double, Set<String>> reversedMap = new HashMap<Double, Set<String>>();
        for (String key : map.keySet()){
            double value = map.get(key);
            if (!reversedMap.containsKey(value)){
                reversedMap.put(value, new HashSet<String>());
                valueList.add(value);
            }
            Set<String> reversedValueSet = reversedMap.get(value);
            reversedValueSet.add(key);
            reversedMap.put(value, reversedValueSet);
        }
        Collections.sort(valueList);
        for (int i = 0; i < valueList.size(); i++){
            Set<String> reversedValueSet = reversedMap.get(valueList.get(i));
            for (String reversedValue : reversedValueSet){
                if (!set.contains(reversedValue)){
                    result.add(reversedValue);
                    set.add(reversedValue);
                }
            }
        }
        return result;
    }
    
    public static List<Object> orderKeyByValueAscentObject(Map<Object, Double> map){
        List<Object> result = new ArrayList<Object>();
        List<Double> valueList = new ArrayList<Double>();
        Map<Double, Set<Object>> reversedMap = new HashMap<Double, Set<Object>>();
        for (Object key : map.keySet()){
            double value = map.get(key);
            if (!reversedMap.containsKey(value)){
                reversedMap.put(value, new HashSet<Object>());
                valueList.add(value);
            }
            Set<Object> reversedValueSet = reversedMap.get(value);
            reversedValueSet.add(key);
            reversedMap.put(value, reversedValueSet);
        }
        Collections.sort(valueList);
        for (int i = 0; i < valueList.size(); i++){
            Set<Object> reversedValueSet = reversedMap.get(valueList.get(i));
            for (Object reversedValue : reversedValueSet){
//                if (!result.contains(reversedValue))
                    result.add(reversedValue);
            }
        }
        return result;
    }

//	public static List<Instance> orderKeyByValueAscentInstanceDouble(Map<Instance, Double> map){
//		List<Instance> result = new ArrayList<Instance>();
//		List<Double> valueList = new ArrayList<Double>();
//		Map<Double, Set<Instance>> reversedMap = new HashMap<Double, Set<Instance>>();
//		for (Instance key : map.keySet()){
//			double value = map.get(key);
//			if (!reversedMap.containsKey(value)){
//				reversedMap.put(value, new HashSet<Instance>());
//				valueList.add(value);
//			}
//			Set<Instance> reversedValueSet = reversedMap.get(value);
//			reversedValueSet.add(key);
//			reversedMap.put(value, reversedValueSet);
//		}
//		Collections.sort(valueList);
//		for (int i = 0; i < valueList.size(); i++){
//			Set<Instance> reversedValueSet = reversedMap.get(valueList.get(i));
//			for (Instance reversedValue : reversedValueSet){
//				if (!result.contains(reversedValue))
//					result.add(reversedValue);
//			}
//		}
//		return result;
//	}

    public static List<Integer> orderKeyByValueAscentIntegerDouble(
            Map<Integer, Double> map){
        List<Integer> result = new ArrayList<Integer>();
        List<Double> valueList = new ArrayList<Double>();
        Map<Double, Set<Integer>> reversedMap = new HashMap<Double, Set<Integer>>();
        for (int key : map.keySet()){
            double value = map.get(key);
            if (!reversedMap.containsKey(value)){
                reversedMap.put(value, new HashSet<Integer>());
                valueList.add(value);
            }
            Set<Integer> reversedValueSet = reversedMap.get(value);
            reversedValueSet.add(key);
            reversedMap.put(value, reversedValueSet);
        }
        Collections.sort(valueList);
        for (int i = 0; i < valueList.size(); i++){
            Set<Integer> reversedValueSet = reversedMap.get(valueList.get(i));
            for (int reversedValue : reversedValueSet){
                if (!result.contains(reversedValue))
                    result.add(reversedValue);
            }
        }
        return result;
    }

    public static double calcJaccardForList(List<String> list1, List<String> list2){
        Set<String> set1 = new HashSet<String>();
        Set<String> set2 = new HashSet<String>();
        for (String str : list1)
            set1.add(str);
        for (String str : list2)
            set2.add(str);
        Set<String> overlap = getOverlapSet(set1, set2);
        Set<String> allSet = getAllSet(set1, set2);
        return (double)overlap.size()/(double)allSet.size();
    }

    public static double calcJaccardForListInteger(List<Integer> list1, List<Integer> list2){
        Set<Integer> set1 = new HashSet<Integer>();
        Set<Integer> set2 = new HashSet<Integer>();
        for (int str : list1)
            set1.add(str);
        for (int str : list2)
            set2.add(str);
        Set<Integer> overlap = getOverlapSetInteger(set1, set2);
        Set<Integer> allSet = getAllSetInteger(set1, set2);
        return (double)overlap.size()/(double)allSet.size();
    }

    public static double calcJaccard(Set<String> set1, Set<String> set2){
        Set<String> overlap = getOverlapSet(set1, set2);
        Set<String> allSet = getAllSet(set1, set2);
        return (double)overlap.size()/(double)allSet.size();
    }

    public static Map<String, Integer> listToRankMap(List<String> rank){
        Map<String, Integer> result = new HashMap<String, Integer>();
        for (int i = 0; i < rank.size(); i++){
            result.put(rank.get(i), i);
        }
        return result;
    }

    public static double calcKendall(List<String> rank1, List<String> rank2){
        Map<String, Integer> keyRankMap1 = listToRankMap(rank1);
        Map<String, Integer> keyRankMap2 = listToRankMap(rank2);
        Set<String> pair1 = new HashSet<String>();
        for (String key1 : keyRankMap1.keySet()){
            for (String key2 : keyRankMap1.keySet()){
                if (keyRankMap1.get(key1) < keyRankMap1.get(key2))
                    pair1.add(key1+"<"+key2);
                else if (keyRankMap1.get(key1) > keyRankMap1.get(key2))
                    pair1.add(key1+">"+key2);
            }
        }
        Set<String> pair2 = new HashSet<String>();
        for (String key1 : keyRankMap2.keySet()){
            for (String key2 : keyRankMap2.keySet()){
                if (keyRankMap2.get(key1) < keyRankMap2.get(key2))
                    pair2.add(key1+"<"+key2);
                else if (keyRankMap2.get(key1) > keyRankMap2.get(key2))
                    pair2.add(key1+">"+key2);
            }
        }
        Set<String> overlap = getOverlapSet(pair1, pair2);
        int N = keyRankMap2.size();
        return 1-
                2*(double)(pair1.size()+pair2.size()-2*overlap.size())/
                        (double)(N*(N-1));
    }
    
    public static double calcKendall2(List<Double> xList, List<Double> yList){
    	double concordantCount = 0; double discordantCount = 0;
    	for (int i = 0; i < xList.size(); i++){
    		for (int j = i+1; j < yList.size(); j++){
    			if ((xList.get(i)-xList.get(j))*(yList.get(i)-yList.get(j)) >= 0)
    				concordantCount++;
    			else discordantCount++;
    		}
    	}
    	double n = xList.size();
    	return (concordantCount-discordantCount)/(n*(n-1)/2.0);
    }

    public static double getEntropy(double problemRate, double globalProblemRate){
        double rProblem = problemRate * (1-globalProblemRate);
        double rGood = (1-problemRate) * globalProblemRate;
        rProblem = rProblem/(rProblem+rGood+Double.MIN_VALUE);
        rGood = rGood/(rProblem+rGood+Double.MIN_VALUE);
        double eProblem = (rProblem == 0 ?
                0 : -rProblem*Math.log(rProblem));
        double eGood = (rGood == 0 ?
                0 : -rGood*Math.log(rGood));
        return eProblem + eGood;
    }

    public static double getMutualInformation(double rate1, double rate2,
                                              double globalProblemRate){
        double entropy1 = getEntropy(rate1, globalProblemRate);
        double entropy2 = getEntropy(rate2, globalProblemRate);
        return entropy1-entropy2;
    }

    public static void addHeader(String header, String filename) {
		List<String> lines = readFileToLines(filename);
		try{
            if (!new File(filename).exists()) {
                File file = new File(filename);
                file.getParentFile().mkdirs();
            }
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(header+"\r\n");
            for (int i = 0; i < lines.size(); i++)
            	out.write(lines.get(i)+"\r\n");
            out.close();
        } catch (Exception e){e.printStackTrace();}
	}
    
    public static List<String> readFileToLines(String filename) {
        List<String> lines = new ArrayList<String>();
        try{
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null){
                lines.add(strLine);
            }
            br.close();
            in.close();
            fstream.close();
        } catch (IOException e){e.printStackTrace();}
        return lines;
    }

    public static List<String> readFileToLinesInRange(
            String filename, int startIndex, int endIndex) {
        List<String> lines = new ArrayList<String>();
        try{
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int count = 0;
            while ((strLine = br.readLine()) != null){
                if (count >= startIndex && count < endIndex)
                    lines.add(strLine);
                if (count >= endIndex) break;
                count++;
            }
            br.close();
            in.close();
            fstream.close();
        } catch (IOException e){e.printStackTrace();}
        return lines;
    }
    
    public static int readFileToNumberOfLines(String filename){
    	int count = 0;
        try{
//            FileInputStream fstream = new FileInputStream(filename);
//            DataInputStream in = new DataInputStream(fstream);
//            BufferedReader br = new BufferedReader(new InputStreamReader(in));
//            String strLine;
//            while ((strLine = br.readLine()) != null) count++;
//            br.close();
//            in.close();
//            fstream.close();
        	
        	LineNumberReader lnr = new LineNumberReader(
        			new FileReader(new File(filename)));
        	lnr.skip(Long.MAX_VALUE);
        	count = lnr.getLineNumber() + 1;
        } catch (IOException e){e.printStackTrace();}
        return count;
    }

    public static List<String> readFileToLinesInSet(
            String filename, Set<Integer> indexSet) {
        List<String> lines = new ArrayList<String>();
        try{
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int count = 0;
            while ((strLine = br.readLine()) != null){
                if (indexSet.contains(count)) lines.add(strLine);
                count++;
            }
            br.close();
            in.close();
            fstream.close();
        } catch (IOException e){e.printStackTrace();}
        return lines;
    }

	public static List<String> getListOfFilesFromFolder(String folderPath) {
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < listOfFiles.length; i++)
			result.add(listOfFiles[i].getName());
		return result;
	}

    public static void delete(File file){
        if(file.isDirectory()){
            //directory is empty, then delete it
            if(file.list().length==0){
                file.delete();
//                System.out.println("Directory is deleted : "
//                        + file.getAbsolutePath());
            }else{
                //list all the directory contents
                String files[] = file.list();
                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);
                    //recursive delete
                    delete(fileDelete);
                }
                //check the directory again, if empty then delete it
                if(file.list().length==0){
                    file.delete();
//                    System.out.println("Directory is deleted : "
//                            + file.getAbsolutePath());
                }
            }
        }else{
            //if file, then delete it
            file.delete();
//            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }

    public static void copyFolder(File src, File dest){
        if(src.isDirectory()){

            //if directory not exists, create it
            if(!dest.exists()){
                dest.mkdir();
//                System.out.println("Directory copied from "
//                        + src + "  to " + dest);
            }

            //list all the directory contents
            String files[] = src.list();

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile,destFile);
            }

        }else{
            //if file, then copy it
            //Use bytes stream to support all file types
            InputStream in;
            try {
                in = new FileInputStream(src);
                OutputStream out = new FileOutputStream(dest);

                byte[] buffer = new byte[1024];

                int length;
                //copy the file content in bytes
                while ((length = in.read(buffer)) > 0){
                    out.write(buffer, 0, length);
                }

                in.close();
                out.close();
//                System.out.println("File copied from " + src + " to " + dest);
            } catch (Exception e) {e.printStackTrace();}
        }
    }
    
    static public void createFolder(String folder){
    	File f = new File(folder);
    	if(!f.exists()) f.mkdir();
    }
    
    static public void copyFile(String src, String dst){
    	File fs = new File(src);
    	File fd = new File(dst);
    	InputStream in;
        try {
            in = new FileInputStream(fs);
            OutputStream out = new FileOutputStream(fd);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = in.read(buffer)) > 0){
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
//            System.out.println("File copied from " + src + " to " + dest);
        } catch (Exception e) {e.printStackTrace();}
    }

//	public static List<Record> SortRecordAscent(Map<Record, Double> keyScoreMap){
//        Map<Double, List<Record>> scoreRecordListMap = new HashMap<Double, List<Record>>();
//        List<Double> scoreList = new ArrayList<Double>();
//        List<Record> sortedRecordList = new ArrayList<Record>();
//        for (Record key : keyScoreMap.keySet()){
//            double score = keyScoreMap.get(key);
//            if (!scoreRecordListMap.containsKey(score))
//                scoreRecordListMap.put(score, new ArrayList<Record>());
//            List<Record> recordList = scoreRecordListMap.get(score);
//            recordList.add(key);
//            scoreRecordListMap.put(score, recordList);
//            if (!scoreList.contains(score))
//                scoreList.add(score);
//        }
//        Collections.sort(scoreList);
//        for (int i = 0; i < scoreList.size(); i++){
//            double score = scoreList.get(i);
//            for (Record key : scoreRecordListMap.get(score)){
//                sortedRecordList.add(key);
//            }
//        }
//        return sortedRecordList;
//    }

    public static String keyRankSummary(Map<String, Double> keyScoreMap, int printTopK, boolean ascent){
        double total = 0.0;
        for (String key : keyScoreMap.keySet()){
            total += keyScoreMap.get(key);
        }
        List<String> keyListAscent = orderKeyByValueAscentDouble(keyScoreMap);
        List<String> keyListTop = new ArrayList<String>();
        for (int i = 0; i < Math.min(keyListAscent.size(), printTopK); i++){
            keyListTop.add(ascent ? keyListAscent.get(i) :
                    keyListAscent.get(keyListAscent.size()-1-i));
        }
        String result = "";
        DecimalFormat df = new DecimalFormat("#.####");
        result += "Total items="+keyScoreMap.size()+"\tTotalScore="+total+"\r\n";
        for (String key : keyListTop){
            double score = keyScoreMap.get(key);
            result += "score="+score+"\t("+df.format(100*score/total)+"%)\tkey="+key+"\r\n";
        }
        return result;
    }
    
    public static String keyRankSummaryObject(Map<Object, Double> keyScoreMap, int printTopK, boolean ascent){
        double total = 0.0;
        for (Object key : keyScoreMap.keySet()){
            total += keyScoreMap.get(key);
        }
        List<Object> keyListAscent = orderKeyByValueAscentObject(keyScoreMap);
        List<Object> keyListTop = new ArrayList<Object>();
        for (int i = 0; i < Math.min(keyListAscent.size(), printTopK); i++){
            keyListTop.add(ascent ? keyListAscent.get(i) :
                    keyListAscent.get(keyListAscent.size()-1-i));
        }
        String result = "";
        DecimalFormat df = new DecimalFormat("#.####");
        result += "Total items="+keyScoreMap.size()+"\tTotalScore="+total+"\r\n";
        for (Object key : keyListTop){
            double score = keyScoreMap.get(key);
            result += "score="+score+"\t("+df.format(100*score/total)+"%)\tkey="+key+"\r\n";
        }
        return result;
    }

    public static String keyRankSummarySimple(Map<String, Double> keyScoreMap, int printTopK, boolean ascent){
        double total = 0.0;
        for (String key : keyScoreMap.keySet()){
            total += keyScoreMap.get(key);
        }
        List<String> keyListAscent = orderKeyByValueAscentDouble(keyScoreMap);
        List<String> keyListTop = new ArrayList<String>();
        for (int i = 0; i < Math.min(keyListAscent.size(), printTopK); i++){
            keyListTop.add(ascent ? keyListAscent.get(i) :
                    keyListAscent.get(keyListAscent.size()-1-i));
        }
        String result = "";
        DecimalFormat df = new DecimalFormat("#.####");
        double sum = 0;
        for (String key : keyListTop){
            double score = keyScoreMap.get(key);
            result += key+"\t"+df.format(score/total*100.0)+"\r\n";
            sum += score/total*100.0;
        }
        result += "others\t"+df.format(100.0-sum)+"\r\n";
        return result;
    }

    public static boolean isValidValue(double v){
        if (Double.isNaN(v) || Float.isNaN((float)v) || v < 0)
            return false;
        return true;
    }

    public static double invalidConstant = Double.NaN;

    public static double getPredictionError(double actualQuality,
                                            double predictedQuality, int metricId) {
        if (!isValidValue(actualQuality) ||
                !isValidValue(actualQuality))
            return invalidConstant;
        if (metricId == 0)
            return Math.abs(predictedQuality-actualQuality);
        if (metricId == 1 || metricId == 2){
            if (actualQuality > 0)
                return Math.abs(predictedQuality-actualQuality)/actualQuality;
            else
                return invalidConstant;
        }
        return invalidConstant;
    }

    public static double getImprovement(double actualQuality,
                                        double newQuality, int metricId) {
        if (!isValidValue(actualQuality) ||
                !isValidValue(actualQuality))
            return invalidConstant;
        if (metricId == 0)
            return actualQuality-newQuality;
        if (metricId == 1){
            if (actualQuality > 0)
                return (newQuality-actualQuality)/actualQuality;
            else
                return invalidConstant;
        }
        if (metricId == 2){
            if (actualQuality > 0)
                return (actualQuality-newQuality)/actualQuality;
            else
                return invalidConstant;
        }
        return invalidConstant;
    }

    public static void printToFusionTable(
            Map<String, Map<String, Integer>> table,
            String filename) {
        String result = "";
        List<String> label2Set = new ArrayList<String>();
        for (String label1 : table.keySet()){
            for (String label2 : table.get(label1).keySet()){
                if (!label2Set.contains(label2))
                    label2Set.add(label2);
            }
        }
        for (String label2 : label2Set){
            result += "\t"+label2;
        }
        result += "\r\n";
        for (String label1 : table.keySet()){
            result += label1+"\t";
            for (String label2 : label2Set){
                if (table.get(label1).containsKey(label2))
                    result += table.get(label1).get(label2)+"\t";
                else
                    result += "0\t";
            }
            result += "\r\n";
        }
        writeStringToFile(filename, result);
    }

    public static String printMinMedianMax(List<Double> list,
                                           DecimalFormat df) {
        Collections.sort(list);
        double size = list.size();
        return ""+df.format(list.get((int)(size*0.25-1)))+
                "\t"+df.format(list.get((int)(size*0.5-1)))+
                "\t"+df.format(list.get((int)(size*0.75-1)));
    }

    static public List<Double> arrayToList(double[] array){
        List<Double> list = new ArrayList<Double>();
        for (int i = 0; i < array.length; i++) list.add(array[i]);
        return list;
    }
    static public double[] listToArray(List<Double> list){
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) array[i] = list.get(i);
        return array;
    }
    static public List<Float> arrayToListFloat(float[] array){
        List<Float> list = new ArrayList<Float>();
        for (int i = 0; i < array.length; i++) list.add(array[i]);
        return list;
    }
    static public float[] listToArrayFloat(List<Float> list){
    	float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) array[i] = list.get(i);
        return array;
    }

    static public List<String> arrayToListStr(String[] array){
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < array.length; i++) list.add(array[i]);
        return list;
    }
    static public String[] listToArrayStr(List<String> list){
    	String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) array[i] = list.get(i);
        return array;
    }

    public static double calculateCoverageOfThreshold(List<Double> list,
                                                      double threshold) {
        double sum1 = 0;
        double sum2 = 0;
        for (double value : list){
            if (value >= threshold)
                sum1 += value;
            sum2 += value;
        }
        return sum1/sum2;
    }

    public static String format(double value, String format) {
        DecimalFormat df = new DecimalFormat(format);
        String result = df.format(value);
//		for (int i = 0; i < format.length()-result.length(); i++)
//			result = result+" ";
        return result;
    }

    public static List<Double> formatDoubleList(List<Double> list, String format) {
        List<Double> newList = new ArrayList<Double>();
        for (int i = 0; i < list.size(); i++){
            newList.add(Double.valueOf(format(list.get(i), format)));
        }
        return newList;
    } 
    public static Map<String, Double> formatDoubleMap(Map<String, Double> map, String format) {
    	Map<String, Double> newMap = new HashMap<String, Double>();
    	for (String str : map.keySet()){
    		newMap.put(str, Double.valueOf(Util.format(map.get(str), format)));
    	}
        return newMap;
    }

    public static List<Double> sampleWithReplacement(List<Double> list, int size) {
        List<Double> result = new ArrayList<Double>();
        Random rand = new Random();
        for (int i = 0; i < size; i++){
            result.add(list.get(rand.nextInt(list.size())));
        }
        return result;
    }

    static public int StringToInteger(String str){
        int sum = 0;
        int charMax = 31;
        int wrapLen = 5;
        for (int i = 0; i < str.length(); i++){
            sum += str.charAt(i)*Math.pow(charMax, i%wrapLen);
        }
        return sum;
    }

    static public double getPdfDistance(double[] distribution1, double[] distribution2){
//		{
//			return getJSDivergence(distribution1, distribution2);
//		}
        {
            double[] distribution3 = new double[distribution2.length];
            double min = 0.0001;
            double sum = 0.0;
            for (int i = 0; i < distribution2.length; i++){
                if (distribution2[i] == 0) distribution3[i] = min;
                else distribution3[i] = distribution2[i];
                sum += distribution3[i];
            }
            for (int i = 0; i < distribution2.length; i++){
                distribution3[i] = distribution3[i]/sum;
            }
            return getKLDivergence(distribution1, distribution3);
        }
    }

    static public double getJSDivergence(
            double[] distribution1, double[] distribution2){
        double[] distribution3 = new double[distribution1.length];
        for (int i = 0; i < distribution1.length; i++){
            distribution3[i] = (distribution1[i]+distribution2[i])/2.0;
        }
        double sum = getKLDivergence(distribution1,distribution3)+
                getKLDivergence(distribution2,distribution3);
        return sum/2.0;
    }
    
    static public double getJSDivergPseudo(
            List<Double> actual, List<Double> predict){
    	List<Double> temp = new ArrayList<Double>();
    	double total = 0;
    	for (int i = 0; i < predict.size(); i++){
    		double value = Math.max(0.001, predict.get(i));
    		temp.add(value); total += value;
    	}
    	List<Double> newPredict = new ArrayList<Double>();
    	for (int i = 0; i < predict.size(); i++) newPredict.add(temp.get(i)/total);
    	return getJSDivergence(actual, newPredict);
    }
    
    static public double getJSDivergence(
            List<Double> distribution1, List<Double> distribution2){
    	double[] array1 = new double[distribution1.size()];
    	double[] array2 = new double[distribution2.size()];
    	for (int i = 0; i < distribution1.size(); i++){
    		array1[i] = distribution1.get(i);
    		array2[i] = distribution2.get(i);
    	}
    	return getJSDivergence(array1, array2);
    }

    static public double getKLDivergence(
            double[] distribution1, double[] distribution2){
        double sum = 0;
        for (int i = 0; i < distribution1.length; i++){
            double p = distribution1[i];
            double q = Math.max(0.0001, distribution2[i]);
            if (p > 0) sum += p*Math.log(p/q);
        }
        return sum;
    }

    public static List<Double> NormalizeList(List<Double> list){
        double sum = 0;
        List<Double> result = new ArrayList<Double>();
        for (double v : list) sum += v;
        if (sum == 0) return list;
        for (int i = 0; i < list.size(); i++) result.add(list.get(i)/sum);
        return result;
    }

    static public double getKLDivergence(
            List<Double> distribution1, List<Double> distribution2){
        double sum = 0;
        for (int i = 0; i < distribution1.size(); i++){
            double p = distribution1.get(i);
            double q = Math.max(0.0001, distribution2.get(i));
            if (p > 0) sum += p*Math.log(p/q);
        }
        return sum;
    }

    static public String getListSummary(List<Double> list){
    	String result = "\r\n";
        DecimalFormat df = new DecimalFormat("#.####");
        result += "Total items="+list.size()+"\r\n";
        result += "Mean="+df.format(getAve(list))+
        		"\tmin="+df.format(getPerc(list,0.0))+
        		"\t10%="+df.format(getPerc(list,0.1))+
        		"\t30%="+df.format(getPerc(list,0.2))+
        		"\t50%="+df.format(getPerc(list,0.3))+
        		"\t70%="+df.format(getPerc(list,0.4))+
        		"\t90%="+df.format(getPerc(list,0.5))+
        		"\tmax="+df.format(getPerc(list,1.0));
        return result;
    }
    
    public static Map<String, Integer> readSchema(String schemaFile, String separator) {
		Map<String, Integer> tagToIndex = new HashMap<String, Integer>();
		String schemaLine = Util.readFileToLinesInRange(schemaFile, 0, 1).get(0);
		String[] fields = schemaLine.split(separator);
		for (int i = 0; i < fields.length; i++){
			tagToIndex.put(fields[i], i);
		}
		return tagToIndex;
	}
    public static String getField(String[] fields, String tag,
			Map<String, Integer> tagToIndex) {
		return fields[tagToIndex.get(tag)];
	}
    
    public static Object deserialize(String fileName){
    	try{
    		FileInputStream fis = new FileInputStream(fileName);
        	BufferedInputStream bis = new BufferedInputStream(fis);
        	ObjectInputStream ois = new ObjectInputStream(bis);
        	Object obj = ois.readObject();
        	ois.close();
        	return obj;
    	} catch (Exception e) {e.printStackTrace();}
		return null;
    }
    public static void serialize(Object obj, String fileName){
    	try{
    		writeStringToFile(fileName, "");
            FileOutputStream fos = new FileOutputStream(fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.close();
    	} catch (Exception e) {e.printStackTrace();}
    }
    
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static void printToBucketizedCsv(List<Double> xList, List<Double> yList, 
    		double numBuckets, String filename) {
    	Util.writeStringToFile(filename, "");
		List<Double> bucketList = new ArrayList<Double>();
		for (int i = 0; i < numBuckets; i++)
			bucketList.add(Util.getPerc(xList, (double)i/(double)numBuckets));
		List<List<Double>> valListByBucket = new ArrayList<List<Double>>();
		for (int i = 0; i < bucketList.size(); i++){
			List<Double> list = new ArrayList<Double>();
			double low = bucketList.get(i);
			double high = i == bucketList.size()-1 ? 
					Double.MAX_VALUE : bucketList.get(i+1);
			for (int j = 0; j < yList.size(); j++){
				if (low <= xList.get(j) && xList.get(j) < high)
					list.add(yList.get(j));
			}
			valListByBucket.add(list);
		}
		for (int i = 0; i < bucketList.size(); i++){
			double low = bucketList.get(i);
			double avg = Util.getAve(valListByBucket.get(i));
			double count = valListByBucket.get(i).size();
			double stdev = Util.getStdDev(valListByBucket.get(i));
			if (count > 0)
				Util.appendNewlineToFile(filename, low+","+avg+","+count+","+stdev);
		}
    }
    public static List<List<Double>> getBuckets(
    		List<Double> xList, List<Double> yList, 
    		double numBuckets) {
		List<Double> bucketList = new ArrayList<Double>();
		for (int i = 0; i < numBuckets; i++)
			bucketList.add(Util.getPerc(xList, (double)i/(double)numBuckets));
		List<List<Double>> valListByBucket = new ArrayList<List<Double>>();
		for (int i = 0; i < bucketList.size(); i++){
			List<Double> list = new ArrayList<Double>();
			double low = bucketList.get(i);
			double high = i == bucketList.size()-1 ? 
					Double.MAX_VALUE : bucketList.get(i+1);
			for (int j = 0; j < yList.size(); j++){
				if (low <= xList.get(j) && xList.get(j) < high)
					list.add(yList.get(j));
			}
			valListByBucket.add(list);
		}
		List<Double> avgList = new ArrayList<Double>();
		List<Double> lowList = new ArrayList<Double>();
		List<Double> countList = new ArrayList<Double>();
		for (int i = 0; i < bucketList.size(); i++){
			double low = bucketList.get(i);
			double avg = Util.getAve(valListByBucket.get(i));
			double count = valListByBucket.get(i).size();
			if (count > 0) {lowList.add(low); avgList.add(avg); countList.add(count);}
		}
		return Arrays.asList(lowList, avgList, countList);
    }
    
	public static void printToBucketizedCsv(
			List<Double> xList, List<Double> yList, String filename) {
		double numBuckets = 10;
		printToBucketizedCsv(xList, yList, numBuckets, filename);
	}
	public static Object getWeightedRandomObj(
			Map<Object, Double> objToWeight) {
		Map<Object, Double> objToNormWeight = new HashMap<Object, Double>();
		double totalSum = 0;
		for (Object o : objToWeight.keySet()) totalSum += objToWeight.get(o);
		for (Object o : objToWeight.keySet())
			objToNormWeight.put(o, objToWeight.get(o)/totalSum);
			
		Random rand = new Random();
		double randomValue = rand.nextDouble();
		double sum = 0;
		for (Object o : objToNormWeight.keySet()){
			double weight = objToNormWeight.get(o);
			if (randomValue >= sum && randomValue < sum+weight)
				return o;
			sum += weight;
		}
		throw new RuntimeException();
	}
	
	public static List<List<Integer>> getAllNonEmptySubsetIndexes(int size) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		List<List<Integer>> temp = size > 2 ? getAllNonEmptySubsetIndexes(size-1) :
					Arrays.asList(Arrays.asList(0), Arrays.asList(1));
		for (List<Integer> subsetIndex : temp){
			List<Integer> newSubsetIndex0 = new ArrayList<Integer>();
			List<Integer> newSubsetIndex1 = new ArrayList<Integer>();
			for (int i = 0; i < subsetIndex.size(); i++) {
				newSubsetIndex0.add(subsetIndex.get(i));
				newSubsetIndex1.add(subsetIndex.get(i));
			}
			newSubsetIndex0.add(0); newSubsetIndex1.add(1);
			result.add(newSubsetIndex0); result.add(newSubsetIndex1);
		}
		return result;
	}
	public static double PearsonCorrelationCoefficient(
			List<Double> xs, List<Double> ys) {
		double xmean = Util.getAve(xs);
		double ymean = Util.getAve(ys);
		double sum1 = 0; double sum2 = 0; double sum3 = 0;
		for (int i = 0; i < xs.size(); i++) {
			double x = xs.get(i); double y = ys.get(i);
			sum1 += (x-xmean)*(y-ymean);
			sum2 += (x-xmean)*(x-xmean);
			sum3 += (y-ymean)*(y-ymean);
		}
		return sum1/(Math.sqrt(sum2)*Math.sqrt(sum3));
	}
	public static double SpearmanRankCorrelation(List<Double> xs, List<Double> ys) {
		Map<String, Double> keyToX = new HashMap<String, Double>();
		Map<String, Double> keyToY = new HashMap<String, Double>();
		for (int i = 0; i < xs.size(); i++) {
			double x = xs.get(i); double y = ys.get(i);
			keyToX.put(String.valueOf(i), x); keyToY.put(String.valueOf(i), y);
		}
		List<String> rankX = Util.orderKeyByValueAscentDouble(keyToX);
		List<String> rankY = Util.orderKeyByValueAscentDouble(keyToY);
		double sum = 0; double n = 0;
		for (String key : rankX) {
			double d = rankX.indexOf(key)-rankY.indexOf(key);
			sum += d*d;
			n++;
		}
		double spearmanScore = 1-(6*sum)/(n*(n*n-1));
		return spearmanScore;
	}
	public static List<String> runCommandLine(String string) {
		List<String> result = new ArrayList<String>();
		try {
			Process p = Runtime.getRuntime().exec(string);
			BufferedReader stdInput = new BufferedReader(new 
					InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new 
					InputStreamReader(p.getErrorStream()));

	        // read the output from the command
			String s;
			while ((s = stdInput.readLine()) != null) {
				result.add(s);
			}

			// read any errors from the attempted command
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
