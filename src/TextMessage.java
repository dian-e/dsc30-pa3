/*
 * NAME: Diane Li
 * PID: A5773774
 */

/**
 * A class that extends Message and allows all users to send texts that don't exceed 500 in length
 *
 * @author Diane Li
 * @since ${01/23/2021}
 */
public class TextMessage extends Message {

    private static final int MAX_MESSAGE_LEN = 500;
    // Error message to use in OperationDeniedException
    private static final String EXCEED_MAX_LENGTH =
            "Your input exceeded the maximum length limit.";

    /**
     * A constructor that initializes the message sender and contents
     * @param sender User who sends the message
     * @param text String to be initialized as the message contents
     * @throws OperationDeniedException with message if the length of the inputted text exceeds 500
     * @throws IllegalArgumentException if the sender or text parameter is null
     */
    public TextMessage(User sender, String text) throws OperationDeniedException {
        super(sender);
        if (text == null) {
            throw new IllegalArgumentException();
        } else if (text.length() > MAX_MESSAGE_LEN) {
            throw new OperationDeniedException(EXCEED_MAX_LENGTH);
        }
        this.contents = text;
    }

    /**
     * A method that returns the message's sender name, date, and text
     * @return String output with the sender's display name, date to String, and text contents
     */
    public String getContents() {
        String senderOut = getSender().displayName();
        String dateOut = getDate().toString();
        String output = senderOut + " [" + dateOut + "]: " + this.contents;
        return output;
    }

}
