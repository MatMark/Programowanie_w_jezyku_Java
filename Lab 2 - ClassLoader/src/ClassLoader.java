import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoader extends JFrame implements ActionListener {

    private File directory;
    private String filePath;
    private String root;
    private int instanceNumber = 0;

    private JFrame secondFrame;
    private JPanel panel;
    private JPanel secondPanel;

    private JMenu file;
    private JMenu infoRoot;
    private JMenuBar menuBar;
    private JMenuItem rootSource;
    private JMenuItem addClass;

    private JButton removeClass;
    private JButton add;
    private JButton remove;

    private JList<Class<? extends JComponent>> listView;
    private DefaultListModel<Class<? extends JComponent>> listModel = new DefaultListModel<>();


    public ClassLoader(String name){
        super(name);
        initForm();
    }

    private void initForm()
    {
        //panele
        secondFrame = new JFrame("JComponents");
        panel = new JPanel();
        secondPanel = new JPanel();

        //menu
        file = new JMenu("Plik");
        infoRoot= new JMenu("Nie ma ścieżki źródłowej");
        menuBar = new JMenuBar();
        rootSource = new JMenuItem("Katalog źródłowy");
        addClass = new JMenuItem("Dodaj klasę");

        //przyciski
        removeClass = new JButton("Usuń klasę");
        add = new JButton("Dodaj");
        remove = new JButton("Usuń ostatni");

        listView = new JList<>(listModel);
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //ustawienia okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        secondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //sluchacze
        rootSource.addActionListener(this);
        addClass.addActionListener(this);
        removeClass.addActionListener(this);
        add.addActionListener(this);
        remove.addActionListener(this);

        //menu
        menuBar.add(file);
        menuBar.add(new JSeparator());
        menuBar.add(infoRoot);
        file.add(rootSource);
        file.add(addClass);
        setJMenuBar(menuBar);
        infoRoot.setSelected(false);

        //panel
        panel.add(new JScrollPane(listView));
        panel.add(removeClass);
        panel.add(add);
        panel.add(remove);

        setContentPane(panel);
        secondFrame.setContentPane(secondPanel);
        panel.setFocusable(true);
        secondPanel.setFocusable(true);
        pack();
        secondFrame.setSize(275, 325);
        secondFrame.setResizable(false);
        secondFrame.setLocation(this.getX() + this.getWidth(), this.getY());
        setVisible(true);
        secondFrame.setVisible(true);
    }

    private void addJComp(JComponent... components)
    {
        for (JComponent component : components)
        {
            component.setPreferredSize(new Dimension(100, 50));
            secondPanel.add(component);
        }
    }

    private void removeLastInstance()
    {
        if(getInstanceNumber()>0)
        {
            System.out.println("Usunięto " + secondPanel.getComponent(getInstanceNumber() - 1).getClass());
            secondPanel.remove(secondPanel.getComponent(getInstanceNumber() - 1));
            secondPanel.repaint();
            secondFrame.setVisible(true);
            decInstanceNum();
        }
        else System.out.println("Nie ma elemantów do usunięcia");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if(source.equals(removeClass))
        {
            listModel.removeElement(listView.getSelectedValue());
            secondFrame.setVisible(true);
        }

        if(source.equals(add))
        {
            if(listView.getSelectedValue() != null)
            {
                try {
                    if(getInstanceNumber() < 10) {
                        System.out.println("Dodano " + listView.getSelectedValue());
                        JComponent obj = listView.getSelectedValue().newInstance();
                        addJComp(obj);
                        secondPanel.repaint();
                        secondFrame.setVisible(true);
                        incInstanceNum();
                    }
                    else System.out.println("Za dużo instancji obiektów");
                } catch (InstantiationException | IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            }
            else System.out.println("Wybierz obiekt z listy");
        }

        if(source.equals(remove))
        {
            removeLastInstance();
        }

        if(source.equals(addClass))
        {
            if (directory != null)
            {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setCurrentDirectory(directory);
                chooser.setDialogTitle("Wybierz klasę do dodania");
                int ret = chooser.showOpenDialog(new JDialog());
                if (ret == JFileChooser.APPROVE_OPTION)
                {
                    File classFile = chooser.getSelectedFile();
                    filePath = classFile.toString();
                    filePath = filePath.replace(directory.toString(), "");
                    filePath = filePath.replace("\\", ".");
                    filePath = filePath.replace(".class", "");
                    filePath = filePath.substring(1);
                    System.out.println(filePath);
                }

                URL[] classLoaderUrls = new URL[0];
                try {
                    classLoaderUrls = new URL[]{new URL(root)};
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
                URLClassLoader classloader1 = new URLClassLoader(classLoaderUrls);
                classLoaderUrls = null;
                try {
                    Class<?> class1 = classloader1.loadClass(filePath);
                    Constructor<?> constructor = class1.getConstructor();
                    Method method = class1.getMethod("toString");

                    Object objClass1 = constructor.newInstance();
                    System.out.println("Wywołanie toString(): " + method.invoke(objClass1));

                    listModel.addElement((Class<? extends JComponent>)class1);
                    classloader1 = null;

                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e1) {
                    e1.printStackTrace();
                }

            }
            else System.out.println("Wybierz najpierw katalog źródłowy");
        }

        if(source.equals(rootSource))
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("Wybierz katalog źródłowy");
            int ret = chooser.showOpenDialog(new JDialog());

            if (ret == JFileChooser.APPROVE_OPTION) {
                directory = chooser.getSelectedFile();
                root = "file:" + directory + "/";
                root=root.replace("\\", "\\\\");
                System.out.println(root);
                infoRoot.setText("Katalog źródłowy: " + root);
            }
        }
    }

    public void incInstanceNum()
    {
        setInstanceNumber(getInstanceNumber()+1);
    }

    public void decInstanceNum()
    {
        setInstanceNumber(getInstanceNumber()-1);
    }

    public int getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(int instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    public static void main(String[] args) {
        new ClassLoader("MainWindow");
    }
}
