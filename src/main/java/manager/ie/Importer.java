package manager.ie;

import manager.model.Password;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Objects;

import static manager.encoder.Encoder.encrypt;

/**
 * Класс Importer предоставляет функциональность импорта паролей из CSV файла.
 */
public class Importer {

    /**
     * Импортирует пароли из CSV файла.
     *
     * @param file Файл CSV
     * @return Список импортированных паролей.
     * @throws IOException если возникает ошибка при чтении файла.
     * @throws InvalidPropertiesFormatException если файл имеет неправильный формат.
     */
    public static List<Password> importPasswordsFromCSV(File file) throws IOException {
        List<Password> passwords = new ArrayList<>();
        List<String> lines = Files.readAllLines(file.toPath());

        boolean header = true;
        for (String line : lines) {
            if (header) {
                if (!Objects.equals(line, "Id,Name,Login,Password")) {
                    throw new InvalidPropertiesFormatException("Wrong file for import.");
                }
                header = false;
                continue;
            }
            String[] values = line.split(",");
            if (values.length == 4) {
                String id = values[0].trim();
                String name = values[1].trim();
                String login = values[2].trim();
                String password;
                try {
                    password = encrypt(values[3].trim());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Password passwordObj = new Password(Integer.parseInt(id), name, login, password);
                passwords.add(passwordObj);
            }
        }

        return passwords;
    }
}
