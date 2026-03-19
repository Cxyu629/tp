package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ContactBuilder;

public class ContactTagComparatorTest {
    private static final Contact JOHN = new ContactBuilder()
        .withName("John Smith")
        .withPhone("94455028").build();
    private static final Contact JANE = new ContactBuilder()
        .withName("Jane Doe")
        .withPhone("94455029")
        .withTags("friends", "contractor").build();
    private static final Contact TOM = new ContactBuilder()
        .withName("Tom D Harry")
        .withPhone("94455030")
        .withTags("friends:1").build();
    private static final Contact MAX = new ContactBuilder()
        .withName("Maximus Primus Secundus")
        .withPhone("94455031")
        .withTags("friends:3").build();
    private static final ContactTagComparator COMPARATOR =
        new ContactTagComparator("friends", ContactComparator.Order.DESCENDING);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContactTagComparator(null, null));
    }

    @Test
    public void compare_equal_returnsZero() {
        assertEquals(0, COMPARATOR.compare(JOHN, JOHN));
        assertEquals(0, COMPARATOR.compare(JANE, JANE));
        assertEquals(0, COMPARATOR.compare(TOM, TOM));
        assertEquals(0, COMPARATOR.compare(MAX, MAX));
    }

    @Test
    public void compare_noTag_isCorrect() {
        assertTrue(COMPARATOR.compare(JOHN, JANE) > 0);
        assertTrue(COMPARATOR.compare(JANE, JOHN) < 0);

        assertTrue(COMPARATOR.compare(JOHN, TOM) > 0);
        assertTrue(COMPARATOR.compare(TOM, JOHN) < 0);

        assertTrue(COMPARATOR.compare(JOHN, MAX) > 0);
        assertTrue(COMPARATOR.compare(MAX, JOHN) < 0);
    }

    @Test
    public void compare_noRank_isCorrect() {
        assertTrue(COMPARATOR.compare(JANE, TOM) > 0);
        assertTrue(COMPARATOR.compare(TOM, JANE) < 0);

        assertTrue(COMPARATOR.compare(JANE, MAX) > 0);
        assertTrue(COMPARATOR.compare(MAX, JANE) < 0);
    }

    @Test
    public void compare_lessRank_returnsPositive() {
        assertTrue(COMPARATOR.compare(TOM, MAX) > 0);
        assertTrue(COMPARATOR.compare(MAX, TOM) < 0);
    }

    @Test
    public void hashCode_sameTag_returnsSameHashCode() {
        ContactTagComparator comparator1 = new ContactTagComparator("friends", ContactComparator.Order.ASCENDING);
        ContactTagComparator comparator2 = new ContactTagComparator("friends", ContactComparator.Order.DESCENDING);
        assertEquals(comparator1.hashCode(), comparator2.hashCode());
    }
}
