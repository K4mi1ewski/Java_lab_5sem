public final class Student extends User {
    public final int bookLimit = 3;
    public final int journalLimit = 3;
    public final int filmLimit = 1;

    Student(int p_id)
    {
        super(p_id, true);
    }

    @Override
    public double fineSum(int p_date)
    {
        double sum = 0;
        for (LibraryItem book: books)
        {
            sum+=book.computeFine(p_date);
        }
        for (LibraryItem journal: journals)
        {
            sum += journal.computeFine(p_date);
        }
        for (LibraryItem film: films)
        {
            sum+=film.computeFine(p_date);
        }
        return sum;
    }

    @Override
    public void addBook(LibraryItem book)
    {
        if (books.size()<bookLimit)
        {
            books.add(book);
        }
        else {
            System.out.println("Can't add book for student ID=" + id + ", limit exceeded");
        }
    }

    @Override 
    public void addJournal(LibraryItem journal)
    {
        if (journals.size()<journalLimit)
        {
            journals.add(journal);
        }
        else {
            System.out.println("Can't add journal for student ID=" + id + ", limit exceeded");
        }
    }

    @Override
    public void addFilm(LibraryItem film)
    {
        if (films.size()<filmLimit) {
            films.add(film);
        }
        else {
            System.out.println("Can't add film for student ID=" + id + ", limit exceeded");
        }
    }
}
