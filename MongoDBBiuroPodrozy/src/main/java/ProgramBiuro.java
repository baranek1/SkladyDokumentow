import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

import static com.mongodb.client.model.Filters.*;

public class ProgramBiuro {
    static Scanner scanner;
    static boolean koniec = false;

    private static String user = "datebase02";
    private static String password = "datebase02";
    private static String host = "localhost";
    private static int port = 27017;
    private static String datebase = "Podroze";

    private static MongoCollection<Document> collection;

    private static void initialize() {
        MongoClient mongoClient = new MongoClient(host, port);
        MongoCredential credential = MongoCredential.createCredential(user, datebase, password.toCharArray());

        MongoDatabase db = mongoClient.getDatabase(datebase);

        db.getCollection("biuropodrozy").drop();

        collection = db.getCollection("biuropodrozy");
    }
    static private void akcja(int wybor) {
        switch (wybor) {
            case 1:
                addNew();
                break;
            case 2:
                showAll();
                break;
            case 3:
                showOneElement();
                break;
            case 4:
                update();
                break;
            case 5:
                deleteOne();
                break;
            case 0:
                koniec = true;
                break;
            default:
                System.out.println("BŁĄD");
        }
    }

    static void menu() {
        while (!koniec) {
            showMenu();
            int wybor = getWyborMenu();
            akcja(wybor);
        }
    }
    private static void addNew() {
        Wycieczka wycieczka = new Wycieczka();
        System.out.println("\n Podaj ID wycieczki:");
        wycieczka.id = Integer.parseInt(scanner.nextLine());

        System.out.println(" Podaj date wizyty: ");
        wycieczka.data = scanner.nextLine();

        System.out.println(" Podaj ilość dni: ");
        wycieczka.iloscDni = Integer.parseInt(scanner.nextLine());

        System.out.println(" Podaj cene: ");
        wycieczka.cena = scanner.nextLine();

        System.out.println(" Podaj kraj: ");
        wycieczka.kraj = scanner.nextLine();

        System.out.println(" Podaj miasto: ");
        wycieczka.miasto = scanner.nextLine();

        Document wyc = new Document("id", wycieczka.id)
                .append("data", wycieczka.data)
                .append("ilość dni", wycieczka.iloscDni)
                .append("cena", wycieczka.cena)
                .append("miejsce",
                        new Document("kraj", wycieczka.kraj)
                                .append("miasto", wycieczka.miasto)
                );

        collection.insertOne(wyc);
    }

    private static void startData() {
        Wycieczka w1 = new Wycieczka();
        w1.id = 1;
        w1.data = "2021-05-04";
        w1.iloscDni = 6;
        w1.cena = "1200 zł";
        w1.kraj = "Malta";
        w1.miasto = "Marfa";
        Document d1 = new Document("id", w1.id)
                .append("data", w1.data)
                .append("ilosc dni", w1.iloscDni)
                .append("cena", w1.cena)
                .append("miejsce",
                        new Document("kraj", w1.kraj)
                                .append("miasto", w1.miasto)

                );
        collection.insertOne(d1);

        Wycieczka w2 = new Wycieczka();
        w2.id = 2;
        w2.data = "2020-07-18";
        w2.iloscDni =6;
        w2.cena = "516 zł";
        w2.kraj = "Chorwacja";
        w2.miasto = "Porec";
        Document d2 = new Document("id", w2.id)
                .append("data", w2.data)
                .append("ilosc dni", w2.iloscDni)
                .append("cena", w2.cena)
                .append("miejsce",
                        new Document("kraj", w2.kraj)
                                .append("miasto", w2.miasto)

                );
        collection.insertOne(d2);

        Wycieczka w3 = new Wycieczka();
        w3.id = 3;
        w3.data = "2021-05-06";
        w3.iloscDni = 4;
        w3.cena = "598 zł";
        w3.kraj = "Bulgaria";
        w3.miasto = "Zlote Piaski";
        Document d3 = new Document("id", w3.id)
                .append("data", w3.data)
                .append("ilosc dni", w3.iloscDni)
                .append("cena", w3.cena)
                .append("miejsce",
                        new Document("kraj", w3.kraj)
                                .append("miasto", w3.miasto)

                );
        collection.insertOne(d3);

        Wycieczka w4 = new Wycieczka();
        w4.id = 4;
        w4.data = "2020-07-18";
        w4.iloscDni = 9;
        w4.cena = "1413 zł";
        w4.kraj = "Wlochy";
        w4.miasto = "Villaputzo";
        Document d4 = new Document("id", w4.id)
                .append("data", w4.data)
                .append("ilosc dni", w4.iloscDni)
                .append("cena", w4.cena)
                .append("miejsce",
                        new Document("kraj", w4.kraj)
                                .append("miasto", w4.miasto)

                );
        collection.insertOne(d4);


    };

