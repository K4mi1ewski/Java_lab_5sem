public final class Film extends LibraryItem {
    private String genre;
    private String title;
    private String director;
    private int year;
    private int runtime;
    private double rating;
    private final int maxBorrowedDays = 2;
    private final double fineValue = 5.0; 
    public Film (
        int p_ID,
        String p_genre,
        String p_title,
        String p_director,
        int p_year,
        int p_runtime,
        double p_rating)
        {
            super(p_ID);
            genre=p_genre;
            title=p_title;
            director=p_director;
            year=p_year;
            runtime=p_runtime;
            rating=p_rating;
        }
    
    @Override
    public int daysOverdue(int p_date)
    {
        if (isBorrowed())
        {
            return p_date-borrowDate-maxBorrowedDays;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public boolean isOverdue(int p_date)
    {
        return daysOverdue(p_date)>0;
    }

    @Override
    public double computeFine(int p_date)
    {
        int daysOverdue = daysOverdue(p_date);
        if (isOverdue(p_date))
        {
            return daysOverdue * fineValue;
        }
        else
        {
            return 0.0;
        }
    }

    @Override
    public String print()
    {
        return "Journal: ID: " + ID + ", genre: " +genre+ ", title: " + title + ", director: " + director + ", year: " + year+", runtime (minutes): "+runtime + ", rating: " + rating;
    }
}
