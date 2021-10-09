package utils;

public class Reformat {
    /*
     * Manipulation takes a string, capitalizes the first letter, and then makes all the other letters
     * lowercase. Even if there is more than one word, each word gets capitalized.
     * StringBuffer is a peer class of String that provides much of the functionality of strings.
     */
    public static String Manipulation(String given) {
        if(given.matches("[a-zA-Z]*")) {
            String[] arr = given.split(" ");

            StringBuffer buff = new StringBuffer();

            for (int i = 0; i < arr.length; i++) {
                buff.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1).toLowerCase()).append(" ");
            }
            return buff.toString().trim();
        }
        else{
            WindowWasher.clearScreen();
            System.out.println("The input entered should only contain letters,\n" +
                    "no numbers or special characters please.\n");
            return "Z";
        }
    }
}

