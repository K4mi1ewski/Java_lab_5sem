import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public final class Library implements ILibrary
{
    private List<LibraryItem> items = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private final double maxFine = 100.0;

    public void addUser(User p_user)
    {
        users.add(p_user);
    }

    public void LoadBooksFromFile(String path_books)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(path_books)))
        {
            String line;
            line = br.readLine();
            while ((line=br.readLine())!=null)
            {
                String[] values = line.split(";");
                String bookTitle = values[0];
                String bookAuthor = values[1];
                String bookGenre = values[2];
                String bookPublisher = "";
                if (values.length==4){
                    bookPublisher = values[3];
                }
                else
                {
                    bookPublisher = values[4];
                }
                int id = items.size();
                items.add(new Book (id,bookTitle,bookAuthor,bookGenre,bookPublisher));
            }
        }
        catch (IOException e)
        {
            System.out.println("Error! Can't read file: " + e.getMessage());
        }
    }

    public void LoadJournalsFromFile(String path_journals)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(path_journals)))
        {
            String line;
            line = br.readLine();
            while ((line=br.readLine())!=null)
            {
                String[] values = line.split(";");
                String journaleISSN = values[3];
                String journalPublisher = values[4];
                String journalLatestIssue = values[6];
                String journalURL = values[12];
                int id = items.size()+1;
                items.add(new Journal (id,journaleISSN, journalPublisher, journalLatestIssue, journalURL));
            }
        }
        catch (IOException e)
        {
            System.out.println("Error! Can't read file: " + e.getMessage());
        }
    }

    public void LoadFilmsFromFile(String path_films)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(path_films)))
        {
            String line;
            line = br.readLine();
            while ((line=br.readLine())!=null)
            {
                String[] values = line.split(";");
                String filmGenre = values[2];
                String filmTitle = values[1];
                String filmDirector = values[4];
                int filmYear = Integer.parseInt(values[6]);
                int filmRuntime = Integer.parseInt(values[7]);
                double filmRating = Double.parseDouble(values[8]);
                int id = items.size()+1;

                items.add(new Film (id,filmGenre,filmTitle,filmDirector,filmYear,filmRuntime,filmRating));
            }
        }
        catch (IOException e)
        {
            System.out.println("Error! Can't read file: " + e.getMessage());
        }
    }

    public void borrowItem(int user_id, int item_id, int p_date)
    {
        if (user_id < 0 || user_id >= users.size() || item_id < 0 || item_id >= items.size())
        {
            System.out.println("Error! Can't borrow an item! item ID = " + item_id + ", user ID=" + user_id);
            return;
        }
        User l_user = users.get(user_id);
        LibraryItem l_item = items.get(item_id);
        if (!checkFine(p_date,l_user))
        {
            System.out.println("Error! User ID = " + user_id + " can't borrow an item with ID=" + item_id + " - too big fine!");
            return;
        }

        if(l_item.isBorrowed())
        {
           System.out.println("Error! User ID = " +user_id +" can't borrow an item with ID = " + item_id + "- already borrowed!");
           return;
        }

        l_item.setBorrowDate(p_date);
        l_item.setBorrower(l_user.getID(), l_user.isStudent());

        switch(l_item)
        {
            case Book b -> l_user.addBook(l_item);
            case Journal j -> l_user.addJournal(l_item);
            case Film f -> l_user.addFilm(l_item);
            default -> System.out.println("Error! Can't borrow an item");
        }

    }

    private boolean checkFine(int p_date, User p_user)
    {
        return (p_user.fineSum(p_date) < maxFine);
    }

    public void printOverdueUsers(int p_date)
    {
        System.out.println("Overdue borrowers for day " + p_date + ":");
        for (User user: users)
        {
            if (user.fineSum(p_date)>0)
            {
                System.out.println("User ID: " + user.getID());
            }
        }
    }

    public void printEveryUserInfo(int p_date)
    {
        System.out.println("Printing every user info: ");
        for (User user: users)
        {
            user.printAll(p_date);
        }
    }

    public LibraryItem getItem (int id)
    {
        return items.get(id);
    }

    @Override
    public List<LibraryItem> getItems()
    {
        return items;
    }


    public Initializer init(String path_books, String path_journals, String path_films)
    {
        //klasa anonimowa wewnętrzna
        return new Initializer()
        {
            @Override
            public void initialize()
            {
                LoadBooksFromFile(path_books);
                LoadJournalsFromFile(path_journals);
                LoadFilmsFromFile(path_films);
            }
        };
    }

    public static void main(String[] args)
    {
        Library lib = new Library();
        Initializer initializer = lib.init("src/books.csv", "src/jlist.csv","src/movies.csv");
        initializer.initialize();

        Student A = new Student(0);
        Student B = new Student(1);
        Teacher C = new Teacher(2);
        Teacher D = new Teacher(3);
        Teacher E = new Teacher(4);
        lib.addUser(A);
        lib.addUser(B);
        lib.addUser(C);
        lib.addUser(D);
        lib.addUser(E);

        lib.borrowItem(0,30,15); //ksiazka
        lib.borrowItem(0,3900,20);//czasopismo
        lib.borrowItem(0,4102,221);//czasopismo

        lib.borrowItem(3,3,330);//ksiazka
        lib.borrowItem(3,2525,166);//czasopismo
        lib.borrowItem(3,5210,87);//film

        lib.printOverdueUsers(40);
        lib.printOverdueUsers(350);

        lib.printEveryUserInfo(40);
        lib.printEveryUserInfo(350);

        A.printFine(10);
        A.printFine(40);
        A.printFine(300);
        D.printFine(20);
        D.printFine(200);

        lib.displayLibraryItemsCount();
    }
}