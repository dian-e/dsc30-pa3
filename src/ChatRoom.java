/*
 * NAME: Diane Li
 * PID: A5773774
 */
import java.util.ArrayList;

/**
 * A class that implements the interface MessageExchange and simulates a chat room that stores logs
 * of the messages and records the users who are in a particular chat room
 *
 * @author Diane Li
 * @since ${01/24/2021}
 */
public class ChatRoom implements MessageExchange {

    // instance variables
    private ArrayList<User> users;
    private ArrayList<Message> log;

    /**
     * A constructor that initializes the users record and log record
     */
    public ChatRoom() {
        users = new ArrayList<User>();
        log = new ArrayList<Message>();
    }

    /**
     * A method that returns the log of this chat room
     * @return ArrayList<Message> that records all messages being sent in this chat room
     */
    public ArrayList<Message> getLog() { return this.log; }

    /**
     * A method that adds the given user to this room and returns true
     * @param u User to be added to this room
     * @return boolean true since there are not restrictions on the user here
     */
    public boolean addUser(User u) {
        users.add(u);
        return true;
    }

    /**
     * A method that removes the given user from the room, regardless of if the user is not found
     * @param u User to be removed from this room, assuming it is checked by its corresponding class
     */
    public void removeUser(User u) {
        users.remove(u);
    }

    /**
     * A method that returns the users of this chat room
     * @return ArrayList<User> of all the users in this room
     */
    public ArrayList<User> getUsers() { return this.users; }

    /**
     * A method that adds a new message to the log of this chat room and returns true
     * @param m Message to be added to the log, assuming not null from corresponding user method
     * @return boolean true since there are no restrictions on the message here
     */
    public boolean recordMessage(Message m) {
        this.log.add(m);
        return true;
    }

}