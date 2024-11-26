
public abstract class LibraryItem {
    
    protected int ID;
    protected boolean isBorrowed=false;
    protected int borrowDate;
    protected int borrowerID;
    protected boolean isBorrowerStudent;

    public abstract int daysOverdue(int p_date);
    public abstract boolean isOverdue(int p_date);
    public abstract double computeFine(int p_date);

    public LibraryItem(int p_ID)
    {
        ID=p_ID;
    }

    public void setBorrowDate(int p_date)
    {
        borrowDate=p_date;
        isBorrowed=true;
    }

    public int getBorrowDate()
    {
        return borrowDate;
    }

    public void setBorrower(int p_id, boolean isStudent)
    {
        borrowerID=p_id;
        isBorrowerStudent=isStudent;
    }

    public boolean isStudent()
    {
        return isBorrowerStudent;
    }

    public int getID()
    {
        return ID;
    }

    public boolean isBorrowed()
    {
        return isBorrowed;
    }

    abstract public String print();
}
