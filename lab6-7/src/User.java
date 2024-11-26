import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected int id;
    protected boolean student;
    protected List<LibraryItem> books = new ArrayList<LibraryItem>();
    protected List<LibraryItem> journals = new ArrayList<LibraryItem>();
    protected List<LibraryItem> films = new ArrayList<LibraryItem>();
    
    User(int p_id, boolean p_student)
    {
        id=p_id;
        student=p_student;
    }

    public int getID()
    {
        return id;
    }

    public boolean isStudent()
    {
        return student;
    }

    abstract public double fineSum(int p_date);
    abstract public void addBook(LibraryItem book);
    abstract public void addJournal(LibraryItem journal);
    abstract public void addFilm(LibraryItem film);

    public void printFine(int p_date)
    {
        double fine = fineSum(p_date);
        System.out.println("ID: " + id + ", fine=" + fineSum(p_date));
    }

    public void printBooks(int p_date)
    {
        for (LibraryItem book: books)
        {
            if (book.getBorrowDate() <= p_date) {
                String info = book.print();
                System.out.println(info);
            }
        }
    }
    public void printJournals(int p_date)
    {
        for (LibraryItem journal: journals)
        {
            if (journal.getBorrowDate()<=p_date) {
                String info = journal.print();
                System.out.println(info);
            }
        }
    }
    
    public void printFilms(int p_date)
    {
        for (LibraryItem film: films)
        {
            if (film.getBorrowDate() <= p_date) {
                String info = film.print();
                System.out.println(info);
            }
        }
    }

    public void printAll(int p_date)
    {
        System.out.println("---Printing user ID:" + id + " info for day " + p_date + "---");
        System.out.println("Books:");
        printBooks(p_date);
        System.out.println("Journals:");
        printJournals(p_date);
        System.out.println("Films:");
        printFilms(p_date);
        System.out.println("---Ended printing user ID: " + id + " info for day" + p_date + "---");
    }
}
