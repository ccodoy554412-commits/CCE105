//tests commit

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class group_project extends JFrame {

    private JTextField inputN;
    private JTextField inputA;
    private JTextField inputB;
    private JTextArea outputArea;
    private JButton calculateBtn;

    public group_project() {
        setTitle("Manasa and Stones - Stack & Array Implementation");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inputPanel.add(new JLabel("Number of Stones (n):"));
        inputN = new JTextField("4");
        inputPanel.add(inputN);

        inputPanel.add(new JLabel("Difference A (a):"));
        inputA = new JTextField("10");
        inputPanel.add(inputA);

        inputPanel.add(new JLabel("Difference B (b):"));
        inputB = new JTextField("100");
        inputPanel.add(inputB);

        add(inputPanel, BorderLayout.NORTH);

        // Center Panel (Button & Output)
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        calculateBtn = new JButton("Calculate (Using Stack & Array)");
        calculateBtn.setFont(new Font("Arial", Font.BOLD, 14));
        centerPanel.add(calculateBtn, BorderLayout.NORTH);
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        centerPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Action Listener
        calculateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveProblemWithStackAndArray();
            }
        });
    }

    private void solveProblemWithStackAndArray() {
        try {
            int n = Integer.parseInt(inputN.getText().trim());
            int a = Integer.parseInt(inputA.getText().trim());
            int b = Integer.parseInt(inputB.getText().trim());

            if (n <= 0) {
                outputArea.setText("Error: Number of stones must be > 0");
                return;
            }

            int gaps = n - 1;
            
            // 1. ARRAY ALLOCATION
            int[] results = new int[n];
            
            // 2. STACK ADT IMPLEMENTATION
            // We use a Stack to store the current steps of a specific path.
            Stack<Integer> stepStack = new Stack<>();
            
            // Iterate through all possible combinations of A and B
            for (int i = 0; i <= gaps; i++) {
                stepStack.clear(); // Clear stack for the new path
                int currentTotal = 0;
                
                // Simulate taking 'i' steps of A
                for (int j = 0; j < i; j++) {
                    stepStack.push(a);
                }
                // Simulate taking the remaining steps of B
                for (int k = 0; k < (gaps - i); k++) {
                    stepStack.push(b);
                }
                
                // Pop all steps from the stack to calculate the final total for this path
                while (!stepStack.isEmpty()) {
                    currentTotal += stepStack.pop();
                }
                
                // Store the final total in the Array
                results[i] = currentTotal;
            }

            // 3. SORTING TECHNIQUE: Bubble Sort
            for (int i = 0; i < results.length - 1; i++) {
                for (int j = 0; j < results.length - i - 1; j++) {
                    if (results[j] > results[j + 1]) {
                        int temp = results[j];
                        results[j] = results[j + 1];
                        results[j + 1] = temp;
                    }
                }
            }

            // 4. OUTPUT & DUPLICATE REMOVAL
            StringBuilder sb = new StringBuilder();
            sb.append("Possible values (Sorted):\n\n");
            
            int previousValue = -1; 
            
            for (int i = 0; i < results.length; i++) {
                if (i == 0 || results[i] != previousValue) {
                    sb.append(results[i]).append(" ");
                    previousValue = results[i];
                }
            }

            outputArea.setText(sb.toString());

        } catch (NumberFormatException ex) {
            outputArea.setText("Error: Please enter valid integers only.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new group_project().setVisible(true);
            }
        });
    }
}