package manager.encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Класс Encoder предоставляет функциональность для шифрования и расшифровки текста.
 */
public class Encoder {

    private static final String key = "cool_key_for_passwords";  // Ключ шифрования (это неправильно, но считаем это
        // допущением)

    /**
     * Шифрует переданный текст с использованием Blowfish алгоритма.
     *
     * @param plaintext Исходный текст для шифрования.
     * @return Зашифрованный текст в формате Base64.
     * @throws Exception Если произошла ошибка в процессе шифрования.
     */
    public static String encrypt(String plaintext) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Расшифровывает переданный зашифрованный текст с использованием Blowfish алгоритма.
     *
     * @param encryptedText Зашифрованный текст в формате Base64.
     * @return Расшифрованный исходный текст.
     * @throws Exception Если произошла ошибка в процессе расшифровки.
     */
    public static String decrypt(String encryptedText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
