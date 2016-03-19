package muyanmoyang.featureSelection_impr;

import java.io.IOException;
import java.util.Vector;
import muyanmoyang.category.KNN;

public class KNN_Impro {
	public static void main(String[] args) throws NumberFormatException, IOException { 
		long start = System.currentTimeMillis() ;

    	int threshold = 3000 ; // ѡȡ������������   
    	
        String testFile = "C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/���Լ�/�Ľ�_���Լ�TFIDF_" + threshold + "������/TFIDF.txt" ;   // ���Լ��������ļ�
        Vector<Double[]> testVector = KNN.initTestVector(testFile,threshold) ;       // �����ı���������ʾ��ÿ��Double�������ÿƪ�ı���TF/IDFֵ����
        
        String trainFile = "C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/ѵ����/�Ľ�_ѵ����TFIDF_" + threshold + "������/TFIDF.txt";   // ѵ�����������ļ�
        Vector<Double[]> trainVector = KNN.initTrainVector(trainFile,threshold) ;       // �����ı���������ʾ��ÿ��Double�������ÿƪ�ı���TF/IDFֵ����
       	//�������ƶ�
        String distanceDir = "C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/�Ľ����ƶ�"+ threshold + "/" ;
        KNN.caculateDist(trainVector,testVector,distanceDir, threshold);  // �������ƶ�
        
        long end = System.currentTimeMillis() ;
        System.out.println("����ʱ�䣺" + (end - start)/1000 + "��......");                
      
        System.gc(); 
        
	}
}
