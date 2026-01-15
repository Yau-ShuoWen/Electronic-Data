package Tool.Code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class FileLineCounter
{

    /**
     * 递归计算目录下符合正则表达式文件的总行数
     *
     * @param s 目录路径
     * @param p 正则表达式列表
     * @return 符合条件文件的总行数
     * @throws IOException
     */
    public static long countLines(String s, List<String> p) throws IOException
    {
        File dir = new File(s);
        if (!dir.exists() || !dir.isDirectory())
        {
            throw new IllegalArgumentException("路径不是有效目录: " + s);
        }

        // 将字符串正则编译成Pattern列表
        List<Pattern> patterns = p.stream().map(Pattern::compile).toList();

        return countLinesRecursive(dir, patterns);
    }

    private static long countLinesRecursive(File file, List<Pattern> patterns) throws IOException
    {
        long totalLines = 0;

        if (file.isDirectory())
        {
            File[] children = file.listFiles();
            if (children != null)
            {
                for (File child : children)
                {
                    totalLines += countLinesRecursive(child, patterns);
                }
            }
        } else
        {
            // 判断文件名是否匹配任意一个正则
            String fileName = file.getName();
            boolean matches = patterns.stream().anyMatch(p -> p.matcher(fileName).matches());
            if (matches)
            {
                // 计算文件行数
                try (BufferedReader br = new BufferedReader(new FileReader(file)))
                {
                    while (br.readLine() != null)
                    {
                        totalLines++;
                    }
                }
            }
        }

        return totalLines;
    }

    // 测试示例
    public static void main(String[] args) throws IOException
    {
        String dirPath = ("E:\\ShuoWen\\V0.2\\YuZong\\src\\main\\java\\com\\shuowen\\yuzong" +
                "").replace('\\','/');; // 替换为你的目录
        List<String> regexList = List.of(".*\\.java", ".*\\.xml"); // 匹配.java和.txt文件
        long totalLines = countLines(dirPath, regexList);
        System.out.println("总行数: " + totalLines);
    }
}
