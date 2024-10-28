
/**
 * Klasa reprezentujaca element, przechowujaca jego imie, kategorie i cene
 */
public class Item
{
    private String name;
    private double price;
    private int category;

    //konstruktor trojargumentowy
    public Item (String p_name, double p_price, int p_category)
    {
        name = p_name;
        price = p_price;
        category = p_category;
    }

    //metoda zwracajaca cene przedmiotu
    public double getPrice()
    {
        return price;
    }

    //metoda wypisujaca informacje o przedmiocie - wypisujaca kategorie jako nazwe w zaleznosci od wartosci inta (category)
    public void printInfo()
    {
        String categoryString = switch(category)
        {
            case 1 -> "Soccer";
            case 2 -> "Basketball";
            case 3 -> "Tennis";
            case 4 -> "Swimming";
            default -> "Unknown category! :(";
        };
        System.out.println("Name: " + name + ", price: " + price + ", category: " + categoryString);
    }
}