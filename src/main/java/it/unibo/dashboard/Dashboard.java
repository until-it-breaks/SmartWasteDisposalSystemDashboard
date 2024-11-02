package it.unibo.dashboard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import it.unibo.dashboard.api.View;

public class Dashboard implements View {

    private static final String TITLE = "Smart Waste Disposal System";
    private static final Dimension WINDOW_SIZE = new Dimension(640, 480);
    private static final String DEFAULT_VALUE = "-";

    private final JLabel tempLabel;
    private final JLabel levelLabel;
    private final JTextArea logArea;

    public Dashboard(Controller controller) {
        final JFrame frame = new JFrame(TITLE);
        frame.setSize(WINDOW_SIZE);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("Dashboard", JLabel.CENTER), BorderLayout.NORTH);

        final JPanel sensorPanel = new JPanel();
        sensorPanel.setLayout(new BoxLayout(sensorPanel, BoxLayout.Y_AXIS));
        sensorPanel.setBorder(BorderFactory.createTitledBorder("Sensors"));

        final JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.X_AXIS));
        tempPanel.add(new JLabel("Temperature: "));
        tempPanel.add(Box.createHorizontalGlue());
        tempLabel = new JLabel(DEFAULT_VALUE);
        tempPanel.add(tempLabel);
        tempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JPanel levelPanel = new JPanel();
        levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.X_AXIS));
        levelPanel.add(new JLabel("Waste Level: "));
        levelPanel.add(Box.createHorizontalGlue());
        levelLabel = new JLabel(DEFAULT_VALUE);
        levelPanel.add(levelLabel);
        levelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        sensorPanel.add(tempPanel);
        sensorPanel.add(levelPanel);

        panel.add(sensorPanel, BorderLayout.WEST);

        final JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Activity Log"));
        logArea = new JTextArea();
        logArea.setEnabled(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        final JScrollPane scrollPane = new JScrollPane(logArea);
        logPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(logPanel, BorderLayout.CENTER);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        final JButton proceedButton = new JButton("Proceed");
        final JButton restoreButton = new JButton("Restore");
        buttonPanel.add(proceedButton);
        buttonPanel.add(restoreButton);

        proceedButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.sendProceedSignal();
            } 
        });

        restoreButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.sendRestoreSignal();
            }
            
        });

        panel.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void addLogEntry(final String string) {
        SwingUtilities.invokeLater(() -> {
            this.logArea.append(string);
        });
    }

    @Override
    public void updateTemp(final String string) {
        SwingUtilities.invokeLater(() -> {
            this.tempLabel.setText(string);
        });
    }

    @Override
    public void updateLevel(final String string) {
        SwingUtilities.invokeLater(() -> {
            this.levelLabel.setText(string);
        });
    }
}
