import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PicModifier extends JFrame implements ActionListener {

    private JPanel mainPanel = new JPanel(new BorderLayout());
    private JPanel workPanel = new JPanel(new GridLayout(1,2, 10,10));
    private JPanel toolPanel = new JPanel(new GridLayout(1,3));
    private ImgPanel originalImg = new ImgPanel();
    private ImgPanel filteredImg = new ImgPanel();
    private JButton chooseImg = new JButton("Wybierz obraz");
    private JButton chooseScript = new JButton("Wybierz skrypt");
    private JButton useFilter = new JButton("Użyj");

    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    private File dir = new File("C:\\Users\\matma\\Documents\\TestForJavaJs");
    private File img;
    private File script;

    public PicModifier() {
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        add(mainPanel);
        init();
        setSize(522, 384);
        setVisible(true);
    }

    private void init(){
        workPanel.add(originalImg);
        workPanel.add(filteredImg);

        toolPanel.add(chooseImg);
        toolPanel.add(chooseScript);
        toolPanel.add(useFilter);

        mainPanel.add(workPanel, BorderLayout.CENTER);
        mainPanel.add(toolPanel, BorderLayout.PAGE_END);

        chooseImg.addActionListener(this);
        chooseScript.addActionListener(this);
        useFilter.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source.equals(chooseImg)) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new FileNameExtensionFilter("Obraz", "png", "jpg"));
            chooser.setCurrentDirectory(dir);
            chooser.setDialogTitle("Wybierz obraz do modyfikacji");
            int ret = chooser.showOpenDialog(new JDialog());

            if (ret == JFileChooser.APPROVE_OPTION) {
                img = chooser.getSelectedFile();
                try {
                    originalImg.setImage(img.getPath());
                    filteredImg.setImage(img.getPath());
                    revalidate();
                    repaint();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (source.equals(chooseScript)) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new FileNameExtensionFilter("Skrypt JS", "js"));
            chooser.setCurrentDirectory(dir);
            chooser.setDialogTitle("Wybierz skrypt");
            int ret = chooser.showOpenDialog(new JDialog());

            if (ret == JFileChooser.APPROVE_OPTION) {
                script = chooser.getSelectedFile();
                try {
                    engine.eval(new FileReader(script));
                } catch (ScriptException | FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (source.equals(useFilter)) {
            Invocable invocable = (Invocable) engine;
            try {
                invocable.invokeFunction("effect", filteredImg.image);
            } catch (ScriptException | NoSuchMethodException ex) {
                ex.printStackTrace();
            }
            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        new PicModifier();
    }
}
