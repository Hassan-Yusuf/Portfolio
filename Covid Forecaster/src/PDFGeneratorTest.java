/*

// THIRD PARTY LIBRARY ONLY USED FOR TESTING //
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.io.FileOutputStream;


class PDFGeneratorTest {
    @Test
    @DisplayName("Testing PDF Writing")
    void readFile(String file){
        try {
            var document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // THIRD PARTY LIBRARY ONLY USED FOR TESTING //
}*/
