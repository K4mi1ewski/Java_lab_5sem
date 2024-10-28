
public class Client //klasa przedstawiajaca klienta
{
    private int clientId; //ID klienta
    private String clientName; //Imie klienta
    public static int count = 0; //statyczny licznik klientow
    
    {
        clientId = ++count;
        System.out.println("Wywolano blok inicjalizacyjny!");
    } // blok inicjalizacyjny

    public Client()
    {
        clientName = "empty";
        System.out.println("Wywolano konstruktor bezargumentowy klasy Client!");
    }//konstruktor bezargumentowy

    public Client(String name)
    {
        clientName = name;
        System.out.println("Wywolano konstruktor jednoargumentowy klasy Client!");
    }//konstruktor argumentowy

    public String getName()
    {
        return clientName;
    }//publiczna metoda zwracajaca imie klienta

    public int getID()
    {
        return clientId;
    }//publiczna metoda zwracajaca ID klienta

    public void print()
    {
        System.out.println("Klient: " + clientName + ", ID: " + clientId);
    }//publiczna metoda wypisujaca dane o kliencie

    public static int countClients()
    {
        return count;
    }//publiczna metoda statyczna zwracajaca licznik klientow

    public static void main(String[] Args)
    {
        System.out.println("Wywolano maina klasy Client!");
        Client client_1 = new Client();
        Client client_2 = new Client("Client_2");
        Client client_3 = new Client ("Client_3");
    }

    
}