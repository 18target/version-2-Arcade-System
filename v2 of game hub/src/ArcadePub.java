import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class ArcadePub extends JFrame {

    final private Font mainFont = new Font("segoe print", Font.BOLD, 18);
    JTextField tfConsole;
    JTextArea taDetails;

    private Date startTime;

    public void initialize() {

        // the console panel form
        JLabel lbConsole = new JLabel(" Select your desired Console (PS4/PS5) \t ||press start to begin timer||");
        lbConsole.setFont(mainFont);

        tfConsole = new JTextField();
        tfConsole.setFont(mainFont);

        JPanel consolePanel = new JPanel();
        consolePanel.setLayout(new GridLayout(3, 1, 6, 6));
        consolePanel.setOpaque(false);
        consolePanel.add(lbConsole);
        consolePanel.add(tfConsole);

        // details sections
        taDetails = new JTextArea();
        taDetails.setFont(mainFont);
        taDetails.setEditable(false);

        // the panel buttonz

        JButton btnStart = new JButton("Start");
        btnStart.setFont(mainFont);
        btnStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                startTime = new Date();
                taDetails.setText("Game session started at " + getTimeAsString(startTime));
            }

        });
           //actions of the buttonz
        JButton btnStop = new JButton("Stop");
        btnStop.setFont(mainFont);
        btnStop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (startTime != null) {
                    Date stopTime = new Date();
                    long durationInMillis = stopTime.getTime() - startTime.getTime();
                    double durationInHours = durationInMillis / (60 * 60 * 1000.0);

                    String consoleType = tfConsole.getText();

                    double chargePerHour = 0;

                    if (consoleType.equalsIgnoreCase("PS5")) {
                        chargePerHour = 150.0;
                    } else if (consoleType.equalsIgnoreCase("PS4")) {
                        chargePerHour = 100.0;
                    } else {
                        taDetails.setText("Invalid console type");
                        return;
                    }
                    //kulipa

                    double totalCharge = durationInHours * chargePerHour;
                    taDetails.append("\nGame session ended at " + getTimeAsString(stopTime) +
                            "\nTotal Payment: ksh" + totalCharge);

                    showPaymentOptions();
                } else {
                    taDetails.setText("Press 'Start' first to begin the game session.");
                }
            }

        });

        JButton btnclear = new JButton("Clear");
        btnclear.setFont(mainFont);
        btnclear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                tfConsole.setText("");
                taDetails.setText("");
            }

        });

        // buttonz area in the form

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 3, 5, 5));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(btnStart);
        buttonsPanel.add(btnStop);
        buttonsPanel.add(btnclear);

        // main area appearing panel

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(128, 128, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(consolePanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(taDetails), BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setTitle("Arcade Pub Payment Calculator");
        setSize(700, 700);
        setMinimumSize(new Dimension(300, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private String getTimeAsString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }

    private void showPaymentOptions() {
        Object[] options = { "M-Pesa", "Cash" };
        int choice = JOptionPane.showOptionDialog(this,
                "Choose payment option:", "Payment Options",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (choice == JOptionPane.YES_OPTION) {
            taDetails.append("\nPayment via M-Pesa. Pay via Till Number 1234");
            showPaidButton();
        } else if (choice == JOptionPane.NO_OPTION) {
            taDetails.append("\nPayment via Cash. Press 'Paid' to continue.");
            showPaidButton();
        }
    }

    private void showPaidButton() {
        JButton btnPaid = new JButton("Paid");
        btnPaid.setFont(mainFont);
        btnPaid.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                taDetails.setText("Thank you for your payment! \n\n\n select you desired console!");
                tfConsole.setText("");
                startTime = null;
            }
        });

        JPanel paidPanel = new JPanel();
        paidPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        paidPanel.add(btnPaid);

        taDetails.append("\nPress 'Paid' after making the payment.");

        JFrame paidFrame = new JFrame("Payment Confirmation");
        paidFrame.setLayout(new BorderLayout());
        paidFrame.add(paidPanel, BorderLayout.CENTER);
        paidFrame.setSize(300, 100);
        paidFrame.setLocationRelativeTo(this);
        paidFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        paidFrame.setVisible(true);
    }

    //end-return
    public static void main(String[] args) {
        ArcadePub myArcade = new ArcadePub();
        myArcade.initialize();
    }
}
