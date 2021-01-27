/*
 * NAME: Diane Li
 * PID: A5773774
 */

/**
 * A class that extends the abstract class Message and allows premium users to send StickerMessages
 * with a source path of a sticker
 *
 * @author Diane Li
 * @since ${01/23/2021}
 */
public class StickerMessage extends Message {

    // instance variable
    private String packName;

    /**
     * A constructor that sets the sender, sticker pack name, and sticker name
     * @param sender User who sends the message
     * @param stickerSource String path to sticker  in the form packName/stickerName
     * @throws OperationDeniedException with message if the sender is not a premium user
     * @throws IllegalArgumentException if the sender or stickerSource is null
     */
    public StickerMessage(User sender, String stickerSource) throws OperationDeniedException {
        super(sender);
        if (!(sender instanceof PremiumUser)) {
            throw new OperationDeniedException(DENIED_USER_GROUP);
        }

        if (stickerSource == null) { throw new IllegalArgumentException(); }
        String[] stickerParts = stickerSource.split("/");
        this.packName = stickerParts[0];
        this.contents = stickerParts[1];
    }

    /**
     * A method that returns the message's sender name, local date, sticker name, and pack name
     * @return String containing the sender display name, date to string, sticker name, pack name
     */
    public String getContents() {
        String senderOut = getSender().displayName();
        String dateOut = getDate().toString();
        String stickerOut = "Sticker [" + this.contents + "] from Pack [" + this.packName + "]";
        String output = senderOut + " [" + dateOut + "]: " + stickerOut;
        return output;
    }

    /**
     * A method that returns the sticker's pack name
     * @return String name of the sticker's pack
     */
    public String getPackName() {
        return this.packName;
    }
}
