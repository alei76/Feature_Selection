package muyanmoyang.featureSelection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import muyanmoyang.utils.KeyUtils;

/**
 * ��Ϣ���棬������ѡ��
 * @author moyang
 *
 */
public class IG {
	/*
	 * ����ĳ�������ʳ��ֵĸ��ʣ��ôʳ��ֵ��ĵ��� / �ܵ��ĵ���
	 */
	private static Double computePofWord(Map<String,String> characterAndTimes,String word) throws IOException{
		
		System.out.println(characterAndTimes.get(word));
		return (Double.parseDouble(characterAndTimes.get(word)) / 20000.0) ;
	}
	
	/*
	 *  ��ȡ����͸ô�����ֵ��ı���Ŀ
	 */
	private static Map<String,String> numOfAppearInText() throws IOException{
		FileReader reader = new FileReader(new File("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/ѵ����/�ʴ�.txt")) ;
		BufferedReader BR = new BufferedReader(reader) ;
		
		Map<String,String> dicMap = new HashMap<String,String>() ;
		FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/ѵ����/����-���ֵ�������.txt")) ;
		String line ;
		while((line = BR.readLine()) != null){
			String str[] = line.split(" ") ;
			dicMap.put(str[0],str[str.length-1]) ;
			writer.write(str[0] + " " + str[str.length-1]); 
			writer.write("\n"); 
			writer.flush();
		}
		writer.close();
		return dicMap ;
	}
	
	/**
	 * ���� P(Ci|t)��ʾ����T��ʱ�����Ci���ֵĸ��ʣ�ֻҪ�ó�����T�����������Ci���ĵ������Գ�����T���ĵ���
	 * @param word               ����
	 * @param Ci                 ���  
	 * @param bagOfWordsMap      �ʴ�ģ��
	 * @param characterAndTimes  ���� �� ���ֵ�������Ŀ 
	 * @return                   ����P(Ci|word)
	 * @throws IOException
	 */
	private static Double computePofCiInFeatureT(String word ,String Ci,Map<String,Vector<KeyUtils>> bagOfWordsMap,
			Map<String,String> characterAndTimes,List<String> classLabel) throws IOException{

		Double titleCount = 0.0 ; 				// �����˸�word�����������Ci���ĵ���
		Vector<KeyUtils> vector = bagOfWordsMap.get(word) ;
		for(int i=0; i<vector.size(); i++){
			KeyUtils keyutils = vector.get(i) ;
			if(classLabel.get(keyutils.getX()-1).equals(Ci)){   // �����˸�word�����Ҹ�word����Ci�����Ŀ
				titleCount ++  ;
			}
		}
		if(titleCount == 0.0){
			return Double.MIN_VALUE ;
		}else{
			Double wordOfNumArticle = Double.parseDouble((characterAndTimes.get(word))) ; // �����˸�word�����Ҹ�word����Ci�����Ŀ / �����˸�word���ĵ���
			return (titleCount / wordOfNumArticle) ;
		}
	}
	
