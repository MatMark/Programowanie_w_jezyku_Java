package JavaBean;

import javax.imageio.ImageIO;
import java.awt.*;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.io.IOException;

public class ClockBeanBeanInfo extends SimpleBeanInfo {

    public Image getIcon(int iconType) {
        String name = "";
        if (iconType == BeanInfo.ICON_COLOR_16x16)
            name = "COLOR_16x16";
        else if (iconType == BeanInfo.ICON_COLOR_32x32)
            name = "COLOR_32x32";
        else if (iconType == BeanInfo.ICON_MONO_16x16)
            name = "MONO_16x16";
        else if (iconType == BeanInfo.ICON_MONO_32x32)
            name = "MONO_32x32";
        else
            return null;
        Image im = null;
        try {
            im = ImageIO.read(ClockBeanBeanInfo.class.getClassLoader().getResourceAsStream("ChartBean_" + name + ".gif"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return im;
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        try {

            return new PropertyDescriptor[]{
                    new PropertyDescriptor("font", ClockBean.class),
                    new PropertyDescriptor("color", ClockBean.class),
            };
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
