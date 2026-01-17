import Tool.DataStructure.tuple.Pair;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class Indexing
{
    private static BigDecimal fromString(String s)
    {
        if (!s.matches("\\d+" +
                "" +
                "")) throw new NumberFormatException("字符串不是纯数字");

        BigDecimal d = new BigDecimal("0." + s);
        if (d.equals(BigDecimal.ZERO)) throw new IllegalArgumentException("zero");
        else return d;
    }

    private static String toString(BigDecimal d)
    {
        return d.stripTrailingZeros().
                toPlainString().
                substring(2);  // 去除"0."
    }

    public static Pair<String, String> endPoint()
    {
        return Pair.of(
                toString(BigDecimal.valueOf(0.1)),
                toString(BigDecimal.valueOf(0.9))
        );
    }

    public static String midPoint(String left, String right)
    {
        BigDecimal l = fromString(left);
        BigDecimal r = fromString(right);
        if (l.compareTo(r) >= 0) throw new IllegalArgumentException();

        var ans = l.add(r).divide(BigDecimal.valueOf(2.0), RoundingMode.UNNECESSARY);

        return toString(ans);
    }

    public static void main(String[] args)
    {
        System.out.println(fromString("15"));
    }
}
