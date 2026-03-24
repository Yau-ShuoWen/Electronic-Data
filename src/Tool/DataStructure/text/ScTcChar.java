package Tool.DataStructure.dataStructure.text;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import Tool.DataStructure.dataStructure.UChar;
import Tool.DataStructure.dataStructure.option.Language;
import lombok.Getter;

@Getter
public class ScTcChar
{
    private final UChar sc;
    private final UChar tc;

    @JsonCreator
    public ScTcChar(@JsonProperty ("sc") String sc,
                    @JsonProperty ("tc") String tc)
    {
        this.sc = UChar.of(sc);
        this.tc = UChar.of(tc);
    }

    public UChar get(Language l)
    {
        return l.isSimplified() ? sc : tc;
    }
}
