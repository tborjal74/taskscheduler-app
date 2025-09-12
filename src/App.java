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

    public Task(String description, LocalDate date, LocalTime time) {
        this.description = description;
        this.date = date;
        this.time = time;
        this.completed = false;
    }

    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public boolean isCompleted() { return completed; }
    public void markCompleted() { completed = true; }

    @Override
    public String toString() {
        String status = completed ? "[X]" : "[ ]";
        String timeStr = (time != null) ? time.toString() : "All day";
        return String.format("%s %s at %s (%s)", status, description, timeStr, date);
    }
}

public class App extends JFrame {
    private java.util.List<Task> tasks = new ArrayList<>();
    private DefaultListModel<Task> taskListModel = new DefaultListModel<>();
    private JList<Task> taskList = new JList<>(taskListModel);

    private JTextField descField = new JTextField(20);
    private JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
    private JTextField timeField = new JTextField(5);

    public App() {
        setTitle("Task Scheduler with Calendar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descField);

        inputPanel.add(new JLabel("Date:"));
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        inputPanel.add(dateSpinner);

        inputPanel.add(new JLabel("Time (HH:mm):"));
        inputPanel.add(timeField);

        JButton addButton = new JButton("Add Task");
        inputPanel.add(addButton);

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Tasks for Selected Date:"), BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(taskList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();
        JButton completeButton = new JButton("Mark Completed");
        JButton removeButton = new JButton("Remove Task");
        actionPanel.add(completeButton);
        actionPanel.add(removeButton);

        add(inputPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // Listeners
        addButton.addActionListener(e -> addTask());
        completeButton.addActionListener(e -> markCompleted());
        removeButton.addActionListener(e -> removeTask());
        dateSpinner.addChangeListener(e -> updateTaskList());

        setVisible(true);
        updateTaskList();
    }
        private void addTask() {
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

    private void updateTaskList() {
        taskListModel.clear();
        Date dateValue = (Date)dateSpinner.getValue();
        LocalDate selectedDate = dateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        for (Task t : tasks) {
            if (t.getDate().equals(selectedDate)) {
                taskListModel.addElement(t);
            }
        }
    }

    private void markCompleted() {
        Task selected = taskList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a task to mark as completed.");
            return;
        }
        selected.markCompleted();
        updateTaskList();
    }

    private void removeTask() {
        Task selected = taskList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a task to remove.");
            return;
        }
        tasks.remove(selected);
        updateTaskList();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}