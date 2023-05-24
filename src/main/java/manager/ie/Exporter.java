package manager.ie;

import manager.model.Password;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static manager.encoder.Encoder.decrypt;

/**
 * Класс Exporter предоставляет функциональность экспорта паролей в CSV файл.
 */
public class Exporter {

    /**
     * Экспортирует пароли в CSV файл.
     *
     * @param folder Папка, в которую будет сохранен CSV файл.
     * @param passwords Список паролей для экспорта.
     * @return Абсолютный путь к экспортированному CSV файлу.
     */
    public static String exportPasswordsToCSV(File folder, List<Password> passwords) {

        // Создание CSV-файла
        String fileName = "passwords.csv";
        File file = new File(folder, fileName);

        try (FileWriter writer = new FileWriter(file)) {
            // Запись заголовка CSV-файла
            writer.write("Id,Name,Login,Password\n");

            // Запись паролей в CSV-файл
            for (Password password : passwords) {
                String line = password.getId() + "," + password.getName() + "," + password.getLogin() + "," +
                        decrypt(password.getPassword()) + "\n";
                writer.write(line);
            }

            return file.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
