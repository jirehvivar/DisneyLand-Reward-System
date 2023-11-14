public class Gold extends Customer {
    private float discount;
    public Gold(String firstName, String lastName, String guestID, float amtSpent, float discount){
        super(firstName, lastName, guestID, amtSpent);
        this.discount = discount;
    }
    
    public float getDiscount(){
        return discount;
    }

    public void setDiscount(float discount){
        this.discount = discount;
    }
    public String toString() {
        return super.toString() + " " + String.format("%.0f%%", discount);
    }
    
}
