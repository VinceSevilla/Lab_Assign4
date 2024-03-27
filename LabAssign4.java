import java.util.HashMap;
import java.util.Scanner;

class MaxLoginAttemptsExceededException extends Exception {
    public MaxLoginAttemptsExceededException(String message) {
        super(message);
    }
}

class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }
}

class LoginService {
    private static final String pass = "password";
    private int maxLoginAttempts;
    private HashMap<String, Integer> loginAttemptsMap;

    public LoginService(int maxLoginAttempts) {
        this.maxLoginAttempts = maxLoginAttempts;
        loginAttemptsMap = new HashMap<>();
    }

    public void login(String username, String password) throws MaxLoginAttemptsExceededException, InvalidPasswordException {
        int attempts = loginAttemptsMap.getOrDefault(username, 0);

        if (attempts >= maxLoginAttempts) {
            throw new MaxLoginAttemptsExceededException("Maximum login attempts exceeded for user: " + username);
        }

        if (!password.equals(pass)) {
            loginAttemptsMap.put(username, attempts + 1);
            throw new InvalidPasswordException("Invalid password");
        }
        System.out.println("Login successful for user: " + username);
        
        loginAttemptsMap.put(username, 0);
    }
}

public class LabAssign4 {
    public static void main(String[] args) {
        LoginService loginService = new LoginService(3); 
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();

                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                loginService.login(username, password);

            } catch (MaxLoginAttemptsExceededException e) {
                System.out.println("Max login attempts exceeded: " + e.getMessage());
            } catch (InvalidPasswordException e) {
                System.out.println("Invalid password: " + e.getMessage());
            }
        }
    }
}
