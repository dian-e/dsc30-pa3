/*
  Name: Diane Li
  PID:  A15773774
 */
import java.time.LocalDate;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Messenger Application Test
 * @author Diane Li
 * @since  01/23/2021
 */
public class MessengerApplicationTest {

    /*
      Messages defined in starter code. Remember to copy and paste these strings to the test file if
      you cannot directly access them. DO NOT change the original declaration to public.
     */
    private static final String EXCEED_MAX_LENGTH =
            "Your input exceeded the maximum length limit.";
    protected static final String DENIED_USER_GROUP =
            "This operation is disabled in your user group.";
    private static final String INVALID_INPUT =
            "The source path given cannot be parsed as photo.";
    protected static final String JOIN_ROOM_FAILED =
            "Failed to join the chat room.";
    protected static final String INVALID_MSG_TYPE =
            "Cannot send this type of message to the specified room.";

    /* Global test variables. Initialize them in @Before method. */
    PremiumUser marina;
    MessageExchange room;
    StandardUser nabi;
    PremiumUser mar;

    TextMessage textA;
    PhotoMessage photoA;
    PhotoMessage photoB;
    StickerMessage stickerA;
    StickerMessage stickerB;

    ChatRoom chatRoomA;
    ChatRoom chatRoomB;
    ChatRoom chatRoomC;
    PhotoRoom photoRoomA;
    PhotoRoom photoRoomB;
    PhotoRoom photoRoomC;
    ArrayList<User> users;

    /*
      The date used in Message and its subclasses. You can directly
      call this in your test methods.
     */
    LocalDate date = LocalDate.now();

    /* Setup */
    @Before
    public void setup() {
        marina = new PremiumUser("Marina", "Instructor");
        room = new ChatRoom();
        nabi = new StandardUser("Nabi", "Nabi's bio");
        mar = new PremiumUser("Mar", "Student");

        try {
            textA = new TextMessage(marina, "Deadline is Tuesday.");
            photoA = new PhotoMessage(marina, "users/Marina/images/IMG_5550.JPG");
            photoB = new PhotoMessage(mar, "selfie.giF");
            stickerA = new StickerMessage(marina, "default-objects/telephone");
            stickerB = new StickerMessage(mar, "mar-collections-8/LOLFace");
        }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }

        chatRoomA = new ChatRoom();
        chatRoomB = new ChatRoom();
        chatRoomC = new ChatRoom();
        photoRoomA = new PhotoRoom();
        photoRoomB = new PhotoRoom();
        photoRoomC = new PhotoRoom();

