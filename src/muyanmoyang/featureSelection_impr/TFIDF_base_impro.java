package muyanmoyang.featureSelection_impr;

import java.io.IOException;

import muyanmoyang.featureSelection.TfIdf;

public class TFIDF_base_impro {
	public static void main(String[] args) throws IOException {
		
		int threshold = 3000 ;    // ѡ�񱣴�threshold�������ʵ��ı�
		TfIdf.computeTfIdf("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/ѵ����/" + threshold + "������/" + "������ѡ��_"+ threshold + ".txt", 
				"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/���Լ�/�µĲ��Լ�2.txt", 
				"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/���Լ�/�Ľ�_���Լ�TFIDF_" + threshold + "������/TFIDF.txt",threshold) ;
		TfIdf.computeTfIdf("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/ѵ����/" + threshold + "������/" + "������ѡ��_"+ threshold + ".txt", 
				"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/ѵ����/�µ�ѵ����2.txt", 
				"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ű���/ѵ����/�Ľ�_ѵ����TFIDF_" + threshold + "������/TFIDF.txt",threshold) ;
		
	}
}
