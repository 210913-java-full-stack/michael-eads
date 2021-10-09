package utils;

public class WindowWasher {
    /*
     * I don't call this one nearly enough. It should clear the screen.
     */
    public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    }
}

