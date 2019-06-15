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
        inputPanel.add(a);
        inputPanel.add(x3In);
        inputPanel.add(b);
        inputPanel.add(x2In);
        inputPanel.add(c);
        inputPanel.add(x1In);
        inputPanel.add(d);
        inputPanel.add(zeroIn);

        inPanel.add(precisionL);
        inPanel.add(precision);

        out1Panel.add(x1Out);
        out1Panel.add(res1);

        out2Panel.add(x2Out);
        out2Panel.add(res2);

        out3Panel.add(x3Out);
        out3Panel.add(res3);

        centerPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        centerPanel.add(out1Panel);
        centerPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        centerPanel.add(out2Panel);
        centerPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        centerPanel.add(out3Panel);

        upperPanel.add(inputPanel);
        upperPanel.add(inPanel);
        upperPanel.add(calculate);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(upperPanel, BorderLayout.PAGE_START);
        add(mainPanel);

        calculate.addActionListener(this);

        res1.setEditable(false);
        res2.setEditable(false);
        res3.setEditable(false);

        accessibility();
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
        calculate.getAccessibleContext().setAccessibleDescription("Trzecie rozwiązanie");
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
