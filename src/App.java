import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;

class Task {
    private String description;
    private LocalDate date;
    private LocalTime time;
    private boolean completed;

    // Constructor for the task class
    public Task(String description, LocalDate date, LocalTime time) {
        this.description = description;
        this.date = date;
        this.time = time;
        this.completed = false;
    }

    // Get Actions
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public boolean isCompleted() { return completed; }
    public void markCompleted() { completed = true; }

    // Description on how tasks Completed will be displayed and how the date and time will return as a string
    @Override
    public String toString() {
        String status = completed ? "[X]" : "[ ]";
        String timeStr = (time != null) ? time.toString() : "All day";
        return String.format("%s %s at %s (%s)", status, description, timeStr, date);
    }
}

public class App extends JFrame { // Declared components and data structures for the application
    private java.util.List<Task> tasks = new ArrayList<>();
    private DefaultListModel<Task> taskListModel = new DefaultListModel<>();
    private JList<Task> taskList = new JList<>(taskListModel);

    private JTextField descField = new JTextField(20);
    private JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
    private JTextField timeField = new JTextField(5);

    public App() { // The objects added to design the application
        // Container for the App
        setTitle("Task Scheduler with Calendar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Text field and label for Description
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descField);

        // Date object to toggle
        inputPanel.add(new JLabel("Date:"));
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        inputPanel.add(dateSpinner);
        // Text field to indicate time 
        inputPanel.add(new JLabel("Time (HH:mm):"));
        inputPanel.add(timeField);
        // Button for Adding Tasks
        JButton addButton = new JButton("Add Task");
        inputPanel.add(addButton);
        // Layout to display all the tasks in the list
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Tasks for Selected Date:"), BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(taskList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Container for the Mark Completed and Remove tasks buttons
        JPanel actionPanel = new JPanel();
        JButton completeButton = new JButton("Mark Completed");
        JButton removeButton = new JButton("Remove Task");
        actionPanel.add(completeButton);
        actionPanel.add(removeButton);

        // Layouts for the input, list and action panels
        add(inputPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // Listeners connected the functions below to trigger actions
        addButton.addActionListener(e -> addTask());
        completeButton.addActionListener(e -> markCompleted());
        removeButton.addActionListener(e -> removeTask());
        dateSpinner.addChangeListener(e -> updateTaskList());

        setVisible(true);
        updateTaskList();
    }
        private void addTask() { // Add Tasks
        String desc = descField.getText().trim();
        if (desc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a description.");
            return;
        }
        Date dateValue = (Date)dateSpinner.getValue();
        LocalDate date = dateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String timeStr = timeField.getText().trim();
        LocalTime time = null;
        if (!timeStr.isEmpty()) {
            try {
                time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid time format. Use HH:mm.");
                return;
            }
        }
        tasks.add(new Task(desc, date, time));
        descField.setText("");
        timeField.setText("");
        updateTaskList();
    }

    private void updateTaskList() { // Updates the list when date is selected in the application
        taskListModel.clear();
        Date dateValue = (Date)dateSpinner.getValue();
        LocalDate selectedDate = dateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        for (Task t : tasks) {
            if (t.getDate().equals(selectedDate)) {
                taskListModel.addElement(t);
            }
        }
    }

    private void markCompleted() { // Marks the task "X" at the left side to indicate as completed.
        Task selected = taskList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a task to mark as completed.");
            return;
        }
        selected.markCompleted();
        updateTaskList();
    }

    private void removeTask() { // Removes tasks from the list
        Task selected = taskList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a task to remove.");
            return;
        }
        tasks.remove(selected);
        updateTaskList();
    }

    public static void main(String[] args) { // Main class
        SwingUtilities.invokeLater(App::new);
    }
}