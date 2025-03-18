import java.awt.*;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scnr;

    public Menu() {
        this.scnr = new Scanner(System.in);
    }

    public void start(String username) {
        if (!username.isEmpty()) {
            this.mainMenu();
        } else {
            this.loginPage();
        }
    }

    private void loginPage() {
        System.out.println("Welcome to the library!");
        System.out.print("(l) log in | (s) sign up  :  ");
        String input = this.scnr.nextLine();

        if (input.equals("l")) {
            this.logIn();
        } else if (input.equals("s")) {
            this.signUp();
        } else {
            System.out.println("Invalid input");
            this.loginPage();
        }
    }

    private void mainMenu() {
        System.out.println("You are logged in!");
    }

    private void logIn() {
        List<String> usernames = UserData.getData("username");

        System.out.print("Enter your username: ");
        String username = this.scnr.nextLine();

        if (usernames.contains(username)) {
            int usernameIndex = 0;
            for (int i = 0; i < usernames.size(); i++) {
                if (username.equals(usernames.get(i))) {
                    usernameIndex = i;
                }
            }

            List<String> passwords = UserData.getData("password");
            this.getPassword(usernameIndex, passwords);
        } else {
            System.out.println("No user found with that username.\n");
            this.loginPage();
        }
    }

    private void getPassword(int usernameIndex, List<String> passwords) {
        System.out.println("Enter your password to login or (q) to quit: ");
        String password = this.scnr.nextLine();

        if (passwords.get(usernameIndex).equals(password)) {
            this.mainMenu();
        } else if (password.equals("q")) {
            this.loginPage();
        } else {
            System.out.println("Invalid password. \n");
            this.getPassword(usernameIndex, passwords);
        }
    }

    private void signUp() {
        String username = this.createUsername();
        String password = this.createPassword(0);

        if (!username.isEmpty() && !password.isEmpty()) {
            this.createAccount(username, password);
        }
    }

    private String createUsername() {
        System.out.println("Create a username: ");
        String username = this.scnr.nextLine();

        List<String> usernames = UserData.getData("username");

        if (usernames.contains(username)) {
            System.out.println("Username already exists.\n");
            this.signUp();
        } else if (username.length() < 2 || username.length() > 20) {
            System.out.println("Invalid username. \n");
            this.createUsername();
        }
        return username;
    }

    private String createPassword(int retries) {
        System.out.println("Create a password: ");
        String password = this.scnr.nextLine();

        List<String> passwords = UserData.getData("password");

        boolean hasRetries = retries < 2;

        if (hasRetries) {
            if (passwords.contains(password)) {
                System.out.println("Password already exists.\n");
                this.signUp();
            } else if (password.length() < 2 || password.length() > 20) {
                System.out.println("Invalid password. \n");
                this.createPassword(retries + 1);
            } else {
                System.out.println("Incorrect password. \n");
                this.createPassword(retries + 1);
            }
        }
        else {
            System.out.println("Max retries exceeded.\n");
            this.loginPage();
        }
        return password;
    }

    private void createAccount(String username, String password) {
        User user = new User(username, password);
        UserData.write(user);
    }
}
