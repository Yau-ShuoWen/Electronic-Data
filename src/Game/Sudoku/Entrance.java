package Game.Sudoku;

import java.util.Scanner;

public class Entrance
{
    static String Error = "不是正常的指令";

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("====数独====");
//        System.out.println("""
//                选项；
//                1. 6×6方格
//                2. 9×9方格
//                """);
        Six six = null;

        while (true)
        {
            if (six == null)
            {
                System.out.println("请选择初始化模式\n+ 空\n- 输入");
                char op = sc.next().charAt(0);
                if (op == '+')
                {
                    six = new Six(true);
                    System.out.println(six);
                    six.openSet();
                } else if (op == '-')
                {
                    six = new Six(false);
                    System.out.println(six);
                } else
                {
                    System.out.println("无效重选");
                    continue;
                }
            }
            String str = sc.next();
            switch (str)
            {
                case "change" ->
                {
                    char x = sc.next().charAt(0);
                    if (x == '+') six.openSet();
                    else if (x == '-') six.closeSet();
                }
                case "set" ->
                {
                    while (true)
                    {
                        int i, j, x;
                        try
                        {
                            i = sc.nextInt();
                            j = sc.nextInt();
                            x = sc.nextInt();
                        } catch (Exception e)
                        {
                            sc.next();
                            System.out.println("结束输入");
                            break;
                        }
                        six.setMap(i, j, x);
                    }
                    System.out.println(six);
                }
                case "print" -> System.out.println(six);
                case "exit" -> System.exit(0);
                case "replay" -> six = null;
                default -> System.out.println("不是正常的指令");
            }
        }
    }
}
