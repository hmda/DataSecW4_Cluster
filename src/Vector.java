import java.util.LinkedList;



public class Vector {
	private LinkedList<Double> vector = new LinkedList<Double>();
	private String nameInput;
	
	public void setName(String name){
		nameInput = name.replace("output_tagger", "output_filter");
	}
	
	public String getName(){
		return nameInput;
	}
	
	public void addNode(double d) {
		vector.add(d);
	}
	
	public void setVector(LinkedList<Double> vector) {
		this.vector = vector;
	}
	
	public LinkedList<Double> getVector(){
		return this.vector; 
	}
	
	public void addValue(int i, double d) {
		vector.set(i, d);
	}
	
	public boolean compare(Vector x) {
		if (nameInput.compareTo(x.getName()) == 0)
			return true;
		return false;
	}
	
	public double calculatingRange(Vector x){
		
		double X = 0;
		
		for (int i = 0; i < this.vector.size(); i++) {
			X = X + (this.vector.get(i) - x.getVector().get(i))*(this.vector.get(i) - x.getVector().get(i));
		}
		
		X = Math.sqrt(X);
		
		return X;
	}

}
