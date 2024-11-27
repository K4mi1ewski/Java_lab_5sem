public final class Journal extends LibraryItem{
    private String eISSN;
    private String Publisher;
    private String Latest_issue;
    private String URL;
    private final double fineValue = 2.0; 
    
    public Journal (
        int p_ID,
        String p_eISSN,
        String p_Publisher,
        String p_LatestIssue,
        String p_URL
        )
        {
            super(p_ID);
            eISSN=p_eISSN;
            Publisher=p_Publisher;
            Latest_issue=p_LatestIssue;
            URL = p_URL;
        }
    
    @Override
    public int daysOverdue(int p_date)
    {
        int maxBorrowedDays = isStudent() ? 3 : 7;
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
        return "Journal: ID: " + ID + ", eISSN: " +eISSN + ", publisher: " + Publisher + ", latest issue: " + Latest_issue + ", URL: " + URL;
    }

}
