package dracarys1;



public class Main {

    public static void main(String[] args) {

        try {
            new Game().startGame();
        } catch (Exception e) {
           System.out.println("An ERROR Occured!\nTry Again");
        }
    }
}
