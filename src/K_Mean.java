import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;



public class K_Mean {
	private static String tagFile, tokenFile;
	private static LinkedList<String> vectorKey = new LinkedList<String>();
    private static LinkedList<Vector> vectors = new LinkedList<Vector>();
    private static LinkedList<Cluster> clusters , newClusters;
    private static int k, n, q=0, i , articles, m;
    private static double ranges[][];
    private static BST tree;
    private static long find;
    

	 public static void main(String[] args) {
	        // TODO code application logic here
	     Vector vector = new Vector();
	     clusters = new LinkedList<Cluster>();
		 newClusters = new LinkedList<Cluster>();
	     
		 tagFile = "input\\baomoi_tag.txt";
		 tokenFile = "input\\token.txt";
	     getKeyList(tagFile);
	     
		 while(q == 0){
		 try {
			System.out.print("Nhập số cum K: " );
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			k = Integer.parseInt(bufferRead.readLine());
			if (k>0) q=1;
			else System.out.println("Xin lỗi đầu vào không hợp lệ!");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 }
		 ranges = new double[k][n];
		 
	       m = vectorKey.size();
	       for (i=0; i<m; i++){
	    	   vector = new Vector();
	    	   vector.setName(vectorKey.get(i).replace("_", " "));
	    	   vectors.add(vector);
	       }
	       System.out.println("Creating Vectors!");
	       creatVectors(tokenFile);
	       System.out.println("Computing Vectors!");
			for (int i = 0; i < k; i++) {
				clusters.add(new Cluster());
				clusters.get(i).setCenter(vectors.get(i));
			}
			int times = 0;
			while (true) {
				times++;
				System.out.println("Time: " +times);
				getRangeOfCoordinateList();							// Tính các khoảng cách bài báo so với tâm
				rebuildClusters();									// Xây dựng lại các cụm dựa trên khoảng cách vừa tính
				if (checkChange()) {								// Nếu có thay đổi so với các cụm trước
					for (int i = 0; i < k; i++) {
						clusters.get(i).copy(newClusters.get(i));
						clusters.get(i).reCalculatingCenter(articles);					// Tính toán lại tâm của các cụm
					}
				} else break;
				if (times == 50) break;	// Lặp lại tối đa 50 lần.
			}
			System.out.println("Có tổng cộng: " + n + " từ khóa.");
			System.out.println("Các từ khóa được chia thành " + k + " cụm:");
			for (int i = 0; i < k; i++) {
				System.out.println(i + ": " + clusters.get(i).getVectors().size());
				clusters.get(i).printOutputFile(i);
			}
		}
	 
	 private static void getRangeOfCoordinateList() {
			int i, j;
			
			for (i = 0; i < k; i++) {
				for (j = 0; j < n; j++) {
					ranges[i][j] = clusters.get(i).getCenter().calculatingRange(vectors.get(j));
				}
			}
		}
	 
	 private static void rebuildClusters() {
			int j, i;			
			newClusters.clear();
			for (i = 0; i < k; i++)
				newClusters.add(new Cluster());
			
			for (j = 0; j < n; j++) {
				i = getMinRange(j);
				newClusters.get(i).put(vectors.get(j));
			}
		}
	 
	 private static int getMinRange(int j) {
			int i, min;
			double minRange;
			
			minRange = ranges[0][j];
			min = 0;
			for (i = 0; i < k; i++)
				if (ranges[i][j] < minRange) {
					min = i;
					minRange = ranges[i][j];
				}
			return min;
		}
	 
	 private static boolean checkChange() {
			int i;
			for (i = 0; i < k; i++)
				if (!clusters.get(i).compare(newClusters.get(i)))
					return true;
			return false;
		}
	 
	 private static void getKeyList(String tag){
			FileInputStream fos1;
			try {
				fos1 = new FileInputStream(tag);
				Reader input = new java.io.InputStreamReader(fos1, "UTF8");
		        BufferedReader inputbuf = new BufferedReader(input);
				String word;
				n = 0;
				while ((word = inputbuf.readLine()) != null){
					n++;
					word = word.replace(" ","_");
					vectorKey.add(word);
//					if (n>200) break;
				}
				input.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 
	 private static void creatVectors(String path){		 
		 FileInputStream fos1;
		 Reader input;
		 BufferedReader inputbuf;
		 String lineRead;
		 tree = new BST();
		 articles = 0;
		 try {
			fos1 = new FileInputStream(path);
			input = new java.io.InputStreamReader(fos1, "UTF8");
		    inputbuf = new BufferedReader(input);
		    while ((lineRead = inputbuf.readLine()) != null){
		    	lineRead = lineRead.toLowerCase();
		    	for (String word : lineRead.split(" ")){
		    		tree.insert(word);
		    	}
		    	i = 0;
			    for (String temp:vectorKey){
			    	find = tree.find(temp);
			    	if (find > 0) vectors.get(i).addNode((double)find);
			    	else vectors.get(i).addNode(0.0);
			    	i++;
			    }
			    tree.clear();
			    articles++;
			    System.out.print(".");
		    }
		    System.out.println();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
