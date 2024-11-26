public class Book extends LibraryItem {
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private final double fineValue = 0.5; 
    private final int maxBorrowedDays = 14;
    public Book (
        int p_ID,
        String p_title,
        String p_author,
        String p_genre,
        String p_publisher
        )
        {
            super(p_ID);
            title=p_title;
            author=p_author;
            genre=p_genre;
            publisher = p_publisher;
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
        return "Book: ID: " + ID + ", Title: " + title + ", Author: " + author + ", Genre: " + genre + ", publisher: " + publisher;
    }
}
