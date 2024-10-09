package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintFactory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class NewSprintForm extends JFrame implements BaseComponent {
    // Form fields for Sprint details
    private JTextField nameField = new JTextField();
    private JTextArea descArea = new JTextArea();
    private SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(5, 1, 999999, 1);
    private JSpinner sprintDays = new JSpinner(spinnerNumberModel);

    // Components for selecting user stories
    private DefaultListModel<String> listModel;
    private JList<String> userStoryList;

    public NewSprintForm() {
        this.init();
    }

    public void init() {
        setTitle("New Sprint");
        setSize(400, 350);

        GridBagLayout myGridbagLayout = new GridBagLayout();
        JPanel myJpanel = new JPanel();
        myJpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        myJpanel.setLayout(myGridbagLayout);

        JLabel nameLabel = new JLabel("Name:");
        myJpanel.add(nameLabel, new CustomConstraints(0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(nameField, new CustomConstraints(1, 0, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        JLabel descLabel = new JLabel("Description:");
        myJpanel.add(descLabel, new CustomConstraints(0, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(new JScrollPane(descArea), new CustomConstraints(1, 1, GridBagConstraints.EAST, 1.0, 0.3, GridBagConstraints.BOTH));

        JLabel pointsLabel = new JLabel("Length (Days):");
        myJpanel.add(pointsLabel, new CustomConstraints(0, 2, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(sprintDays, new CustomConstraints(1, 2, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.WEST));

        // Set up the user story selection list
        listModel = new DefaultListModel<>();
        for (UserStory userStory : UserStoryStore.getInstance().getUserStories()) {
            listModel.addElement("US #" + userStory.getId() + " - " + userStory.getName());
        }

        // Create the userStoryList JList component
        userStoryList = new JList<>(listModel);
        // Enable multiple interval selection mode
        userStoryList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollPane = new JScrollPane(userStoryList);

        JLabel userStoriesLabel = new JLabel("User Stories:");
        myJpanel.add(userStoriesLabel, new CustomConstraints(0, 3, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(scrollPane, new CustomConstraints(1, 3, GridBagConstraints.EAST, 1.0, 0.3, GridBagConstraints.BOTH));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            Sprint newSprint = getSprintObject();
            SprintStore.getInstance().addSprint(newSprint);
            dispose();
        });

        myJpanel.add(cancelButton, new CustomConstraints(0, 4, GridBagConstraints.EAST, GridBagConstraints.NONE));
        myJpanel.add(submitButton, new CustomConstraints(1, 4, GridBagConstraints.WEST, GridBagConstraints.NONE));

        add(myJpanel);
    }

    // Retrieve selected user stories and create a new sprint object
    public Sprint getSprintObject() {
        String name = nameField.getText();
        String description = descArea.getText();
        Integer length = (Integer) sprintDays.getValue();

        SprintFactory sprintFactory = SprintFactory.getSprintFactory();
        Sprint mySprint = sprintFactory.createNewSprint(name, description, length);

        // Add selected user stories to the sprint
        int[] selectedIndices = userStoryList.getSelectedIndices();
        List<UserStory> allUserStories = UserStoryStore.getInstance().getUserStories();
        for (int index : selectedIndices) {
            if (index >= 0 && index < allUserStories.size()) {
                mySprint.addUserStory(allUserStories.get(index));
            }
        }

        return mySprint;
    }
}
