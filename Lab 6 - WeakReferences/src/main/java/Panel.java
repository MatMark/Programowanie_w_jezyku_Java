import au.com.bytecode.opencsv.CSVReader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Panel extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JButton changePath;
    private JTextField pathField;
    private JButton previousButton;
    private JButton nextButton;
    private JLabel fileName;
    private JPanel tablePanel;
    private JScrollPane scrollPane = new JScrollPane();

    private File directory;
    private int displayedFile = 0;
    private List<String> fileList = new ArrayList<>();
    private List<WeakReference<JTable>> tableList = new ArrayList<>();


    public Panel() throws HeadlessException {
        super("CSV reader");
        add(mainPanel);
        changePath.addActionListener(this);
        previousButton.addActionListener(this);
        nextButton.addActionListener(this);
        tablePanel.add(scrollPane);
    }

    //odswiezenie nazwy wyswietlanego pliku
    private void updateFileName(){
        fileName.setText(fileList.get(displayedFile).replace((directory.getPath()+"\\"),""));
    }

    private void showData(String file) throws IOException {
        if(tableList.get(displayedFile).get() == null)
        {
            //wczytywanie pliku csv
            Object[] columns;
            FileReader fileReader = new FileReader(file);
            CSVReader csvReader = new CSVReader(fileReader);
            List myEntries = csvReader.readAll();
            columns = (String[])myEntries.get(0);
            DefaultTableModel tableModel = new DefaultTableModel(columns, myEntries.size() -1);

            //ustawianie wartosci w tabeli wedlug pliku
            for(int i = 0; i < tableModel.getRowCount() + 1; i++) {
                if(i > 0) {
                    int col = 0;
                    for(String cell : (String[])myEntries.get(i)) {
                        tableModel.setValueAt(cell, i - 1, col);
                        col++;
                    }
                }
            }

            //dodanie slabej referencji tabeli z utworzonym modelem
            tableList.set(displayedFile, new WeakReference<>(new JTable(tableModel)));
        }
        //wyswietlenie tabeli
        scrollPane.setViewportView(tableList.get(displayedFile).get());
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        //wybieranie folderu z plikami csv
        if (source.equals(changePath)) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("Wybierz katalog z plikami CSV do załadowania");
            int ret = chooser.showOpenDialog(new JDialog());

            if (ret == JFileChooser.APPROVE_OPTION) {
                directory = chooser.getSelectedFile();
                pathField.setText(directory.getPath());

                //pobieranie ścieżek wszystkich plików ze wskazanego folderu
                try {
                    Files.walk(Paths.get(directory.toString()))
                            .filter(Files::isRegularFile)
                            .forEach(file -> fileList.add(file.toString()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                for (String filePath: fileList) tableList.add(new WeakReference<>(null));

                //Wyświetlanie pierwszego pliku
                try {
                    showData(fileList.get(displayedFile));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                updateFileName();
            }
        }

        //poprzedni plik
        if (source.equals(previousButton)) {
            displayedFile--;
            if(displayedFile < 0) displayedFile = fileList.size() - 1;
            try {
                showData(fileList.get(displayedFile));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            updateFileName();
        }

        //kolejny plik
        if (source.equals(nextButton)) {
            displayedFile++;
            if(displayedFile > fileList.size() - 1) displayedFile = 0;
            try {
                showData(fileList.get(displayedFile));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            updateFileName();
        }
    }

    public static void main(String[] args) {

        Panel panel = new Panel();
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setSize(675, 500);
        panel.setResizable(false);
        panel.setVisible(true);
    }
}
