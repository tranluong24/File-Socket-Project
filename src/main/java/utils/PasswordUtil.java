package utils;


import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash mật khẩu người dùng
    public static String hashPassword(String password) {
        // Tạo ra salt và hash mật khẩu
        String salt = BCrypt.gensalt(); // Tạo salt mặc định (logRounds = 10)
        return BCrypt.hashpw(password, salt);
    }

    // So sánh mật khẩu người dùng nhập vào với mật khẩu đã hash trong DB
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        // So sánh mật khẩu plain với mật khẩu đã được hash
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
