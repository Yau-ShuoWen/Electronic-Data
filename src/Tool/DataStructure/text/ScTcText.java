package Tool.DataStructure.dataStructure.text;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import Tool.DataStructure.JavaUtilExtend.StringTool;
import Tool.DataStructure.OrthoCharset;
import Tool.DataStructure.dataStructure.UString;
import Tool.DataStructure.dataStructure.error.IllegalStringException;
import Tool.DataStructure.dataStructure.option.Dialect;
import Tool.DataStructure.dataStructure.option.Language;
import Tool.DataStructure.dataStructure.tuple.Twin;
import lombok.Getter;

import static Tool.DataStructure.ProofreadTool.escapeCharTraslate;

/**
 * 简体繁体对<br>
 * 有的时候没有别的事情，单纯校验一下，并且少写一个三元表达式
 */
@Getter
public class ScTcText
{
    private UString sc;
    private UString tc;

    public ScTcText()
    {
    }

    /**
     * 标准构造方法，由json对象构造而来
     */
    @JsonCreator
    public ScTcText(@JsonProperty ("sc") String sc,
                    @JsonProperty ("tc") String tc)
    {
        StringTool.checkTrimValid(sc, tc);
        this.sc = UString.of(sc);
        this.tc = UString.of(tc);
        if (sc.length() != tc.length())
            throw new IllegalStringException("简体繁体字符串长度不等");
    }

    /**
     * 给出繁体，繁体简体，自动机翻
     */
    public ScTcText(String tc)
    {
        this.tc = UString.of(tc);
        this.sc = escapeCharTraslate(this.tc, Language.TC, new OrthoCharset());
    }

    /**
     * 给出繁体，繁体简体，通过方言判断翻译特点
     */
    public ScTcText(String tc, Dialect d)
    {
        this.tc = UString.of(tc);
        this.sc = escapeCharTraslate(this.tc, Language.TC, new OrthoCharset(d));
    }

    public UString get(Language l)
    {
        return l.isSimplified() ? sc : tc;
    }

    public Twin<UString> getTwin()
    {
        return Twin.of(sc, tc);
    }

    /**
     * 对一个简单的字符串做机翻
     */
    public static UString get(String tc, Language l)
    {
        return l.isSimplified() ?
                escapeCharTraslate(UString.of(tc), Language.TC, new OrthoCharset()) :
                UString.of(tc);
    }

    public static UString get(String tc, Dialect d, Language l)
    {
        return l.isSimplified() ?
                escapeCharTraslate(UString.of(tc), Language.TC, new OrthoCharset(d)) :
                UString.of(tc);
    }

    public int length()
    {
        return sc.length();
    }
}
