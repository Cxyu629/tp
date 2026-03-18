package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.logic.parser.TimePointParser;
import seedu.address.model.timepoint.TimePoint;

/**
 * Represents a contact's last updated time.
 * Guarantees: immutable; value is non-blank.
 */
public class LastUpdated {
    public static final String MESSAGE_CONSTRAINTS = "Last updated time should not be blank.";

    public final TimePoint<?> value;

    /**
     * Constructs a {@code LastUpdated} from raw user input.
     *
     * @param rawLastUpdated A non-blank last updated value.
     */
    public LastUpdated(String rawLastUpdated) {
        requireNonNull(rawLastUpdated);
        String trimmedValue = rawLastUpdated.trim();
        checkArgument(isValidLastUpdated(trimmedValue), MESSAGE_CONSTRAINTS);
        value = TimePointParser.toTimePoint(trimmedValue);
    }

    /**
     * Constructs a {@code LastUpdated} from a {@code TimePoint}.
     *
     * @param timePoint A non-null time point.
     */
    public LastUpdated(TimePoint<?> timePoint) {
        requireNonNull(timePoint);
        value = timePoint;
    }

    /**
     * Returns a new {@code LastUpdated} using the current local date-time.
     */
    public static LastUpdated now() {
        return new LastUpdated(TimePoint.of(LocalDateTime.now()));
    }

    /**
     * Returns true if a given string is a valid last updated input.
     */
    public static boolean isValidLastUpdated(String test) {
        return test != null && !test.trim().isEmpty();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LastUpdated)) {
            return false;
        }
        LastUpdated otherLastUpdated = (LastUpdated) other;
        return value.equals(otherLastUpdated.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value.toString());
    }
}
