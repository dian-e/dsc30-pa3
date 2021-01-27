/*
 * NAME: Diane Li
 * PID: A5773774
 */
import java.util.ArrayList;

/**
 * An abstract class that defines the functionality of a user in the messaging app
 *
 * @author Diane Li
 * @since ${01/24/2021}
 */
public abstract class User {

    // Error message to use in OperationDeniedException
    protected static final String JOIN_ROOM_FAILED =
            "Failed to join the chat room.";
    protected static final String INVALID_MSG_TYPE =
            "Cannot send this type of message to the specified room.";

    // instance variables
    protected String username;
    protected String bio;
    protected ArrayList<MessageExchange> rooms;

    /**
     * A constructor that initializes the username, bio, and message exchange room
     * @param username String representing user's name
     * @param bio String representing user's bio
     * @throws IllegalArgumentException if the username or bio is null
     */
    public User(String username, String bio) {
        if (username == null || bio == null) { throw new IllegalArgumentException(); }
        this.username = username;
        this.bio = bio;
        this.rooms = new ArrayList<MessageExchange>();
    }

    /**
     * A method that updates the class variable bio with a new one
     * @param newBio String to update the class variable bio
     * @throws IllegalArgumentException if the new bio is null
     */
    public void setBio(String newBio) {
        if (newBio == null) { throw new IllegalArgumentException(); }
        this.bio = newBio;
    }

    /**
     * A method that returns the bio
     * @return String containing the bio
     */
    public String displayBio() { return this.bio; }

    /**
     * A method that adds the user to the list of users in the message exchange platform and adds
     * the platform to the list of rooms of this user
     * @param me MessageExchange to add the user to
     * @throws OperationDeniedException w/ message if the room is already in list of user's room
     * or if the joining failed to add the user to the ME
     * @throws IllegalArgumentException if the message exchange parameter is null
     */
    public void joinRoom(MessageExchange me) throws OperationDeniedException {
        if (me == null) { throw new IllegalArgumentException(); }
        // checks user doesn't have this room already and the room will successfully add the user
        boolean alreadyInRoom = rooms.contains(me);
        boolean meFailedToAdd = !me.addUser(this);
        if (alreadyInRoom || meFailedToAdd) {
            throw new OperationDeniedException(JOIN_ROOM_FAILED);
        }

        this.rooms.add(me);
    }

    /**
     * A method that removes the message exchange platform from the list of rooms that this user is
     * a member of and removes the user from the list of users in the MessageExchange object
     * @param me MessageExchange to remove the user from
     * @throws IllegalArgumentException if the message exchange parameter is null
     */
    public void quitRoom(MessageExchange me) {
        if (me == null) { throw new IllegalArgumentException(); }
        this.rooms.remove(me);
        me.removeUser(this);
    }

    /**
     * A method that creates a new instance of the ChatRoom and calls the joinRoom method for each
     * user in the users list, adding this user (who creates the room) to the list. If a user
     * cannot join the room for any reason, the exception message is printed and the joining
     * proceeds with the next user
     * @param users ArrayList<User> of users to add to the new ChatRoom
     * @return ChatRoom of the newly created instance
     * @throws IllegalArgumentException if the users list parameter is null
     */
    public MessageExchange createChatRoom(ArrayList<User> users) {
        if (users == null) { throw new IllegalArgumentException(); }

        ChatRoom newRoom = new ChatRoom();
        // adds this user to the list of users to join the room
        users.add(this);
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
     * A method that creates an instance of a message with the given type and records the message
     * instance in the Message Exchange
     * @param me MessageExchange to record the message in
     * @param msgType MessageType of message being created that will be validated
     * @param contents String of message content
     * @throws IllegalArgumentException if the message exchange, message type, or contents is null
     * @throws IllegalArgumentException if the user didn't join the message exchange
     */
    public void sendMessage(MessageExchange me, MessageType msgType, String contents) {
        if (me == null || msgType == null || contents == null) {
            throw new IllegalArgumentException();
        } else if (!me.getUsers().contains(this)) {
            throw new IllegalArgumentException();
        }

        Message message;
        try {
            // creates new object of given message type
            if (msgType == MessageType.TEXT) {
                message = new TextMessage(this, contents);
            } else if (msgType == MessageType.PHOTO) {
                message = new PhotoMessage(this, contents);
            } else if (msgType == MessageType.STICKER) {
                message = new StickerMessage(this, contents);
            } else {
                throw new OperationDeniedException();
            }

            // ensures the ME room accepts the message type created (PhotoRoom only accepts Photos)
            if (me instanceof PhotoRoom && !(message instanceof PhotoMessage)) {
                System.out.println(INVALID_MSG_TYPE);
            } else {
                me.recordMessage(message);
            }
        } catch (OperationDeniedException ode) {
            System.out.println(ode.getMessage());
        }
    }

    /**
     * A method that fetches messages from the log of the MessageExchange, appending the contents
     * of the messages and preserving the order
     * @param me MessageExchange to fetch message log from
     * @return String composed of the message contents
     */
    public abstract String fetchMessage(MessageExchange me);

    /**
     * A method that returns the user's username
     * @return String username of user
     */
    public abstract String displayName();

}
