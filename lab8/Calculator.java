import java.io.*;
import java.util.Stack;

public class Calculator {
    public static void main(String[] args) {
        File file = new File("src/example.txt");

        if (!file.exists()) {
            System.out.println("File not found or unable to open!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                try {
                    System.out.print(line);
                    double result = parseLine(line);
                    System.out.println(result);
                }
                catch(SyntaxErrorException | ZeroDivisionException | IntegerLimitException |
                      UndefinedVariableException | IllegalCharacterException e)
                {
                    System.out.println("Error: " + e.getMessage());
                }
                catch (Exception e)
                {
                    System.out.println("Unexpected error: " + e.getMessage());
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error: Unable to read the file: " + e.getMessage());
        }
    }

    private static double parseLine (String p_line) throws
            SyntaxErrorException, ZeroDivisionException, UndefinedVariableException, IllegalCharacterException,
            IntegerLimitException
    {
        if (!p_line.matches("[0-9+\\-*/=()a-zA-Z\\s]*"))
        {
           throw new IllegalCharacterException("Illegal character.");
        }
        if (!p_line.trim().endsWith("="))
        {
            throw new SyntaxErrorException("Syntax error: '=' expected.");
        }
        if ((p_line.contains("(") && !p_line.contains(")")))
        {
            throw new SyntaxErrorException("Syntax error: ')' missing");
        }
        if ((p_line.contains(")") && !p_line.contains("(")))
        {
            throw new SyntaxErrorException("Syntax error: '(' missing");
        }
        if (p_line.matches(".*[a-zA-Z].*"))
        {
            throw new UndefinedVariableException("Undefined variable.");
        }
        p_line = p_line.replaceAll("=", "");
        p_line = p_line.replaceAll(" ", "");
        double result = calculate(p_line);
        if (result>Integer.MAX_VALUE || result < Integer.MIN_VALUE)
            throw new IntegerLimitException("Integer limit exceeded (>32-bit)");
        return result;
    }

    private static double calculate(String p_line) throws ZeroDivisionException, SyntaxErrorException
    {
           Stack<Double> values = new Stack<>();
           Stack<Character> operators = new Stack<>();
           int i = 0;

           while (i<p_line.length())
           {
               char ch = p_line.charAt(i);
               if (Character.isDigit(ch))
                {
                    StringBuilder sb = new StringBuilder();
                    while (i<p_line.length() && (Character.isDigit(ch) || ch == '.'))
                    {
                        sb.append(ch);
                        i++;
                        if (i<p_line.length())
                            ch = p_line.charAt(i);
                    }
                    values.push(Double.parseDouble(sb.toString()));
                }
               else if (ch == '(')
               {
                   operators.push(ch);
                   i++;
               }
               else if (ch == ')')
               {
                    while (!operators.isEmpty() && operators.peek() != '(')
                    {
                        values.push(applyOperator(operators.pop(),values.pop(),values.pop()));
                    }
                    if (operators.isEmpty() || operators.pop() != '(')
                    {
                        throw new SyntaxErrorException("Syntax error: unmatched parentheses");
                    }
                    i++;
               }
               else if (isOperator(ch))
               {
                   while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch))
                   {
                       values.push(applyOperator(operators.pop(),values.pop(),values.pop()));
                   }
                   operators.push(ch);
                   i++;
               }
               else
                   throw new SyntaxErrorException("Syntax error: unrecognized operator.");
           }
       while (!operators.isEmpty())
       {
           if (operators.peek() == '(' || operators.peek() == ')')
           {
               throw new SyntaxErrorException("Syntax error: unmatched parentheses");
           }
           values.push(applyOperator(operators.pop(),values.pop(),values.pop()));
       }

       if (values.size()!=1)
       {
           throw new SyntaxErrorException("Syntax error: invalid expression");
       }
       return values.pop();
    }

    private static boolean isOperator(char ch)
    {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private static int precedence(char operator)
    {
        if (operator == '+' || operator == '-')
            return 1;
        else if (operator == '*' || operator == '/')
            return 2;
        return -1;
    }

    private static double applyOperator(char operator, double a, double b) throws ZeroDivisionException
    {
        switch (operator)
        {
            case '+': return a+b;
            case '-': return a-b;
            case '*': return a*b;
            case '/': if (b==0) throw new ZeroDivisionException("Division by zero.");
            return a/b;
            default: throw new IllegalStateException("Unexpected operator.");
        }
    }
}
