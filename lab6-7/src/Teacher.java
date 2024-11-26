public final class Teacher extends User {
    Teacher(int p_id)
    {
        super(p_id, false);
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
        books.add(book);
    }

    @Override 
    public void addJournal(LibraryItem journal)
    {
        journals.add(journal);
    }

    @Override
    public void addFilm(LibraryItem film)
    {
     films.add(film);
    }
}
