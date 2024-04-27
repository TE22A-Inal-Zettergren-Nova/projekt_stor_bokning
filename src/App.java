import java.util.Scanner;

public class App {

    static char[][] platser;
    static String[] bokadeNamn;
    static String[] personnummer;
    static Scanner tb = new Scanner(System.in);


    // För att hittaplats
    static int hittaBokadPlats(String info) {
        for (int i = 0; i < bokadeNamn.length; i++) {
            if (info.equals(bokadeNamn[i]) || info.equals(personnummer[i])) {
                return i + 1; // Platsnumret är index + 1
            }
        }
        return -1; // Returnera -1 om ingen bokad plats hittas
    }
    

    // För att avboka
    static void avbokning() {
        System.out.println("Ange ditt namn eller personnummer för avbokning:");
        String avbokningsinfo = tb.nextLine();

        for (int i = 0; i < bokadeNamn.length; i++) {
            if (avbokningsinfo.equals(bokadeNamn[i]) || avbokningsinfo.equals(personnummer[i])) {
                int radnummer = i / platser[0].length;
                int kolumnnummer = i % platser[0].length;
                platser[radnummer][kolumnnummer] = 'G'; // Återställ platsen till ledig
                bokadeNamn[i] = null; // Ta bort namnet från listan över bokade
                personnummer[i] = null; // Ta bort personnumret från listan över bokade
                System.out.println("Platsen" + platser[radnummer][kolumnnummer] + "har avbokats för " + avbokningsinfo);
                meny();

            }else{
                System.out.println("Ingen bokning hittades för angivet namn eller personnummer.");}
            }
        }

    // För att boka plats
    static void bokningssystem() {
        int antalLedigaPlatser = 20;
        platser = new char[5][4]; // Array med 5 rader och 4 kolumner
        bokadeNamn = new String[platser.length * platser[0].length]; // Array för namn på bokade
        personnummer = new String[platser.length * platser[0].length]; // Array för personnummer på bokade

        for (int i = 0; i < platser.length; i++) {
            for (int j = 0; j < platser[i].length; j++) {
                if (j == 0 || j == 3) {
                    platser[i][j] = 'F'; // 'F' representerar en fönsterplats
                } else {
                    platser[i][j] = 'G'; // 'G' representerar en gångplats
                }
            }
        }


        while (true) {
            System.out.println("Ange ditt namn:");
            String namn = tb.nextLine();

            System.out.println("Ange ditt personnummer:");
            String pnr = tb.nextLine();

            System.out.println("Vill du boka en (1) fönsterplats eller en (2) gångplats?");
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
                            bokadeNamn[platsnummer - 1] = namn; // Lägg till bokad namn på platsen
                            personnummer[platsnummer - 1] = pnr; // Lägg till personnummer på platsen
                            System.out.println("Din bokning lyckades för (" + pnr + ", " + namn + ")");
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
                            bokadeNamn[platsnummer - 1] = namn; // Lägg till bokad namn på platsen
                            personnummer[platsnummer - 1] = pnr; // Lägg till personnummer på platsen
                            System.out.println("Din bokning lyckades för (" + pnr + ", " + namn + ")");
                            System.out.println("Din platsnummer är: " + ((radnummer * platser[0].length) + kolumnnummer));
                            skrivUtPlatser(platser);
                        } else {
                            System.out.println("Tyvärr, inga tillgängliga gångplatser finns");
                        }
                    }
                    break;

                default:
                    System.out.println("Ogiltigt val. Välj 1 eller 2");
                    break;
            }

            System.out.println("Vill du boka en till plats eller gå tillbaka till menyn? (boka/meny)");
            String svar = tb.nextLine();
            if (svar.equals("meny")) {
                skrivUtPlatser(platser);
                meny();
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

    static void meny() {
        boolean running = true;

        while (running) {
            System.out.println("Meny \n 1. Boka \n 2. Avboka \n 3. Hitta plats \n 4. Beräkna vinst \n 5. Avsluta");
            try {
                int startval = Integer.parseInt(tb.nextLine());

                switch (startval) {
                    case 1: //Boka plats
                        bokningssystem();
                        running = false;
                        break;
                    case 2: //Avboka plats
                        avbokning();
                        running = false;
                        break;
                    case 3: //Hitta plats
                        System.out.println("Ange ditt namn eller personnummer för att hitta din plats:");
                        String info = tb.nextLine();
                        int platsnummer = hittaBokadPlats(info);
                        if (platsnummer != -1) {
                            System.out.println("Du har plats: " + platsnummer);} 
                        else {
                            System.out.println("Ingen bokning hittades för angivet namn eller personnummer.");
                            meny();
                        }
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
