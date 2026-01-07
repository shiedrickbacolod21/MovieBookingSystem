package moviesystem;

import java.util.HashMap;
import java.util.Map;

public class MovieBookingSystem extends BookingSystem {

    /** Available tickets per showtime. */
    private Map<String, Integer> availableTickets = new HashMap<>();
    /** Booked tickets per showtime. */
    private Map<String, Integer> bookedTickets = new HashMap<>();
    /** Total capacity per showtime. */
    private final int totalCapacity = 50;

    /**
     * Constructs the movie booking system.
     */
    public MovieBookingSystem() {
        availableTickets.put("10:00 AM", totalCapacity);
        availableTickets.put("1:00 PM", totalCapacity);

        bookedTickets.put("10:00 AM", 0);
        bookedTickets.put("1:00 PM", 0);
    }

    /**
     * Retrieves the number of available tickets for a specific show time.
     *
     * @param showTime the time of the show to check
     * @return the number of available tickets,
     * or 0 if the show time is not found
     */
    public int getAvailableTickets(final String showTime) {
        return availableTickets.getOrDefault(showTime, 0);
    }

    /**
     * Retrieves the number of booked tickets for a specific show time.
     *
     * @param showTime the time of the show to check
     * @return the number of booked tickets,
     * or 0 if the show time is not found
     */
    public int getBookedTickets(final String showTime) {
        return bookedTickets.getOrDefault(showTime, 0);
    }

    @Override
    public final void checkAvailability(final String showTime) {
        if (availableTickets.containsKey(showTime)) {
            System.out.println("=== Showtime: " + showTime
                    + " == Available Ticket: "
                    + availableTickets.get(showTime) + " ===");
        } else {
            System.out.println("Showtime " + showTime + " not found. \n");
        }
    }

    @Override
    public final void bookTicket(final String showTime, final int tickets) {
        if (tickets <= 0) {
            System.out.println("[BOOK] Invalid number of tickets: " + tickets
                    + ". Must be greater than 0.\n");
            return;
        }

        if (!availableTickets.containsKey(showTime)) {
            System.out.println("Showtime " + showTime + " not found. \n");
            return;
        }

        int available = availableTickets.get(showTime);

        if (tickets <= available) {
            availableTickets.put(showTime, available - tickets);
            bookedTickets.put(showTime, bookedTickets.get(showTime) + tickets);

            System.out.println(
                    tickets + " tickets successfully booked for " + showTime);
            System.out.println("=== " + showTime + " -> Available: "
                    + availableTickets.get(showTime) + ", Booked: "
                    + bookedTickets.get(showTime) + " ===\n");
        } else {
            System.out.println("[BOOK] Showtime: " + showTime + " || Ticket: "
                    + tickets
                    + "\nNot enough tickets available for this showtime");
            System.out.println("=== " + showTime + " -> Available: "
                    + availableTickets.get(showTime) + ", Booked: "
                    + bookedTickets.get(showTime) + " ===\n");
        }
    }

    @Override
    public final void cancelReservation(final String showTime,
            final int tickets) {
        if (tickets <= 0) {
            System.out.println("[CANCEL] Invalid number of tickets: " + tickets
                    + ". Must be greater than 0.\n");
            return;
        }

        if (!bookedTickets.containsKey(showTime)) {
            System.out.println("Showtime " + showTime + " not found. \n");
            return;
        }

        int booked = bookedTickets.get(showTime);

        if (tickets <= booked) {
            bookedTickets.put(showTime, booked - tickets);
            availableTickets.put(showTime,
                    availableTickets.get(showTime) + tickets);

            System.out.println(
                    tickets + " tickets successfully canceled for " + showTime);
            System.out.println("=== " + showTime + " -> Available: "
                    + availableTickets.get(showTime) + ", Booked: "
                    + bookedTickets.get(showTime) + " ===\n");
        } else {
            System.out.println("[CANCEL] Showtime: " + showTime + " || Ticket: "
                    + tickets + "\nInvalid operation (Attempt to cancel "
                    + "more tickets than booked)");
            System.out.println("=== " + showTime + " -> Available: "
                    + availableTickets.get(showTime) + ", Booked: "
                    + bookedTickets.get(showTime) + " ===\n");
        }
    }

    /**
     * Display all showtimes and their availability.
     */
    public void displayShowtimes() {
        System.out.println("=== Movie Showtimes ===");
        for (String showtime : availableTickets.keySet()) {
            System.out.println(
                    showtime + " - Available: " + availableTickets.get(showtime)
                            + ", Booked: " + bookedTickets.get(showtime));
        }
        System.out.println("=======================\n");
    }

    /**
     * The main entry point of the Movie Booking System.
     *
     * @param args Main Function.
     */
    public static void main(final String[] args) {

        final int book5 = 5;
        final int book100 = 100;
        final int cancel3 = 3;
        final int book2 = 2;
        final int cancel5 = 5;

        MovieBookingSystem system = new MovieBookingSystem();

        System.out.println("=== Movie Ticket Booking System ===\n");
        system.displayShowtimes();

        // Test Case 1
        system.bookTicket("10:00 AM", book5);

        // Test Case 2
        system.bookTicket("10:00 AM", book100);

        // Test Case 3
        system.cancelReservation("10:00 AM", cancel3);

        // Test Case 4
        system.bookTicket("1:00 PM", book2);

        // Test Case 5
        system.cancelReservation("1:00 PM", cancel5);
    }

}
