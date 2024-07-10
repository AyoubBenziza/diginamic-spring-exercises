package fr.diginamic.springdemo.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Utility class to export data to files
 * @see CSVPrinter
 * @see CSVFormat
 * @see HttpServletResponse
 * @author AyoubBenziza
 */
public class ExportsUtils {
    /**
     * Export a set of data to a CSV file
     * @param data the data to export
     * @param filename the name of the file
     * @param headers the headers of the CSV file
     * @param response the HttpServletResponse
     */
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

    public static void toPDFFile(Set<?> data, String filename, String[] headers, HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            PdfPTable table = new PdfPTable(headers.length);
            Stream.of(headers).forEach(h -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(h));
                table.addCell(header);
            });

            data.forEach(d -> {
                String objectString = d.toString();
                // Extracting the content inside the curly braces
                String propertiesString = objectString.substring(objectString.indexOf('{') + 1, objectString.lastIndexOf('}'));

                // Handling the cities property separately
                String citiesString = propertiesString.substring(propertiesString.indexOf("cities="));
                propertiesString = propertiesString.substring(0, propertiesString.indexOf(", cities="));

                // Splitting the remaining properties by comma
                String[] properties = propertiesString.split(", ");
                for (String property : properties) {
                    // Extracting the value after the '=' character
                    String value = property.substring(property.indexOf('=') + 1).trim().replace("'", ""); // Removing single quotes around the name value
                    table.addCell(value);
                }

                // Handling the cities property, assuming it's a collection
                if (citiesString.contains("cities=")) {
                    PdfPTable cityTable = new PdfPTable(3); // Adjust the number of columns to match CityDTO properties
                    // Adding headers to the city table
                    Stream.of("Name", "Population", "Department Code").forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(1);
                        header.setPhrase(new Phrase(headerTitle));
                        cityTable.addCell(header);
                    });
                    String citiesValue = citiesString.substring(citiesString.indexOf('=') + 1).trim();
                    citiesValue = citiesValue.substring(1, citiesValue.length() - 1); // Removing the leading and trailing brackets
                    // Splitting based on the start of each city's toString representation, considering the format
                    String[] cityValues = citiesValue.split(", (?=\\{name=)");

                    for (String city : cityValues) {
                        // Check if the city string ends with '}' and remove it
                        if (city.endsWith("}")) {
                            city = city.substring(0, city.length() - 1);
                        }
                        // Splitting the city's properties by comma
                        String[] cityProps = city.split(", ");
                        for (String prop : cityProps) {
                            // Extracting the value after the '=' character and removing single quotes
                            String cityPropValue = prop.substring(prop.indexOf('=') + 1).trim().replace("'", "");
                            cityTable.addCell(cityPropValue);
                        }
                    }
                    // Assuming you want to add the cityTable as a single cell to the main table
                    PdfPCell cityCell = new PdfPCell(cityTable);
                    table.addCell(cityCell);
                }
            });

            document.add(table);
        } catch (DocumentException | IOException e) {
            System.err.println("Error while writing data to PDF file: " + e.getMessage());
        } finally {
            document.close();
        }
    }
}
