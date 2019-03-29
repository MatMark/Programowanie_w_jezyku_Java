package JavaBean;

import javax.swing.*;
import java.awt.*;
import java.beans.*;

public class ClockBeanCustomizer extends JPanel implements Customizer {

    private ClockBean bean;
   // private Color color;
    private PropertyEditor colorEditor;
    private PropertyEditor fontEditor;
    //private Font font;
    //private JComboBox<Font> fonts;
    //private DefaultComboBoxModel<Font> comboFonts = new DefaultComboBoxModel<>();



    public ClockBeanCustomizer(){

        //fonts = new JComboBox<>(comboFonts);


        colorEditor = PropertyEditorManager.findEditor(Color.class);
        colorEditor.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                setColor((Color) colorEditor.getValue());
            }
        });

        add(colorEditor.getCustomEditor());


        fontEditor = PropertyEditorManager.findEditor(Font.class);
        fontEditor.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                setFont((Font) fontEditor.getValue());
            }
        });

        add(fontEditor.getCustomEditor());

    }

    @Override
    public void setFont(Font newValue) {
        if (bean == null)
            return;
        Font oldValue = bean.getFont();
        bean.setFont(newValue);
        firePropertyChange("font", oldValue, newValue);
    }

    public void setColor(Color newValue) {
        if (bean == null)
            return;
        Color oldValue = bean.getColor();
        bean.setColor(newValue);
        firePropertyChange("color", oldValue, newValue);
    }

//    public void addPropertyChangeListener(PropertyChangeListener l) {
//        addPropertyChangeListener(l);
//    }
//
//    public void removePropertyChangeListener(PropertyChangeListener l) {
//        removePropertyChangeListener(l);
//    }

        @Override
    public void setObject(Object obj) {
        bean = (ClockBean) obj;
        //color = bean.getColor();
        //font = bean.getFont();
    }
}