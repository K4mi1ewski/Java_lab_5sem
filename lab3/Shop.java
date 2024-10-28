import java.util.ArrayList;
import java.util.Scanner;

/**
 * Klasa reprezentujaca sklep
 */
public class Shop
{   
    //lista przedmiotow w sklepie
    private ArrayList<Item> items;
    {
        items = new ArrayList<Item>();
    }
    public static void main(String[] args)
    {
        
        Shop sportShop = new Shop();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the amount of items: ");
        int itemCount = scanner.nextInt();

        for (int i = 0; i<itemCount; i++){
            System.out.println("Enter the item name: ");
            //scanner.nextInt();
            String itemName = scanner.next();
              
            System.out.println("Enter the category(1-4): ");
            System.out.println("1. Soccer");
            System.out.println("2. Basketball");
            System.out.println("3. Tennis");
            System.out.println("4. Swimming");

            int category = scanner.nextInt();

            String message = switch (category){
                case 1 -> "Kicking it into gear!";
                case 2 -> "Nothing but net!";
                case 3 -> "Game, set, match!";
                case 4 -> "Dive into excellence!";
                default -> "Unknown category! :(";
            };
            System.out.println(message);
            System.out.println("Enter the price: ");
            double price = scanner.nextDouble();
            
            Item nextItem = new Item (itemName, price, category);
            sportShop.add(nextItem);
        }
        scanner.close();
        sportShop.printItems();
        double totalPrice = sportShop.sumPrice();
        System.out.println("Total price of all items: " + totalPrice);

        if (totalPrice > 200.0)
        {
            System.out.println("You receive 15% discount!");
            totalPrice = totalPrice * 0.85;
            System.out.println("Total price after discount: " + totalPrice);
        }
        else if (totalPrice > 100.0)
        {
            System.out.println("You receive 10% discount!");
            totalPrice = totalPrice*0.9;
            System.out.println("Total price after discount: " + totalPrice);
        }
        
    }


    //metoda dodajaca przedmiot to listy
    public void add (Item p_item)
    {
        items.add(p_item);
    }

    //metoda obliczajaca calkowita cene wszystkich przedmiotow na liscie
    public double sumPrice()
    {
        double sum = 0;
        for (Item l_item: items)
        {
            sum+=l_item.getPrice();
        }
        return sum;
    }

    //metoda wypisujaca informacje o wszystkich przedmiotach z listy
    public void printItems()
    {
        System.out.println("The list of all items: ");
        for (Item l_item: items)
        {
            l_item.printInfo();
        }
    }
}