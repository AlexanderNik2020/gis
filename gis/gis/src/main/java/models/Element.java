package models;

public class Element {

    private float amount;
    private String pollutedBy;
    private String unit;

    Element(){}

    public Element(float amount, String pollutedBy, String unit) {
        this.amount = amount;
        this.pollutedBy = pollutedBy;
        this.unit = unit;
    }

    public Element(float amount, String pollutedBy) {
        this.amount = amount;
        this.pollutedBy = pollutedBy;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getPollutedBy() {
        return pollutedBy;
    }

    public void setPollutedBy(String pollutedBy) {
        this.pollutedBy = pollutedBy;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Element{" +
                "pollutedBy='" + pollutedBy +
                ", amount=" + amount + '\'' +
                '}';
    }
}
