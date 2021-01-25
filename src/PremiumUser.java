/*
 * NAME: Diane Li
 * PID: A5773774
 */
import java.util.ArrayList;
import java.util.List;

/**
 * A class that extends the abstract class User for users who are not limited in fetching messages
 * and have a custom title that will always display before their name
 *
 * @author Diane Li
 * @since ${01/24/2021}
 */
public class PremiumUser extends User {

    // instance variable
    private String customTitle;

    /**
     * A constructor that initializes the username, bio, message exchange room, and custom title
     * @param username String representing user's name
     * @param bio String representing user's bio
     * @throws IllegalArgumentException if the username is null
     * @throws IllegalArgumentException if the bio is null
     */
    public PremiumUser(String username, String bio) {
        super(username, bio);
        this.customTitle = "Premium";
    }

    /**
     * A method that fetches messages from the log of the MessageExchange, appending the contents
     * of the messages and preserving the order
     * @param me MessageExchange to fetch message log from
     * @return String composed of the message contents
     * @throws IllegalArgumentException if the inputted MessageExchange parameter is null
     * @throws IllegalArgumentException if the user is not in the room me
     */
    public String fetchMessage(MessageExchange me) {
        if (me == null) { throw new IllegalArgumentException(); }
        else if (!me.getUsers().contains(this)) { throw new IllegalArgumentException(); }

        ArrayList<Message> log = me.getLog();
        String output = "";
        for (Message message : log) {
            output += message.getContents() + "\n";
        }
        return output;
    }

    /**
     * A method that creates a new instance of the PhotoRoom and calls the joinRoom method for each
     * user in the users list, adding this user (who creates the room) to the list. If a user
     * cannot join the room for any reason, the exception message is printed and the joining
     * proceeds with the next user
     * @param users ArrayList<User> of users to add to the new PhotoRoom
     * @return PhotoRoom of the newly created instance
     * @throws IllegalArgumentException if the users list parameter is null
     */
    public MessageExchange createPhotoRoom(ArrayList<User> users) {
        if (users == null) { throw new IllegalArgumentException(); }

        PhotoRoom newRoom = new PhotoRoom();
        for (User person : users) {
            try {
                person.joinRoom(newRoom);
            } catch (OperationDeniedException ode) {
                System.out.println(ode.getMessage());
            }
        }
        return newRoom;
    }

    /**
     * A method that returns the username and customTitle
     * @return String in the format <customTitle> username
     */
    public String displayName() { return "<" + this.customTitle + "> " + this.username; }

    /**
     * A method that updates the customTitle to the new value
     * @param newTitle String to update the customTitle
     */
    public void setCustomTitle(String newTitle) { this.customTitle = newTitle; }

}
