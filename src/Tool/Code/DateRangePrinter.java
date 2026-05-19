package Tool.Code;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateRangePrinter {
    public static void main(String[] args) {
        // 輸入開始日期與結束日期
        String sttStr = "2026-03-01";
        String endStr = "2026-03-07";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate startDate = LocalDate.parse(sttStr, formatter);
        LocalDate endDate = LocalDate.parse(endStr, formatter);

        // 輸出區間內所有日期（包含開始與結束）
        while (!startDate.isAfter(endDate)) {
            System.out.println(startDate);
            startDate = startDate.plusDays(1);
        }
    }
}