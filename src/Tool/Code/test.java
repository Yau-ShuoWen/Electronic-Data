package Tool.Code;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.*;
import java.util.Objects;

public class test
{
    public static void main(String[] args)
    {
        List<Object> list = new ArrayList<>();
        list.add(list);
        System.out.println(list.equals(List.of()));
    }
}

