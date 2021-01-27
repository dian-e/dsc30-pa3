/*
 * NAME: Diane Li
 * PID: A5773774
 */
import java.util.ArrayList;

/**
 * A class that implements the interface MessageExchange and simulates a chat room that records
 * logs of messages and a list of users in the room. Only PremiumUser can join this room, and only
 * PhotoMessage can be sent
 *
 * @author Diane Li
 * @since ${01/24/2021}
 */
public class PhotoRoom implements MessageExchange {

    // instance variables
    private ArrayList<User> users;
    private ArrayList<Message> log;

    /**
     * A constructor that initializes the users record and log record
     */
    public PhotoRoom() {
        users = new ArrayList<User>();
        log = new ArrayList<Message>();
    }

    /**
     * A method that returns the log of this chat room
     * @return ArrayList<Message> that records all messages being sent in this chat room
     */
    public ArrayList<Message> getLog() { return this.log; }

    /**
     * A method that adds the given user to this room and returns true if successful
     * @param u User to be added to this room
     * @return boolean true if the user is a PremiumUser adn is successfully added to the room
     */
    public boolean addUser(User u) {
        if (!(u instanceof PremiumUser)) { return false;}
        else {
            users.add(u);
            return true;
        }
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
     * A method that adds a new message to the log of this chat room and returns true if successful
     * @param m Message to be added to the log, assuming not null from corresponding user method
     * @return boolean true if the message is a PhotoMessage that is successfully recorded
     */
    public boolean recordMessage(Message m) {
        if (!(m instanceof PhotoMessage)) { return false;}
        else {
            log.add(m);
            return true;
        }
    }

}
