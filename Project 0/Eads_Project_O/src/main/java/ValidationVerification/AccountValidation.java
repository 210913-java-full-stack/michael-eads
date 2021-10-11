package ValidationVerification;

public class AccountValidation {
    /*
     * accountCheck checks a string to ensure that it is of the proper length and
     * contains numbers.
     */
    public static boolean accountCheck(String input){
    int accNum = 0;
    if (!input.matches("[0-9]*")) {
        System.out.println("I'm sorry the account number must consist of numbers.");
        return false;
    } else if (input.length() != 5) {
        System.out.println("The account number is only five digits long.");
        return false;
    }
    return true;
}
}
