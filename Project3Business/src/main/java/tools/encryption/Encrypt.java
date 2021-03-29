package tools.encryption;

import config.AppConfig;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Formatter;

public class Encrypt {

    public static String string2Base64(String plainText) {
        return Base64.getEncoder().encodeToString(plainText.getBytes(StandardCharsets.UTF_8));
    }

    public static String base642string(String base64Text) {
        byte[] base64decodedBytes = Base64.getDecoder().decode(base64Text);
        return new String(base64decodedBytes, StandardCharsets.UTF_8);
    }

    public static String sha256EncryptSalt(String encryptStr, String salt) {
        MessageDigest md = null;
        String encryptCode = null;

        byte[] bt = (encryptStr + salt).getBytes();
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(bt);
            encryptCode = byteToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return encryptCode;
    }

    private static String byteToHex(final byte[] hash) {
        try (final Formatter formatter = new Formatter()) {
            for (byte b : hash)
                formatter.format("%02x", b);
            return formatter.toString();
        }
    }
}
