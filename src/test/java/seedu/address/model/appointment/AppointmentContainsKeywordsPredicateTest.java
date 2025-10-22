package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AppointmentContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AppointmentContainsKeywordsPredicate firstPredicate = 
            new AppointmentContainsKeywordsPredicate(firstPredicateKeywordList);
        AppointmentContainsKeywordsPredicate secondPredicate = 
            new AppointmentContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AppointmentContainsKeywordsPredicate firstPredicateCopy = 
            new AppointmentContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_appointmentContainsKeywords_returnsTrue() {
        Appointment testAppointment = new Appointment(
            new AppointmentDatetime("2025-01-01T12:00"), ALICE, BOB);

        // One keyword matching seller name
        AppointmentContainsKeywordsPredicate predicate = 
            new AppointmentContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(testAppointment));

        // One keyword matching buyer name
        predicate = new AppointmentContainsKeywordsPredicate(Collections.singletonList("Bob"));
        assertTrue(predicate.test(testAppointment));

        // One keyword matching datetime
        predicate = new AppointmentContainsKeywordsPredicate(Collections.singletonList("2025-01-01T12:00"));
        assertTrue(predicate.test(testAppointment));

        // One keyword matching seller address
        predicate = new AppointmentContainsKeywordsPredicate(Collections.singletonList("Jurong"));
        assertTrue(predicate.test(testAppointment));

        // Multiple keywords
        predicate = new AppointmentContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(testAppointment));

        // Mixed-case keywords
        predicate = new AppointmentContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(testAppointment));

        // Partial keyword matching seller name
        predicate = new AppointmentContainsKeywordsPredicate(Collections.singletonList("Ali"));
        assertFalse(predicate.test(testAppointment));

        // Keywords matching different fields
        predicate = new AppointmentContainsKeywordsPredicate(Arrays.asList("Alice", "Jurong", "2025-01-01T12:00"));
        assertTrue(predicate.test(testAppointment));
    }

    @Test
    public void test_appointmentDoesNotContainKeywords_returnsFalse() {
        Appointment testAppointment = new Appointment(
            new AppointmentDatetime("2025-01-01T12:00"), ALICE, BOB);

        // Zero keywords
        AppointmentContainsKeywordsPredicate predicate = 
            new AppointmentContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(testAppointment));

        // Non-matching keyword
        predicate = new AppointmentContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(testAppointment));

        // Keywords that don't match any field
        predicate = new AppointmentContainsKeywordsPredicate(Arrays.asList("NonExistent", "NotFound"));
        assertFalse(predicate.test(testAppointment));

        // Keyword that doesn't match datetime
        predicate = new AppointmentContainsKeywordsPredicate(Arrays.asList("2024-12-31"));
        assertFalse(predicate.test(testAppointment));

        // Keyword that doesn't match address
        predicate = new AppointmentContainsKeywordsPredicate(Arrays.asList("Downtown"));
        assertFalse(predicate.test(testAppointment));
    }

    @Test
    public void test_emptyKeywordList_returnsFalse() {
        Appointment testAppointment = new Appointment(
            new AppointmentDatetime("2025-01-01T12:00"), ALICE, BOB);
        AppointmentContainsKeywordsPredicate predicate = 
            new AppointmentContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(testAppointment));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        AppointmentContainsKeywordsPredicate predicate = 
            new AppointmentContainsKeywordsPredicate(keywords);

        String expected = AppointmentContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
