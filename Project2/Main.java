//Jireh Vivar 10.24.2023
//jxv220001
import java.util.*;
import java.io.*;


public class Main {
    static Customer[] regularCustomer = new Customer[10];
    static Customer[] preferredCustomer = null;
    static Customer[] new_array = new Customer[10];
    static boolean ifPref = false;


    public static Customer check_id(String id, Customer[] array){
        for(int i = 0; i < array.length; i++){
            if (array[i].getGuestID().equals(id)){  
                return array[i];  
            }
        }
            return  null;
    }
    
    public static Customer[] remove_customer(Customer cus, Customer[] array) {
        Customer[] new_array = new Customer[array.length-1];
         int i, k=0; 
        for( i=0; i < array.length; i++) {
            if(!(cus.equals(array[i]))){
                new_array[k] = array[i];
                k++;
            }
        }
         return new_array;
        }
    
    public static Customer[] put_customer(Customer cus, Customer[] array) {

            if (array == null) {
                Customer[] new_array = new Customer[1];
                new_array[0] = cus;
                return new_array;
            }
            // Check if cus is already in the array
            for (Customer customer : array) {
                if (customer.equals(cus)) {
                    return array; // The customer is already in the array, so return the same array.
                }
            }
            Customer[] new_array = new Customer[array.length + 1];
            System.arraycopy(array, 0, new_array, 0, array.length);
            new_array[array.length] = cus;
            return new_array;
        }
        
    public static float calculate_price(String size, String type, float inch_p, int quantity){
            
            float diameter  = 0.0f;
            float height = 0.0f;
            float price_per_oz = 0;
            switch(size.toLowerCase()) {
                case "s": 
                    price_per_oz = 12; 
                    height = 4.5f;
                    diameter = 4.0f;
                break;
                case "m":
                    price_per_oz = 20;
                    height = 5.75f;
                    diameter = 4.5f;
                break;
                case "l":
                    price_per_oz = 32;
                    height = 7.0f;
                    diameter = 5.5f;
                break;
                default:
                    break;
            }
            //if it returns soda or tea or punch
            switch(type.toLowerCase()){
                case "soda": 
                    price_per_oz *= 0.2f;
                break;
                case "tea":
                    price_per_oz *= 0.12f;
                break;
                case "punch":
                    price_per_oz *= 0.15f;
                break;
            }
            

            float sizePrice = ((float)((diameter * height * Math.PI * inch_p)*100))/100;
            float drinkPrice = sizePrice + price_per_oz;
             drinkPrice *= quantity;
             return drinkPrice;
        } 

    public static Customer[] readArrayIntoCustomerClass( String[] array, int lineCount) throws IOException {
        //getting the array with the new lineCount to not read null 
        Customer[] customers = new Customer[lineCount];
        try{ //breaking the array by each space into the customer class 
            for(int i = 0; i < lineCount; i++) {
                String[] lineBreak = array[i].split(" ");
                String guestId = lineBreak[0];
                String firstName = lineBreak[1];
                String lastName = lineBreak[2];
                String amtSpentString = lineBreak[3]; // Keep the string value
                
                
                float amtSpent = Float.parseFloat(amtSpentString);
        
                //place into customer class
                customers[i] = new Customer(firstName, lastName, guestId, amtSpent);
                
                //if amtSpent read more than 50 and less than 200, place that cusotmer into gold status
                if(amtSpent >= 50 && amtSpent < 200){
                    float prefDiscount = Float.parseFloat(lineBreak[4].replace("%", ""));
                    customers[i] = new Gold(firstName, lastName, guestId, amtSpent, prefDiscount);
                    
                }else if(amtSpent >= 200){
                    //if the amount is spent more than 200 put it into platinum class in order to read the bonusBucks already given before spending more money 
                    float prefBonus = Float.parseFloat(lineBreak[4]);
                    customers[i] = new Platinum(firstName, lastName, guestId, amtSpent, prefBonus);             
                }
            }
        }catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return customers;
    }
    