	/**
	 * ���� P(Ci|T��)��ʾ������T��ʱ�����Ci���ֵĸ��ʣ�ֻҪ�ò�����T�����������Ci���ĵ������Բ�����T���ĵ���
	 * @param word               ����
	 * @param Ci                 ���  �� "1" or "0"
	 * @param bagOfWordsMap      �ʴ�ģ��
	 * @param characterAndTimes  ���� �� ���ֵ�������Ŀ 
	 * @return                   ����P(Ci|word)
	 * @return
	 */
	private static Double computePofCiWithoutFeatureT(String word ,String Ci,Map<String,Vector<KeyUtils>> bagOfWordsMap,
			Map<String,String> characterAndTimes,List<String> classLabel) {
		// TODO Auto-generated method stub
		Double titleCount = 0.0 ; 				// �����ָ�word�����������Ci���ĵ���
		
		List<Integer> articleList = new ArrayList<Integer>() ;
		List<Integer> tempList = new ArrayList<Integer>() ; // ��¼word���ֵ��ı����
		Vector<KeyUtils> vector = bagOfWordsMap.get(word) ;
		for(int i=1; i<=20000; i++){
			articleList.add(i) ;
		}
		for(int i=0; i<vector.size(); i++){
			tempList.add(vector.get(i).getX()) ;
		}
		articleList.removeAll(tempList) ; //��������word���ı���ż��� , tempList ��¼����wordû�г��ֹ����ı����

		for(int i=0; i<articleList.size(); i++){
			if(classLabel.get(articleList.get(i)-1).equals(Ci)){   // �����ָ�word����������Ci��
				titleCount ++  ;
			}
		}
		
		Double wordOfNumArticle = 20000.0 - Double.parseDouble((characterAndTimes.get(word))) ; // �����ָ�word���ĵ���
		return (titleCount / wordOfNumArticle) ;  // �����ָ�word����������Ci����ĵ��� / �����ָ�word���ĵ���
	}
	
	
	/*
	 *   ת������б�
	 */
	public static List<String> getLabel() throws IOException{
		FileReader reader = new FileReader(new File("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/ѵ����/train.txt")) ;
		BufferedReader BR = new BufferedReader(reader) ;
		FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/ѵ����/����б�.txt")) ;
		int count = 0 ;
		List<String> list = new ArrayList<String>() ;
		String textLine ;
		while((textLine = BR.readLine())!= null){
			String str[] = textLine.split("\t") ;
//			System.out.println(count + ":" + str.length); 
			writer.write(count+1 + " " + str[0] + "\n");  //�������ش��� 
			writer.flush();
			list.add(str[0]);
			count ++ ;
		}
		writer.close();
		return list ;
	}
	
	
//	private static Map<String,String>  wordAndNumofarticle() throws IOException{
//		FileReader reader = new FileReader(new File("C:/Users/Administrator/Desktop/����(��С������ݼ�)/�ʴ�.txt")) ;
//		BufferedReader BR = new BufferedReader(reader) ;
//		
//		Map<String,String> dicMap = new HashMap<String,String>() ;
//		String line2 ;
//		while((line2 = BR.readLine()) != null){
//			String str[] = line2.split(" ") ;
//			dicMap.put(str[0],str[str.length-1]) ; // �ʣ����ֵ��ܵ��ĵ���
//		}
//		return dicMap ;
//	}
	
	
	/**
	 *  ��ȡ�ʴ�ģ�ͣ��洢������Map
	 * @param fileOfBag   �ʴ��ļ�
	 * @return Map<String, Vector<KeyUtils>> �ʴ�ģ�ͼ���   ʵ����[  Ҫ�� :11458 1;12248 1;111129 1;146004 1;315091 1;390447 1;446011 1;500701 1;625715 1; 9 ]
	 * @throws IOException 
	 */
	private static Map<String, Vector<KeyUtils>> readBofWords(String fileOfBag) throws IOException {
		// TODO Auto-generated method stub
		Map<String, Vector<KeyUtils>>  bagMap = new HashMap<String, Vector<KeyUtils>>() ; // �ʴ�ģ��
		Vector<KeyUtils> vector = new Vector<KeyUtils>() ;
		FileReader reader = new FileReader(new File(fileOfBag)) ;
		BufferedReader BR = new BufferedReader(reader) ;
		int count = 0 ;
		String line ;
		while((line=BR.readLine())!=null){
			count ++ ;
			System.out.println("��ȡ�ʴ��ļ����ؽ�Map���ϣ���ȡ���˵�" + count + "��......") ;
			if(vector == null)
			{
				vector = new Vector<KeyUtils>() ;
			}
			String str[] = line.split(" :") ;
			String str2 = str[1] ;
			String keyUtilsStr[] = str2.split(";") ;
			for(int i=0; i<keyUtilsStr.length-1; i++){
				String tempStr[] = keyUtilsStr[i].split(" ") ;
				KeyUtils keyUtils = new KeyUtils(Integer.parseInt(tempStr[0]),Integer.parseInt(tempStr[1])) ;
				vector.add(keyUtils) ;
			}
			bagMap.put(str[0],vector) ;
			vector = null ;
		}
		return bagMap;
	}
	
	/**
	 *  ��ȡ���Ｏ��
	 * @param string
	 * @return
	 * @throws IOException 
	 */
	private static Vector<String> getCharacterList(String characterDir) throws IOException {
		// TODO Auto-generated method stub
		Vector<String> vector = new Vector<String>() ;
		FileReader reader = new FileReader(new File(characterDir)) ;
		BufferedReader BR = new BufferedReader(reader) ; 
		String line ;
		while((line=BR.readLine())!=null){
			vector.add(line) ;
		}
		return vector;
	}
	
