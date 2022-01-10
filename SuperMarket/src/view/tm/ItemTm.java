package view.tm;

public class ItemTm {
    private String code;
    private String description;
    private int packetSize;
    private double unitPrice;
    private int qtyOnHand;
    private int discount;


    public ItemTm() {
    }

    public ItemTm(String code, String description, int packetSize, double unitPrice, int qtyOnHand, int discount) {
        this.code = code;
        this.description = description;
        this.packetSize = packetSize;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.discount = discount;
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
        return "ItemTm{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", packetSize=" + packetSize +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", discount=" + discount +
                '}';
    }
}