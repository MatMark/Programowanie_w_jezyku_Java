import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImgPanel extends JPanel {
    public BufferedImage image;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(image != null)
            g.drawImage(image, 0, 0, 256, 256, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    public void setImage(String path) throws IOException {
        image = ImageIO.read(new File(path));
    }
}
