package JavaBean.InnerClasses;

import JavaBean.ClockBean;

import javax.swing.*;
import java.awt.*;

public class ClockPanel extends JPanel{

    private DefaultComboBoxModel<Integer> comboHours = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel<Integer> comboMinutes = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel<Integer> comboSeconds = new DefaultComboBoxModel<>();

    private JComboBox<Integer> hours = new JComboBox<>(comboHours);
    private JComboBox<Integer> minutes = new JComboBox<>(comboMinutes);
    private JComboBox<Integer> seconds = new JComboBox<>(comboSeconds);

    private JLabel h = new JLabel("Godzina:", SwingConstants.CENTER);
    private JLabel min = new JLabel("Minuty:", SwingConstants.CENTER);
    private JLabel sec = new JLabel("Sekundy:", SwingConstants.CENTER);

    public ClockPanel(ClockBean panel) {
        super(new GridLayout(0,1,5,5));
        setBackground(panel.getBackground().darker());
        setFonts(panel.getFont().deriveFont(14f));

        for(int i = 0; i <= 23; i++) comboHours.addElement(i);
        for(int i = 0; i <= 59; i++) {comboMinutes.addElement(i);  comboSeconds.addElement(i);}

        add(h);
        add(hours);
        add(min);
        add(minutes);
        add(sec);
        add(seconds);
    }

    public void setFonts(Font font){
        h.setFont(font);
        min.setFont(font);
        sec.setFont(font);
        hours.setFont(font);
        minutes.setFont(font);
        seconds.setFont(font);
    }


    public int getSelectedHour(){
        return (int) hours.getSelectedItem();
    }

    public int getSelectedMinute(){
        return (int) minutes.getSelectedItem();
    }

    public int getSelectedSecond(){
        return (int) seconds.getSelectedItem();
    }

}
