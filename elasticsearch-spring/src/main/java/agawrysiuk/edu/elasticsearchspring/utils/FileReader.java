package agawrysiuk.edu.elasticsearchspring.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static agawrysiuk.edu.elasticsearchspring.exceptions.messages.ExceptionMessage.NO_DATA;

public class FileReader {

    public static String readFile(String filePath) throws IOException {
        InputStream is = FileReader.class.getResourceAsStream(filePath);
        if (is == null) {
            throw new RuntimeException(NO_DATA.getText());
        }
        return IOUtils.toString(is, StandardCharsets.UTF_8);
    }

    public static String readFile(String filePath, Class<?> clazz) throws IOException {
        InputStream is = clazz.getResourceAsStream(filePath);
        if (is == null) {
            throw new RuntimeException(NO_DATA.getText());
        }
        return IOUtils.toString(is, StandardCharsets.UTF_8);
    }
}
