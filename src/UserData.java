import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserData {
    public static List<String> getData(String dataType) {
        return UserData.readDataFromFile(dataType);
    }

    private static List<String> readDataFromFile(String dataType) {
        Path filePath = Paths.get(System.getProperty("user.dir"), "..", "user_data.txt").normalize();

        List<String> data = new ArrayList<>();

        try(BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                if (split.length == 2) {
                    if (dataType.equals("username")) {
                        data.add(split[0].trim());
                    } else if (dataType.equals("password")) {
                        data.add(split[1].trim());
                    } else {
                        throw new IllegalArgumentException("Invalid data type: '" + dataType + "' : type must be username or password");
                    }
                } else {
                    throw new IOException("Invalid line format: " + line);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return data;
    }

    public static void write(User user) {

    }
}
