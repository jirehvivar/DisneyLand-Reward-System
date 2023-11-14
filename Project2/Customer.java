class Customer{
    String firstName;
    String lastName;
    String guestID;
    float amountSpent;


    public Customer(String firstName, String lastName, String guestID, float amountSpent) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.guestID = guestID;
        this.amountSpent = amountSpent;
    }
    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String newFirstName){
        this.firstName = newFirstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String newLastName){
        this.lastName = newLastName;
    }

    public String getGuestID(){
        return this.guestID;
    }

    public void setGuestID(String newGuestID){
        this.guestID = newGuestID;
    }

    public float getAmountSpent(){
        return this.amountSpent;
    }

    public void setAmountSpent(float newAmountSpent){
        this.amountSpent = newAmountSpent;
    }

    //override
    @Override
    public String toString() {
        String formattedTotalAmtSpent = String.format("%.2f", amountSpent);
        return  guestID + " " + firstName + " " + lastName + " " + formattedTotalAmtSpent;
    }
    
}
