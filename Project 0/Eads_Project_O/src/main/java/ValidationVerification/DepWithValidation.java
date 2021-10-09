package ValidationVerification;

public class DepWithValidation {
    /*
     * depWithVal (deposit, withdraw validation) checks the length and if the user typed
     * their desired amount with a dollar sign ($).
     */

    public static double depWithVal(String input) {
        double money = -1;
        //Check for "$" symbol

        int length = input.length();
        if ((input.indexOf('.')) != (length - 3) && (input.indexOf('.')) != -1) {
            System.out.println("Input invalid the specified amount must be in \\\"xx.xx\\\" format.\"");
            return -1;
        }
        else {
            try{
                money = Double.parseDouble(input);
            }
            catch (NumberFormatException e){
                System.out.println("The amount specified must ONLY contain numbers\n" +
                        "and be in \"xx.xx\" format.");
                return -1;
            }
            return money;
        }
    }
}
