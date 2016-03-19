package muyanmoyang.preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import muyanmoyang.utils.DBUtil;

/**
 * ����ѵ�����ݼ��Ͳ��Լ�,���ֱ�д�������ļ�,�ܹ�28000 �����ţ�ѵ����ȡ20000(�����ʼ�10000) �� ���Լ�ȡ8000(�����ʼ�4000) ��
 * @author moyang
 *
 */
public class CreateTrainAndTestSet {
	
	public static void main(String[] args) throws IOException, SQLException { 
//		getSogouTextFromMySQL() ;  // �����ݿ��ȡ���ݼ� 
//		preprocess("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ı�/lady.txt"
//				 ,"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ı�/lady_2.txt") ;
		String label = "lady" ;
		divideTrainAndTest("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ı�/" + label + "_2.txt",
							"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ı�/ѵ����/"+ label +".txt",
				"C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ı�/���Լ�/" + label + ".txt");
		

	}
	
	private static void preprocess(String readerFile,String writerDir) throws IOException{
		InputStreamReader reader = new InputStreamReader(new FileInputStream(readerFile),"GBK") ;
		BufferedReader BR = new BufferedReader(reader) ;
		FileWriter writer = new FileWriter(new File(writerDir)) ;
		String line ;
		while((line=BR.readLine()) != null){
			String str[] = line.split("\t") ;
			if(str[1].length() > 10){
				writer.write(str[0] + "\t" + str[1] + "\n");
				writer.flush();
			}
		}
		writer.close();
	}
	/*
	 *  ��ԭ���ݼ���������,ͬһ�������ͬһ��txt�ļ�
	 */
//	private static void readTxtFromOriginalText(String fileDir) throws IOException{
//		BufferedReader BR ;
//		File file = new File(fileDir);
//		File[] fileList = file.listFiles();
//		int count = 0 ;
//		FileWriter writer = new FileWriter(new File("C:/Users/Administrator/Desktop/���ģ�������ȡ��/������ѧ�����ı��������Ͽ�/����.txt")) ;
//		for (int i = 0; i < fileList.length; i++) {
//			if (fileList[i].isFile()) {
//				count ++ ; // ��countƪ�ı�
//				BR = new BufferedReader(new InputStreamReader(new FileInputStream(fileList[i]),"GBK")) ;
//				String line ;
//				while((line=BR.readLine()) != null){
//					if(!line.startsWith("���¹⻪") && !line.startsWith("--") && !line.startsWith(" ������") &&
//							!line.startsWith("��  ��") && !line.startsWith("����վ") && !line.startsWith("��") && 
//							!line.startsWith("�� ��Դ") &&  !line.startsWith("[������һҳ]") && !line.startsWith("��")){
//						writer.write(line) ;
//					}
//				}
//				writer.write("\n");
//				writer.flush();
//			}
//		}
//		writer.close();
//	}
	
	
	/*
	 *  ����ѵ���� �� ���Լ�
	 */
	private static void divideTrainAndTest(String fileDir,String trainDir,String testDir) throws IOException{
		File readerFile = new File(fileDir);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(readerFile),"GBK") ;
		BufferedReader BR = new BufferedReader(reader) ;
		
		FileWriter trainWriter = new FileWriter(new File(trainDir)) ;
		FileWriter testWriter = new FileWriter(new File(testDir)) ;
		String line ;
		int count = 0 ;
		while((line=BR.readLine()) != null){
			count ++ ;
			if(count <= 4000){
				trainWriter.write(line);
				trainWriter.write("\n");
				trainWriter.flush();
			}
			if(count>4000 && count <=6000 ){
				testWriter.write(line);
				testWriter.write("\n");
				testWriter.flush();
			}
		}
		trainWriter.close();
		testWriter.close();
	} 
	

	/**
	 * ��MySQL���ݿ��ȡ�����ı�����Map<Integer,String[]>��ʽ����
	 * @return Map<Integer,String[]>�������ı�
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void getSogouTextFromMySQL() throws SQLException, IOException
	{ 
		ArrayList<String> sougouTextListFromSql = new ArrayList<String>() ;//������ȡ�����ݿ���select����ÿ�����ϼ�¼
		Map<Integer,String[]> sougouTextMapFromSql = new HashMap<Integer, String[]>() ;//��������ݿ���select�������ϼ�¼
		FileWriter fileWriter = new FileWriter(new File("C:/Users/Administrator/Desktop/���ģ�������ȡ��/���ı�/lady.txt")) ;
 		
		String url = "jdbc:mysql://localhost:3306/sogou?useUnicode=true&characterEncoding=UTF-8";
		String username = "root";
		String password = "123456";
		Connection conn = DBUtil.getConnection(url, username, password);
		
	    Statement stmt = null;  //��ʾ���ݿ�ĸ��²���  
	    ResultSet result = null; //��ʾ�������ݿ�Ĳ�ѯ���  
	    stmt = conn.createStatement();
	    result = stmt.executeQuery(""
	    		+ "SELECT newstitle,category FROM sohunews_reduced where category='lady' "
	    		+ "AND LENGTH(newstitle)>10"); //ִ��SQL ��䣬��ѯ���ݿ�  
	    int count = 0 ; //��¼ResultSet��¼���ڵ�����
	    int tempcount = 0; //��¼ResultSet������count��ֵ
	    while(result.next()) 
	    {  
	    	String newstitle = result.getString("newstitle") ;
	    	String category = result.getString("category") ;
	    	
//	    	ResultSetMetaData m = result.getMetaData() ;//��ȡ�� ResultSet ������еı�š����ͺ�����
//	    	int columns = m.getColumnCount() ;//���ش� ResultSet �����е�����
	    	
	    	//��ÿ�������ı���ӵ�ArrayList��
//	    	if( newscontent.length() > 30 && newscontent.length() < 200){
	    		sougouTextListFromSql.add(category) ;
		    	sougouTextListFromSql.add(newstitle) ;
		    	 
		    	count++ ;
		    	tempcount = count ;
		    	System.out.println("��" + count + "��"); 
		    	
		    	String[] textStringArray = {sougouTextListFromSql.get(0),sougouTextListFromSql.get(1)} ;
		    	
		    	sougouTextMapFromSql.put(count,textStringArray) ; //��������Map����ʽ���� 
		    	sougouTextListFromSql.clear() ; //�Ƴ����б��е�����Ԫ��
//	    	}
	    }
	    System.out.println("����" + tempcount); 
	    
	    for(int i=1 ; i<=tempcount ; i++)
	    {
	    	String[] str = sougouTextMapFromSql.get(i) ;//������Map����sougouTextMapFromSql
	    	//д�뵽ָ��txt�ļ����б���
	    	fileWriter.write(str[0] + "\t" + str[1] + "\n") ; 
	    }
	    fileWriter.flush() ;
	    fileWriter.close() ;
	    result.close();  
	    conn.close(); // 4���ر����ݿ�  
//		return sougouTextMapFromSql ;
	}
	
}
