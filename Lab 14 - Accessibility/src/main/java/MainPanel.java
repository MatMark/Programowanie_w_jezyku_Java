import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JFrame implements ActionListener {

    private JPanel mainPanel = new JPanel(new BorderLayout());
    private JPanel inputPanel = new JPanel(new FlowLayout());
    private JPanel upperPanel = new JPanel(new GridLayout(3,1));
    private JPanel centerPanel = new JPanel(new GridLayout(6,1));
    private JPanel inPanel = new JPanel(new FlowLayout());
    private JPanel out1Panel = new JPanel(new FlowLayout());
    private JPanel out2Panel = new JPanel(new FlowLayout());
    private JPanel out3Panel = new JPanel(new FlowLayout());

    private JLabel x3In = new JLabel(" x^3 + ");
    private JLabel x2In = new JLabel(" x^2 + ");
    private JLabel x1In = new JLabel(" x + ");
    private JLabel zeroIn = new JLabel(" = 0 ");

    private JTextField a = new JTextField(6);
    private JTextField b = new JTextField(6);
    private JTextField c = new JTextField(6);
    private JTextField d = new JTextField(6);

    private JLabel precisionL = new JLabel("Dokładność: ");
    private JTextField precision = new JTextField(3);

    private JButton calculate = new JButton("Oblicz");

    private JLabel x1Out = new JLabel("x1 = ");
    private JLabel x2Out = new JLabel("x2 = ");
    private JLabel x3Out = new JLabel("x3 = ");

    private JTextField res1 = new JTextField(40);
    private JTextField res2 = new JTextField(40);
    private JTextField res3 = new JTextField(40);

    public MainPanel() throws HeadlessException {
        super("Lab 14 - Cubic equation");
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        init();
        setVisible(true);
    }

    private void init(){
        addToPanel(inputPanel, a, x3In, b, x2In, c, x1In, d, zeroIn);
        addToPanel(inPanel, precisionL, precision);
        addToPanel(out1Panel, x1Out, res1);
        addToPanel(out2Panel, x2Out, res2);
        addToPanel(out3Panel, x3Out, res3);
        addToPanel(centerPanel, new JSeparator(SwingConstants.HORIZONTAL), out1Panel, new JSeparator(SwingConstants.HORIZONTAL), out2Panel, new JSeparator(SwingConstants.HORIZONTAL), out3Panel);
        addToPanel(upperPanel, inputPanel, inPanel, calculate);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(upperPanel, BorderLayout.PAGE_START);
        add(mainPanel);

        calculate.addActionListener(this);

        res1.setEditable(false);
        res2.setEditable(false);
        res3.setEditable(false);

        accessibility();
    }

    void addToPanel(JPanel panel, JComponent... components){
            for (JComponent component : components) {
                panel.add(component);
            }
    }

    void accessibility(){
        a.setToolTipText("Enter the a factor");
        a.getAccessibleContext().setAccessibleDescription("Wpisz współczynnik a");
        a.getAccessibleContext().setAccessibleName("Enter the a factor");

        b.setToolTipText("Enter the b factor");
        b.getAccessibleContext().setAccessibleDescription("Wpisz współczynnik b");
        b.getAccessibleContext().setAccessibleName("Enter the b factor");

        c.setToolTipText("Enter the c factor");
        c.getAccessibleContext().setAccessibleDescription("Wpisz współczynnik c");
        c.getAccessibleContext().setAccessibleName("Enter the c factor");

        d.setToolTipText("Enter the d factor");
        d.getAccessibleContext().setAccessibleDescription("Wpisz współczynnik d");
        d.getAccessibleContext().setAccessibleName("Enter the d factor");

        precision.setToolTipText("Enter precision");
        precision.getAccessibleContext().setAccessibleDescription("Wpisz liczbę cyfr po przecinku");
        precision.getAccessibleContext().setAccessibleName("Enter precision");

        res1.setToolTipText("First result");
        res1.getAccessibleContext().setAccessibleDescription("Pierwsze rozwiązanie");
        res1.getAccessibleContext().setAccessibleName("First result");

        res2.setToolTipText("Second result");
        res2.getAccessibleContext().setAccessibleDescription("Drugie rozwiązanie");
        res2.getAccessibleContext().setAccessibleName("Second result");

        res3.setToolTipText("Third result");
        res3.getAccessibleContext().setAccessibleDescription("Trzecie rozwiązanie");
        res3.getAccessibleContext().setAccessibleName("Third result");

        calculate.setToolTipText("Calculate button");
        calculate.getAccessibleContext().setAccessibleDescription("Oblicz miejsca zerowe");
        calculate.getAccessibleContext().setAccessibleName("Calculate button");
    }

    private boolean isFilled(){
        return !a.getText().isEmpty() && !b.getText().isEmpty() && !c.getText().isEmpty() && !d.getText().isEmpty() && !precision.getText().isEmpty();
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source.equals(calculate) && isFilled()) {
            double ad = Double.parseDouble(a.getText());
            double bd = Double.parseDouble(b.getText());
            double cd = Double.parseDouble(c.getText());
            double dd = Double.parseDouble(d.getText());
            int prec = Integer.parseInt(precision.getText());
            CubicEquations cubic = new CubicEquations(ad, bd, cd ,dd, prec);
//            new CubicEquations(ad, bd, cd ,dd);

            if(cubic.isMore()){
                res1.setText(cubic.getX1().toString());
                res2.setText(cubic.getX2().toString());
                res3.setText(cubic.getX3().toString());
            }else{
                res1.setText(cubic.getX1().toString());
                res2.setText("-");
                res3.setText("-");
            }
        }
    }

    public static void main(String[] args) {
        new MainPanel();
    }
}
