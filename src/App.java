import java.util.Scanner;
import java.text.DecimalFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class App {

    static char[][] platser;
    static String[] bokadeNamn;
    static String[] bokadeEfternamn;
    static String[] personnummer;
    static LocalDate[] födelsedatum;
    final static Scanner tb = new Scanner(System.in);


    // Metod för att beräkna vinsten
    static double beräknaVinstRekursivt(int index) {
        if (index >= bokadeNamn.length) {
            return 0; // När ingen bokad plats kvar att behandla
        }

        if (bokadeNamn[index] != null) {
            int ålder = Period.between(födelsedatum[index], LocalDate.now()).getYears();
            double pris = (ålder < 18) ? 149.9 : 299.9;
            return pris + beräknaVinstRekursivt(index + 1); // Adderar priset för bokad plats och går vidare
        } else {
            return beräknaVinstRekursivt(index + 1); // Hoppa över null-platser och gå vidare
        }
    }

    // Metod för att ändra priset till två decimaler
    static String formateraPris(double pris) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(pris);
    }

    // Metod för att kontrollera om personnumret är korrekt
    static boolean ärKorrektPersonnummer(String personnummer) {
        if (personnummer.length() != 8) {
            return false; // Personnumret måste vara åtta siffror långt
        }

        try {
            int year = Integer.parseInt(personnummer.substring(0, 4));
            int month = Integer.parseInt(personnummer.substring(4, 6));
            int day = Integer.parseInt(personnummer.substring(6, 8));

            if (year < 1900 || year > Year.now().getValue()) {
                return false; // Ogiltigt år
            }

            if (month < 1 || month > 12) {
                return false; // Ogiltig månad
            }

            if (day < 1 || day > Month.of(month).length(Year.isLeap(year))) {
                return false; // Ogiltig dag
            }

            return true; // Konverteringen lyckades, personnumret är korrekt
        } catch (NumberFormatException e) {
            return false; // Felaktigt format
        } catch (DateTimeException e) {
            return false; // Ogiltigt datum
        }
    }

    // Konvertera personnummer till födelsedatum
    static LocalDate konverteraTillfödelsedatum(String personnummer) {
        // Personnumret är av formatet YYYYMMDD, de första fyra siffrorna är år, nästa två är månad, och sista två är dag.
        int year = Integer.parseInt(personnummer.substring(0, 4));
        int month = Integer.parseInt(personnummer.substring(4, 6));
        int day = Integer.parseInt(personnummer.substring(6, 8));
        return LocalDate.of(year, month, day);
    }

    // Skriv ut bokade platser med förnamn, efternamn, födelsedatum och platsnummer, sorterat från yngst till äldst
    static void skrivUtBokadePlatser() {
        // Skapa en kopia av bokadeNamn, personnummer och födelsedatum
        String[] kopieradeNamn = bokadeNamn.clone();
        String[] kopieradePnr = personnummer.clone();
        LocalDate[] kopieradefödelsedatum = födelsedatum.clone();

        // Sortera bokade platser efter födelsedatum
        for (int i = 0; i < kopieradefödelsedatum.length - 1; i++) {
            for (int j = 0; j < kopieradefödelsedatum.length - i - 1; j++) {
                if (kopieradefödelsedatum[j] != null && kopieradefödelsedatum[j + 1] != null &&
                    kopieradefödelsedatum[j].isAfter(kopieradefödelsedatum[j + 1])) {
                    LocalDate tempDatum = kopieradefödelsedatum[j];
                    kopieradefödelsedatum[j] = kopieradefödelsedatum[j + 1];
                    kopieradefödelsedatum[j + 1] = tempDatum;

                    String tempNamn = kopieradeNamn[j];
                    kopieradeNamn[j] = kopieradeNamn[j + 1];
                    kopieradeNamn[j + 1] = tempNamn;

                    String tempPnr = kopieradePnr[j];
                    kopieradePnr[j] = kopieradePnr[j + 1];
                    kopieradePnr[j + 1] = tempPnr;
                }
            }
        }

        // Skriv ut bokade platser sorterade efter ålder
        System.out.println("Bokade platser (sorterat efter ålder):");
        for (int i = 0; i < kopieradeNamn.length; i++) {
            if (kopieradeNamn[i] != null) {
                int platsnummer = i + 1;
                System.out.println("Platsnummer: " + platsnummer + ", Namn: " + kopieradeNamn[i] +
                        ", Födelsedatum: " + kopieradefödelsedatum[i].format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        }
    }


    // För att hitta bokad plats
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
                födelsedatum[i] = null; // Ta bort födelsedatumet från listan över bokade
                System.out.println("Platsen" + platser[radnummer][kolumnnummer] + "har avbokats för " + avbokningsinfo);
                meny();

            }else{
                System.out.println("Ingen bokning hittades för angivet namn eller personnummer.");
                meny();
            }
            }
        }

    // För att boka plats
    static void bokningssystem() {
        int antalLedigaPlatser = 20;
        platser = new char[5][4]; // Array med 5 rader och 4 kolumner
        bokadeNamn = new String[platser.length * platser[0].length]; // Array för namn på bokade
        bokadeEfternamn = new String[platser.length * platser[0].length];
        personnummer = new String[platser.length * platser[0].length]; // Array för personnummer på bokade
        födelsedatum = new LocalDate[platser.length * platser[0].length];


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

            System.out.println("Ange ditt efternamn:");
            String efternamn = tb.nextLine();

            String pnr = ""; // Tilldela ett standardvärde
            do {
                System.out.println("Ange ditt personnummer (YYYYMMDD):");
                pnr = tb.nextLine();
                if (!ärKorrektPersonnummer(pnr)) {
                    System.out.println("Ogiltigt personnummer. Försök igen.");
                }
            } while (!ärKorrektPersonnummer(pnr));
        
            // Konvertera personnumret till födelsedatum
            LocalDate födelsedatum = konverteraTillfödelsedatum(pnr);

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
                            bokadeEfternamn[platsnummer -1] = efternamn; // Lägg til efternamn på platsen
                            personnummer[platsnummer - 1] = pnr; // Lägg till personnummer på platsen
                            App.födelsedatum[platsnummer - 1] = födelsedatum; // Lägg till födelsedatum på platsen
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
                            bokadeEfternamn[platsnummer -1] = efternamn; // Lägg til efternamn på platsen (För att sortera)
                            personnummer[platsnummer - 1] = pnr; // Lägg till personnummer på platsen
                            App.födelsedatum[platsnummer - 1] = födelsedatum; // Lägg till födelsedatum på platsen
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
            System.out.println("Meny \n 1. Boka \n 2. Avboka \n 3. Hitta plats \n 4. Bokade platser \n 5. Beräkna vinst \n 6. Avsluta");
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
                    case 4: // Visar bokade platser och vem som bokat (äldst till yngst)
                        skrivUtBokadePlatser();
                        skrivUtPlatser(platser);
                        running = false;
                        break;
                    case 5: // Beräknar vinst 
                        double vinstRekursivt = beräknaVinstRekursivt(0);
                        System.out.println("Total vinst på alla bokade platser: " + formateraPris(vinstRekursivt) + " kr");
                        meny();
                        running = false;
                        break;
                    case 6: // Avsluat
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
