package ValidationVerification;

public class PasswordValidation {
    /*
     * A great big thank you to Max O'Didily for the excellent video showing
     * this validation method, and to Erika Johnson for directing me to it.
     *10/09/21  https://www.youtube.com/watch?v=kN8mqSvNFa4
     *
     * This class takes in a string, checks the length, and then checks it to
     * make sure it has 1 capital letter, 1 lower case, and 1 number
     */

    public static boolean PassVal(String pw){
        if(pw.length() > 7){
            if(checkPass (pw)){
                return true;
            }
            else{
                System.out.println("The password must contain at least: one (1) capital\n" +
                        "letter, one (1) lower case letter, and one (1) number");
                return false;
            }
        }
        else{
            System.out.println("Password is too small.");
            return false;
        }
    }

    public static boolean checkPass(String pw) {
        boolean hasNum = false;
        boolean hasCap = false;
        boolean hasLow = false;
        char c;
        for (int i = 0; i < pw.length(); i++) {
            c = pw.charAt(i);
            if (Character.isDigit(c)) {
                hasNum = true;
            } else if (Character.isUpperCase(c)) {
                hasCap = true;
            } else if (Character.isLowerCase(c)) {
                hasLow = true;
            }
            if (hasNum && hasCap && hasLow) {
                return true;
            }
        }
        return false;
    }
}
