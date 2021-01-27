/*
 * NAME: Diane Li
 * PID: A5773774
 */
import java.util.ArrayList;
import java.util.List;

/**
 * A class that extends the abstract class User for users who are limited in fetching messages
 *
 * @author Diane Li
 * @since ${01/24/2021}
 */
public class StandardUser extends User {

    // Message to append when fetching non-text message
    private static final String FETCH_DENIED_MSG =
            "This message cannot be fetched because you are not a premium user.";
    private static final int MAX_MSGS_FETCHED = 100;

    /**
     * A constructor that initializes the username, bio, and message exchange room
     * @param username String representing user's name
     * @param bio String representing user's bio
     * @throws IllegalArgumentException if the username or bio is null
     */
    public StandardUser(String username, String bio) { super(username, bio); }

    /**
     * A method that fetches messages from the log of the MessageExchange, appending the contents
     * of the messages and preserving the order. Standard users can only fetch the last 100
     * messages and view the messages of type TextMessage
     * @param me MessageExchange to fetch message log from
     * @return String composed of the message contents
     * @throws IllegalArgumentException if the inputted MessageExchange parameter is null
     * @throws IllegalArgumentException if the user is not in the room me
     */
    public String fetchMessage(MessageExchange me) {
        if (me == null) { throw new IllegalArgumentException(); }
        else if (!me.getUsers().contains(this)) { throw new IllegalArgumentException(); }

        List<Message> log = me.getLog();
        int logLen = log.size();
        // takes last 100 messages if the log has more than that
        if (logLen > MAX_MSGS_FETCHED) { log = log.subList(logLen - MAX_MSGS_FETCHED, logLen); }

        String output = "";
        for (Message message : log) {
            if (message instanceof TextMessage) { output += message.getContents(); }
            else { output += FETCH_DENIED_MSG; }
            output += "\n";
        }
        return output;
    }

    /**
     * A method that returns the user's username
     * @return String username of user
     */
    public String displayName() { return this.username; }
}
