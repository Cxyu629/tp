package seedu.address.model.contact;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Tests that a {@code Contact}'s passes every predicate in a set of {@code Predicate<Contact>}.
 */
public class ConjunctiveContactPredicateSet extends ContactPredicateSet {
    /**
     * Creates an empty {@code ConjunctiveContactPredicateSet}.
     */
    public ConjunctiveContactPredicateSet() {
        super();
    }

    /**
     * Creates a {@code ConjunctiveContactPredicateSet} from a set of {@code Predicate<Contact>}.
     */
    public ConjunctiveContactPredicateSet(Set<Predicate<Contact>> contactPredicates) {
        super(contactPredicates);
    }

    @Override
    public boolean test(Contact contact) {
        return getPredicates().stream().allMatch(predicate -> predicate.test(contact));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ConjunctiveContactPredicateSet otherPredicate)) {
            return false;
        }

        return getPredicates().equals(otherPredicate.getPredicates());
    }

    @Override
    public String toString() {
        return getPredicates().stream().map(Predicate::toString).collect(Collectors.joining("&"));
    }
}
