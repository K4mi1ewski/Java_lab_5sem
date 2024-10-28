import java.util.ArrayList;

public class Store // klasa przechowujaca liste klientow
{
    private ArrayList<Client> clients; // lista klientow

    public Store()
    {
        clients = new ArrayList<Client>();
    }

    public static void main(String[] Args)
    {
        System.out.println("Wywolano maina klasy Store!");
        Client cl1 = new Client();
        Client cl2 = new Client("Client Two");
        Client cl3 = new Client ("Client Three");

        Store clientStore = new Store();
        
        System.out.println("Dane pierwszego klienta: imie = " + cl1.getName() + 
                           ", Id = " + cl1.getID());
        
        clientStore.add(cl1); clientStore.add(cl2); clientStore.add(cl3);
        clientStore.print();

        for (int i = 0; i<=4;i++)
        {
            int ID_to_find = i;
            Client client_temp = clientStore.find(ID_to_find);
            
            System.out.println("Wyszukiwanie klienta o ID="+ID_to_find);
            if (client_temp!=null)
            {
                System.out.println("Imie klienta z ID="+ID_to_find+" to "+client_temp.getName());
            }
            else
            {
                System.out.println("Nie udalo sie znalezc klienta o ID="+ID_to_find);
            }
        }
    }

    public void add (Client aClient) //metoda dodajaca klienta do listy
    {
        clients.add(aClient);
    }

    public void print() //metoda wypisujaca dane wszystkich klientow w liscie
    {
        System.out.println("Dane wszystkich klientow w liscie: ");

        for (Client cl: clients)
        {
            cl.print();
        }
    }
    
    Client find(int ID) //metoda znajdujaca klienta o podanym ID w liscie
    {
        for (Client cl: clients)
        {
            int client_id = cl.getID();
            if (client_id == ID)
            {
                return cl;
            }
        }

        return null;
    }
}