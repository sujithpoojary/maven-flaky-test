package com.example.calculator;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        // Zero args: show demo & usage
        if (args.length == 0) {
            System.out.println("Calculator demo:");
            System.out.println("1 + 2 = " + calculator.add(1, 2));
            System.out.println("10 - 4 = " + calculator.subtract(10, 4));
            System.out.println("7 * 6 = " + calculator.multiply(7, 6));
            System.out.println("8 / 2 = " + calculator.divide(8, 2));
            System.out.println("Usage: java -jar maven-flaky-test-1.0-SNAPSHOT.jar <a> <op> <b>");
            System.out.println("Operators: + - * /");
            return;
        }

        // Exactly 3 args: treat as a op b where op is +,-,*,/
        if (args.length == 3) {
            try {
                int a = Integer.parseInt(args[0]);
                String op = args[1];
                int b = Integer.parseInt(args[2]);
                int result;
                switch (op) {
                    case "+": result = calculator.add(a, b); break;
                    case "-": result = calculator.subtract(a, b); break;
                    case "*": result = calculator.multiply(a, b); break;
                    case "/": result = calculator.divide(a, b); break;
                    default: throw new IllegalArgumentException("Unknown operator: " + op);
                }
                System.out.println(a + " " + op + " " + b + " = " + result);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            return;
        }

        // Invalid count (1,2, >3) -> error message to stderr
        System.err.println("Error: expected 3 arguments (<a> <op> <b>). Got " + args.length);
    }
}
