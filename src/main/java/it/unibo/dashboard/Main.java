package it.unibo.dashboard;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Smart Waste Disposal System");
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("Dashboard", JLabel.CENTER), BorderLayout.NORTH);

        JPanel sensorPanel = new JPanel();
        sensorPanel.setLayout(new BoxLayout(sensorPanel, BoxLayout.Y_AXIS));
        sensorPanel.setBorder(BorderFactory.createTitledBorder("Sensors"));

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.X_AXIS));
        tempPanel.add(new JLabel("Temperature: "));
        tempPanel.add(Box.createHorizontalGlue());
        JLabel tempBox = new JLabel("23 °C");
        tempPanel.add(tempBox);
        tempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel levelPanel = new JPanel();
        levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.X_AXIS));
        levelPanel.add(new JLabel("Waste Level: "));
        levelPanel.add(Box.createHorizontalGlue());
        JLabel levelBox = new JLabel("100%");
        levelPanel.add(levelBox);
        levelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        sensorPanel.add(tempPanel);
        sensorPanel.add(levelPanel);

        panel.add(sensorPanel, BorderLayout.WEST);

        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Activity Log"));
        JTextArea logArea = new JTextArea();
        logArea.setEditable(true);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(logArea);
        logPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(logPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton proceedButton = new JButton("Proceed");
        JButton restoreButton = new JButton("Restore");
        buttonPanel.add(proceedButton);
        buttonPanel.add(restoreButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(panel);
        frame.setVisible(true);
    }
}