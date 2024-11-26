import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryItemTest {

    private Library lib;

    @BeforeEach
    void SetUp() {
        lib = new Library();
        lib.LoadBooksFromFile("test/booksTest.csv"); //ID 0-29 (30)
        lib.LoadJournalsFromFile("test/jlistTest.csv"); //ID 30-58 (29)
        lib.LoadFilmsFromFile("test/moviesTest.csv"); //ID 59-88 (30)

        Student student1 = new Student(0);
        Student student2 = new Student (1);
        Student student3 = new Student(2);

        Teacher teacher1 = new Teacher(3);
        Teacher teacher2 = new Teacher(4);
        Teacher teacher3 = new Teacher(5);
        //0,1,2 - studenci
        //3,4,5 - nauczyciele
        lib.addUser(student1);
        lib.addUser(student2);
        lib.addUser(student3);
        lib.addUser(teacher1);
        lib.addUser(teacher2);
        lib.addUser(teacher3);
    }

    @org.junit.jupiter.api.Test
    void daysOverdue_Journal_student() {
        int borrowdate = 10;
        lib.borrowItem(0,40,borrowdate);
        int currentdate = 15;
        int expectedResult = currentdate - borrowdate - 3;
        assertEquals(expectedResult, lib.getItem(40).daysOverdue(currentdate));
    }

    @org.junit.jupiter.api.Test
    void daysOverdue_Journal_teacher() {
        int borrowdate = 50;
        lib.borrowItem(4, 45, borrowdate);
        int currentdate = 65;
        int expectedResult = currentdate - borrowdate - 7;
        assertEquals(expectedResult, lib.getItem(45).daysOverdue(currentdate));
    }

    @org.junit.jupiter.api.Test
    void daysOverdue_Journal_notLate() {
        int borrowdate1 = 75;
        int borrowdate2 = 70;
        lib.borrowItem(0,33,borrowdate1);
        lib.borrowItem(4, 35, borrowdate2);
        int currentdate = 76;
        int expectedResult1 = currentdate - borrowdate1 - 3;
        int expectedResult2 = currentdate - borrowdate2 - 7;
        assertEquals(expectedResult1, lib.getItem(33).daysOverdue(currentdate));
        assertEquals(expectedResult2, lib.getItem(35).daysOverdue(currentdate));
    }

    @org.junit.jupiter.api.Test
    void daysOverdue_Film() {
        int borrowdate = 69;
        lib.borrowItem(1,69,borrowdate);
        int currentdate = 73;
        int expectedResult = currentdate - borrowdate - 2;
        assertEquals(expectedResult, lib.getItem(69).daysOverdue(currentdate));
    }

    @org.junit.jupiter.api.Test
    void daysOverdue_Book() {
        int borrowdate = 127;
        lib.borrowItem(2,4,borrowdate);
        int currentdate = 147;
        int expectedResult = currentdate - borrowdate - 14;
        assertEquals(expectedResult, lib.getItem(4).daysOverdue(currentdate));
    }

    @org.junit.jupiter.api.Test
    void isOverdue_Journal() //true
    {
        int borrowdate = 222;
        lib.borrowItem(2,55,borrowdate);//student
        lib.borrowItem(3, 54,borrowdate);//teacher
        int currentdate = 232;
        assertTrue(lib.getItem(54).isOverdue(currentdate));
        assertTrue(lib.getItem(55).isOverdue(currentdate));
        int anotherDate = 226;
        assertFalse(lib.getItem(54).isOverdue(anotherDate));
        assertTrue(lib.getItem(55).isOverdue(anotherDate));
        int yetAnotherDate = 223;
        assertFalse(lib.getItem(54).isOverdue(yetAnotherDate));
        assertFalse(lib.getItem(55).isOverdue(yetAnotherDate));
    }

    @org.junit.jupiter.api.Test
    void isOverdue_Film()
    {
        int borrowdate = 150;
        lib.borrowItem(3,80,borrowdate);
        int currentDate = 154;
        assertTrue(lib.getItem(80).isOverdue(currentDate));
        int notLateDate = 151;
        assertFalse(lib.getItem(80).isOverdue(notLateDate));
    }

    @org.junit.jupiter.api.Test
    void isOverdue_Book()
    {
        int borrowdate = 190;
        lib.borrowItem(4,25,borrowdate);
        int currentDate = 205;
        assertTrue(lib.getItem(25).isOverdue(currentDate));
        int notLateDate = 199;
        assertFalse(lib.getItem(25).isOverdue(notLateDate));
    }

    @org.junit.jupiter.api.Test
    void computeFine_Journal() {
        int borrowdate = 17;
        lib.borrowItem(1,32,borrowdate);
        int currentDate = 30;
        LibraryItem item = lib.getItem(32);
        double expectedResult =  (currentDate - borrowdate - 3) * 2.0; //30-17-3>0
        assertEquals(expectedResult, item.computeFine(currentDate));

        int borrowdate2 = 19;
        lib.borrowItem(5,31,borrowdate2);
        int anotherDate = 36;
        LibraryItem item2 = lib.getItem(31);
        double expectedResult2 = (anotherDate - borrowdate2 - 7) * 2.0; // 36-19-7 > 0
        assertEquals(expectedResult2, item2.computeFine(anotherDate));
    }

    @org.junit.jupiter.api.Test
    void computeFine_Book() {
        int borrowdate = 269;
        lib.borrowItem(1,28,borrowdate);
        int currentDate =  289;
        LibraryItem item = lib.getItem(28);
        double expectedResult = (currentDate-borrowdate-14)*0.5;
        assertEquals(expectedResult, item.computeFine(currentDate));
    }

    @org.junit.jupiter.api.Test
    void computeFine_Film() {
        int borrowdate = 299;
        lib.borrowItem(3,77,borrowdate);
        int currentDate =  309;
        LibraryItem item = lib.getItem(77);
        double expectedResult = (currentDate-borrowdate-2)*5.0;
        assertEquals(expectedResult, item.computeFine(currentDate));
    }
}