package muyanmoyang.featureSelection_impr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import muyanmoyang.category.KNN_Classification;

public class KNN_classification_Impro {
	
	public static void main(String[] args) throws IOException { 
		int threshold = 3000  ;
		int k = 10 ;
		List<String> classList = new ArrayList<String>() ; 
		classList.add("ent");
		classList.add("finance");
		classList.add("sports");
		classList.add("IT");
		classList.add("lady");
		Double recall, precision ,Fscore ;
		for(int i=1; i<=10000; i++){
			
			//  �����ƶ�����
			System.out.println("��10000ƪ�����ı��������˵�:" + i + "ƪ�ı�......") ;
			KNN_Classification.sortOfDistance(threshold, "C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/�Ľ����ƶ�"+ threshold + "/��" + i +"ƪ�����ı����ƶ�.txt",
					"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/�Ľ����ƶ�"+ threshold + "/������/������_��" + i +"ƪ�����ı����ƶ�.txt") ;
			
			//  �� ѡȡK����
//			System.out.println("ѡȡKNN���ڣ���10000ƪ�����ı��������˵�:" + i + "ƪ�ı�......"); 
//			KNN_Classification.selectKNeighbors(k ,"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/�Ľ����ƶ�"+ threshold + "/������/������_��" + i +"ƪ�����ı����ƶ�.txt",
//					"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/�Ľ����ƶ�"+ threshold + "/����_"+ k +"/��" + i +"ƪ�����ı����ƶ�.txt") ;
			
		}
//		FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/�Ľ����ƶ�"+ threshold + "/����_" + k + "/�Ľ����.txt"));
//		for(int i=1;i<=10000;i++){
//			//  �� ������
//				System.out.println("���࣬��10000ƪ�����ı��������˵�:" + i + "ƪ�ı�......"); 
//				String predictLabel = KNN_Classification.classification("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/�Ľ����ƶ�"+ threshold + "/����_" + k + "/��" + i +"ƪ�����ı����ƶ�.txt",i);
//				writer.write(i + "\t" + predictLabel + "\n") ;
//				writer.flush();
//		}
//		writer.close();
//		
//		for(int i=0; i<classList.size(); i++){
//			String classLabel = classList.get(i) ;
//			// �� ����ָ��
////			System.out.println("�������#" + classLabel + "#��ָ�� ----> ������ȡ" + threshold + "��������ȡ" + k + "��......") ;
//			// ��ʵ�������
//			Map<Integer, String> testResultMap = KNN_Classification.getTestRealResult("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/���Լ�/test.txt") ;
//
//			// ׼ȷ��
//			precision = KNN_Classification.computePrecision(classLabel,"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/�Ľ����ƶ�"+ threshold + "/����_" + k + "/�Ľ����.txt", testResultMap);
//			// �ٻ���
//			recall = KNN_Classification.computeRecallrate(classLabel,"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/�Ľ����ƶ�"+ threshold + "/����_" + k + "/�Ľ����.txt", testResultMap);
//			
//			// Fֵ
//			Fscore = 2*precision*recall / (precision + recall) ;
//			System.out.print(Fscore + "\t") ;
//		}
		
	}
}
