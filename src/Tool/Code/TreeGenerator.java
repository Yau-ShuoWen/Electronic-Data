package Tool.Code;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//我在迁移这个树状类的同时也用到了原始版本666
public class TreeGenerator
{
    public static void main(String[] args) throws IOException
    {
        String s = "E:\\計算機編程\\學習筆記".replace('\\', '/');
        List<String> excludeDirs=Arrays.asList(".git", "__pycache__", ".idea", "node_modules");
        generateDirectoryTree(s, "src/Tool/Code/TreeGenerator.txt", excludeDirs, 3);
    }

    public static void generateDirectoryTree(String directoryPath, String outputFile,
                                             List<String> excludeDirs, Integer maxDepth) throws IOException
    {
        Path rootPath = Paths.get(directoryPath);
        if (!Files.exists(rootPath))
        {
            throw new IllegalArgumentException("目录不存在: " + directoryPath);
        }

        if (excludeDirs == null)
        {
            excludeDirs = Arrays.asList(".git", "__pycache__", ".idea", "node_modules");
        }

        List<String> treeLines = new ArrayList<>();

        // 递归扫描目录
        scanDirectory(rootPath, "", 0, treeLines, excludeDirs, maxDepth);

        // 构建结果
        StringBuilder result = new StringBuilder();
        result.append(rootPath.getFileName()).append("/\n");
        for (String line : treeLines)
        {
            result.append(line).append("\n");
        }

        System.out.println(result.toString());

        // 输出到文件或控制台
        if (outputFile != null && !outputFile.isEmpty())
        {
            Files.write(Paths.get(outputFile), result.toString().getBytes());
            System.out.println("目录树已保存到: " + outputFile);
        }
        else
        {
            System.out.println();
            System.out.println("保存失败");
        }
    }

    private static void scanDirectory(Path currentPath, String prefix, int depth,
                                      List<String> treeLines, List<String> excludeDirs, Integer maxDepth)
    {
        if (maxDepth != null && depth > maxDepth)
        {
            return;
        }

        try
        {
            List<Path> items = new ArrayList<>();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentPath))
            {
                for (Path path : stream)
                {
                    if (!excludeDirs.contains(path.getFileName().toString()))
                    {
                        items.add(path);
                    }
                }
            }

            // 排序：目录在前，文件在后
            items.sort((p1, p2) ->
            {
                boolean isDir1 = Files.isDirectory(p1);
                boolean isDir2 = Files.isDirectory(p2);
                if (isDir1 && !isDir2) return -1;
                if (!isDir1 && isDir2) return 1;
                return p1.getFileName().toString().compareTo(p2.getFileName().toString());
            });

            for (int i = 0; i < items.size(); i++)
            {
                Path item = items.get(i);
                boolean isLast = i == items.size() - 1;

                if (Files.isDirectory(item))
                {
                    treeLines.add(prefix + (isLast ? "└── " : "├── ") + item.getFileName() + "/");
                    String newPrefix = prefix + (isLast ? "    " : "│   ");
                    scanDirectory(item, newPrefix, depth + 1, treeLines, excludeDirs, maxDepth);
                }
                else
                {
                    treeLines.add(prefix + (isLast ? "└── " : "├── ") + item.getFileName());
                }
            }
        } catch (IOException e)
        {
            treeLines.add(prefix + "└── [扫描错误: " + e.getMessage() + "]");
        }
    }
}
