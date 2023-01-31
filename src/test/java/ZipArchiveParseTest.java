import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class ZipArchiveParseTest {
    ClassLoader cl = ZipArchiveParseTest.class.getClassLoader();

    @Test
    public void parseZipArchive() throws Exception {
        try(InputStream resource = cl.getResourceAsStream("Archive.zip");
            ZipInputStream zis = new ZipInputStream(resource);
        ) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {

                if (zipEntry.getName().endsWith(".csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = csvReader.readAll();
                    assertThat(content.get(0)[1]).contains("Client");
                    assertThat(content.size()).isEqualTo(16);
                }

                if (zipEntry.getName().endsWith(".xlsx")) {
                    XLS parseXLS = new XLS(zis);
                    assertThat(parseXLS.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue()).contains("IP-FL");
                }
                if (zipEntry.getName().endsWith(".pdf")) {
                    PDF pdfParser = new PDF(zis);
                    assertThat(pdfParser.creator).isEqualTo("PDF24 Creator");
                    assertThat(pdfParser.numberOfPages).isEqualTo(1);
                    assertThat(pdfParser.text).contains("Snyatie+Vziskanie");
                }

            }
        }
    }
}