        users = new ArrayList<>();
        users.add(nabi);
        users.add(mar);
    }

    /* Recap: Assert exception without message */
    @Test (expected = IllegalArgumentException.class)
    public void testPremiumUserThrowsIAE() { marina = new PremiumUser("Marina", null); }

    /* Assert exception with message */
    @Test
    public void testPhotoMessageThrowsODE() {
        try {
            PhotoMessage pm = new PhotoMessage(marina, "PA02.zip");
            fail("Exception not thrown"); // will execute if last line didn't throw exception
        } catch (OperationDeniedException ode) {
            assertEquals(INVALID_INPUT, ode.getMessage());
        }
    }

    /* Assert message content without hardcoding the date */
    @Test
    public void testTextMessageGetContents() {
        try {
            TextMessage tm = new TextMessage(marina, "A sample text message.");

            // concatenating the current date when running the test
            String expected = "<Premium> Marina [" + date + "]: A sample text message.";
            assertEquals(expected, tm.getContents());
        } catch (OperationDeniedException ode) {
            fail("ODE should not be thrown");
        }
    }

    // Message concrete classes
    @Test
    public void testMessageGetDate() { assertEquals(date, textA.getDate()); }

    @Test
    public void testMessageGetSender() { assertEquals(marina, photoA.getSender()); }

    // TextMessage class
    @Test
    public void testTextMessageConstructorThrowsODE() {
        try {
            String maxLen = "YoUu&UcjBa(SXwBi#kkgw#7VcDw)eZu(oyb.NySi5Y0.-Xu#m&S)MA^iCMFFOzb(FH,KP$ykR+2oUuvu=NCs)H,osco&LG,uS*+t%qyQs3%iBCY0dd3qfpc$B846AS-hBu5;T)faa5n)rEW$z&cgIUX-%GL2K+*tgl*7g4Q;ySPu&^LYqG6kuseOFg-0g909A#s^jo6w;631rIgmhkgao7mA-AhnstaxfJMlL&11^.3N54WNep521Q#^G.y3t0+$cY6Z8NPv)HZ7tHa+OZ(Dv4Y94,3X$t(1lNvdsFJ$E%-KnMW8)hWKEr(LsL$sdCj@(ok.ozwtkbHoxp#!VLy2h8dH7^p%OlRd;y6%=RAu1PM6Y=;53K3FpNReOdLG;ddN,6#CI=&bU5vK=L3s8=L!l13AOL3A5ffT2e.itL=DEM^7Z7f)iwnZft3wuzm&7gw)BUWfSPD-3)zk7,vUYItjXF8&m*3$r-ElQ2H2-AAkdSCJiua2M!wD\n";
            String overMaxLen = maxLen + '.';
            TextMessage text1 = new TextMessage(marina, overMaxLen);
            fail("Exception not thrown");
        } catch (OperationDeniedException ode) {
            assertEquals(EXCEED_MAX_LENGTH, ode.getMessage());
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testTextMessageConstructorThrowsIAESender() {
        try { TextMessage text2 = new TextMessage(null, "message"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testTextMessageConstructorThrowsIAEText() {
        try { TextMessage text3 = new TextMessage(marina, null); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }
    }

    @Test
    public void testTextMessageConstructor() {
        try { TextMessage text3 = new TextMessage(marina, "message"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }

        try { TextMessage text4 = new TextMessage(marina, "This is my message"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }

        try { TextMessage text5 = new TextMessage(marina, "ANOTHER~~~~~~ MESS * AGE!!>GJKDLHB<>?{)(J TE$T"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }
    }

    @Test
    public void testTextGetContents() {
        String marinaTextContents = "<Premium> Marina [" + date + "]: Deadline is Tuesday.";
        assertEquals(marinaTextContents, textA.getContents());
    }

    // PhotoMessage class
    @Test
    public void testPhotoMessageConstructorThrowsODEUser() {
        try {
            PhotoMessage photo1 = new PhotoMessage(nabi, "pm.jpg");
            fail("Exception not thrown");
        } catch (OperationDeniedException ode) {
            assertEquals(DENIED_USER_GROUP, ode.getMessage());
        }
    }

    @Test
    public void testPhotoMessageConstructorThrowsODEPhoto1() {
        try {
            PhotoMessage photo2 = new PhotoMessage(marina, "saJ.Md&.^dsas.F123");
            fail("Exception not thrown"); // will execute if last line didn't throw exception
        } catch (OperationDeniedException ode) {
            assertEquals(INVALID_INPUT, ode.getMessage());
        }
    }

    @Test
    public void testPhotoMessageConstructorThrowsODEPhoto2() {
        try {
            PhotoMessage photo3 = new PhotoMessage(marina, "d/images/example.pdf");
            fail("Exception not thrown"); // will execute if last line didn't throw exception
        } catch (OperationDeniedException ode) {
            assertEquals(INVALID_INPUT, ode.getMessage());
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPhotoMessageConstructorThrowsIAESender() {
        try { PhotoMessage photo4 = new PhotoMessage(null, "pm.jpg"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPhotoMessageConstructorThrowsIAEText() {
        try { PhotoMessage photo5 = new PhotoMessage(marina, null); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }
    }

    @Test
    public void testPhotoMessageConstructor() {
        try { PhotoMessage photo6 = new PhotoMessage(marina, "Nabi.Yuxuan.jpg"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }

        try { PhotoMessage photo7 = new PhotoMessage(marina, "?????.GiF"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }

        try { PhotoMessage photo8 = new PhotoMessage(marina, "A.E.I.O.U.TIF"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }
    }

    @Test
    public void testPhotoGetContents() {
        String marinaPhotoContents = "<Premium> Marina [" + date + "]: Picture at users/Marina/images/IMG_5550.JPG";
        assertEquals(marinaPhotoContents, photoA.getContents());

        String marPhotoContents = "<Premium> Mar [" + date + "]: Picture at selfie.giF";
        assertEquals(marPhotoContents, photoB.getContents());
    }

    @Test
    public void testPhotoGetExtension() {
        assertEquals("jpg", photoA.getExtension());
        assertEquals("gif", photoB.getExtension());
    }

    // StickerMessage class
    @Test
    public void testStickerMessageConstructorThrowsODEUser() {
        try {
            StickerMessage sticker1 = new StickerMessage(nabi, "packName/stickerName");
            fail("Exception not thrown");
        } catch (OperationDeniedException ode) {
            assertEquals(DENIED_USER_GROUP, ode.getMessage());
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testStickerMessageConstructorThrowsIAESender() {
        try { StickerMessage sticker2 = new StickerMessage(null, "packName/stickerName"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testStickerMessageConstructorThrowsIAEText() {
        try { StickerMessage sticker3 = new StickerMessage(marina, null); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }
    }

    @Test
    public void testStickerMessageConstructor() {
        try { StickerMessage sticker4 = new StickerMessage(marina, "packName/stickerName"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }

        try { StickerMessage sticker5 = new StickerMessage(marina, "stick/er"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }

        try { StickerMessage sticker6 = new StickerMessage(mar, "yourcraft-8/Questioning"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }
    }

    @Test
    public void testStickerGetContents() {
        String marinaStickerContents = "<Premium> Marina [" + date + "]: Sticker [telephone] from Pack [default-objects]";
        assertEquals(marinaStickerContents, stickerA.getContents());

        String marStickerContents = "<Premium> Mar [" + date + "]: Sticker [LOLFace] from Pack [mar-collections-8]";
        assertEquals(marStickerContents, stickerB.getContents());
    }

    @Test
    public void testStickerGetPackName() {
        assertEquals("default-objects", stickerA.getPackName());
        assertEquals("mar-collections-8", stickerB.getPackName());
    }

    // User abstract class
    @Test (expected = IllegalArgumentException.class)
    public void testUserSetBioThrowsIAE() { nabi.setBio(null); }

    @Test
    public void testUserSettersAndGetters() {
        assertEquals("Nabi's bio", nabi.displayBio());
        nabi.setBio("NEW BIO !");
        assertEquals("NEW BIO !", nabi.displayBio());

        assertEquals("Instructor", marina.displayBio());
        marina.setBio("DSC30 Instructor");
        assertEquals("DSC30 Instructor", marina.displayBio());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testUserJoinRoomThrowsIAE() {
        try {
            marina.joinRoom(null);
        } catch (OperationDeniedException ode) {
            System.out.println(ode.getMessage());
        }
    }

    @Test
    public void testUserJoinRoomUserThrowsODE() {
        try { nabi.joinRoom(chatRoomA); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }

        try {
            nabi.joinRoom(chatRoomA);
            fail("Exception not thrown");
        } catch (OperationDeniedException ode) {
            assertEquals(JOIN_ROOM_FAILED, ode.getMessage());
        }
    }

    @Test
    public void testUserJoinRoomMEThrowsODE() {
        try {
            nabi.joinRoom(photoRoomA);
            fail("Exception not thrown");
        } catch (OperationDeniedException ode) {
            assertEquals(JOIN_ROOM_FAILED, ode.getMessage());
        }
    }

    @Test
    public void testUserJoinRoom() {
        try {
            marina.joinRoom(chatRoomA);
            nabi.joinRoom(chatRoomA);
            mar.joinRoom(chatRoomA);
        }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }
        assertTrue(chatRoomA.getUsers().contains(marina));
        assertTrue(chatRoomA.getUsers().contains(nabi));

        try {
            marina.joinRoom(photoRoomA);
            mar.joinRoom(photoRoomA);
        }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }
        assertTrue(photoRoomA.getUsers().contains(mar));
        assertFalse(photoRoomA.getUsers().contains(nabi));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testUserQuitRoomThrowsIAE() { marina.quitRoom(null); }

    @Test
    public void testUserQuitRoom() {
        try {
            marina.joinRoom(chatRoomA);
            nabi.joinRoom(chatRoomA);
            mar.joinRoom(chatRoomA);

            marina.joinRoom(photoRoomA);
            mar.joinRoom(photoRoomA);
        }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }

        marina.quitRoom(chatRoomA);
        assertFalse(chatRoomA.getUsers().contains(marina));

        mar.quitRoom(photoRoomA);
        assertFalse(photoRoomA.getUsers().contains(mar));

        nabi.quitRoom(photoRoomA);
        assertFalse(photoRoomA.getUsers().contains(nabi));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testUserCreateChatRoomThrowsIAE() { marina.createChatRoom(null); }

    @Test
    public void testUserCreateChatRoom() {
        MessageExchange chatRoom1 = marina.createChatRoom(users);
        assertTrue(chatRoom1.getUsers().contains(marina));
        assertTrue(chatRoom1.getUsers().contains(nabi));
        assertTrue(chatRoom1.getUsers().contains(mar));

        ArrayList<User> users2 = new ArrayList<>();
        MessageExchange chatRoom2 = marina.createChatRoom(users2);
        assertTrue(chatRoom2.getUsers().contains(marina));
        assertFalse(chatRoom2.getUsers().contains(mar));
        assertFalse(chatRoom2.getUsers().contains(nabi));

        ArrayList<User> users3 = new ArrayList<>();
        users3.add(mar);
        MessageExchange chatRoom3 = marina.createChatRoom(users3);
        assertTrue(chatRoom3.getUsers().contains(mar));
        assertTrue(chatRoom3.getUsers().contains(marina));
        assertFalse(chatRoom3.getUsers().contains(nabi));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testUserSendMessageMEThrowsIAE() { marina.sendMessage(null, MessageType.TEXT, "oh hello"); }

    @Test (expected = IllegalArgumentException.class)
    public void testUserSendMessageTypeThrowsIAE() { marina.sendMessage(chatRoomA, null, "oh hello"); }

    @Test (expected = IllegalArgumentException.class)
    public void testUserSendMessageContentsThrowsIAE() { marina.sendMessage(chatRoomA, MessageType.TEXT, null); }

    @Test (expected = IllegalArgumentException.class)
    public void testUserSendMessageUserThrowsIAE() { marina.sendMessage(chatRoomA, MessageType.TEXT, "oh hello"); }

    @Test
    public void testUserSendMessageMETypeThrowsIAE() {
        chatRoomA.addUser(nabi);
        chatRoomA.addUser(marina);
        photoRoomA.addUser(mar);

        // should print exception message using exception.getMessage()
        nabi.sendMessage(chatRoomA, MessageType.PHOTO, "img.jpg");

        // should print INVALID_MSG_TYPE message
        mar.sendMessage(photoRoomA, MessageType.TEXT, "img.jpg");

        // should properly record
        nabi.sendMessage(chatRoomA, MessageType.TEXT, "image.jpg");
        marina.sendMessage(chatRoomA, MessageType.PHOTO, "img.jpg");
        marina.sendMessage(chatRoomA, MessageType.STICKER, "newestPack/coolSticker");
        assertEquals(3, chatRoomA.getLog().size());

        mar.sendMessage(photoRoomA, MessageType.PHOTO, "GIF.GIF");
        mar.sendMessage(photoRoomA, MessageType.PHOTO, "jpg.jpeg.raw");
        assertEquals(2, photoRoomA.getLog().size());
    }

    // StandardUser Class
    @Test
    public void testStandardUserConstructor() {
        StandardUser standard1 = new StandardUser("user10205", "doesn't matter here");
        StandardUser standard2 = new StandardUser("myUser", " b i o ");
        StandardUser standard3 = new StandardUser("diane", "writing junit test");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testStandardUserFetchMessageMEThrowsIAE() { nabi.fetchMessage(null); }

    @Test (expected = IllegalArgumentException.class)
    public void testStandardUserFetchMessageUserThrowsIAE() { nabi.fetchMessage(chatRoomC); }

    @Test
    public void testStandardUserFetchMessage() {
        chatRoomC.addUser(nabi);
        chatRoomC.addUser(marina);
        nabi.sendMessage(chatRoomC, MessageType.TEXT, "image.jpg");
        marina.sendMessage(chatRoomC, MessageType.PHOTO, "img.jpg");
        marina.sendMessage(chatRoomC, MessageType.STICKER, "newestPack/coolSticker");
        String expectedSUFetch1 = "Nabi [" + date + "]: image.jpg\nThis message cannot be fetched because you are not a premium user.\nThis message cannot be fetched because you are not a premium user.\n";
        assertEquals(expectedSUFetch1, nabi.fetchMessage(chatRoomC));

        chatRoomB.addUser(nabi);
        chatRoomB.addUser(marina);
        assertEquals("", nabi.fetchMessage(chatRoomB));

        marina.sendMessage(chatRoomB, MessageType.STICKER, "mar-collections-8/LOLFace");
        assertEquals("This message cannot be fetched because you are not a premium user.\n", nabi.fetchMessage(chatRoomB));
    }

    @Test
    public void testStandardUserDisplayName() { assertEquals("Nabi", nabi.displayName()); }

    // PremiumUser Class
    @Test
    public void testPremiumUserConstructor() {
        PremiumUser premium1 = new PremiumUser("Professional", "i'm profesh");
        PremiumUser premium2 = new PremiumUser("Mom", "new account!!");
        PremiumUser premium3 = new PremiumUser("Family", "entire family account");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPremiumUserFetchMessageMEThrowsIAE() { marina.fetchMessage(null); }

    @Test (expected = IllegalArgumentException.class)
    public void testPremiumUserFetchMessageUserThrowsIAE() { marina.fetchMessage(chatRoomA); }

    @Test
    public void testPremiumUserFetchMessage() {
        chatRoomA.addUser(nabi);
        chatRoomA.addUser(marina);
        nabi.sendMessage(chatRoomA, MessageType.TEXT, "image.jpg");
        marina.sendMessage(chatRoomA, MessageType.PHOTO, "img.jpg");
        marina.sendMessage(chatRoomA, MessageType.STICKER, "newest Pack/cool Sticker");
        String expectedSUFetch1 = "Nabi [" + date + "]: image.jpg\n<Premium> Marina [" + date + "]: Picture at img.jpg\n<Premium> Marina [" + date + "]: Sticker [cool Sticker] from Pack [newest Pack]\n";
        assertEquals(expectedSUFetch1, marina.fetchMessage(chatRoomA));

        chatRoomB.addUser(marina);
        chatRoomB.addUser(mar);
        assertEquals("", marina.fetchMessage(chatRoomB));

        mar.sendMessage(chatRoomB, MessageType.STICKER, "mar-collections-8/LOLFace");
        assertEquals(stickerB.getContents() + "\n", mar.fetchMessage(chatRoomB));
    }

    @Test
    public void testPremiumUserDisplayName() { assertEquals("<Premium> Mar", mar.displayName()); }

    @Test
    public void testPremiumUserSetCustomTitle() {
        marina.setCustomTitle("DSC30 Instructor");
        assertEquals("<DSC30 Instructor> Marina", marina.displayName());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPremiumUserCreatePhotoRoomThrowsIAE() { marina.createChatRoom(null); }

    @Test
    public void testPremiumUserCreatePhotoRoom() {
        MessageExchange photoRoom1 = marina.createPhotoRoom(users);
        assertTrue(photoRoom1.getUsers().contains(marina));
        assertFalse(photoRoom1.getUsers().contains(nabi));
        assertTrue(photoRoom1.getUsers().contains(mar));

        ArrayList<User> users2 = new ArrayList<>();
        MessageExchange photoRoom2 = marina.createPhotoRoom(users2);
        assertTrue(photoRoom2.getUsers().contains(marina));
        assertFalse(photoRoom2.getUsers().contains(mar));
        assertFalse(photoRoom2.getUsers().contains(nabi));

        ArrayList<User> users3 = new ArrayList<>();
        users3.add(mar);
        MessageExchange photoRoom3 = marina.createPhotoRoom(users3);
        assertTrue(photoRoom3.getUsers().contains(mar));
        assertTrue(photoRoom3.getUsers().contains(marina));
        assertFalse(photoRoom3.getUsers().contains(nabi));
    }

    // ChatRoom Class
    @Test
    public void testChatRoomConstructor() { ChatRoom chat1 = new ChatRoom(); }

    @Test
    public void testChatRoomGetLog() {
        chatRoomA.recordMessage(textA);
        chatRoomA.recordMessage(textA);
        ArrayList<Message> chatA = new ArrayList<>();
        chatA.add(textA);
        chatA.add(textA);
        assertEquals(chatA, chatRoomA.getLog());
    }

    @Test
    public void testChatRoomAddUser(){
        assertTrue(chatRoomC.addUser(nabi));
        assertTrue(chatRoomC.addUser(marina));
        assertEquals(2, chatRoomC.getUsers().size());

        assertTrue(chatRoomC.addUser(nabi));
        assertEquals(3, chatRoomC.getUsers().size());
    }

    @Test
    public void testChatRoomRemoveUser() {
        chatRoomA.removeUser(nabi);
        assertEquals(0, chatRoomA.getUsers().size());

        chatRoomA.addUser(nabi);
        chatRoomA.addUser(nabi);
        chatRoomA.removeUser(nabi);
        assertEquals(1, chatRoomA.getUsers().size());

        chatRoomA.addUser(nabi);
        chatRoomA.addUser(marina);
        chatRoomA.addUser(mar);
        chatRoomA.removeUser(nabi);
        chatRoomA.removeUser(mar);
        assertEquals(2, chatRoomA.getUsers().size());
    }

    @Test
    public void testChatRoomGetUsers() {
        chatRoomB.addUser(nabi);
        chatRoomB.addUser(marina);
        ArrayList<User> chatB = new ArrayList<>();
        chatB.add(nabi);
        chatB.add(marina);
        assertEquals(chatB, chatRoomB.getUsers());
    }

    @Test
    public void testChatRoomRecordMessage() {
        assertTrue(chatRoomC.recordMessage(textA));
        assertTrue(chatRoomC.recordMessage(textA));
        assertTrue(chatRoomC.recordMessage(photoA));
        assertEquals(3, chatRoomC.getLog().size());
    }

    // PhotoRoom Class
    @Test
    public void testPhotoRoomConstructor() { PhotoRoom photo1 = new PhotoRoom(); }

    @Test
    public void testPhotoRoomGetLog() {
        photoRoomA.recordMessage(photoA);
        photoRoomA.recordMessage(photoA);
        ArrayList<Message> photo2 = new ArrayList<>();
        photo2.add(photoA);
        photo2.add(photoA);
        assertEquals(photo2, photoRoomA.getLog());
    }

    @Test
    public void testPhotoRoomAddUser(){
        assertFalse(photoRoomC.addUser(nabi));
        assertTrue(photoRoomC.addUser(marina));
        assertEquals(1, photoRoomC.getUsers().size());

        assertTrue(photoRoomC.addUser(mar));
        assertTrue(photoRoomC.addUser(mar));
        assertEquals(3, photoRoomC.getUsers().size());
    }

    @Test
    public void testPhotoRoomRemoveUser() {
        photoRoomA.removeUser(nabi);
        assertEquals(0, photoRoomA.getUsers().size());

        photoRoomA.addUser(nabi);
        photoRoomA.addUser(marina);
        photoRoomA.removeUser(nabi);
        assertEquals(1, photoRoomA.getUsers().size());

        photoRoomA.addUser(marina);
        photoRoomA.addUser(mar);
        photoRoomA.removeUser(mar);
        photoRoomA.removeUser(mar);
        assertEquals(2, photoRoomA.getUsers().size());
    }

    @Test
    public void testPhotoRoomGetUsers() {
        photoRoomB.addUser(nabi);
        photoRoomB.addUser(marina);
        ArrayList<User> photoB = new ArrayList<>();
        photoB.add(marina);
        assertEquals(photoB, photoRoomB.getUsers());
    }

    @Test
    public void testPhotoRoomRecordMessage() {
        assertTrue(photoRoomC.recordMessage(photoA));
        assertTrue(photoRoomC.recordMessage(photoA));
        assertFalse(photoRoomC.recordMessage(textA));
        assertEquals(2, photoRoomC.getLog().size());
    }

}
