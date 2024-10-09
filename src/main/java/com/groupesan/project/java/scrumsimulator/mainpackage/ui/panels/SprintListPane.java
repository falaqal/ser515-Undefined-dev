package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.SprintWidget;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SprintListPane extends JFrame implements BaseComponent {
    private List<SprintWidget> widgets = new ArrayList<>();
    private JPanel subPanel;

    public SprintListPane() {
        this.init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Sprints list");
        setSize(600, 400);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BorderLayout());

        // Subpanel to hold the list of sprints
        subPanel = new JPanel();
        subPanel.setLayout(new GridBagLayout());
        refreshSprintList();

        JScrollPane scrollPane = new JScrollPane(subPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton newSprintButton = new JButton("Add Sprint");
        newSprintButton.addActionListener(e -> showNewSprintForm());

        JButton deleteSprintButton = new JButton("Delete Sprint");
        deleteSprintButton.addActionListener(e -> deleteSelectedSprint());

        JButton backButton = new JButton("Cancel/Back");
        backButton.addActionListener(e -> dispose());

        buttonsPanel.add(newSprintButton);
        buttonsPanel.add(deleteSprintButton);
        buttonsPanel.add(backButton);

        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void showNewSprintForm() {
        NewSprintForm form = new NewSprintForm();
        form.setVisible(true);
        form.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                refreshSprintList();
            }
        });
    }

    public void refreshSprintList() {
        subPanel.removeAll();
        widgets.clear();

        for (Sprint sprint : SprintStore.getInstance().getSprints()) {
            SprintWidget widget = new SprintWidget(sprint);
            widgets.add(widget);
        }

        int i = 0;
        for (SprintWidget widget : widgets) {
            subPanel.add(widget, new GridBagConstraints(0, i++, 1, 1, 1.0, 0.1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        }

        subPanel.revalidate();
        subPanel.repaint();
    }

    private void deleteSelectedSprint() {
        List<SprintWidget> toRemove = new ArrayList<>();
        for (SprintWidget widget : widgets) {
            if (widget.isSelected()) {
                Sprint selectedSprint = SprintStore.getInstance().getSprints().get(widgets.indexOf(widget));
                SprintStore.getInstance().removeSprint(selectedSprint);
                toRemove.add(widget);
            }
        }
        widgets.removeAll(toRemove);
        refreshSprintList();
    }
}
