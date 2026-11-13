package healthandbike.utils;

import java.util.Scanner;

public class FormValidation {

    private static final Scanner sc = new Scanner(System.in);

    public static Scanner getScanner() {
        return sc;
    }

    public static int validateInt(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                String line = sc.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número entero válido.");
            }
        }
    }

    public static double validateDouble(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                String line = sc.nextLine().trim().replace(',', '.');
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número decimal válido (ej. 150000.0).");
            }
        }
    }

    public static boolean validateBoolean(String prompt) {
        while (true) {
            System.out.println(prompt);
            String line = sc.nextLine().trim().toLowerCase();
            if (line.equals("true") || line.equals("si") || line.equals("s") || line.equals("1")) {
                return true;
            }
            if (line.equals("false") || line.equals("no") || line.equals("n") || line.equals("0")) {
                return false;
            }
            System.out.println("Error: ingrese 'true'/'si' o 'false'/'no'.");
        }
    }

    public static String validateString(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = sc.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }
            System.out.println("Error: el campo no puede estar vacío.");
        }
    }

    public static boolean validateEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean validatePhone(String phone) {
        return phone != null && phone.matches("^\\d{7,10}$");
    }

    public static boolean validatePlate(String plate) {
        return plate != null && plate.trim().length() >= 5;
    }
}