	/*
	 * ������Ϣ����(IG)
	 */
	public static void main(String[] args) throws IOException, SQLException { 
//		computePofWord() ;
		Vector<String> characterList = getCharacterList("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/ѵ����/���Ｏ��.txt") ;  
		Map<String,Vector<KeyUtils>> bagOfWordsMap = readBofWords("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/ѵ����/�ʴ�.txt") ; 
		
		// �����������IGֵд���ļ�
		FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/ѵ����/IGOfWords.txt")) ;
		
		Double PofWord ,PofC1InFeatureT ,PofC0InFeatureT, PofC0WithoutFeatureT, PofC1WithoutFeatureT,PofC2InFeatureT,PofC3InFeatureT,PofC2WithoutFeatureT,PofC3WithoutFeatureT,PofC4InFeatureT,PofC4WithoutFeatureT;
		double IG1, IG2, IG3, IG;
		//ent
		double pOfC0 = 4000.0 / 20000.0;
		//finance
		double pOfC1 =  pOfC0;
		//sports
		double pOfC2 = pOfC0 ;
		//IT
		double pOfC3 = pOfC0 ;
		//lady
		double pOfC4 = pOfC0 ;
		
		String line ;
		int count = 0 ;  //ִ�м�����
		Map<String,String> characterAndTimes = numOfAppearInText() ;  //��ȡ����͸ô�����ֵ��ı���Ŀ   : ���� --- ���ֵĴ���
		List<String> classLabel = getLabel() ;//�������
		for(int i=0; i<characterList.size(); i++){
			count ++ ;
			System.out.println("����ÿ�������ʵ���Ϣ����ֵIG���Ѵ����˵�" + count + "������......");
			PofWord = computePofWord(characterAndTimes,characterList.get(i)) ;
			System.out.println("PofWord:   " + PofWord); 
			
			// IT
			PofC0InFeatureT = computePofCiInFeatureT(characterList.get(i),"ent",bagOfWordsMap,characterAndTimes,classLabel) ;
			System.out.println("ent    " + PofC0InFeatureT);
			//finance
			PofC1InFeatureT = computePofCiInFeatureT(characterList.get(i),"finance",bagOfWordsMap,characterAndTimes,classLabel) ;
			System.out.println("finance    " + PofC1InFeatureT);
			//society
			PofC2InFeatureT = computePofCiInFeatureT(characterList.get(i),"sports",bagOfWordsMap,characterAndTimes,classLabel) ;
			System.out.println("sports    " + PofC2InFeatureT);
			//sports
			PofC3InFeatureT = computePofCiInFeatureT(characterList.get(i),"IT",bagOfWordsMap,characterAndTimes,classLabel) ;
			System.out.println("IT    " + PofC3InFeatureT);
			// lady
			PofC4InFeatureT = computePofCiInFeatureT(characterList.get(i),"lady",bagOfWordsMap,characterAndTimes,classLabel) ;
			System.out.println("lady    " + PofC4InFeatureT);
			
			//TODO   ����
			// IT
			PofC0WithoutFeatureT = computePofCiWithoutFeatureT(characterList.get(i),"ent",bagOfWordsMap,characterAndTimes,classLabel) ;
			System.out.println("ent    " + PofC0WithoutFeatureT); 
			//finance
			PofC1WithoutFeatureT = computePofCiWithoutFeatureT(characterList.get(i),"finance",bagOfWordsMap,characterAndTimes,classLabel) ;
			System.out.println("finance    " + PofC1WithoutFeatureT);
			//society
			PofC2WithoutFeatureT = computePofCiWithoutFeatureT(characterList.get(i),"sports",bagOfWordsMap,characterAndTimes,classLabel) ;
			System.out.println("sports    " + PofC2WithoutFeatureT); 
			//sports
			PofC3WithoutFeatureT = computePofCiWithoutFeatureT(characterList.get(i),"IT",bagOfWordsMap,characterAndTimes,classLabel) ;
			System.out.println("IT    " + PofC3WithoutFeatureT);
			//lady
			PofC4WithoutFeatureT = computePofCiWithoutFeatureT(characterList.get(i),"lady",bagOfWordsMap,characterAndTimes,classLabel) ;
			System.out.println("lady    " + PofC4WithoutFeatureT);
			
			
			IG1 = (-1) * (pOfC0 * (Math.log((double)pOfC0)/ Math.log((double)2)) 
					+ pOfC1 * (Math.log((double)pOfC1)/ Math.log((double)2)) + pOfC2 * (Math.log((double)pOfC2)/ Math.log((double)2)) 
					+ pOfC3 * (Math.log((double)pOfC3)/ Math.log((double)2))+ pOfC4 * (Math.log((double)pOfC4)/ Math.log((double)2)));
			IG2 = PofWord * ((PofC0InFeatureT * (Math.log((double)PofC0InFeatureT)/ Math.log((double)2))) +
					(PofC1InFeatureT * (Math.log((double)PofC1InFeatureT)/ Math.log((double)2)))
					+(PofC2InFeatureT * (Math.log((double)PofC2InFeatureT)/ Math.log((double)2)))
					+(PofC3InFeatureT * (Math.log((double)PofC3InFeatureT)/ Math.log((double)2)))
					+(PofC4InFeatureT * (Math.log((double)PofC4InFeatureT)/ Math.log((double)2)))) ;
			//-------------------------------------------------------------------------------------------------------------
			IG3 = (1-PofWord) * ((PofC0WithoutFeatureT * (Math.log((double)(PofC0WithoutFeatureT))/ Math.log((double)2))) +
					((PofC1WithoutFeatureT) * (Math.log((double)(PofC1WithoutFeatureT))/ Math.log((double)2)))
					+(PofC2WithoutFeatureT * (Math.log((double)(PofC2WithoutFeatureT))/ Math.log((double)2)))
					+(PofC3WithoutFeatureT * (Math.log((double)(PofC3WithoutFeatureT))/ Math.log((double)2)))
					+(PofC4WithoutFeatureT * (Math.log((double)(PofC4WithoutFeatureT))/ Math.log((double)2)))) ;
			//-------------------------------------------------------------------------------------------------------------
			IG = IG1 + IG2 + IG3;
			System.out.println("��Ϣ����ֵ��" + IG) ; 
			writer.write(characterList.get(i) + ":" + IG) ;
			writer.write("\n");
			writer.flush();
		}
		writer.close();
	}

}


