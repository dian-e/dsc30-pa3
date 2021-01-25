/*
  Name: Diane Li
  PID:  A15773774
 */

import java.time.LocalDate;
import org.junit.*;
import static org.junit.Assert.*;

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

        try { textA = new TextMessage(marina, "Deadline is Tuesday."); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }

        try { photoA = new PhotoMessage(marina, "users/Marina/images/IMG_5550.JPG"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }
        try { photoB = new PhotoMessage(mar, "selfie.giF"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }

        try { stickerA = new StickerMessage(marina, "default-objeccts/telephone"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }
        try { stickerB = new StickerMessage(marina, "mar-collections-8/LOLFace"); }
        catch (OperationDeniedException ode) { fail("ODE should not be thrown"); }
    }

    /* Recap: Assert exception without message */
    @Test (expected = IllegalArgumentException.class)
    public void testPremiumUserThrowsIAE() {
        marina = new PremiumUser("Marina", null);
    }

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
    public void testMessageGetters() {
        assertEquals(date, textA.getDate());
        assertEquals(marina, photoA.getSender());
    }

    // TextMessage class
    @Test (expected = OperationDeniedException.class)
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
        String marinaTextContents = "<Instructor> Marina " + date + ": Deadline is Tuesday.";
        assertEquals(marinaTextContents, textA.getContents());
    }

    // PhotoMessage class
    @Test (expected = OperationDeniedException.class)
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
        try { TextMessage photo5 = new TextMessage(marina, null); }
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
        String marinaPhotoContents = "<Instructor> Marina " + date + ": Picture at users/Marina/images/IMG_5550.JPG";
        assertEquals(marinaPhotoContents, photoA.getContents());

        String marPhotoContents = "<Student> Mar " + date + ": Picture at selfie.giF";
        assertEquals(marPhotoContents, photoB.getContents());
    }

    @Test
    public void testPhotoGetExtension() {
        assertEquals(".JPG", photoA.getExtension());
        assertEquals(".giF", photoB.getContents());
    }

    // StickerMessage class
    @Test (expected = OperationDeniedException.class)
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
        String marinaStickerContents = "<Instructor> Marina " + date + ": Sticker [Questioning] from Pack [yourcraft-8]";
        assertEquals(marinaStickerContents, stickerA.getContents());

        String marPhotoContents = "<Student> Mar " + date + ": Picture at selfie.giF";
        assertEquals(marPhotoContents, photoB.getContents());
    }

    @Test
    public void testStickerGetPackName() {
        assertEquals("default-objects", stickerA.getPackName());
        assertEquals("mar-collections-8", stickerB.getPackName());
    }

}
