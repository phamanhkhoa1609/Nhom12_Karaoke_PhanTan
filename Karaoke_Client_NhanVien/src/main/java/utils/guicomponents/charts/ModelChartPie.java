package utils.guicomponents.charts;


import java.awt.Color;
import java.time.LocalDate;

public class ModelChartPie {

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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ModelChartPie(String name, double value, Color color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }
    
    public ModelChartPie(String name, double value, Color color, int quantity) {
        this.name = name;
        this.value = value;
        this.color = color;
        this.quantity = quantity;
    }

    public ModelChartPie() {
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
    private Color color;
}
