package models;

import models.Element;
import util.Controller;

import java.util.List;

public class Sample {

    private String label;
    private double lat;
    private double _long;
    private List<Element> elements;

    public Sample(){}

    public Sample(String label, double lat, double _long, List<Element> elements) {
        this.label = label;
        this.lat = lat;
        this._long = _long;
        this.elements = elements;
    }

    public String toString(){
        return label + "\n lat - " + lat + "\n long -" + _long;
    }
}
