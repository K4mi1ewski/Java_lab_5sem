import java.util.ArrayList;
import java.util.List;

public interface ILibrary {
    default List<LibraryItem> getItems()
    {
        return new ArrayList<>();
    }

    default void displayLibraryItemsCount()
    {
        List<LibraryItem> Items = getItems();
        int booksCounter = 0;
        int journalsCounter = 0;
        int filmsCounter = 0;

        for (LibraryItem item: Items)
        {
            switch(item){
                case Book b -> booksCounter++;
                case Journal j -> journalsCounter++;
                case Film f -> filmsCounter++;
                default -> System.out.println("Error in counting the items in the library!");
            }
        }
        System.out.println("Printing the amount of items in the library:");
        System.out.println("Books: " + booksCounter);
        System.out.println("Journals: " + journalsCounter);
        System.out.println("Films: " + filmsCounter);
    }
}
