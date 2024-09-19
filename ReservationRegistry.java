import java.util.HashMap;

public class ReservationRegistry {
    private static ReservationRegistry instance = null;

    private final HashMap<String, Reservation> registry;

    private ReservationRegistry() {
        registry = new HashMap<>();
    }

    public static synchronized ReservationRegistry getReservationRegistry() {
        if (instance == null) {
            instance = new ReservationRegistry();
        }

        return instance;
    }

    public static synchronized Reservation getReservation(String reservationID) {
        if (instance == null) {
            instance = new ReservationRegistry();
        }

        return instance.registry.getOrDefault(reservationID, null);
    }

    public static boolean containsReservation(String reservationId) {
        if (instance == null) {
            instance = new ReservationRegistry();
        }

        return instance.registry.containsKey(reservationId);
    }


    public static void requestReservation(String customerId, String restaurantId, int partySize, String reservationDate, String reservationTime, int creditsAwarded) {
        if (instance == null) {
            instance = new ReservationRegistry();
        }
        try {
            new Reservation(customerId, restaurantId, partySize, reservationDate,
                    reservationTime, creditsAwarded, false);
        } catch (Exception e) {
            if (e.getMessage().isEmpty()) {
                return;
            }
            System.out.println(e.getMessage());
        }
    }

    public static void addReservation(String reservationId, Reservation reservation) {
        if (instance == null) {
            instance = new ReservationRegistry();
        }

        instance.registry.put(reservationId, reservation);
    }

    public static void requestReservationGUI(String customerId, String restaurantId, int partySize, String reservationDate, String reservationTime, int creditsAwarded) throws Exception {
        if (instance == null) {
            instance = new ReservationRegistry();
        }
        new Reservation(customerId, restaurantId, partySize, reservationDate, reservationTime, creditsAwarded, false);
    }
}
