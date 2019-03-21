import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.swing.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class MainPanel extends JFrame implements ActionListener {

    //JSON
    private Map<String, ?> config = Collections.singletonMap(JsonGenerator.PRETTY_PRINTING, true);
    private JsonWriterFactory writerFactory = Json.createWriterFactory(config);

    //Validator
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();
    private ArrayList<Field> formFields = new ArrayList<>();
    private String errorMsg;

    //Swing
    private DefaultComboBoxModel<Class<?>> comboBoxModel = new DefaultComboBoxModel<>();

    private ArrayList<JTextField> textFields = new ArrayList<>();

    private File directory;
    private String root;
    private String filePath;
    private Object tmpObject;
    private Constructor<?> constructor;

    private JPanel mainPanel;
    private JPanel formPanel;
    private JButton accept;
    private JComboBox<Class<?>> forms;

    private JMenu file;
    private JMenuBar menuBar;
    private JMenuItem chooseRoot;
    private JMenuItem addForm;


    public MainPanel() throws HeadlessException {
        super("Java Annotations");
        init();
    }

    private void init()
    {
        mainPanel = new JPanel(new BorderLayout(10,10));
        formPanel = new JPanel(new GridLayout(0,2,5,5));
        file = new JMenu("Plik");
        menuBar = new JMenuBar();
        chooseRoot = new JMenuItem("Wybierz katalog źródłowy");
        addForm = new JMenuItem("Dodaj nowy formularz");
        accept = new JButton("Zatwierdź formularz");
        forms= new JComboBox<>(comboBoxModel);

        //frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(275, 400);
        setResizable(false);
        setJMenuBar(menuBar);
        setContentPane(mainPanel);

        //menu
        menuBar.add(file);
        file.add(addForm);
        file.add(chooseRoot);

        //główny panel
        mainPanel.add(forms, BorderLayout.PAGE_START);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(accept, BorderLayout.PAGE_END);

        //panel formularza
        formPanel.setBackground(Color.LIGHT_GRAY);

        //sluchacze
        accept.addActionListener(this);
        addForm.addActionListener(this);
        chooseRoot.addActionListener(this);
        forms.addActionListener(this);

        setVisible(true);
    }

    //-------------------dodanie nowego pola------------------------
    private void addNewField(String fieldName)
    {
        formPanel.add(new JLabel(fieldName+": ", SwingConstants.CENTER));
        JTextField txtField = new JTextField("",20);
        formPanel.add(txtField);
        textFields.add(txtField);
    }

    //------------------usunięcie poprzednich pól------------------------
    private void removeOldFields() { formPanel.removeAll(); }

    //----------------------dodanie pól do wypełnienia na panel------------------
    private void makeFields(ArrayList<Field> formFields)
    {
        removeOldFields();
        textFields.clear();
        for (Field field: formFields) {
            addNewField(field.getName());
        }
        formPanel.revalidate();
        formPanel.repaint();
    }

    //------------------sprawdzenie pól klasy---------------------
    private void getFields(Class<?> Class1){
        formFields.clear();
        for(Field field : Class1.getDeclaredFields()){
            if(field.isAnnotationPresent(Annotate.class)){
                formFields.add(field);
            }
        }
    }

    //------------------------------------ustawienie danej wartości------------------------------------
    private void setValue(String propertyName, String value) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor desc = new PropertyDescriptor(propertyName, (Class<?>) Objects.requireNonNull(forms.getSelectedItem()));
        Class type = desc.getPropertyType();
        if (type.equals(String.class)) { desc.getWriteMethod().invoke(tmpObject, value); }
        else if (type.equals(int.class)) { desc.getWriteMethod().invoke(tmpObject, Integer.valueOf(value)); }
    }

    //--------------------------przypisanie wartości z pól do obiektu------------------------------------
    private void setValues() throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        int i = 0;
        for (Field field: formFields) {
            if(!textFields.get(i).getText().equals(""))setValue(field.getName(), textFields.get(i).getText());
            i++;
        }
    }

    //--------------------------sprawdzenie poprawności utworzonego obiektu---------------------------------
    private boolean validateFields(){
        StringBuilder stringBuilder = new StringBuilder();
        Set<ConstraintViolation<Object>> violations = validator.validate(tmpObject);
        for (ConstraintViolation<Object> violation : violations){
            stringBuilder.append(violation.getMessage() + " : " + violation.getInvalidValue() + "\n");
        }
        errorMsg = stringBuilder.toString();
        if(!errorMsg.equals(""))JOptionPane.showMessageDialog(this, errorMsg, "Błędnie wypełnione pola",JOptionPane.ERROR_MESSAGE);
        return errorMsg.equals("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        //-----------------------------------zatwierdzenie formularza----------------------------------------
        if (source.equals(accept)) {
            try {
                setValues();
            } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e1) {
                e1.printStackTrace();
            }

            if(validateFields()) {
                //----------------serializacja do JSON----------------
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                int i = 0;
                for (JTextField textField : textFields) {
                    arrayBuilder.add(Json.createObjectBuilder().add(formFields.get(i).getName(), textField.getText()));
                    i++;
                }
                JsonObject jsonObject = Json.createObjectBuilder().add(tmpObject.getClass().getName(), arrayBuilder).build();

                try {
                    writerFactory.createWriter(new FileOutputStream(JOptionPane.showInputDialog(this, "Podaj nazwę pliku do zapisania", "Nazwa pliku", JOptionPane.QUESTION_MESSAGE) + ".txt")).write(jsonObject);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        }

        //-----------------------------dodanie nowego formularza---------------------------
        if (source.equals(addForm)) {
            if (directory != null) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setCurrentDirectory(directory);
                chooser.setDialogTitle("Wybierz klasę z formularzem do dodania");
                int ret = chooser.showOpenDialog(new JDialog());
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File classFile = chooser.getSelectedFile();
                    filePath = classFile.toString();
                    filePath = filePath.replace(directory.toString(), "");
                    filePath = filePath.replace("\\", ".");
                    filePath = filePath.replace(".class", "");
                    filePath = filePath.substring(1);
                }

                URL[] classLoaderUrls = new URL[0];
                try {
                    classLoaderUrls = new URL[]{new URL(root)};
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
                URLClassLoader classloader1 = new URLClassLoader(classLoaderUrls);
                try {
                    Class<?> class1 = classloader1.loadClass(filePath);
                    comboBoxModel.addElement(class1);

                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

            } else System.out.println("Wybierz najpierw katalog źródłowy");
        }

        //------------------------------zmiana katalogu źródłowego-----------------------
        if(source.equals(chooseRoot))
        {
            {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setDialogTitle("Wybierz katalog źródłowy");
                int ret = chooser.showOpenDialog(new JDialog());

                if (ret == JFileChooser.APPROVE_OPTION) {
                    directory = chooser.getSelectedFile();
                    root = "file:" + directory + "/";
                    root=root.replace("\\", "\\\\");
                }
            }
        }

        //-------------------------------zmiana formularza--------------------------------
        if (source.equals(forms)) {
            Class<?> class1 = (Class<?>) forms.getSelectedItem();
            try {
                if (class1 != null) {
                    constructor = class1.getConstructor();
                    getFields(class1);
                }
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            }
            try {
                tmpObject = constructor.newInstance();
                makeFields(formFields);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new MainPanel();
    }
}
