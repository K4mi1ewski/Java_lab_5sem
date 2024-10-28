public class Main2 {
    
    static double G = -9.81;

    public static double calculate(double t, double x0, double v0)
    {
        return 0.5*G*t*t + v0*t + x0;
    }
    public static void main(String[] args){
        double x = 257; //metry
        double v = 63 / 3.6; //km/h * 1000/3600 w celu konwersji na m/s
        double t = 2.2; // sekundy

        double result = calculate(t,x,v);

        System.out.println("Result: "+result);
    }
}
