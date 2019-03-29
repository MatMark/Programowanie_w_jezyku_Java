package JavaBean;

import JavaBean.InnerClasses.ClockPanel;
import JavaBean.InnerClasses.Counter;
import JavaBean.InnerClasses.MyTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;


public class ClockBean extends JPanel implements ActionListener {

    private MyTimer myTimer;
    public JButton startButton = new JButton("Start");
    private Font font = new Font("Segoe Print", Font.BOLD, 14);
    //private Color color = Color.green;
    //private Font font = new Font("Papyrus", Font.PLAIN, 14);
    private Color color = Color.yellow;
    private ClockPanel clockPanel;

    public ClockBean() {
        super(new BorderLayout(10,10));
        startButton.addActionListener(this);
        super.setBackground(color);

        startButton.setFont(font);

        clockPanel = new ClockPanel(this);
        myTimer = new MyTimer(this);

        add(clockPanel, BorderLayout.CENTER);
        add(startButton, BorderLayout.PAGE_END);
        add(myTimer, BorderLayout.PAGE_START);
    }


    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public ClockPanel getClockPanel() {
        return clockPanel;
    }

    @Override
    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public Font getFont() {
        return font;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source.equals(startButton)) {
            {
                    Date time = new Date();
                    time.setHours(clockPanel.getSelectedHour());
                    time.setMinutes(clockPanel.getSelectedMinute());
                    time.setSeconds(clockPanel.getSelectedSecond());

                    if(new Date().before(time)) clockPanel.add(new Counter(this, time));
                    else {
                        time.setDate(new Date().getDate()+1);
                        clockPanel.add(new Counter(this, time));
                    }
            }
        }
    }
    public static void main(String[] args) {
        JFrame window = new JFrame();
        ClockBean clock = new ClockBean();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(275, 400);
        window.setContentPane(clock);
        window.setVisible(true);
    }
}
