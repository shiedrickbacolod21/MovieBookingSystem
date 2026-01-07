package moviesystem;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieBookingSystemTest {

    private MovieBookingSystem system;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        system = new MovieBookingSystem();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.out.println(outContent.toString());
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("=== All tests are done ===");
    }

    @Test
    @Order(1)
    @DisplayName("Test Case 1: Check availability for valid showtime")
    void testCheckAvailability_WhenValidShowtime_ReturnAvailableTickets() {
        system.checkAvailability("10:00 AM");

        assertTrue(outContent.toString().contains("=== Showtime: 10:00 AM =="));
        assertEquals(50, system.getAvailableTickets("10:00 AM"),
                "Initial available tickets should be 50 for 10:00 AM");
    }

    @Test
    @Order(2)
    @DisplayName("Test Case 2: Check availability for invalid showtime")
    void testCheckAvailability_WhenInvalidShowtime_ReturnShowtimeNotFound() {
        system.checkAvailability("5:00 PM");

        assertTrue(outContent.toString().contains("Showtime 5:00 PM not found."));
    }

    @Test
    @Order(3)
    @DisplayName("Test Case 3: Book tickets for a valid showtime")
    void testBookTicket_WhenValidShowtime_ReturnBookSuccessfully() {
        system.bookTicket("10:00 AM", 5);

        assertTrue(outContent.toString()
                .contains("5 tickets successfully booked for 10:00 AM"));
        assertEquals(45, system.getAvailableTickets("10:00 AM"),
                "Available tickets should decrease by 5 after booking");
        assertEquals(5, system.getBookedTickets("10:00 AM"),
                "Booked tickets should increase by 5 after booking");
    }

    @Test
    @Order(4)
    @DisplayName("Test Case 4: Book tickets for invalid showtime")
    void testBookTicket_WhenInvalidShowtime_ReturnShowtimeNotFound() {
        system.bookTicket("8:00 PM", 4);

        assertTrue(outContent.toString().contains("Showtime 8:00 PM not found."));
    }

    @Test
    @Order(5)
    @DisplayName("Test Case 5: Try booking more tickets than available")
    void testBookTicket_WhenExceedAvailableTickets_ReturnNotEnoughTickets() {
        system.bookTicket("10:00 AM", 100);

        assertTrue(outContent.toString()
                .contains("Not enough tickets available for this showtime"));
        assertEquals(50, system.getAvailableTickets("10:00 AM"),
                "Available tickets should not change if booking exceeds capacity");
        assertEquals(0, system.getBookedTickets("10:00 AM"),
                "Booked tickets should not change if booking exceeds capacity");
    }

    @Test
    @Order(6)
    @DisplayName("Test Case 6: Cancel tickets for a valid showtime")
    void testCancelReservation_WhenValidShowtime_ReturnCancelSuccessfully() {
        system.bookTicket("10:00 AM", 5);
        outContent.reset();

        system.cancelReservation("10:00 AM", 3);

        assertTrue(outContent.toString()
                .contains("3 tickets successfully canceled for 10:00 AM"));
    }

    @Test
    @Order(7)
    @DisplayName("Test Case 7: Cancel reservation with invalid showtime")
    void testCancelReservation_WhenInvalidShowtime_ReturnShowtimeNotFound() {
        system.cancelReservation("9:00 PM", 2);

        assertTrue(outContent.toString().contains("Showtime 9:00 PM not found"));
    }

    @Test
    @Order(8)
    @DisplayName("Test Case 8: Book tickets for another showtime")
    void testBookTicket_WhenAnotherShowtime_ReturnBookSuccessfully() {
        system.bookTicket("1:00 PM", 2);

        assertTrue(outContent.toString()
                .contains("2 tickets successfully booked for 1:00 PM"));
    }

    @Test
    @Order(9)
    @DisplayName("Test Case 9: Try to cancel more tickets than booked")
    void testCancelReservation_WhenExceedBookedTickets_ReturnInvalidOperation() {
        system.bookTicket("1:00 PM", 2);
        outContent.reset();

        system.cancelReservation("1:00 PM", 5);

        assertTrue(outContent.toString().contains(
                "Invalid operation (Attempt to cancel more tickets than booked)"));
    }
    
    @Test
    @Order(10)
    @DisplayName("Book 0 tickets (invalid)")
    void testBookZeroTicket_WhenBookZeroTicket_ReturnInvalidMessage() {
        system.bookTicket("10:00 AM", 0);
        assertTrue(outContent.toString()
                .contains("[BOOK] Invalid number of tickets: 0"));
    }

    @Test
    @Order(11)
    @DisplayName("Book negative tickets (invalid)")
    void testBookNegativeTicket_WhenBookNegativeTicket_ReturnInvalidMessage() {
        system.bookTicket("10:00 AM", -5);
        assertTrue(outContent.toString()
                .contains("[BOOK] Invalid number of tickets: -5"));
    }

    @Test
    @Order(12)
    @DisplayName("Cancel 0 tickets (invalid)")
    void testCancelZeroTicket_WhenCancelZeroTicket_ReturnInvalidMessage() {
        system.cancelReservation("10:00 AM", 0);
        assertTrue(outContent.toString()
                .contains("[CANCEL] Invalid number of tickets: 0"));
    }

    @Test
    @Order(13)
    @DisplayName("Cancel negative tickets (invalid)")
    void testCancelNegativeTicket_WhenCancelNegativeTicket_ReturnInvalidMessage() {
        system.cancelReservation("10:00 AM", -3);
        assertTrue(outContent.toString()
                .contains("[CANCEL] Invalid number of tickets: -3"));
    }
    
    @Test
    @Order(14)
    @DisplayName("Book ticket with invalid showtime format")
    void testBookInvalidShowTimeFormat_WhenBookInvalidShowTimeFormat_ReturnInvalidMessage() {
        system.bookTicket("TenOclock AM", 5);

        assertTrue(
                outContent.toString()
                        .contains("[BOOK] Invalid showtime format: TenOclock AM")
        );
    }

    @Test
    @Order(15)
    @DisplayName("Cancel reservation with invalid showtime format")
    void testCancelInvalidShowTimeFormat_WhenCancelInvalidShowTimeFormat_ReturnInvalidMessage() {
        system.cancelReservation("OneOclock PM", 3);

        assertTrue(
                outContent.toString()
                        .contains("[CANCEL] Invalid showtime format: OneOclock PM")
        );
    }

    @Test
    @DisplayName("Main Function")
    void testMain_ReturnVoid() {
        MovieBookingSystem.main(null);
    }

}
