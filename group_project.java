import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class group_project extends JFrame {

    // --- UI Components ---
    static JTextField stonesInput;
    static JTextField differenceAInput;
    static JTextField differenceBInput;
    static JTextArea outputArea;
    static JButton calculateButton;
    static JButton encryptButton;

    // Variable to store the raw numbers before encryption
    static String rawResultText = "";

    public group_project() {
        // Setup Window
        setTitle("Manasa and Stones - IT Project");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        // 1. Input Panel Setup
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inputPanel.add(new JLabel("Number of Stones (n):"));
        stonesInput = new JTextField();
        inputPanel.add(stonesInput);

        inputPanel.add(new JLabel("Difference A (a):"));
        differenceAInput = new JTextField();
        inputPanel.add(differenceAInput);

        inputPanel.add(new JLabel("Difference B (b):"));
        differenceBInput = new JTextField();
        inputPanel.add(differenceBInput);

        add(inputPanel, BorderLayout.NORTH);

        // 2. Center Panel Setup (Buttons & Output)
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        calculateButton = new JButton("Calculate & Sort");
        encryptButton = new JButton("Encrypt Result");
        encryptButton.setEnabled(false); // Disabled until calculation is done
        
        buttonPanel.add(calculateButton);
        buttonPanel.add(encryptButton);
        centerPanel.add(buttonPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        centerPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // 3. Button Actions
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatePaths();
            }
        });

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encryptText();
            }
        });
    }

    private void calculatePaths() {
        try {
            // Get values from text fields
            int totalStones = Integer.parseInt(stonesInput.getText().trim());
            int diffA = Integer.parseInt(differenceAInput.getText().trim());
            int diffB = Integer.parseInt(differenceBInput.getText().trim());

            if (totalStones <= 0) {
                outputArea.setText("Error: Number of stones must be greater than 0.");
                return;
            }

            int gaps = totalStones - 1;
            
            // Data Structure 1: Array to hold results
            int[] resultsArray = new int[totalStones];
            
            // Data Structure 2: Stack for calculating paths
            Stack<Integer> pathStack = new Stack<>();
            
            for (int i = 0; i <= gaps; i++) {
                pathStack.clear(); 
                int currentTotal = 0;
                
                // Push difference A
                for (int j = 0; j < i; j++) {
                    pathStack.push(diffA);
                }
                // Push difference B
                for (int k = 0; k < (gaps - i); k++) {
                    pathStack.push(diffB);
                }
                
                // Pop everything from the stack to add it up
                while (!pathStack.isEmpty()) {
                    currentTotal += pathStack.pop();
                }
                
                resultsArray[i] = currentTotal;
            }

            // Sorting Algorithm: Bubble Sort
            for (int i = 0; i < resultsArray.length - 1; i++) {
                for (int j = 0; j < resultsArray.length - i - 1; j++) {
                    if (resultsArray[j] > resultsArray[j + 1]) {
                        // Swap
                        int temp = resultsArray[j];
                        resultsArray[j] = resultsArray[j + 1];
                        resultsArray[j + 1] = temp;
                    }
                }
            }

            StringBuilder finalOutput = new StringBuilder();
            finalOutput.append("Possible values (Sorted):\n\n");
            
            int previousValue = -1; 
            
            for (int i = 0; i < resultsArray.length; i++) {
                if (i == 0 || resultsArray[i] != previousValue) {
                    finalOutput.append(resultsArray[i]).append(" ");
                    previousValue = resultsArray[i];
                }
            }

            // Save the result
            rawResultText = finalOutput.toString();
            outputArea.setText(rawResultText);
            encryptButton.setEnabled(true); 

        } catch (NumberFormatException ex) {
            outputArea.setText("Error: Please enter valid integers only.");
        }
    }

    // Cryptography: Caesar Cipher
    private void encryptText() {
        if (rawResultText.isEmpty()) return;

        int shift = 3; 
        StringBuilder encryptedText = new StringBuilder();
        encryptedText.append("--- ENCRYPTED (Caesar Shift +3) ---\n\n");

        for (char letter : rawResultText.toCharArray()) {
            if (Character.isLetter(letter)) {
                char base = Character.isUpperCase(letter) ? 'A' : 'a';
                char newLetter = (char) ((letter - base + shift) % 26 + base);
                encryptedText.append(newLetter);
            } else {
                // Leave numbers and spaces alone
                encryptedText.append(letter);
            }
        }

        outputArea.setText(encryptedText.toString());
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