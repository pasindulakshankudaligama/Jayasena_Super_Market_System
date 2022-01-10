package model;

import java.util.Objects;

public class Item {
    private String code;
    private String description;
    private int packetSize;
    private double unitPrice;
    private int qtyOnHand;
    private int discount;


    public Item() {
    }

    public Item(String code, String description, int packetSize, double unitPrice, int qtyOnHand, int discount) {
        this.setCode(code);
        this.setDescription(description);
        this.setPacketSize(packetSize);
        this.setUnitPrice(unitPrice);
        this.setQtyOnHand(qtyOnHand);
        this.setDiscount(discount);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPacketSize() {
        return packetSize;
    }

    public void setPacketSize(int packetSize) {
        this.packetSize = packetSize;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", packetSize=" + packetSize +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", discount=" + discount +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return packetSize == item.packetSize && Double.compare(item.unitPrice, unitPrice) == 0 && qtyOnHand == item.qtyOnHand && discount == item.discount && Objects.equals(code, item.code) && Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, description, packetSize, unitPrice, qtyOnHand, discount);
    }
}

