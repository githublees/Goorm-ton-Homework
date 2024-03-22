package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadCsv {
	
	private ArrayList<Product> list = new ArrayList<>();
	
	ReadCsv(){};
	
	public ArrayList<Product> read() {
		
		BufferedReader br = null;
		
		try {
			
			// 현재 작업 디렉토리를 확인
			String currentDir = System.getProperty("user.dir");
			
			// 파일에 접근할 수 있는 경로를 생성
			Path filePath = Paths.get(currentDir, "File.csv");
			
			br = Files.newBufferedReader(filePath, Charset.forName("UTF-8"));
			
			String line = "";
			
			while((line = br.readLine()) != null) {
				
				String[] arr = line.split(",");
				
				int id = Integer.parseInt(arr[0]);
				String name = arr[1];
				int price = Integer.parseInt(arr[2]);
				
				Product product = new Product(id, name, price);
				
				list.add(product);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return this.list;
	}
}
