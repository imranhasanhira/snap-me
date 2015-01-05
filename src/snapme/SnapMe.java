/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snapme;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

/**
 *
 * @author Md Imran Hasan Hira
 */
public class SnapMe {

    static File destinationDirectory;
    private static boolean running = true;

    private static void continueSnapping() {
        SnapMe snapMe = new SnapMe();
        snapMe.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String nextLine = scanner.nextLine();
            if (nextLine.equals("exit")) {
                W.prl("SnapMe says - Good Bye. Hope to see you :) ");
                running = false;
                System.exit(0);
            } else if (nextLine.equals("stop")) {
                W.prl("Stopping SnapMe...");
                snapMe.stop();
            } else if (nextLine.startsWith("restart -t")) {
                try {
                    long interval = Long.parseLong(nextLine.replace("restart -t", "").trim());
                    try {
                        Pref.setLong(Pref.DEFAULT_INTERVAL, interval);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    snapMe.restartWithInterval(interval);
                    W.prl("interval is set to " + interval);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else if (nextLine.startsWith("pause")) {
                snapMe.pause();
                W.prl("SnapMe paused");
            } else if (nextLine.startsWith("resume")) {
                snapMe.resume();
                W.prl("SnapMe resumed");
            } else if (nextLine.startsWith("set")) {
//                nextLine.replace("set", "").trim().split(" ");
//                W.set(nextLine, nextLine);
            } else if (nextLine.startsWith("comment ")) {
                String comment = nextLine.replace("comment", "").trim();
                if (comment.length() == 0) {
                    comment = null;
                    snapMe.setComment(comment);
                }
            }
        }
    }
    Webcam webcam;
    Timer t;
    TimerTask tt;
    long interval;
    String comment;

    public SnapMe() {
        interval = Pref.getLong(Pref.DEFAULT_INTERVAL, 30000);
    }

    private void takePicure(final Webcam webcam) {
        try {

            File destinationFile = new File(destinationDirectory, "WBC_" + W.getDateTime() + (comment == null ? "" : ("(" + comment + ")")) + ".jpg");
            ImageIO.write(webcam.getImage(), "JPG", destinationFile);
            W.prl("Taken - " + destinationFile.getName());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private File createDestinationDirectory() {
        destinationDirectory = new File("images/" + W.getSlashSeparatedDateStr());
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdirs();
        }
        return destinationDirectory;
    }

    private void scheduleWebCam() {
        if (tt != null) {
            tt.cancel();
        }
        tt = new TimerTask() {
            @Override
            public void run() {
                takePicure(webcam);
            }
        };
        System.out.println("interval:  " + interval);
        t.scheduleAtFixedRate(tt, 0, interval);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String commands = "SnapMe comamnds: \n"
                + "> comment [sample comment]\n"
                + "\t comments will be added with filename.\n"
                + "> exit\n"
                + "\t exit from SnapMe.\n"
                + "> pause\n"
                + "\t pause SnapMe.\n"
                + "> restart -t [000]\n"
                + "\t restarts SnapMe with a different timeout value, default is 30s.\n"
                + "> resume\n"
                + "\t resumes from paused state.\n";
        System.out.println(commands);

        while (running) {
            try {
                continueSnapping();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void start() {
        W.prl("SnapMe starting....");
        createDestinationDirectory();
        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        W.prl("SnapMe says - Hello dear, I'll snap you :)");
        webcam.open();
        t = new Timer();
        scheduleWebCam();
    }

    private void stop() {
        tt.cancel();
        t.cancel();
        webcam.close();
    }

    private void restartWithInterval(long interval) {
        this.interval = interval;
        scheduleWebCam();
    }

    private void resume() {
        scheduleWebCam();
    }

    private void pause() {
        tt.cancel();
    }

    private void setComment(String nextLine) {
        comment = nextLine;
    }
}
