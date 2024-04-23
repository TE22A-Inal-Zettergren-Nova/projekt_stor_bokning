import java.util.Scanner;


public class App {
    
    // För att avboka
    static void avbokning() {

    }

    // För att boka plats
    static void bokningssystem() {
        int antalLedigaPlatser = 20;
        char[][] platser = new char[5][4]; // Array med 5 rader och 4 kolumner
    
        for (int i = 0; i < platser.length; i++) {
            for (int j = 0; j < platser[i].length; j++) {
                if (j == 0 || j == 3) {
                    platser[i][j] = 'F'; // 'F' representerar en fönsterplats
                } else {
                    platser[i][j] = 'G'; // 'G' representerar en gångplats
                }
            }
        }
    
        Scanner tb = new Scanner(System.in);
    
        while (true) {
            System.out.println("Ange ditt namn:");
            String namn = tb.nextLine();
    
            System.out.println("Ange ditt personnummer:");
            String personnummer = tb.nextLine();
    
            System.out.println("Vill du boka en fönsterplats, gångplats eller avsluta? (1: fönster 2: gång 3: avsluta)");
            int val = tb.nextInt();
            tb.nextLine(); // För att konsumera ny rad, undiva skippad inmatning från användaren
    
            switch (val) {
                case 1: // Fönsterplats
                    if (antalLedigaPlatser == 0) {
                        System.out.println("Tyvärr, det finns inga tillgängliga platser");
                    } else {
                        int[] plats = hittaLedigPlats(platser, 'F');
                        if (plats != null) {
                            platser[plats[0]][plats[1]] = 'X'; // 'X' representerar en upptagen plats
                            antalLedigaPlatser--;
                            int platsnummer = (plats[0] * platser[0].length + plats[1] + 1);
                            int radnummer = platsnummer / platser[0].length;
                            int kolumnnummer = platsnummer % platser[0].length;
                            System.out.println("Din bokning lyckades för (" + personnummer + ", " + namn + ")");
                            System.out.println("Din platsnummer är: " + ((radnummer * platser[0].length) + kolumnnummer));
                            skrivUtPlatser(platser);
                        } else {
                            System.out.println("Tyvärr, inga tillgängliga fönsterplatser finns");
                        }
                    }
                    break;
    
                case 2: // Gångplats
                    if (antalLedigaPlatser == 0) {
                        System.out.println("Tyvärr, det finns inga tillgängliga platser");
                    } else {
                        int[] plats = hittaLedigPlats(platser, 'G');
                        if (plats != null) {
                            platser[plats[0]][plats[1]] = 'X'; // 'X' representerar en upptagen plats
                            antalLedigaPlatser--;
                            int platsnummer = (plats[0] * platser[0].length + plats[1] + 1);
                            int radnummer = platsnummer / platser[0].length;
                            int kolumnnummer = platsnummer % platser[0].length;
                            System.out.println("Din bokning lyckades för (" + personnummer + ", " + namn + ")");
                            System.out.println("Din platsnummer är: " + ((radnummer * platser[0].length) + kolumnnummer));
                            skrivUtPlatser(platser);
                        } else {
                            System.out.println("Tyvärr, inga tillgängliga gångplatser finns");
                        }
                    }
                    break;
    
                case 3: // Avsluta
                    System.out.println("Hejdå…");
                    skrivUtPlatser(platser);
                    return;
    
                default:
                    System.out.println("Ogiltigt val. Välj 1, 2 eller 3");
                    break;
            }
    
            System.out.println("Vill du boka en till plats eller avsluta? (boka/avsluta)");
            String svar = tb.nextLine();
            if (svar.equals("avsluta")) {
                System.out.println("Tack för du bokade hos oss!");
                skrivUtPlatser(platser);
                return;
            }
        }
    }
    
    // Metod som hittar lediga platser
    static int[] hittaLedigPlats(char[][] platser, char typ) {
        for (int i = 0; i < platser.length; i++) {
            for (int j = 0; j < platser[i].length; j++) {
                if (platser[i][j] == typ) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
    
    // Skriver ut en bild på arrayen med antalet platser
    static void skrivUtPlatser(char[][] platser) {
        System.out.println("Platser:");
        for (int i = 0; i < platser.length; i++) {
            for (int j = 0; j < platser[i].length; j++) {
                System.out.print("[" + platser[i][j] + "] ");
            }
            System.out.println();
        }
    }


    static void meny(){
         Scanner tb = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Meny \n 1. Boka \n 2. Hitta plats \n 3. Avboka \n 4. Beräkna vinst \n 5. Avsluta");
            try {
                int startval = Integer.parseInt(tb.nextLine());

                switch (startval) {
                    case 1: //Boka plats
                        bokningssystem();
                        running = false;
                        break;
                    case 2: //hitta plats

                    case 3: //Avboka plats
                        System.out.println("Ditt personnummer krävs för att avboka \n Skriv in ditt personnummer här: ");
                        running = false;
                        break;
                    case 4: //Beräkna vinst
                        System.out.println("Den totala vinsten är...");
                        running = false;
                        break;
                    case 5: //Avsluta
                        System.out.println("Programmet avslutas...");
                        running = false;
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

