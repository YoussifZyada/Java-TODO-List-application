import java.util.*;

class Task {
    private final int id;
    private String title;
    private String description;
    private String time;
    private String priority;
    private String status;

    public Task(final int id, final String title, final String description, final String time, final String priority, final String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.priority = priority;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(final String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nTitle: " + title + "\nDescription: " + description + "\nTime: " + time +
                "\nPriority: " + priority + "\nStatus: " + status + "\n";
    }
}

class User {
    private final String username;
    private final String password;

    public User(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class TaskManager {
    private final List<Task> tasks = new ArrayList<>();
    private int taskIdCounter = 1;

    public void addTask(final String title, final String description, final String time, final String priority, final String status) {
        tasks.add(new Task(taskIdCounter++, title, description, time, priority, status));
        System.out.println("Task added successfully.");
    }

    public void updateTask(final int id, final String title, final String description, final String time, final String priority, final String status) {
        for (final Task task : tasks) {
            if (task.getId() == id) {
                if (title != null && !title.isEmpty()) task.setTitle(title);
                if (description != null && !description.isEmpty()) task.setDescription(description);
                if (time != null && !time.isEmpty()) task.setTime(time);
                if (priority != null && !priority.isEmpty()) task.setPriority(priority);
                if (status != null && !status.isEmpty()) task.setStatus(status);
                System.out.println("Task updated successfully.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public void deleteTask(final int id) {
        final Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            final Task task = iterator.next();
            if (task.getId() == id) {
                iterator.remove();
                System.out.println("Task deleted successfully.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public void showTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (final Task task : tasks) {
                System.out.println(task);
            }
        }
    }
}

public class ToDoListCLI {
    private static final Map<String, User> users = new HashMap<>();
    private static final TaskManager taskManager = new TaskManager();
    private static User currentUser = null;

    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nAvailable Commands: register, login, add_task, update_task, delete_task, show_tasks, logout, exit");
            System.out.print("Enter command: ");
            final String command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "register":
                    register(scanner);
                    break;
                case "login":
                    login(scanner);
                    break;
                case "add_task":
                    addTask(scanner);
                    break;
                case "update_task":
                    updateTask(scanner);
                    break;
                case "delete_task":
                    deleteTask(scanner);
                    break;
                case "show_tasks":
                    showTasks();
                    break;
                case "logout":
                    logout();
                    break;
                case "exit":
                    System.out.println("Exiting application.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid command.");
            }
        }
    }

    private static void register(final Scanner scanner) {
        System.out.print("Enter username: ");
        final String username = scanner.nextLine();
        System.out.print("Enter password: ");
        final String password = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Username already exists.");
        } else {
            users.put(username, new User(username, password));
            System.out.println("Registration successful.");
        }
    }

    private static void login(final Scanner scanner) {
        System.out.print("Enter username: ");
        final String username = scanner.nextLine();
        System.out.print("Enter password: ");
        final String password = scanner.nextLine();

        final User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Login successful.");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void addTask(final Scanner scanner) {
        if (currentUser == null) {
            System.out.println("You must be logged in to add tasks.");
            return;
        }

        System.out.print("Enter title: ");
        final String title = scanner.nextLine();
        System.out.print("Enter description: ");
        final String description = scanner.nextLine();
        System.out.print("Enter time: ");
        final String time = scanner.nextLine();
        System.out.print("Enter priority (High, Medium, Low): ");
        final String priority = scanner.nextLine();
        System.out.print("Enter status (To-Do, In Progress, Completed): ");
        final String status = scanner.nextLine();

        if (title.isEmpty() || description.isEmpty() || time.isEmpty() || priority.isEmpty() || status.isEmpty()) {
            System.out.println("All fields are required. Please try again.");
            return;
        }

        taskManager.addTask(title, description, time, priority, status);
    }

    private static void updateTask(final Scanner scanner) {
        if (currentUser == null) {
            System.out.println("You must be logged in to update tasks.");
            return;
        }

        try {
            System.out.print("Enter task ID to update: ");
            final int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter new title (leave blank to keep current): ");
            final String title = scanner.nextLine();
            System.out.print("Enter new description (leave blank to keep current): ");
            final String description = scanner.nextLine();
            System.out.print("Enter new time (leave blank to keep current): ");
            final String time = scanner.nextLine();
            System.out.print("Enter new priority (leave blank to keep current): ");
            final String priority = scanner.nextLine();
            System.out.print("Enter new status (leave blank to keep current): ");
            final String status = scanner.nextLine();

            taskManager.updateTask(id, title, description, time, priority, status);
        } catch (final NumberFormatException e) {
            System.out.println("Invalid task ID. Please enter a valid integer.");
        }
    }

    private static void deleteTask(final Scanner scanner) {
        if (currentUser == null) {
            System.out.println("You must be logged in to delete tasks.");
            return;
        }

        try {
            System.out.print("Enter task ID to delete: ");
            final int id = Integer.parseInt(scanner.nextLine());
            taskManager.deleteTask(id);
        } catch (final NumberFormatException e) {
            System.out.println("Invalid task ID. Please enter a valid integer.");
        }
    }

    private static void showTasks() {
        if (currentUser == null) {
            System.out.println("You must be logged in to view tasks.");
            return;
        }

        taskManager.showTasks();
    }

    private static void logout() {
        if (currentUser == null) {
            System.out.println("You are not logged in.");
        } else {
            currentUser = null;
            System.out.println("Logged out successfully.");
        }
    }
}
