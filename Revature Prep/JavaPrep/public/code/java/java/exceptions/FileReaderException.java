import java.io.File;
import java.io.FileReader;

public class FileReaderException {
    public static void main(String[] args) {
        File file = new File("E://File.txt");

        FileReader fr = new FileReader(file);
    }
}