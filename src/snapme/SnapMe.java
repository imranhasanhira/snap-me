/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snapme;

import com.github.sarxos.webcam.Webcam;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author hirayami
 */
public class SnapMe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Webcam webcam = Webcam.getDefault();
            webcam.open();
            ImageIO.write(webcam.getImage(), "PNG", new File("hello-world.png"));
        } catch (IOException ex) {
            Logger.getLogger(SnapMe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
