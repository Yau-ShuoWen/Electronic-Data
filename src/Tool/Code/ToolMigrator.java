package Tool.Code;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

public class ToolMigrator
{

    // 原路径
    private static final String SOURCE_ROOT =
            "E:\\ShuoWen\\V0.2\\YuZong\\src\\main\\java\\com\\shuowen\\yuzong\\Tool";

    // 目标路径
    private static final String TARGET_ROOT =
            "E:\\IDEA\\Script\\src\\Tool";

    // package 替换
    private static final String OLD_PACKAGE = "com.shuowen.yuzong.Tool";
    private static final String NEW_PACKAGE = "Tool.DataStructure";

    public static void main(String[] args) throws IOException
    {
        Path sourcePath = Paths.get(SOURCE_ROOT);
        Path targetPath = Paths.get(TARGET_ROOT);

        Files.walk(sourcePath).forEach(path ->
        {
            try
            {
                Path relative = sourcePath.relativize(path);
                Path target = targetPath.resolve(relative);

                if (Files.isDirectory(path))
                {
                    Files.createDirectories(target);
                }
                else
                {
                    processFile(path, target);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        System.out.println("迁移完成！");
    }

    private static void processFile(Path source, Path target) throws IOException
    {
        String content = new String(Files.readAllBytes(source), StandardCharsets.UTF_8);

        // 只处理 Java 文件
        if (source.toString().endsWith(".java"))
        {
            content = content.replace(OLD_PACKAGE, NEW_PACKAGE);
        }

        Files.write(target, content.getBytes(StandardCharsets.UTF_8));
    }
}