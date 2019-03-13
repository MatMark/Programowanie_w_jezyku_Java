import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static java.lang.Thread.sleep;

public class RandomIntClass extends JLabel implements Runnable{
    private int h, w;
    private boolean isRunning = true;

    public RandomIntClass() {
        super("RandomInt");
        super.setPreferredSize(new Dimension(100, 50));
        super.setHorizontalAlignment(JTextField.CENTER);
        h = (int) super.getPreferredSize().getHeight();
        w = (int) super.getPreferredSize().getWidth();
        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.blue);
        g.fillOval(5, (h/2)-5, 10, 10);
        g.fillOval(w-15, (h/2)-5, 10, 10);
    }

    @Override
    public void run() {
        while(isRunning) {
            try {
                super.setText(Integer.toString(new Random().nextInt(999) + 235000));
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
