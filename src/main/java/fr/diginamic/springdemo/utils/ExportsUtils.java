package fr.diginamic.springdemo.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.util.Set;

public class ExportsUtils {
    public static void toCSVFile(Set<?> data, String filename, String[] headers, HttpServletResponse response) {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        CSVFormat csvFormat = CSVFormat.EXCEL.builder().setHeader(headers).build();

        try (CSVPrinter printer = new CSVPrinter(response.getWriter(), csvFormat)) {
            data.forEach(d -> {
                try {
                    printer.printRecord(d);
                } catch (IOException e) {
                    System.err.println("Error while writing data to CSV file: "+ e.getMessage());
                }
            });
        } catch (IOException e) {
            System.err.println("Error while writing data to CSV file: "+ e.getMessage());
        }
    }
}
