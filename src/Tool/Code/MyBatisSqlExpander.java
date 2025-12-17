package Tool.Code;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;

public class MyBatisSqlExpander
{

    // ====== 直接写绝对路径 ======
    private static final String INPUT_XML =
            "E:\\ShuoWen\\V0.2\\YuZong\\src\\main\\resources\\mapper\\Character\\CharMapper.xml";

    private static final String OUTPUT_SQL =
            "src/Tool/Code//OutputMyBatisSqlExpander.txt";

    // ==========================
    private static final Map<String, String> SQL_FRAGMENT_MAP = new HashMap<>();

    public static void main(String[] args) throws Exception
    {
        expand(INPUT_XML, OUTPUT_SQL);
        System.out.println("SQL expanded successfully.");
    }

    /**
     * 核心入口方法
     */
    public static void expand(String inputPath, String outputPath) throws Exception
    {
        File input = new File(inputPath);
        File output = new File(outputPath);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setNamespaceAware(false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(input);

        // 1️⃣ 收集 <sql id="...">
        NodeList sqlNodes = doc.getElementsByTagName("sql");
        for (int i = 0; i < sqlNodes.getLength(); i++)
        {
            Element el = (Element) sqlNodes.item(i);
            String id = el.getAttribute("id");
            String content = getInnerXml(el).trim();
            SQL_FRAGMENT_MAP.put(id, content);
        }

        StringBuilder result = new StringBuilder();

        // 2️⃣ 展开 SQL
        processStatements(doc, "select", result);
        processStatements(doc, "insert", result);
        processStatements(doc, "update", result);
        processStatements(doc, "delete", result);

        // 3️⃣ #{ } → ${ }
        String finalSql = result.toString()
                .replaceAll("#\\{", "\\$\\{");

        try (Writer w = new OutputStreamWriter(
                new FileOutputStream(output),
                StandardCharsets.UTF_8))
        {
            w.write(finalSql);
        }
    }

    private static void processStatements(Document doc, String tag, StringBuilder out)
    {
        NodeList nodes = doc.getElementsByTagName(tag);
        for (int i = 0; i < nodes.getLength(); i++)
        {
            Element el = (Element) nodes.item(i);
            out.append("\n\n-- ===== ")
                    .append(tag.toUpperCase())
                    .append(" : ")
                    .append(el.getAttribute("id"))
                    .append(" =====\n");

            String expanded = expandIncludes(getInnerXml(el));
            out.append(expanded.trim()).append(";\n");
        }
    }

    /**
     * 递归展开 <include refid="xxx"/>
     */
    private static String expandIncludes(String sql)
    {
        Pattern p = Pattern.compile("<include\\s+refid=\"([^\"]+)\"\\s*/>");
        Matcher m = p.matcher(sql);

        StringBuffer sb = new StringBuffer();
        while (m.find())
        {
            String refId = m.group(1);
            String fragment = SQL_FRAGMENT_MAP.getOrDefault(refId, "");
            fragment = expandIncludes(fragment); // 支持嵌套
            m.appendReplacement(sb, Matcher.quoteReplacement(fragment));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private static String getInnerXml(Element element)
    {
        NodeList children = element.getChildNodes();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < children.getLength(); i++)
        {
            sb.append(nodeToString(children.item(i)));
        }
        return sb.toString();
    }

    private static String nodeToString(Node node)
    {
        if (node.getNodeType() == Node.TEXT_NODE)
        {
            return node.getTextContent();
        }

        if (node.getNodeType() == Node.ELEMENT_NODE)
        {
            Element el = (Element) node;

            // ⭐ 如果是 include，原样保留
            if ("include".equals(el.getTagName()))
            {
                return "<include refid=\"" + el.getAttribute("refid") + "\"/>";
            }

            // 其他标签（select / where / choose 等）
            return el.getTextContent();
        }

        return "";
    }

}