    private static void showAll() {
        System.out.println("\n Wszystkie wycieczki:");
        for (Document doc : collection.find()) {
            System.out.println(doc.toJson());
        }
    }

    private static void showOneElement() {
        System.out.println("Podaj id wyieczki:");
        int id = Integer.parseInt(scanner.nextLine());
        Document doc = collection.find(lte("id", id)).first();
        System.out.println(doc.toJson());
    }

    private static void update() {
        System.out.println("Podaj id  wycieczki:");
        int id = Integer.parseInt(scanner.nextLine());
        Document doc = collection.find(lte("id", id)).first();
        System.out.println(doc.toJson());

        System.out.println("ABY POMINĄĆ WCIŚNIJ 0");
        System.out.println("Zmien id:");
        String idNew = scanner.nextLine();
        if (idNew.equals("0") == false) {
            int test=Integer.parseInt(idNew);
            collection.updateOne(eq("id", id),
                    new Document("$set",
                            new Document("id", test)
                    )
            );
        }

        System.out.println("Zmien date");
        String dataNew = scanner.nextLine();
        if (dataNew.equals("0") == false) {
            collection.updateOne(eq("id", id),
                    new Document("$set",
                            new Document("data", dataNew)
                    )
            );
        }

        System.out.println("Zmien ilosc dni:");
        int iloscDniNew = Integer.parseInt(scanner.nextLine());
        if (iloscDniNew != 0) {
            collection.updateOne(eq("id", id),
                    new Document("$set",
                            new Document("iloscDni", iloscDniNew)
                    )
            );
        }

        System.out.println("Zmien cene:");
        String cenaNew = scanner.nextLine();
        if (cenaNew.equals("0") == false) {

            collection.updateOne(eq("id", id),
                    new Document("$set",
                            new Document("cena", cenaNew)
                    )
            );
        }

        System.out.println("Zmien kraj:");
        String krajNew = scanner.nextLine();
        if (krajNew.equals("0") == false) {
            collection.updateOne(eq("id", id),
                    new Document("$set",
                            new Document("miejsce.kraj", krajNew)
                    )
            );
        }

        System.out.println("Zmien miasto:");
        String miastoNew= scanner.nextLine();
        if (miastoNew.equals("0") == false) {
            collection.updateOne(eq("id", id),
                    new Document("$set",
                            new Document("miejsce.miasto", miastoNew)
                    )
            );
        }
    }

    private static void deleteOne() {
        System.out.println("\n Podaj numer id:");
        int id = Integer.parseInt(scanner.nextLine());
        collection.deleteOne(eq("id", id));
        System.out.println("\n Usunięto wycieczkę");
    }

    static private void showMenu() {
        System.out.println("\n**************  Biuro Podróży:  ***************");
        System.out.println("1 - Dodaj nowż wycieczkę ");
        System.out.println("2 - Wyświetl wszystkie wycieczki ");
        System.out.println("3 - Wyświetl wycieczkę po id ");
        System.out.println("4 - Zaktualizuj wycieczkę");
        System.out.println("5 - Usuń wycieczkę");
        System.out.println("0 - WYJŚCIE\n");
    }

    static private int getWyborMenu() {
        int wybor = -1;
        do {
            System.out.println("Podaj wybór:");
            wybor = Integer.parseInt(scanner.nextLine());
            if (wybor < 0 || wybor > 6) {
                System.out.println("Brak takiej opcji!");
            }
        } while (wybor < 0 || wybor > 6);
        return wybor;
    }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);
        scanner = new Scanner(System.in);
        initialize();
        startData();
        menu();
        scanner.nextInt();
    }

}
