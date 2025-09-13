import java.util.ArrayList;
import java.util.Scanner;

public class restaurantorder {
    public static void main(String[] args) {

        Scanner inputScanner = new Scanner(System.in); 
        ArrayList<String> order = new ArrayList<>();

        System.out.println("Welcome to the Local Restaurant! What's would you like to add to your order?:");
        System.out.println("1. Burger" + " " + "2.Fries" + " " + "3. Hotdog" + " " + "4.Salad");
        
        String confirmOrder = "";
        do { 
            String addOrder = inputScanner.nextLine();
            if(addOrder.equals("burger")){
                order.add("Burger");
            }
            if(addOrder.equals("fries")){
                order.add("Fries");
            }
            if(addOrder.equals("hotdog")){
                order.add("Hotdog");
            }
            if(addOrder.equals("salad")){
                order.add("Salad");
            }
            System.out.println("Do you wish to add more orders?:");
            confirmOrder = inputScanner.nextLine();

            if(confirmOrder.equals("no")){
                break;
            }
            
        } while (!confirmOrder.equals("no"));


        System.out.println("Your order:");
        for (String item : order) {
            System.out.println(item);
        }
    
    }
}
