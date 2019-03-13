import javax.swing.*;
import java.awt.*;

public class TextBoxClass extends JTextField{
    private int h, w;

    public TextBoxClass() {
        super("Notatka");
        super.setPreferredSize(new Dimension(100, 50));
        super.setHorizontalAlignment(JTextField.CENTER);
        h = (int) super.getPreferredSize().getHeight();
        w = (int) super.getPreferredSize().getWidth();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.green);
        g.fillOval(5, (h/2)-5, 10, 10);
        g.fillOval(w-15, (h/2)-5, 10, 10);
    }
}
