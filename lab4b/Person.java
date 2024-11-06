import java.util.Scanner;
public class Person {

    private PersonalData data;
    private int number;

    public Person(String p_name, String p_street, int p_number)
    {
        data = new PersonalData(p_name, p_street);
    
        number = p_number;
    }


    public static void main (String[] args)
    {
        final int count = 2;
        Person[] persons = new Person[count];
        Scanner scanner = new Scanner (System.in);

        for (int i = 0; i<count; i++)
        {
            System.out.println ("Enter the " + (i+1) + ". person's name: ");
            String personName = scanner.next();
            System.out.println ("Enter the " + (i+1) + ". person's street: ");
            String personStreet = scanner.next();
            System.out.println ("Enter the " + (i+1) + ". person's number: ");
            int personNumber = scanner.nextInt();
            Person next_person = new Person (personName,personStreet,personNumber);
            persons[i] = next_person;
        }

        Person person_shallow = new Person(persons[0]);//shallow
        Person person_deep = new Person(persons[0],1);//deep
        System.out.println("Before change: ");
        System.out.println("Original first object: ");
        persons[0].printDetails();
        System.out.println("shallow: ");
        person_shallow.printDetails();
        System.out.println("deep: ");
        person_deep.printDetails();
        //changing data in the shallow copy
        person_shallow.data.street = "changed street";
        person_shallow.data.name = "changed name";
        //printing after change"
        System.out.println("After change in shallow copy: ");
        System.out.println("first object: ");
        persons[0].printDetails();
        System.out.println("shallow: ");
        person_shallow.printDetails();
        System.out.println("Deep: ");
        person_deep.printDetails();
        //now changing the deep copy
        person_deep.data.street= "deepchanged";
        person_deep.data.name = "deepchanged";
        System.out.println("After change in the deep copy: ");
        System.out.println("first object: ");
        persons[0].printDetails();
        System.out.println("shallow: ");
        person_shallow.printDetails();
        System.out.println("Deep: ");
        person_deep.printDetails();


    }

    public String getName()
    {
        return data.name;
    }

    public String getStreet()
    {
        return data.street;
    }

    public int getNumber()
    {
        return number;
    }

    public void setName(String p_name)
    {
        data.name = p_name;
    }

    public void setStreet(String p_street)
    {
        data.street = p_street;
    }

    public void setNumber(int p_number)
    {
        number = p_number;
    }

    public Person(Person other)//shallow copy
    {
        data = other.data;
        number = other.number;
    }

    public Person (Person other, int n) //deep copy
    {
        number = other.number;
        data = new PersonalData(new String(other.data.name), new String(other.data.street));

    }

    public void printDetails()
    {
        System.out.println("Name: " + data.name + ", Street: " + data.street + ", Number: " + number);
    }
}
