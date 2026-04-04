package Tool.Code;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class CompareFiles
{

    public static void main(String[] args)
    {
        String pathA = "C:\\Users\\26530\\Desktop\\2026-3-11整理qq圖片備份（原版完全入梗圖）";
        String pathB = "F:\\哏圖";

        File folderA = new File(pathA);
        File folderB = new File(pathB);

        if (!folderA.exists() || !folderA.isDirectory())
        {
            System.out.println("Path A 不存在或不是資料夾");
            return;
        }

        if (!folderB.exists() || !folderB.isDirectory())
        {
            System.out.println("Path B 不存在或不是資料夾");
            return;
        }

        // 🔁 遞迴取得 B 所有檔名
        Set<String> filesInB = new HashSet<>();
        collectFiles(folderB, filesInB);

        System.out.println("=== A 有但 B 沒有的檔案 ===");
        for (File file : folderA.listFiles())
        {
            if (file.isFile())
            {
                String fileName = file.getName();
                if (!filesInB.contains(fileName))
                {
                    System.out.println(fileName);
                }
            }
        }

        System.out.println("\n=== A 和 B 都有的檔案 ===");
        for (File file : folderA.listFiles())
        {
            if (file.isFile())
            {
                String fileName = file.getName();
                if (filesInB.contains(fileName))
                {
                    System.out.println(fileName);
                }
            }
        }
    }

    // 🔁 遞迴收集檔案名稱
    private static void collectFiles(File folder, Set<String> fileSet)
    {
        File[] files = folder.listFiles();
        if (files == null) return;

        for (File file : files)
        {
            if (file.isFile())
            {
                fileSet.add(file.getName());
            }
            else if (file.isDirectory())
            {
                collectFiles(file, fileSet);
            }
        }
    }
}