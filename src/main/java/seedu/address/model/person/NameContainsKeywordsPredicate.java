package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.Objects;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 * Can optionally filter by buyer or seller role with /b or /s flag.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final String roleFilter;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.roleFilter = null;
    }

    public NameContainsKeywordsPredicate(List<String> keywords, String roleFilter) {
        this.keywords = keywords;
        this.roleFilter = roleFilter;
    }

    @Override
    public boolean test(Person person) {
        if (roleFilter != null) {
            boolean roleMatches = person.getRole().value.toLowerCase().equals(roleFilter.toLowerCase());
            if (!roleMatches) {
                return false;
            }
        }

        if (keywords.isEmpty()) {
            return true;
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
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
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords)
                && Objects.equals(roleFilter, otherNameContainsKeywordsPredicate.roleFilter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).add("roleFilter", roleFilter).toString();
    }
}
