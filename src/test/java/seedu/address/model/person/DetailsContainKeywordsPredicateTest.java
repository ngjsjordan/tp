package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class DetailsContainKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DetailsContainKeywordsPredicate firstPredicate =
                new DetailsContainKeywordsPredicate(firstPredicateKeywordList);
        DetailsContainKeywordsPredicate secondPredicate =
                new DetailsContainKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DetailsContainKeywordsPredicate firstPredicateCopy =
                new DetailsContainKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        DetailsContainKeywordsPredicate predicate =
                new DetailsContainKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords matching name
        predicate = new DetailsContainKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords matching different fields
        predicate = new DetailsContainKeywordsPredicate(Arrays.asList("buyer"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withRole("buyer").build()));

        // Keywords matching phone
        predicate = new DetailsContainKeywordsPredicate(Arrays.asList("12345"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345").build()));

        // Keywords matching email
        predicate = new DetailsContainKeywordsPredicate(Arrays.asList("alice@email.com"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").withEmail("alice@email.com").build()));

        // Keywords matching address and property type
        predicate = new DetailsContainKeywordsPredicate(Arrays.asList("Street"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withAddress("Main Street",
                "HDB_4").build()));

        // Mixed-case keywords
        predicate = new DetailsContainKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords matching tags
        predicate = new DetailsContainKeywordsPredicate(Arrays.asList("friend"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("friend").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DetailsContainKeywordsPredicate predicate = new DetailsContainKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword - no field contains "Carol"
        predicate = new DetailsContainKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street", "HDB_4")
                .withRole("buyer").build()));

        // Keywords that don't match any field
        predicate = new DetailsContainKeywordsPredicate(Arrays.asList("NonExistent", "NotFound"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street", "HDB_4")
                .withRole("buyer").build()));

        // Keyword doesn't match any tags
        predicate = new DetailsContainKeywordsPredicate(Arrays.asList("family"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTags("friend", "colleague").build()));

        // Non-matching keyword
        predicate = new DetailsContainKeywordsPredicate(Arrays.asList("Serangoon"));
        assertFalse(predicate.test(new PersonBuilder()
                .withAddress("Block 442, Clementi Ave 1", "HDB_4").build()));

        // Non-matching property type
        predicate = new DetailsContainKeywordsPredicate(Arrays.asList("LANDED_FH"));
        assertFalse(predicate.test(new PersonBuilder()
                .withAddress("Block 442, Clementi Ave 1", "HDB_4").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        DetailsContainKeywordsPredicate predicate = new DetailsContainKeywordsPredicate(keywords);

        String expected = DetailsContainKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
