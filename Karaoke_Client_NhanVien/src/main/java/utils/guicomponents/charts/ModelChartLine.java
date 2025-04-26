package utils.guicomponents.charts;


import java.time.LocalDate;

public class ModelChartLine {

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}


	public ModelChartLine(String name, double value) {
		this.name = name;
		this.value = value;
	}
	
	public ModelChartLine(String name, double value, int quantity) {
		this.name = name;
		this.value = value;
		this.quantity = quantity;
	}

	public ModelChartLine() {
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	private String name;
	private double value;
	private int quantity;
}
