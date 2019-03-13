import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClass extends JButton implements ActionListener {

    private int h, w;
    public ButtonClass() {
        super("Don't touch");
        super.setPreferredSize(new Dimension(100,50));
        h = (int) super.getPreferredSize().getHeight();
        w = (int) super.getPreferredSize().getWidth();
        addActionListener(this);
    }


    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.red);
        g.fillOval(5, (h/2)-5, 10, 10);
        g.fillOval(w-15, (h/2)-5, 10, 10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this))
        {
            JOptionPane.showMessageDialog(null, "You touch it!", "DON'T TOUCH!!!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
