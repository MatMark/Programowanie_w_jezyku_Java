import Interfaces.IRoom;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class StartPanel extends JFrame implements ActionListener {

    private Client client;

    private JPanel mainPanel = new JPanel(new BorderLayout());
    private JPanel centerPanel = new JPanel(new GridLayout(1,2,5,5));
    private JPanel namePanel = new JPanel(new GridLayout(1,3,5,5));
    private JPanel roomPanel = new JPanel(new GridLayout(3,1,5,5));
    private JPanel listPanel = new JPanel(new GridLayout(2,1,5,5));

    private DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> list = new JList<>(model);

    private JButton joinButton = new JButton("Dołącz");
    private JButton createButton = new JButton("Stwórz nowy");
    private JButton changeName = new JButton("Zmień");

    private JLabel nick = new JLabel("Nick: ");
    private JLabel rooms = new JLabel("Pokoje: ");
    private JLabel roomName = new JLabel("Nazwa pokoju: ");

    private JTextField nickText = new JTextField("Gość");
    private JTextField roomNameText = new JTextField("Nowy pokój");

    public StartPanel(Client client) throws HeadlessException, RemoteException {
        super(client.getName());
        this.client = client;
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        init();
        setVisible(true);
    }

    private void init() throws RemoteException {

        namePanel.add(nick);
        namePanel.add(nickText);
        namePanel.add(changeName);

        roomPanel.add(roomName);
        roomPanel.add(roomNameText);
        roomPanel.add(createButton);

        listPanel.add(rooms);
        listPanel.add(list = new JList<>(model));

        centerPanel.add(listPanel);
        centerPanel.add(roomPanel);

        mainPanel.add(namePanel, BorderLayout.PAGE_START);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(joinButton, BorderLayout.PAGE_END);

        add(mainPanel);

        nickText.setText(client.getName());

        createButton.addActionListener(this);
        joinButton.addActionListener(this);
        changeName.addActionListener(this);
    }

    public void setList() throws RemoteException {
        model.clear();
        for (IRoom room: client.getRoomList()) {
            model.addElement(room.getName());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source.equals(createButton)) {
            try {
                client.getServer().newRoom(roomNameText.getText());
            } catch (RemoteException | MalformedObjectNameException | NotCompliantMBeanException | InstanceAlreadyExistsException | MBeanRegistrationException ex) {
                ex.printStackTrace();
            }
            try {
                client.refreshStart();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }

        if (source.equals(joinButton)) {
            try {
                client.joinToRoom(client.getRoomList().get(list.getSelectedIndex()));
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
            try {
                new RoomPanel(client, client.room);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }

        if (source.equals(changeName)) {
            client.setName(nickText.getText());
        }
    }
}
