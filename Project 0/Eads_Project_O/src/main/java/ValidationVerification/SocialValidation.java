package ValidationVerification;

public class SocialValidation {
    /*
     * Validates the social security number for length and makes sure it's all numbers.
     */
    public static boolean SocialVal(String given) {
        int length = given.length();
        if (length != 4) {
            System.out.println("We only need the last FOUR (4) of your social.");
            return false;
        } else if (!given.matches("[0-9]*")) {
            System.out.println("Your SSN should only consist of " +
                    "numbers, letters are not permitted");
            return false;
        }
        return true;
    }
}
