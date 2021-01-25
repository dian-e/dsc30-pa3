/*
 * NAME: Diane Li
 * PID: A5773774
 */

/**
 * An abstract class that implements instance variables: sender of the message, the time the
 * message is created, and the contents of the message. Also implements 2 getters for subclasses
 *
 * @author Diane Li
 * @since ${01/23/2021}
 */

import java.time.LocalDate;

public abstract class Message {

    // Error message to use in OperationDeniedException
    protected static final String DENIED_USER_GROUP =
            "This operation is disabled in your user group.";

    // instance variables
    private LocalDate date;
    private User sender;
    protected String contents;

    /**
     * A constructor that sets the sender and date (local time) fields
     * @param sender User who sends the message
     * @throws IllegalArgumentException if the sender is null
     */
    public Message(User sender) {
        if (sender == null) { throw new IllegalArgumentException(); }
        this.date = LocalDate.now();
        this.sender = sender;
    }

    /**
     * A method that returns the date of the message
     * @return LocalDate formatted time the message was sent
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * A method that returns the sender of the message
     * @return User object who sent the message
     */
    public User getSender() { return this.sender; }

    /**
     * An abstract method that returns the contents of the Message as a String for the subclass
     * @return String containing Message contents specific to subclass
     */
    public abstract String getContents();

}