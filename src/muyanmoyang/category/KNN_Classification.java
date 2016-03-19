package muyanmoyang.category;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

/**
 *  KNN�㷨���ࡪ���������ƶ�����ѡ�����ڡ�������
 * @author hadoop
 *
 */
public class KNN_Classification {
	
	/**
	 *  ��ÿƪ�����ı��������ƶ�����
	 * @param distanceMap
	 * @param threshold
	 * @throws IOException 
	 */
	public static void sortOfDistance(int threshold, String distanceDir,String sortDisDir) throws IOException{
		FileWriter writer = new FileWriter(new File(sortDisDir)) ;
		FileReader reader = new FileReader(new File(distanceDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		Map<String,Double> distanceMap = new HashMap<String,Double>() ;
		
		String line ;
		if((line=BR.readLine()) == null){
			writer.write("null");
		}
		while((line=BR.readLine()) != null){
			String str[] = line.split("\t") ;
			distanceMap.put(str[0],Double.parseDouble(str[1])) ;
		}
		
		List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(distanceMap.entrySet());
        //sort list based on comparator
        Collections.sort(list, new Comparator(){
             public int compare(Object o1, Object o2) 
             {
            	 return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
             }
        });
 
        //put sorted list into map again
        for (Iterator it = list.iterator(); it.hasNext();) {
        	Map.Entry entry = (Map.Entry)it.next();
        	writer.write(entry.getKey() + "\t" + entry.getValue());
        	writer.write("\n"); 
        	writer.flush();
        }
        writer.close();
	}
	
	/**
	 *  ѡ��K������,����û�н��ڵ��ı����д���
	 * @param sortDisDir
	 * @param k
	 * @throws IOException 
	 */
	public static void selectKNeighbors(int k ,String sortDisDir, String kNeighborDir) throws IOException{
		FileWriter writer = new FileWriter(new File(kNeighborDir)) ;
		FileReader reader = new FileReader(new File(sortDisDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		String line ;
		int count = 0 ;
		while((line=BR.readLine()) != null){
			count ++ ;
			if(! line.equals("null")){
				String str[] = line.split("\t") ;
				if(count <= k){
					writer.write(str[0] + "\t" + str[1] + "\n") ;
					writer.flush();
				}
			}else{
				writer.write("no neighbor");
			}
		}
	}
	
	/**
	 *  �� �������ı� ���з���
	 * @param args
	 * @throws IOException
	 */
	public static String classification(String kNeighborDir,int i) throws IOException{
		Map<Integer,Double> neighborMap = new HashMap<Integer,Double>() ;   // ѵ���ı���� �� ���ƶ�
		FileReader reader = new FileReader(new File(kNeighborDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		Map<Integer,String> messageAndLabel = getLabel() ; // ��ȡ�ı����Ӧ���
		
		String line ;
		if((line=BR.readLine()) == null){               // ����޽��ڣ�����0��
//			if(i>0 && i<289){
//				return "finance" ;
//			}
//			if(i>289 && i<580){
//				return "IT" ;
//			}
//			if(i>580 && i<902){
//				return "society" ;
//			}
//			else{
//				return "sports" ;
//			}
			return "null" ;
		}
		while((line=BR.readLine()) != null){
			String str[] = line.split("\t") ;
			neighborMap.put(Integer.parseInt(str[0]), Double.parseDouble(str[1])) ;  // ����--->  ѵ�����ı���� �� ���ƶ�ֵ
		}
		
		Iterator<Map.Entry<Integer, Double>> it = neighborMap.entrySet().iterator() ;
		
		int ITClass = 0, financeClass = 0, entClass = 0, sportsClass = 0, ladyClass = 0; 
		while (it.hasNext()) {
			Entry<Integer, Double> entry = it.next(); 
			if(messageAndLabel.get(entry.getKey()).equals("ent")){
				entClass ++ ;
			}
			if(messageAndLabel.get(entry.getKey()).equals("finance")){
				financeClass ++ ;
			}
			if(messageAndLabel.get(entry.getKey()).equals("sports")){
				sportsClass ++ ;
			}
			if(messageAndLabel.get(entry.getKey()).equals("IT")){
				ITClass ++ ;
			}
			if(messageAndLabel.get(entry.getKey()).equals("lady")){
				ladyClass ++ ;
			}
		}
		int temp , temp1, temp2, temp3 ;
		temp = entClass > financeClass? entClass : financeClass ;
		temp1 = sportsClass > temp ? sportsClass : temp;
		temp2 = ITClass > temp1 ? ITClass : temp1;
		temp3 = ladyClass > temp2 ? ladyClass : temp2; 
		
		if(ITClass == temp3){
			return "IT" ;
		}
		if(financeClass == temp3){
			return "finance" ;
		}
		if(entClass == temp3){
			return "ent" ;
		}
		if(ladyClass == temp3){
			return "lady" ;
		}else{
			return "sports" ;
		}
	}
	
	/**
	 * ת������б�
	 * @return
	 * @throws IOException
	 */
	public static Map<Integer,String> getLabel() throws IOException{
		FileReader reader = new FileReader(new File("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/ѵ����/����б�.txt")) ;
		BufferedReader BR = new BufferedReader(reader) ;
		Map<Integer,String> messageAndLabel = new HashMap<Integer,String>() ;
		String textLine ;
		while((textLine = BR.readLine())!= null){
			String str[] = textLine.split(" ") ;
			messageAndLabel.put(Integer.parseInt(str[0]),str[1]) ;
		}
		return messageAndLabel ;
	}
	
	/**
	 *  ��ȡ��ʵ�������
	 * @param testSetDir
	 * @return
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static Map<Integer,String> getTestRealResult(String testSetDir) throws NumberFormatException, IOException{
		FileReader reader = new FileReader(new File(testSetDir)) ; 
		BufferedReader BR = new BufferedReader(reader) ;
		Map<Integer,String> testResultMap = new LinkedHashMap<Integer,String>() ;
		String line ;
		int count = 0 ;
		while((line=BR.readLine()) != null){ 
			count ++ ;
			String str[] = line.split("\t") ; 
			testResultMap.put(count,str[0]) ;
		}
		return testResultMap ;
	}
	
	/**
	 * �����ٻ���
	 * @param args
	 * @throws IOException
	 */
	public static Double computeRecallrate(String classLabel, String resultDir, Map<Integer,String> testResultMap) throws IOException{
		
		FileReader reader = new FileReader(new File(resultDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		Map<Integer,String> resultMap = new LinkedHashMap<Integer,String>() ;
		
		Double resultCount = 0.0 ; // ��¼��ȷ����ĸ���
		Double recall = 0.0 ; // �ٻ���
		String line ;
		while((line = BR.readLine()) != null){ 
			String str[] = line.split("\t") ;
			resultMap.put(Integer.parseInt(str[0]),str[1]) ;    // ��¼��ѵ�������ķ�����
		}
		
//		Iterator<Map.Entry<Integer, Integer>> it = testResultMap.entrySet().iterator() ; // �������Լ������������
//		while(it.hasNext()){
//			Entry<Integer, Integer> entry = it.next() ;
//			if(entry.getValue() == classLabel){
//				classLabelNum ++ ;
//			}
//		}
		
		//  ѵ��׼ȷ�ĸ���
		for(Map.Entry<Integer, String> entry1 : testResultMap.entrySet()){
            String m1value = entry1.getValue() ;
            if(m1value.equals(classLabel)){
            	String m2value = resultMap.get(entry1.getKey()) ;
                if (m1value.equals(m2value)){             // �������ͬ
                	resultCount ++ ;
                }
            }
        } 
		recall = resultCount / 2000.0  ;   // �ٻ���
		System.out.print(recall + "\t") ;
		return recall ;
	}
	
	/**
	 *  ����׼ȷ��
	 * @param classLabel
	 * @param resultDir
	 * @param testResultMap    ��¼�������ķ����� <----------��Դ�ڲ��Լ�
	 * @throws IOException
	 */
	public static Double computePrecision(String classLabel, String resultDir, Map<Integer,String> testResultMap) throws IOException{
		
		FileReader reader = new FileReader(new File(resultDir)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		
		Map<Integer,String> resultMap = new LinkedHashMap<Integer,String>() ;  // ��¼��ѵ�������ķ�����
		
		Double resultCount = 0.0 ; // ��¼��ȷ����ĸ��� 
		Double numOfError = 0.0 ; // ����ֵ�classlabel����Ŀ
		Double precision = 0.0 ; // �ٻ���
		
		String line ;
		while((line = BR.readLine()) != null){
			String str[] = line.split("\t") ;
			resultMap.put(Integer.parseInt(str[0]),str[1]) ;  // // ��¼��ѵ�������ķ�����
		}
		
		Iterator<Map.Entry<Integer, String>> it = resultMap.entrySet().iterator() ; // �������Լ����
		while(it.hasNext()){
			Entry<Integer, String> entry = it.next() ;
			if(entry.getValue().equals(classLabel)){
				if(testResultMap.get(entry.getKey()).equals(classLabel)){
					resultCount ++ ;
				}else{
					numOfError ++ ;
				}
			}
		}
		
		precision = resultCount / (resultCount + numOfError) ;  // ׼ȷ��
		System.out.print(precision + "\t") ;
		return precision ;
	}
	
	
	
	/*
	 *  ���ࡢָ�����
	 */
	public static void main(String[] args) throws IOException {
		int threshold = 500 ;
		int k ;
		List<String> classList = new ArrayList<String>() ; 
		classList.add("ent");
		classList.add("finance");
		classList.add("sports");
		classList.add("IT");
		classList.add("lady");
		
		Double recall, precision ,Fscore ;
		
		for(int m=9; m<=10; m++){
			k = m * 10 ;
			File file = new File("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/���ƶ�"+ threshold + "/����_"+ k) ;
			if(!file.exists()){
				file.mkdir() ;
			}
			for(int i=1; i<=10000; i++){
				
				//  �����ƶ�����
//				System.out.println("��10000ƪ�����ı��������˵�:" + i + "ƪ�ı�......") ;
//				sortOfDistance(threshold, "C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/���ƶ�"+ threshold + "/��" + i +"ƪ�����ı����ƶ�.txt",
//						"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/���ƶ�"+ threshold + "/������/������_��" + i +"ƪ�����ı����ƶ�.txt") ;
				
				//  �� ѡȡK����
//				System.out.println("ѡȡKNN���ڣ���10000ƪ�����ı��������˵�:" + i + "ƪ�ı�......"); 
				selectKNeighbors(k ,"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/���ƶ�"+ threshold + "/������/������_��" + i +"ƪ�����ı����ƶ�.txt",
						"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/���ƶ�"+ threshold + "/����_"+ k +"/��" + i +"ƪ�����ı����ƶ�.txt") ;
			}
			FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/���ƶ�"+ threshold + "/����_" + k + "/���.txt"));
			for(int i=1;i<=10000;i++){
				 //  �� ������
//				System.out.println("���࣬��10000ƪ�����ı��������˵�:" + i + "ƪ�ı�......"); 
				String predictLabel = classification("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/���ƶ�"+ threshold + "/����_" + k + "/��" + i +"ƪ�����ı����ƶ�.txt",i);
				writer.write(i + "\t" + predictLabel + "\n") ;
				writer.flush();
			}
			writer.close();
			
			for(int i=0; i<classList.size(); i++){
				String classLabel = classList.get(i) ;
				// �� ����ָ��
//				System.out.println("�������#" + classLabel + "#��ָ�� ----> ������ȡ" + threshold + "��������ȡ" + k + "��......") ;
				// ��ʵ�������
				Map<Integer, String> testResultMap = getTestRealResult("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/���Լ�/test.txt") ;
				// ׼ȷ��
				precision = computePrecision(classLabel,"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/���ƶ�"+ threshold + "/����_" + k + "/���.txt", testResultMap);
				// �ٻ���
				recall = computeRecallrate(classLabel,"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/���ƶ�"+ threshold + "/����_" + k + "/���.txt", testResultMap);
				// Fֵ
				Fscore = 2*precision*recall / (precision + recall) ;
				System.out.print(Fscore + "\t") ;
			}
			System.out.println();
		}
	}
}



