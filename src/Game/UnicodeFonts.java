package Game;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * 基于统一码的字体变化可复制
 * */

public class UnicodeFonts
{
    public static void main(String[] args)
    {
        List<Boolean> options = List.of(false, true);
        String[] describe = List.of("无", "是").toArray(new String[0]);
        List<Function<String, String>> change = List.of(s -> s/*, String::toLowerCase, String::toUpperCase*/);

        Scanner sc = new Scanner(System.in);
        while (true)
        {
            String s = sc.nextLine();

            System.out.println("\n普通字体；");
            for (boolean i : options)
            {
                for (boolean j : options)
                {
                    System.out.print(describe[i ? 1 : 0] + "粗体 " + describe[j ? 1 : 0] + "斜体 ");
                    for (Function<String, String> k : change)
                    {
                        System.out.print(toBoldItalic(k.apply(s), i, j));
                        System.out.print(" ");
                    }
                    System.out.println();
                }
            }

            System.out.println("\n无衬线字体：");
            for (boolean i : options)
            {
                for (boolean j : options)
                {
                    System.out.print(describe[i ? 1 : 0] + "粗体 " + describe[j ? 1 : 0] + "斜体 ");
                    for (Function<String, String> k : change)
                    {
                        System.out.print(toSansSerif(k.apply(s), i, j));
                        System.out.print(" ");
                    }
                    System.out.println();
                }
            }

            System.out.println("\n手写体；");
            for (boolean i : options)
            {
                System.out.print(describe[i ? 1 : 0] + "粗体 ");
                for (Function<String, String> k : change)
                {
                    System.out.print(toScript(k.apply(s), i));
                    System.out.print(" ");
                }
                System.out.println();
            }

            System.out.println("\n双线字体：");
            for (Function<String, String> k : change)
            {
                System.out.print(toDoubleStruck(k.apply(s)));
                System.out.print(" ");
            }

            System.out.println("\n\n等宽字体：");
            for (Function<String, String> k : change)
            {
                System.out.print(toMonoSpace(k.apply(s)));
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * 双重大写字体
     */
    public static String toDoubleStruck(String s)
    {
        String[] lower = "𝕒 𝕓 𝕔 𝕕 𝕖 𝕗 𝕘 𝕙 𝕚 𝕛 𝕜 𝕝 𝕞 𝕟 𝕠 𝕡 𝕢 𝕣 𝕤 𝕥 𝕦 𝕧 𝕨 𝕩 𝕪 𝕫".split(" ");
        String[] upper = "𝔸 𝔹 ℂ 𝔻 𝔼 𝔽 𝔾 ℍ 𝕀 𝕁 𝕂 𝕃 𝕄 ℕ 𝕆 ℙ ℚ ℝ 𝕊 𝕋 𝕌 𝕍 𝕎 𝕏 𝕐 ℤ".split(" ");
        String[] number = "𝟘 𝟙 𝟚 𝟛 𝟜 𝟝 𝟞 𝟟 𝟠 𝟡".split(" ");
        return transfer(upper, lower, number, s);
    }

    /**
     * 普通字体加粗斜体
     *
     * @param bold   是否加粗
     * @param italic 是否斜体
     * @return 如果都是false会直接返回
     */
    public static String toBoldItalic(String s, boolean bold, boolean italic)
    {
        String[] lower, upper, number;
        if (bold)
        {
            number = "𝟬 𝟭 𝟮 𝟯 𝟰 𝟱 𝟲 𝟳 𝟴 𝟵".split(" ");
            if (italic)
            {
                upper = "𝑨 𝑩 𝑪 𝑫 𝑬 𝑭 𝑮 𝑯 𝑰 𝑱 𝑲 𝑳 𝑴 𝑵 𝑶 𝑷 𝑸 𝑹 𝑺 𝑻 𝑼 𝑽 𝑾 𝑿 𝒀 𝒁".split(" ");
                lower = "𝒂 𝒃 𝒄 𝒅 𝒆 𝒇 𝒈 𝒉 𝒊 𝒋 𝒌 𝒍 𝒎 𝒏 𝒐 𝒑 𝒒 𝒓 𝒔 𝒕 𝒖 𝒗 𝒘 𝒙 𝒚 𝒛".split(" ");
            }
            else
            {
                upper = "𝐀 𝐁 𝐂 𝐃 𝐄 𝐅 𝐆 𝐇 𝐈 𝐉 𝐊 𝐋 𝐌 𝐍 𝐎 𝐏 𝐐 𝐑 𝐒 𝐓 𝐔 𝐕 𝐖 𝐗 𝐘 𝐙".split(" ");
                lower = "𝐚 𝐛 𝐜 𝐝 𝐞 𝐟 𝐠 𝐡 𝐢 𝐣 𝐤 𝐥 𝐦 𝐧 𝐨 𝐩 𝐪 𝐫 𝐬 𝐭 𝐮 𝐯 𝐰 𝐱 𝐲 𝐳".split(" ");
            }
        }
        else
        {
            if (italic)
            {
                number = null;
                upper = "𝐴 𝐵 𝐶 𝐷 𝐸 𝐹 𝐺 𝐻 𝐼 𝐽 𝐾 𝐿 𝑀 𝑁 𝑂 𝑃 𝑄 𝑅 𝑆 𝑇 𝑈 𝑉 𝑊 𝑋 𝑌 𝑍".split(" ");
                lower = "𝑎 𝑏 𝑐 𝑑 𝑒 𝑓 𝑔 ℎ 𝑖 𝑗 𝑘 𝑙 𝑚 𝑛 𝑜 𝑝 𝑞 𝑟 𝑠 𝑡 𝑢 𝑣 𝑤 𝑥 𝑦 𝑧".split(" ");
            }
            else
            {
                return s;
            }
        }
        return transfer(upper, lower, number, s);
    }

    /**
     * 印刷体
     *
     * @param bold 是否加粗
     */
    public static String toScript(String s, boolean bold)
    {
        String[] lower, upper;
        if (bold)
        {
            upper = "𝓐 𝓑 𝓒 𝓓 𝓔 𝓕 𝓖 𝓗 𝓘 𝓙 𝓚 𝓛 𝓜 𝓝 𝓞 𝓟 𝓠 𝓡 𝓢 𝓣 𝓤 𝓥 𝓦 𝓧 𝓨 𝓩".split(" ");
            lower = "𝓪 𝓫 𝓬 𝓭 𝓮 𝓯 𝓰 𝓱 𝓲 𝓳 𝓴 𝓵 𝓶 𝓷 𝓸 𝓹 𝓺 𝓻 𝓼 𝓽 𝓾 𝓿 𝔀 𝔁 𝔂 𝔃".split(" ");
        }
        else
        {
            upper = "𝒜 ℬ 𝒞 𝒟 ℰ ℱ 𝒢 ℋ ℐ 𝒥 𝒦 ℒ ℳ 𝒩 𝒪 𝒫 𝒬 ℛ 𝒮 𝒯 𝒰 𝒱 𝒲 𝒳 𝒴 𝒵".split(" ");
            lower = "𝒶 𝒷 𝒸 𝒹 ℯ 𝒻 ℊ 𝒽 𝒾 𝒿 𝓀 𝓁 𝓂 𝓃 ℴ 𝓅 𝓆 𝓇 𝓈 𝓉 𝓊 𝓋 𝓌 𝓍 𝓎 𝓏".split(" ");
        }
        return transfer(upper, lower, null, s);
    }

    /**
     * 无衬线字体
     *
     * @param bold   是否加粗
     * @param italic 是否斜体
     */
    public static String toSansSerif(String s, boolean bold, boolean italic)
    {
        String[] lower, upper, number;
        if (bold)
        {
            number = "𝟬 𝟭 𝟮 𝟯 𝟰 𝟱 𝟲 𝟳 𝟴 𝟵".split(" ");
            if (italic)
            {
                upper = "𝘼 𝘽 𝘾 𝘿 𝙀 𝙁 𝙂 𝙃 𝙄 𝙅 𝙆 𝙇 𝙈 𝙉 𝙊 𝙋 𝙌 𝙍 𝙎 𝙏 𝙐 𝙑 𝙒 𝙓 𝙔 𝙕".split(" ");
                lower = "𝙖 𝙗 𝙘 𝙙 𝙚 𝙛 𝙜 𝙝 𝙞 𝙟 𝙠 𝙡 𝙢 𝙣 𝙤 𝙥 𝙦 𝙧 𝙨 𝙩 𝙪 𝙫 𝙬 𝙭 𝙮 𝙯".split(" ");
            }
            else
            {
                upper = "𝗔 𝗕 𝗖 𝗗 𝗘 𝗙 𝗚 𝗛 𝗜 𝗝 𝗞 𝗟 𝗠 𝗡 𝗢 𝗣 𝗤 𝗥 𝗦 𝗧 𝗨 𝗩 𝗪 𝗫 𝗬 𝗭 ".split(" ");
                lower = "𝗮 𝗯 𝗰 𝗱 𝗲 𝗳 𝗴 𝗵 𝗶 𝗷 𝗸 𝗹 𝗺 𝗻 𝗼 𝗽 𝗾 𝗿 𝘀 𝘁 𝘂 𝘃 𝘄 𝘅 𝘆 𝘇 ".split(" ");
            }
        }
        else
        {
            number = "𝟢 𝟣 𝟤 𝟥 𝟦 𝟧 𝟨 𝟩 𝟪 𝟫".split(" ");
            if (italic)
            {
                upper = "𝘈 𝘉 𝘊 𝘋 𝘌 𝘍 𝘎 𝘏 𝘐 𝘑 𝘒 𝘓 𝘔 𝘕 𝘖 𝘗 𝘘 𝘙 𝘚 𝘛 𝘜 𝘝 𝘞 𝘟 𝘠 𝘡".split(" ");
                lower = "𝘢 𝘣 𝘤 𝘥 𝘦 𝘧 𝘨 𝘩 𝘪 𝘫 𝘬 𝘭 𝘮 𝘯 𝘰 𝘱 𝘲 𝘳 𝘴 𝘵 𝘶 𝘷 𝘸 𝘹 𝘺 𝘻 ".split(" ");
            }
            else
            {
                upper = "𝖠 𝖡 𝖢 𝖣 𝖤 𝖥 𝖦 𝖧 𝖨 𝖩 𝖪 𝖫 𝖬 𝖭 𝖮 𝖯 𝖰 𝖱 𝖲 𝖳 𝖴 𝖵 𝖶 𝖷 𝖸 𝖹 ".split(" ");
                lower = "𝖺 𝖻 𝖼 𝖽 𝖾 𝖿 𝗀 𝗁 𝗂 𝗃 𝗄 𝗅 𝗆 𝗇 𝗈 𝗉 𝗊 𝗋 𝗌 𝗍 𝗎 𝗏 𝗐 𝗑 𝗒 𝗓 ".split(" ");
            }
        }
        return transfer(upper, lower, number, s);
    }


    /**
     * 等宽字体
     */
    public static String toMonoSpace(String s)
    {
        String[] upper = "𝙰 𝙱 𝙲 𝙳 𝙴 𝙵 𝙶 𝙷 𝙸 𝙹 𝙺 𝙻 𝙼 𝙽 𝙾 𝙿 𝚀 𝚁 𝚂 𝚃 𝚄 𝚅 𝚆 𝚇 𝚈 𝚉".split(" ");
        String[] lower = "𝚊 𝚋 𝚌 𝚍 𝚎 𝚏 𝚐 𝚑 𝚒 𝚓 𝚔 𝚕 𝚖 𝚗 𝚘 𝚙 𝚚 𝚛 𝚜 𝚝 𝚞 𝚟 𝚠 𝚡 𝚢 𝚣".split(" ");
        String[] number = "𝟶 𝟷 𝟸 𝟹 𝟺 𝟻 𝟼 𝟽 𝟾 𝟿".split(" ");
        return transfer(upper, lower, number, s);
    }

    private static String transfer(String[] upper, String[] lower, String[] number, String s)
    {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < s.length(); i++)
        {
            char ch = s.charAt(i);
            if (ch >= 'A' && ch <= 'Z' && upper != null)
            {
                ans.append(upper[ch - 'A']);
                continue;
            }
            if (ch >= 'a' && ch <= 'z' && lower != null)
            {
                ans.append(lower[ch - 'a']);
                continue;
            }
            if (ch >= '0' && ch <= '9' && number != null)
            {
                ans.append(number[ch - '0']);
                continue;
            }
            ans.append(ch);
        }
        return ans.toString();
    }


}
