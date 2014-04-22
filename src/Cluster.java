

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.LinkedList;

public class Cluster {
	
	private LinkedList<Vector> vectors;
	private Vector center;
	
	public Cluster() {
		// TODO Auto-generated constructor stub
		this.vectors = new LinkedList<Vector>();
	}

	public void put(Vector x) {
		this.vectors.add(x);
	}
	public void push(Vector x) {
		this.vectors.push(x);
	}
	
	public int size() {
		return this.vectors.size();
	}
	
	public LinkedList<Vector> getVectors() {
		return this.vectors;
	}
	
	public void setCenter(Vector center) {
		this.center = center;
	}
	public Vector getCenter() {
		return this.center;
	}
	public void reCalculatingCenter(int nVector){
		double d;
		LinkedList<Double> vector = new LinkedList<Double>();
		for (int i = 0; i < nVector; i++){
			vector.add(0.0);
		}
//		System.out.println(nVector);
		for (Vector vector1 : this.vectors) {
			d = 0;
			for (int i = 0; i < nVector; i++){
				d = vector.get(i);
				d += vector1.getVector().get(i);
				vector.set(i, d);
			}
		}
		for (int i = 0; i < nVector; i++) {
			d = vector.get(i);
			d = d / nVector;
			vector.set(i, d);
		}
		this.center.setVector(vector);
	}
	
	/**
	 * @param cluster
	 * @return true if == else return false
	 */
	public boolean compare(Cluster cluster) {
		if (this.vectors.size() != cluster.getVectors().size())
			return false;
		for (int i = 0; i < this.vectors.size(); i++)
			if (vectors.get(i).compare(cluster.getVectors().get(i)))
				return false;
		return true;
	}
	
	public void copy(Cluster cluster) {
		this.vectors.clear();
		this.vectors.addAll(cluster.getVectors());
	}

	public void printOutputFile(int stt){		
		String path, name, x;
		try {
			String name_output = "clusters\\" + stt + ".txt";
			File file = new File(name_output);
			file.getParentFile().mkdirs();
			FileOutputStream fos1;
			fos1 = new FileOutputStream(name_output);
			Writer output = new java.io.OutputStreamWriter(fos1,"UTF8");
		for (Vector vector : this.vectors) {
				name = vector.getName();
				output.write(name + "\n");
				//System.out.println(content);
			}
		output.close();
	    }
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Khong mo duoc file");
			e.printStackTrace();
		}
	}
	
}
