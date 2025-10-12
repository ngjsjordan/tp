package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s details match any of the keywords given.
 * Searches across name, role, address, email, phone, and tags.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> matchesAnyField(person, keyword));
    }

    /**
     * Checks if the keyword matches any field of the person.
     */
    private boolean matchesAnyField(Person person, String keyword) {
        return StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                || StringUtil.containsWordIgnoreCase(person.getRole().value, keyword)
                || StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword)
                || StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword)
                || StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword)
                || person.getTags().stream()
                    .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