    public static String[] readFileIntoArray(File inFile, int lineCount) throws IOException {
        String[] tempRegular = new String[lineCount];
        Scanner scnrFile = new Scanner(inFile);
        try {
            //count the amount of lines are in the file and return them 
            for (int i = 0; i < lineCount && scnrFile.hasNextLine(); i++) {
                tempRegular[i] = scnrFile.nextLine();
            }
    
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        scnrFile.close();
        return tempRegular;
    }
    
    public static int countLFile(File inFile)throws IOException{
        
        Scanner scnrFile = new Scanner(inFile);
        String fileline;
        int custCount = 0;//just counting lines in file in order to create exact array size
        while(scnrFile.hasNextLine()){
            fileline = scnrFile.nextLine();
            custCount++;
        }
        scnrFile.close();
        return custCount;
    }

    public static boolean validSize(String size) {

        if (size.equals("S") || size.equals("M") || size.equals("L")){
            return true; 
        }
        else{
            return false; 
        }
    
    }
    
    public static boolean validDrink(String drink) {
            
        if (drink.equals("soda") || drink.equals("punch") || drink.equals("tea")) {
            return true; 
        } else {
            return false; 
        }
    
    }

    public static boolean validSqInPr(float sqInPr) {
        if (sqInPr >= 0) {
            return true;
        } else {
            return false; 
        }
    }

    public static void processOrder(File inFile) throws IOException {
        try {
            Scanner scnrFile = new Scanner(inFile);
            //take in the orders
            while (scnrFile.hasNextLine()) {
                    try{
                        String line = scnrFile.nextLine();
                        String[] lineBreak = line.split(" ");
                        if (lineBreak.length != 5) {
                            continue; // Skip to the next line
                        }
                        //gather the inputs 
                        String guestId = lineBreak[0];
                        String size = lineBreak[1];
                        String drink = lineBreak[2];
                        float sq_In_pr = 0.0f;
                        int quantity = 0;
                
                        try {//make sure the input is an integer
                            sq_In_pr = Float.parseFloat(lineBreak[3]);
                            quantity = Integer.parseInt(lineBreak[4]);
                        } catch (NumberFormatException e) {
                            continue; //skip to the next line
                        }
                    //declared variable for every order being read
                    ifPref = false;//<-- variable for later use in applyClassChanges [used to check if already in preferredArray or regularArray before processing Orders]

                    //validate size, drinks, and square inch per price 
                    if(!validSize(size) || !validDrink(drink) || !validSqInPr(quantity)){
                        continue;
                    }

                    //if the customer is found in regularcustomer place it in customer class
                    Customer foundID = check_id(guestId, regularCustomer);
                    //calculate orderAmt 
                    float orderSpent = calculate_price(size, drink, sq_In_pr, quantity);
                    //if its not in RegularArray, check in preferred, if its in preferred already, make ifPref true
                    if(foundID == null){
                        foundID = check_id(guestId, preferredCustomer);
                        ifPref = true;
                    }
                    //validate if foundID was found even after checking preferred
                    if(foundID == null){
                        continue;
                    }
                    //write the orders into classes 
                    applyClassChanges(orderSpent, foundID);
                    
                    
                }catch(InputMismatchException e){
                    System.out.println("InputMismatchException: " + e.getMessage());
                    e.printStackTrace();
                
                }
            }//end of while loop   
        scnrFile.close();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void applyClassChanges(float orderSpent, Customer foundID){
        float currentAmountSpent = foundID.getAmountSpent();
        //add customers Already spent with order 
        float totalAmtSpent = currentAmountSpent + orderSpent;
        if(totalAmtSpent >= 50){//this is for any preferred customers     
            float discount = getDiscount( totalAmtSpent);//returns -1 if preferred
                
            if(discount != -1){//anything else besides -1 returns gold        
                if(!ifPref){//regular to gold 
                    Gold newCust = new Gold(foundID.firstName, foundID.lastName, foundID.guestID, foundID.amountSpent+((1 - discount/100)*orderSpent), discount);
                    //must remove from regular to preferred
                    regularCustomer = remove_customer(foundID, regularCustomer);
                    preferredCustomer = put_customer(newCust, preferredCustomer);
                    foundID = newCust;
                } else if(ifPref){//if its gold already then reset gold
                    foundID.setAmountSpent(foundID.amountSpent+((1 - discount/100)*orderSpent));
                    ((Gold)foundID).setDiscount(discount);
                }
            } else if(discount == -1){//then its platinum 
                ifPlatinum(discount, currentAmountSpent, foundID, orderSpent);
            }
            
        } else if(totalAmtSpent <= 50){
            foundID.setAmountSpent(totalAmtSpent);
        }
    }

    public static void ifPlatinum(float discount, float currentAmountSpent, Customer foundID, float orderSpent){
        float bonusBucks;
        
        if(ifPref && (currentAmountSpent < 200)){//gold to plat with applied discount
            discount = getDiscount(currentAmountSpent);//
            
            float oldAmtSpent = foundID.amountSpent;
            foundID.setAmountSpent(foundID.amountSpent+((1 - discount/100)*orderSpent));
            if(foundID.getAmountSpent() > 200){//after the discount if its still over 200
                discount = 15f;
                foundID.setAmountSpent(oldAmtSpent+((1 - discount/100)*orderSpent));
                bonusBucks = (float)Math.floor(((foundID.getAmountSpent())- 200)/5);
                Platinum newCust = new Platinum(foundID.firstName, foundID.lastName, foundID.guestID, foundID.amountSpent, bonusBucks);
                //without this then preferred3 file crashes: classCastException, Gold cannot be cast to Platinum 
                //therefore instead of using customer[] remove and put methods, will simply find the location and rewrite at that line
                for (int i = 0; i < preferredCustomer.length; i++) {
                    if (preferredCustomer[i] == foundID) {
                        preferredCustomer[i] = newCust;
                        break; // Found and updated, exit the loop
                    }
                }
                foundID = newCust;
            } 
        }else {//is platinum setting to platinum
                bonusBucks = ((Platinum)foundID).getBonusBucks();
            //if the order amount has enough to use bonusBucks
            if(Math.ceil(orderSpent) >= ((Platinum)foundID).getBonusBucks()){
                orderSpent -= ((Platinum)foundID).getBonusBucks();
                ((Platinum)foundID).setBonusBucks(0);
                bonusBucks = 0;   
                //this fixes piggy do not remove
                for (int i = 0; i < preferredCustomer.length; i++) {
                    if (preferredCustomer[i] == foundID) {
                        preferredCustomer[i] = foundID;
                        break; // Found and updated, exit the loop
                    }
                }  
            //if the order amount is anything else besides enough to use bonusBucks  
            }else {
                bonusBucks -= Math.ceil(orderSpent);
                orderSpent = 0;  
            }
            //gets bonus bucks and removes them from order
            bonusBucks += (float)Math.floor(orderSpent /5);
            //sets with new bonusBucks
            ((Platinum)foundID).setBonusBucks(bonusBucks);
            ((Platinum)foundID).setAmountSpent(currentAmountSpent + orderSpent);
        }
    }

    public static float getDiscount(float totalAmtSpent) {
        //gets the discount if its less than a certain amount 
        float[] spentAmt = {50.0f, 100.0f, 150.0f, 200f};
        float[] discountAmt = {5.0f, 10.0f, 15.0f, -1f};
        float appliedDiscount = 0.0f;
        for(int i = 0; i< spentAmt.length; i++){
            if(totalAmtSpent >= spentAmt[i]){
                appliedDiscount = discountAmt[i];//returns discount, if -1 then its platinum
            } 
        }
        return appliedDiscount;
    }
    
    public static void runFiles(String fileName) throws IOException{

        File inFile = new File(fileName);

        try {
            if(inFile.exists()){
                Scanner scnrFile = new Scanner(inFile);
                int lineCount = countLFile(inFile);

                if(fileName.contains("customer")){//for customer file
                    String[] regular = new String[lineCount];
                    regular = readFileIntoArray(inFile, lineCount);
                    regularCustomer = readArrayIntoCustomerClass(regular, lineCount);

                } else if(fileName.contains("preferred")){//for preferred file
                    String[]preferred = new String[lineCount];
                    preferred = readFileIntoArray(inFile, lineCount);
                    preferredCustomer = readArrayIntoCustomerClass( preferred, lineCount );

                }else if(fileName.contains("order")){//when the input file is the orders
                        processOrder(inFile);
                }
                scnrFile.close();

            }else{
                System.out.println("File is empty");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while opening the file: " + e.getMessage());
        }

    }

    public static void main(String[] args) throws IOException {
        Scanner scnr = new Scanner(System.in);
        
        //opening customer file 
        String custFile = "";
        System.out.print("Enter a customer file name: ");
        custFile = scnr.next();
        runFiles(custFile);
        
        //opening preferred file 
        String prefFile = "" ;
        System.out.print("Enter a preferred file name: ");
        prefFile = scnr.next();
        runFiles(prefFile);
        //opening orderFile
        String orderFile = "";
        System.out.print("Enter order file name: ");
        orderFile = scnr.next();
        runFiles(orderFile);

        //output the files after processing order and assigning customers to classes 
        String outputCust = "customer.dat";
        String outputPref = "preferred.dat";
        //write regular File
        FileWriter outputWriter = new FileWriter(new File(outputCust));
        for (int i = 0; i < regularCustomer.length; i++) {
            if (regularCustomer[i] != null) {
                outputWriter.write("\n" + regularCustomer[i].toString());
            }
        }
        outputWriter.close();
        //write preferred file 
        FileWriter outputer = new FileWriter(new File(outputPref));
        for ( int i = 0; i < preferredCustomer.length; i++) {
            if (preferredCustomer[i] != null) {
                System.out.println(preferredCustomer[i]);
                outputer.write("\n" + preferredCustomer[i].toString());
            }
        }
        outputer.close();
        scnr.close();
    }
}