import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Frame extends JFrame implements ActionListener {

    private File sourcePath;
    private int instanceNumber = 0;

    private JFrame secondFrame;
    private JPanel panel;
    private JPanel secondPanel;

    private JMenu file;
    private JMenuBar menuBar;
    private JMenuItem fileSource;

    private JButton start;
    private JButton add;
    private JButton remove;

    private JList<Class<? extends JComponent>> listView;
    DefaultListModel<Class<? extends JComponent>> listModel = new DefaultListModel<>();


    public Frame(String name){
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
        menuBar = new JMenuBar();
        fileSource = new JMenuItem("Katalog źródłowy");

        //przyciski
        start = new JButton("Start");
        add = new JButton("Dodaj");
        remove = new JButton("Usuń ostatni");

        //lista
        listModel.addElement(ButtonClass.class);
        listModel.addElement(TextBoxClass.class);
        listModel.addElement(RandomIntClass.class);

        listView = new JList<>(listModel);
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //ustawienia okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        secondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //sluchacze
        fileSource.addActionListener(this);
        start.addActionListener(this);
        add.addActionListener(this);
        remove.addActionListener(this);

        //menu
        menuBar.add(file);
        file.add(fileSource);
        setJMenuBar(menuBar);

        //panel
        panel.add(new JScrollPane(listView));
        panel.add(start);
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

    public void addJComp(JComponent ... components)
    {
        for (JComponent component : components)
        {
            component.setPreferredSize(new Dimension(100, 50));
            secondPanel.add(component);
        }
    }

    public void removeLastInstance()
    {
        secondPanel.remove(secondPanel.getComponent(getInstanceNumber() - 1));
        decInstanceNum();
        secondPanel.repaint();
        secondFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if(source.equals(start))
        {
            secondFrame.setVisible(true);
        }

        if(source.equals(add))
        {
            System.out.println(listView.getSelectedValue());
            try {
                if(getInstanceNumber() < 10) {
                    addJComp(listView.getSelectedValue().newInstance());
                    incInstanceNum();
                    secondPanel.repaint();
                    secondFrame.setVisible(true);
                }
                else System.out.println("Za dużo instancji obiektów\n");
            } catch (InstantiationException | IllegalAccessException e1) {
                e1.printStackTrace();
            }

        }

        if(source.equals(remove))
        {
            removeLastInstance();
        }

        if(source.equals(fileSource))
        {
//            JFileChooser chooser = new JFileChooser();
//            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//            int ret = chooser.showOpenDialog(new JDialog());
//
//            if (ret == JFileChooser.APPROVE_OPTION) {
//                    sourcePath = chooser.getSelectedFile();
//                    System.out.println(sourcePath);
//            }
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int ret = chooser.showOpenDialog(new JDialog());

            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();

                URL[] classLoaderUrls = new URL[0];
                try {
                    //-------------------------------------WSTAWIAC URL Z FILE--------------------------------------------------
                    classLoaderUrls = new URL[]{new URL("file:E:\\OneDrive PWr\\OneDrive - Politechnika Wroclawska\\Ingeniarius\\Semestr VI\\Prog. JAVA\\Lab 2 - ClassLoader\\out\\production\\Lab 2 - ClassLoader/")};
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
                URLClassLoader classloader1 = new URLClassLoader(classLoaderUrls);
                try {
                    //------------------------------------BRAC NAZWE Z NAZWY FILE-----------------------------------------------
                    Class<?> class1 = classloader1.loadClass("ButtonClass");
                    System.out.println(class1);
                    listModel.addElement((Class<? extends JComponent>)class1);

                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

                System.out.println(file);
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
}
