/*
 * NAME: Diane Li
 * PID: A5773774
 */
import java.util.ArrayList;

/**
 * A class that extends the abstract class Message and allows premium users to send PhotoMessages
 * with a source path of a photo that contains a valid extension
 *
 * @author Diane Li
 * @since ${01/23/2021}
 */
public class ChatRoom implements MessageExchange {

    // instance variables
    private ArrayList<User> users;
    private ArrayList<Message> log;

    public ChatRoom() {
        /* TODO */
    }

    public ArrayList<Message> getLog() {
        /* TODO */
        return null;
    }

    public boolean addUser(User u) {
        /* TODO */
        return false;
    }

    public void removeUser(User u) {
        /* TODO */
    }

    public ArrayList<User> getUsers() {
        /* TODO */
        return null;
    }

    public boolean recordMessage(Message m) {
        /* TODO */
        return false;
    }


}