package zadanie_slonie;

import java.util.ArrayList;

public class Cycle {
	
	ArrayList<Integer> elements;
	private int minWeigth;
	private long sumWeigth;
	
	
	public Cycle () {
		this.elements = new ArrayList<Integer>();
	}


	public int getMinWeigth() {
		return minWeigth;
	}


	public void setMinWeigth(int minWeigth) {
		this.minWeigth = minWeigth;
	}


	public long getSumWeigth() {
		return sumWeigth;
	}


	public void setSumWeigth(long sumWeigth) {
		this.sumWeigth = sumWeigth;
	}


	public ArrayList<Integer> getElements() {
		return elements;
	}


	public void setElements(ArrayList<Integer> elements) {
		this.elements = elements;
	}
	
	
	
	

}
