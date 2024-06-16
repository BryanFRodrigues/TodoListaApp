import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TodoListApp extends JFrame {
    private JTextField taskInput;
    private DefaultListModel<String> taskModel;
    private JList<String> taskList;
    private JButton addButton, removeButton;
    private ArrayList<Boolean> taskStatus;

    public TodoListApp() {
        setTitle("Lista de Tarefas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializando componentes
        taskInput = new JTextField(20);
        addButton = new JButton("Adicionar Tarefa");
        removeButton = new JButton("Remover Tarefa");
        taskModel = new DefaultListModel<>();
        taskList = new JList<>(taskModel);
        taskStatus = new ArrayList<>();

        // Painel para adicionar tarefas
        JPanel panel = new JPanel();
        panel.add(taskInput);
        panel.add(addButton);

        // Adicionando componentes ao frame
        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);
        add(removeButton, BorderLayout.SOUTH);

        // Ação do botão Adicionar Tarefa
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = taskInput.getText();
                if (!task.isEmpty()) {
                    taskModel.addElement(task);
                    taskStatus.add(false);
                    taskInput.setText("");
                }
            }
        });

        // Ação do botão Remover Tarefa
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskModel.remove(selectedIndex);
                    taskStatus.remove(selectedIndex);
                }
            }
        });

        // Adicionando um mouse listener para marcar as tarefas como concluídas
        taskList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int index = taskList.locationToIndex(evt.getPoint());
                if (index >= 0 && index < taskModel.size()) {
                    taskStatus.set(index, !taskStatus.get(index));
                    String task = taskModel.getElementAt(index);
                    if (taskStatus.get(index)) {
                        task = "<html><strike>" + task + "</strike></html>";
                    } else {
                        task = task.replaceAll("<html><strike>", "").replaceAll("</strike></html>", "");
                    }
                    taskModel.set(index, task);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TodoListApp().setVisible(true);
            }
        });
    }
}
