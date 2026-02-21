package Game;

import java.util.*;

/**
 * 24 点计算器
 */
public class Point24
{
    public static List<String> calc24(int... numbers)
    {
        List<String> expressions = new ArrayList<>();
        for (int num : numbers)
        {
            expressions.add(String.valueOf(num));
        }
        return calc24(expressions.toArray(new String[0]));
    }

    public static List<String> calc24(String... expressions)
    {
        // Sort the expressions to create a key
        String[] sortedExpressions = expressions.clone();
        Arrays.sort(sortedExpressions);

        Set<String> result = new LinkedHashSet<>();
        Map<String, Boolean> hash = new HashMap<>();
        String[] operator = {"+", "-", "*", "/"};

        solve(expressions, result, hash, operator);
        return new ArrayList<>(result);
    }

    private static void solve(String[] expressions, Set<String> result,
                              Map<String, Boolean> hash, String[] operator)
    {
        int len = expressions.length;

        // Create a unique key for this combination
        String[] temp = expressions.clone();
        Arrays.sort(temp);
        String groupStr = Arrays.toString(temp);

        if (!hash.containsKey(groupStr))
        {
            hash.put(groupStr, true);

            if (len > 1)
            {
                for (int i = 0; i < len - 1; i++)
                {
                    for (int j = i + 1; j < len; j++)
                    {
                        // Create a copy of the expression list
                        List<String> expList = new ArrayList<>(Arrays.asList(expressions));

                        // Remove elements at j and i (note: remove larger index first)
                        String exp1 = expList.remove(j);
                        String exp2 = expList.remove(i);

                        for (int n = 0; n < 4; n++)
                        {
                            // Create new expression list
                            List<String> newExpList = new ArrayList<>(expList);

                            // Build expression
                            String newExpr;
                            if (n > 1 || len == 2)
                            {
                                newExpr = exp1 + operator[n] + exp2;
                            }
                            else
                            {
                                newExpr = "(" + exp1 + operator[n] + exp2 + ")";
                            }

                            newExpList.add(0, newExpr);
                            solve(newExpList.toArray(new String[0]), result, hash, operator);

                            // For non-commutative operations, try reverse order
                            if (!exp1.equals(exp2) && n % 2 == 1)
                            {
                                if (n > 1 || len == 2)
                                {
                                    newExpr = exp2 + operator[n] + exp1;
                                }
                                else
                                {
                                    newExpr = "(" + exp2 + operator[n] + exp1 + ")";
                                }

                                newExpList.set(0, newExpr);
                                solve(newExpList.toArray(new String[0]), result, hash, operator);
                            }
                        }
                    }
                }
            }
            else if (len == 1)
            {
                // Evaluate the expression
                try
                {
                    double value = evaluate(expressions[0]);
                    if (Math.abs(value - 24) < 1e-6)
                    {
                        result.add(expressions[0]);
                    }
                } catch (Exception e)
                {
                    // Skip invalid expressions (e.g., division by zero)
                }
            }
        }
    }

    private static double evaluate(String expression)
    {
        // Remove all whitespace
        expression = expression.replaceAll("\\s+", "");

        // Handle parentheses recursively
        while (expression.contains("("))
        {
            int lastOpen = expression.lastIndexOf("(");
            int nextClose = expression.indexOf(")", lastOpen);
            String subExpr = expression.substring(lastOpen + 1, nextClose);
            double subValue = evaluate(subExpr);
            expression = expression.substring(0, lastOpen) + subValue + expression.substring(nextClose + 1);
        }

        // Handle addition and subtraction
        List<Double> numbers = new ArrayList<>();
        List<Character> ops = new ArrayList<>();

        int i = 0;
        int len = expression.length();

        // Parse first number
        StringBuilder numStr = new StringBuilder();
        while (i < len && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.'))
        {
            numStr.append(expression.charAt(i));
            i++;
        }

        if (numStr.length() == 0)
        {
            throw new IllegalArgumentException("Invalid expression");
        }

        numbers.add(Double.parseDouble(numStr.toString()));

        // Parse operators and remaining numbers
        while (i < len)
        {
            char op = expression.charAt(i);
            i++;

            numStr = new StringBuilder();
            while (i < len && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.'))
            {
                numStr.append(expression.charAt(i));
                i++;
            }

            if (numStr.isEmpty()) throw new IllegalArgumentException("Invalid expression");

            double num = Double.parseDouble(numStr.toString());

            // Handle multiplication and division immediately
            if (op == '*' || op == '/')
            {
                double lastNum = numbers.remove(numbers.size() - 1);
                if (op == '*')
                {
                    numbers.add(lastNum * num);
                }
                else
                {
                    if (Math.abs(num) < 1e-10)
                    {
                        throw new ArithmeticException("Division by zero");
                    }
                    numbers.add(lastNum / num);
                }
            }
            else
            {
                numbers.add(num);
                ops.add(op);
            }
        }

        // Handle addition and subtraction from left to right
        double result = numbers.get(0);
        for (int j = 0; j < ops.size(); j++)
        {
            char op = ops.get(j);
            double num = numbers.get(j + 1);
            if (op == '+')
            {
                result += num;
            }
            else if (op == '-')
            {
                result -= num;
            }
        }

        return result;
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int[] list = new int[4];
        for (int i = 0; i < 4; i++) list[i] = sc.nextInt();
        List<String> results = calc24(list[0], list[1], list[2], list[3]);
        System.out.println(results.isEmpty() ? "no" : "yes");
        sc.next();
        for (String expr : results)
        {
            System.out.println(expr + " = 24");
        }
    }
}
