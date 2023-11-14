public class Platinum extends Customer {
    private float bonusBucks;
    public Platinum(String firstName, String lastName, String guestID, float amtSpent, float bonusbucks){
        super(firstName, lastName, guestID, amtSpent);
        this.bonusBucks = bonusbucks;

        
    }

    public float getBonusBucks(){
        return bonusBucks;
    }
    
    public void setBonusBucks(float bonusBucks){
        this.bonusBucks = bonusBucks;
    }

    public String toString() {
        return super.toString() + " " + (int)bonusBucks;
    }
}
