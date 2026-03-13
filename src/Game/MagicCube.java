package Game;

import java.util.*;

public class MagicCube
{
// RU2 R'U'RUR'U' RU'R'
    //[上 上旋 下][右 上 左 下][右 上 右 下]
    //3

// RU2 R2U' R2U' R2U2R
    //[上 上旋][右旋 右 右旋 右 右旋][上旋 上]


    //U 左 U'右 U2上旋
    //R 上 R'下 R2右旋
    //F 顺 F'逆 F2前旋
    //L 顶 L'底 L2左旋
    //D 下面右 D'下面左 D2下旋
    //B 背面顺 B'背面逆 B2背面旋

    //x 魔方上翻 x'魔方下翻 x2 纸筒旋转
    //y 魔方左翻 y'魔方右翻 y2 地球旋转
    //z 魔方顺翻 z'魔法逆翻 z2 时钟旋转

    //u 双左 u'双右 u2 双上旋
    //r 双上 r'双下 r2 双右旋
    //f 双顺 f'双逆 f2 双前旋
    //l 双顶 l'双底 l2 双左旋
    //d 下面双右 d'下面双左 d2 双下旋
    //b 背面双顺 b'背面双逆 b2 双背面旋

    //E 中层右 E' 中层左 E2 中层双旋
    //M 中层下 M' 中层上 M2 中层双上
    //S 中层顺 S' 中层逆 S2 中层旋

    static Map<String, String> map = new HashMap<>();

    static {
        // U
        map.put("U", "左");
        map.put("U'", "右");
        map.put("U2", "上旋");

        // R
        map.put("R", "上");
        map.put("R'", "下");
        map.put("R2", "右旋");

        // F
        map.put("F", "顺");
        map.put("F'", "逆");
        map.put("F2", "前旋");

        // L
        map.put("L", "顶");
        map.put("L'", "底");
        map.put("L2", "左旋");

        // D
        map.put("D", "下面右");
        map.put("D'", "下面左");
        map.put("D2", "下旋");

        // B
        map.put("B", "背面顺");
        map.put("B'", "背面逆");
        map.put("B2", "背面旋");

        // x
        map.put("x", "魔方上翻");
        map.put("x'", "魔方下翻");
        map.put("x2", "纸筒旋转");

        // y
        map.put("y", "魔方左翻");
        map.put("y'", "魔方右翻");
        map.put("y2", "地球旋转");

        // z
        map.put("z", "魔方顺翻");
        map.put("z'", "魔方逆翻");
        map.put("z2", "时钟旋转");

        // u
        map.put("u", "双左");
        map.put("u'", "双右");
        map.put("u2", "双上旋");

        // r
        map.put("r", "双上");
        map.put("r'", "双下");
        map.put("r2", "双右旋");

        // f
        map.put("f", "双顺");
        map.put("f'", "双逆");
        map.put("f2", "双前旋");

        // l
        map.put("l", "双顶");
        map.put("l'", "双底");
        map.put("l2", "双左旋");

        // d
        map.put("d", "下面双右");
        map.put("d'", "下面双左");
        map.put("d2", "双下旋");

        // b
        map.put("b", "背面双顺");
        map.put("b'", "背面双逆");
        map.put("b2", "双背面旋");

        // E
        map.put("E", "中层右");
        map.put("E'", "中层左");
        map.put("E2", "中层双旋");

        // M
        map.put("M", "中层下");
        map.put("M'", "中层上");
        map.put("M2", "中层双上");

        // S
        map.put("S", "中层顺");
        map.put("S'", "中层逆");
        map.put("S2", "中层旋");
    }

    public static String translate(String input) {

        input = input.replaceAll("\\s+", "");
        List<String> result = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {

            char c = input.charAt(i);
            String key = String.valueOf(c);

            if (i + 1 < input.length()) {
                char next = input.charAt(i + 1);

                if (next == '\'' || next == '2') {
                    key += next;
                    i++;
                }
            }

            result.add(map.getOrDefault(key, key));
        }

        return String.join(" ", result);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("输入公式:");

        String input = sc.nextLine();

        String output = translate(input);

        System.out.println("输出:");
        System.out.println(output);
    }
}
