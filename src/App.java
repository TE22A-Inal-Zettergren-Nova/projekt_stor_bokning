import java.util.Scanner;

public class App {

    static void meny(){
        Scanner tb = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Meny \n 1. Boka \n 2. Avboka \n 3. Beräkna vinst \n 4. Avsluta");
            try {
                int startval = Integer.parseInt(tb.nextLine());

                switch (startval) {
                    case 1:
                        System.out.println("Vill du boka en fönsterplats eller en gångplats?");
                        running = false;
                        break;
                    case 2:
                        System.out.println("Ditt personnummer krävs för att avboka \n Skriv in ditt personnummer här: ");
                        running = false;
                        break;
                    case 3:
                        System.out.println("Den totala vinsten är...");
                        running = false;
                        break;
                    case 4:
                        System.out.println("Programmet avslutas...");
                        running = false;
                        break;
                    default:
                        System.out.println("Detta val finns inte");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Felaktig inmatning. Ange en siffra.");
            
        }
    }
}
    


    public static void main(String[] args) throws Exception {
            meny();
            }
    }

