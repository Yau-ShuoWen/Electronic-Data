package Tool.Code;


import java.util.Scanner;

public class test
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine();

        System.out.println(s
                .replace("ph", "f")
                .replace("th", "ŧ")
                .replace("kh", "ħ")
                .replace("ng", "ŋ")
                .replace("ngh", "ŋ")
                .replace("nh", "ñ")
                .replace("gi", "ż")
                .replace("g", "ǥ")
                .replace("gh", "ǥ")
                .replace("tr", "ĉ")
                .replace("ch", "ċ")
                .replace("uy", "ÿ")


        );

        main(args);
    }
}

