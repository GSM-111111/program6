import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SliceOHeaven {
    private String img1, img2, img3;
    private String pizzaSize;
    private char extraCheese;
    private String sideDish;
    private String drinks;
    private char wantDiscount;
    private static final long blacklistedNumber = 12345678901234L; // 示例黑名单卡号

    public void takeOrder() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter three ingredients for your pizza (use spaces to separate ingredients):");
        String[] ingredients = scanner.nextLine().split(" ");
        img1 = ingredients[0];
        img2 = ingredients[1];
        img3 = ingredients[2];
        
        System.out.println("Enter size of pizza (Small, Medium, Large):");
        pizzaSize = scanner.nextLine();
        
        System.out.println("Do you want extra cheese (Y/N):");
        extraCheese = scanner.nextLine().toUpperCase().charAt(0);
        
        System.out.println("Enter one side dish (Calzone, Garlic bread, None):");
        sideDish = scanner.nextLine();
        
        System.out.println("Enter drinks(Cold Coffee, Cocoa drink, Coke, None):");
        drinks = scanner.nextLine();
        
        System.out.println("Would you like the chance to pay only half for your order? (Y/N):");
        wantDiscount = scanner.nextLine().toUpperCase().charAt(0);
        
        if (wantDiscount == 'Y') {
            isItYourBirthday();
        } else {
            makeCardPayment();
        }
    }

    private void isItYourBirthday() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your birthday (yyyy-MM-dd):");
        LocalDate birthdate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate today = LocalDate.now();
        
        int age = Period.between(birthdate, today).getYears();
        boolean isBirthdayToday = birthdate.getMonth() == today.getMonth() 
                                && birthdate.getDayOfMonth() == today.getDayOfMonth();
        
        if (age < 18 && isBirthdayToday) {
            System.out.println("Congratulations! You pay only half the price for your order");
        } else {
            System.out.println("Too bad! You do not meet the conditions to get our 50% discount");
            makeCardPayment();
        }
    }

    private void makeCardPayment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your card number:");
        long cardNumber = scanner.nextLong();
        scanner.nextLine(); // 消耗换行符
        
        System.out.println("Enter card's expiry date (yyyy-MM):");
        String expiryDate = scanner.nextLine();
        
        System.out.println("Enter card's CVV:");
        int cvv = scanner.nextInt();
        
        processCardPayment(cardNumber, expiryDate, cvv);
    }

    private void processCardPayment(long cardNumber, String expiryDate, int cvv) {
        String cardStr = Long.toString(cardNumber);
        
        // 检查卡号长度
        if (cardStr.length() != 14) {
            System.out.println("Invalid card");
            return;
        }
        System.out.println("Card accepted");
        
        // 检查黑名单
        if (cardNumber == blacklistedNumber) {
            System.out.println("Card is blacklisted. Please use another card");
            return;
        }
        
        // 处理卡号显示
        String firstCardDigit = cardStr.substring(0, 1);
        String lastFourDigits = cardStr.substring(cardStr.length() - 4);
        String masked = firstCardDigit + "********" + lastFourDigits;
        System.out.println("Processed card: " + masked);
    }

    // 其他方法（如printReceipt）需要根据实际情况更新
    public static void main(String[] args) {
        SliceOHeaven program = new SliceOHeaven();
        program.takeOrder();
    }
}