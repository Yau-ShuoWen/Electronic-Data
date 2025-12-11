package Tool.Code;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathRelativeCalculator
{
    public static void main(String[] args)
    {
        String sourceFile = "E:\\ShuoWen\\V0.2\\yuzongweb\\src\\views\\Developer\\EditReferencePage.vue";
        String targetFile = "E:\\ShuoWen\\V0.2\\yuzongweb\\src\\components\\Select\\DictSelect.vue";

        System.out.println("源文件: " + sourceFile);
        System.out.println("目标文件: " + targetFile);
        System.out.println();

        // 计算普通相对路径
        String relativePath = calculateRelativePath(sourceFile, targetFile);
        System.out.println("相对路径: " + relativePath);

    }

    public static String calculateRelativePath(String sourceFile, String targetFile) {
        try {
            // 转换为Path对象
            Path sourcePath = Paths.get(sourceFile).toAbsolutePath();
            Path targetPath = Paths.get(targetFile).toAbsolutePath();

            // 获取源文件所在目录
            Path sourceDir = sourcePath.getParent();

            // 计算相对路径
            Path relativePath = sourceDir.relativize(targetPath);

            // 转换为字符串，并确保使用正斜杠（适合Vue等前端框架）
            String result = relativePath.toString().replace("\\", "/");

            return result;

        } catch (IllegalArgumentException e) {
            System.err.println("无法计算相对路径，请检查文件路径是否在同一文件系统中");
            return null;
        } catch (Exception e) {
            System.err.println("计算相对路径时发生错误: " + e.getMessage());
            return null;
        }
    }
}
