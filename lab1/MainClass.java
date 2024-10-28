public class MainClass {

    public MainClass() {
        System.out.println("Main Class Constructor");
    }

    public void method1() {
        System.out.println("Main class method1");
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();

        mainClass.method1();

        Class1 class1obj = new Class1();
        class1obj.method1();
    }
}