/*
 * NAME: Diane Li
 * PID: A5773774
 */
import java.util.Locale;

/**
 * A class that extends the abstract class Message and allows premium users to send PhotoMessages
 * with a source path of a photo that contains a valid extension
 *
 * @author Diane Li
 * @since ${01/23/2021}
 */
public class PhotoMessage extends Message {

    // Error message to use in OperationDeniedException
    private static final String INVALID_INPUT =
            "The source path given cannot be parsed as photo.";

    // instance variable
    private String extension;

    /**
     * A constructor that initializes the message's sender, contents, and extension
     *
     * @param sender User who sends the message
     * @param photoSource String containing path to photo  in the form [filename].[extension]
     * @throws OperationDeniedException if the sender is not a premium user
     * @throws OperationDeniedException if the photoSource's suffix is not an acceptable extension
     * @throws IllegalArgumentException if the sender or photoSource is null
     */
    public PhotoMessage(User sender, String photoSource) throws OperationDeniedException {
        super(sender);
        if (!(sender instanceof PremiumUser)) {
            throw new OperationDeniedException(DENIED_USER_GROUP);
        }
        if (photoSource == null) { throw new IllegalArgumentException(); }
        this.contents = photoSource;

        String[] validExtensions = {".jpg", ".jpeg", ".gif", ".png", ".tif", ".tiff", ".raw"};
        String contentsLower = this.contents.toLowerCase();
        boolean validExtension = false;
        for (String exten : validExtensions) {
            if (contentsLower.endsWith(exten)) {
                validExtension = true;
                this.extension = exten.substring(1);
                break;
            }
        }
        if (!validExtension) { throw new OperationDeniedException(INVALID_INPUT); }
    }

    /**
     * A method that returns the extension of the photo path
     *
     * @return String photoSource extension after the period
     */
    public String getContents() {
        String senderOut = getSender().displayName();
        String dateOut = getDate().toString();
        String output = senderOut + " " + dateOut + ": " + this.contents;
        return output;
    }

    /**
     * A method that returns the PhotoMessage's sender name, date, and path
     *
     * @return String containing the sender's displayed name, local date to string, and path file
     */
    public String getExtension() {
        return this.extension;
    }

}
