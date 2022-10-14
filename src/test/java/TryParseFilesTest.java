import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class TryParseFilesTest {
static String archivePath = "src/test/resources/test.zip";
static String archiveName = "test.zip";
ClassLoader cl = TryParseFilesTest.class.getClassLoader();
    @DisplayName("check PDF file")
    @Test
    void readPdfTest() throws Exception {
        ZipFile archive = new ZipFile(new File(archivePath));
        try(ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream(archiveName)))
            { ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().contains(".pdf")) {
                        try (InputStream pdfStream = archive.getInputStream(entry)){
                            PDF pdfFile = new PDF(pdfStream);
                            assertThat(pdfFile.text).contains("Windows & Linux keymap");
                        }
                    }
                }
            }
        }
    @DisplayName("check CSV file")
    @Test
    void readCsvFile() throws Exception {
        ZipFile archive = new ZipFile(new File(archivePath));
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream(archiveName)))
            {
                ZipEntry entry;
                    while ((entry = zis.getNextEntry()) != null) {
                        if (entry.getName().contains(".csv")) {
                            try (InputStream csvStream = archive.getInputStream(entry);
                                CSVReader reader = new CSVReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8))) {
                                    List<String[]> contentCSV = reader.readAll();
                                    assertThat(contentCSV.get(0)).contains("John", "Doe", "120 jefferson st.", "Riverside", " NJ", " 08075");
                            }
                        }
                    }
            }
    }
    @DisplayName("check XLSX file")
    @Test
    void readExcelFile() throws Exception {
        ZipFile archive = new ZipFile(new File(archivePath));
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream(archiveName)))
            {
                ZipEntry entry;
                    while ((entry = zis.getNextEntry()) != null)
                    {
                        if (entry.getName().contains(".xls"))
                        {
                            try (InputStream xlsxStream = archive.getInputStream(entry))
                            {
                                XLS xls = new XLS(xlsxStream);
                                double xlsData = xls.excel.getSheetAt(0).getRow(1).getCell(2).getNumericCellValue();
                                assertThat(xlsData).isEqualTo(80078);
                                System.out.println();
                            }
                        }
                    }
            }
    }
}

