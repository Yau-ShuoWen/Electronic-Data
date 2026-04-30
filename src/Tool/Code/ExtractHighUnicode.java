package Tool.Code;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class ExtractHighUnicode
{
    public static void main(String[] args)
    {
        String filePath = "E:\\NCbackup.sql".replace('\\','/');

        try
        {
            String content = new String(Files.readAllBytes(Paths.get(filePath)), "UTF-8");

            // ✅ 1. 移除 mdr_char 的 INSERT 區塊
            content = content.replaceAll(
                    "INSERT INTO `mdr_char`[\\s\\S]*?UNLOCK TABLES;",
                    ""
            );

            HashSet<String> result = new HashSet<>();

            // ✅ 2. 正常掃描 Unicode
            content.codePoints().forEach(cp ->
            {
                if (cp > 0x10000)
                {
                    result.add(new String(Character.toChars(cp)));
                }
            });

            // 輸出結果
            System.out.println("找到的字符：");
            for (String s : result)
            {
                System.out.println(s + " (U+" + Integer.toHexString(s.codePointAt(0)).toUpperCase() + ")");
            }

            System.out.println("總數: " + result.size());

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}