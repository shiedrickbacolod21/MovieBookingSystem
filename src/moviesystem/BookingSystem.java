package moviesystem;

public abstract class BookingSystem {
    /**
     * Checks ticket availability for a given showtime.
     *
     * @param showTime the movie showtime
     */
    public abstract void checkAvailability(String showTime);

    /**
     * Books tickets for a given showtime.
     *
     * @param showTime the movie showtime
     * @param tickets  the number of tickets to book
     */
    public abstract void bookTicket(String showTime, int tickets);

    /**
     * Cancels a reservation for a given showtime.
     *
     * @param showTime the movie showtime
     * @param tickets  the number of tickets to cancel
     */
    public abstract void cancelReservation(String showTime, int tickets);
}
