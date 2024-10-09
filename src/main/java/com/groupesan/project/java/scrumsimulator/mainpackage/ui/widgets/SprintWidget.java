package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SprintWidget extends JPanel implements BaseComponent {
    private JLabel nameLabel;
    private JLabel descLabel;
    private JLabel lengthLabel;
    private JLabel remainingDaysLabel;
    private JLabel numUserStoriesLabel;
    private JTextArea userStoriesArea;  // Display user stories in a text area

    private boolean isSelected;  // Track selection state

    public SprintWidget(Sprint sprint) {
        // Initialize labels with Sprint attributes
        nameLabel = new JLabel("Name: " + sprint.getName());
        descLabel = new JLabel("Description: " + sprint.getDescription());
        lengthLabel = new JLabel("Length: " + sprint.getLength() + " Days");
        remainingDaysLabel = new JLabel("Remaining: " + sprint.getDaysRemaining() + " Days");
        numUserStoriesLabel = new JLabel("User Stories: " + sprint.getUserStories().size());

        // Initialize the text area for user stories
        userStoriesArea = new JTextArea(5, 20);
        userStoriesArea.setEditable(false);
        userStoriesArea.setLineWrap(true);
        userStoriesArea.setWrapStyleWord(true);

        // Set text for user stories area
        StringBuilder userStoryText = new StringBuilder();
        List<UserStory> userStories = sprint.getUserStories();
        for (UserStory story : userStories) {
            userStoryText.append("US #").append(story.getId()).append(": ").append(story.getName()).append("\n");
        }
        userStoriesArea.setText(userStoryText.toString());

        this.isSelected = false;  // Initial selection state
        this.init();  // Initialize the UI components

        // Add mouse listener to detect selection
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleSelection();  // Toggle selection on click
            }
        });
    }

    // Initialize the UI components and layout
    public void init() {
        GridBagLayout myGridBagLayout = new GridBagLayout();
        setLayout(myGridBagLayout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        add(descLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        add(lengthLabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        add(remainingDaysLabel, gbc);
//
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        add(numUserStoriesLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(userStoriesArea), gbc);
    }

    // Toggle the selection state and update background color
    private void toggleSelection() {
        isSelected = !isSelected;
        setBackground(isSelected ? Color.LIGHT_GRAY : getBackground());
        repaint();
    }

    // Method to get the selection state of this SprintWidget
    public boolean isSelected() {
        return isSelected;
    }

    // Method to set the selection state of this SprintWidget
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        setBackground(selected ? Color.LIGHT_GRAY : getBackground());
        repaint();  // Repaint to reflect background change
    }
}
