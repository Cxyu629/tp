package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Notes;

public class NotesCommandTest {
    private static final Notes NOTES = new Notes("Lorem ipsum");

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_success() {
        Contact contactToEdit = model.getFilteredContactList().get(0);
        Contact editedContact = new Contact(contactToEdit.getName(), contactToEdit.getPhone(), contactToEdit.getEmail(),
                contactToEdit.getAddress(), NOTES, contactToEdit.getTags());
        NotesCommand notesCommand = new NotesCommand(INDEX_FIRST_CONTACT, NOTES);
        String expectedMessage = String.format(NotesCommand.MESSAGE_ADD_NOTES_SUCCESS,
                Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getFilteredContactList().get(0), editedContact);

        assertCommandSuccess(notesCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidContactIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        NotesCommand notesCommand = new NotesCommand(outOfBoundIndex, NOTES);

        assertCommandFailure(notesCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final NotesCommand standardCommand = new NotesCommand(INDEX_FIRST_CONTACT, NOTES);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new NotesCommand(INDEX_SECOND_CONTACT, NOTES)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new NotesCommand(INDEX_FIRST_CONTACT, new Notes("Likes ice cream"))));
    }
}
