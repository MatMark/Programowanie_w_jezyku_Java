import Interfaces.IClient;
import Interfaces.IRoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

public class RoomPanel extends JFrame implements ActionListener {

    private Client client;
    private IRoom room;
    private Timer refresher = new Timer();

    private JPanel mainPanel = new JPanel(new BorderLayout());
    private JPanel centerPanel = new JPanel(new GridLayout(1,2,5,5));
    private JPanel leftPanel = new JPanel(new BorderLayout());
    private JPanel rightPanel = new JPanel(new BorderLayout());

    private DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> list = new JList<>(model);

    private JTextArea chatBox = new JTextArea();

    private JTextField msgText = new JTextField();

    private JButton sendButton = new JButton("Wyślij");
    private JButton quitButton = new JButton("Wyjdź");

    public RoomPanel(Client client, IRoom room) throws HeadlessException, RemoteException {
        super(client.getName() + " - " + room.getName());
        this.client = client;
        this.room = room;
        setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        init();
        setVisible(true);
    }

    private void init() throws RemoteException {
        mainPanel.add(quitButton, BorderLayout.PAGE_END);

        leftPanel.add(chatBox, BorderLayout.CENTER);
        leftPanel.add(msgText, BorderLayout.PAGE_END);

        rightPanel.add(list, BorderLayout.CENTER);
        rightPanel.add(sendButton, BorderLayout.PAGE_END);

        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);
        mainPanel.add(centerPanel);
        add(mainPanel);

        chatBox.setEditable(false);

        TimerTask task = new TimerTask() {
            public void run() {
                try {
                    setList();
                    setChatText();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
        refresher.schedule(task, 1000, 1000);

        quitButton.addActionListener(this);
        sendButton.addActionListener(this);
    }

    public void setList() throws RemoteException {
        model.clear();
        for (IClient client: room.getClients()) {
            model.addElement(client.getName());
        }
    }

    private void setChatText() throws RemoteException {
        chatBox.setText("");
        for (String msg: room.getMessages()) {
            chatBox.append(msg);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source.equals(quitButton)) {
            try {
                room.quit(client);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
            setVisible(false);
            dispose();
        }

        if (source.equals(sendButton)) {
            try {
                room.appendMessages(client.getName() + " >>> " + msgText.getText());
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
            try {
                setChatText();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
            msgText.setText("");
        }
    }
}
