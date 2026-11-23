import java.awt.*;
import java.util.HashSet;
import java.util.PriorityQueue; // This is the Min-Heap
import java.util.Set;
import javax.swing.*;

// --- ADT: Abstract Data Type Definition (From Week 1 SIM-SDL) ---
// This interface defines WHAT operations we do, not HOW.
interface StoneCalculationADT {
    void calculatePaths(int n, int a, int b);
    String getSortedResults();
}

// --- Implementation Class ---
public class group_project extends JFrame implements StoneCalculationADT {

    // --- UI Components ---
    private JTextField stonesInput;
    private JTextField differenceAInput;
    private JTextField differenceBInput;
    private JTextArea outputArea;
    private JButton calculateButton;
    private JButton encryptButton;

    // Variable to store the raw numbers before encryption
    private String rawResultText = "";

    public group_project() {
        // Setup Window
        setTitle("Manasa and Stones - IT Project (ADT & Heaps)");
        setSize(600, 650);
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
        calculateButton = new JButton("Calculate (Min-Heap)");
        encryptButton = new JButton("Encrypt Result");
        encryptButton.setEnabled(false); 
        
        buttonPanel.add(calculateButton);
        buttonPanel.add(encryptButton);
        centerPanel.add(buttonPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        centerPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // 3. Button Actions (Lambdas)
        calculateButton.addActionListener(e -> {
            try {
                int n = Integer.parseInt(stonesInput.getText().trim());
                int a = Integer.parseInt(differenceAInput.getText().trim());
                int b = Integer.parseInt(differenceBInput.getText().trim());
                calculatePaths(n, a, b);
            } catch (NumberFormatException ex) {
                outputArea.setText("Error: Please enter valid integers.");
            }
        });

        encryptButton.addActionListener(e -> encryptText());
    }

    // --- Implementation of ADT Method ---
    @Override
    public void calculatePaths(int totalStones, int diffA, int diffB) {
        if (totalStones <= 0) {
            outputArea.setText("Error: Number of stones must be greater than 0.");
            return;
        }

        // Data Structure: Min-Heap (Priority Queue)
        // From "BINARY-MAX HEAP - MIN HEAP" document.
        // The Min-Heap automatically sorts elements as they are added.
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        // Data Structure: Set to avoid duplicates (Part of ADT specification)
        Set<Integer> uniqueResults = new HashSet<>();

        int gaps = totalStones - 1;

        // Logic to simulate paths (using a Queue/BFS approach conceptually)
        // We are effectively generating all combinations of A and B.
        for (int i = 0; i <= gaps; i++) {
            int currentTotal = (i * diffA) + ((gaps - i) * diffB);
            
            // Add to heap if unique
            if (uniqueResults.add(currentTotal)) {
                minHeap.add(currentTotal);
            }
        }

        // Build Output string
        StringBuilder finalOutput = new StringBuilder();
        finalOutput.append("--- RESULTS (Sorted via Min-Heap) ---\n\n");
        
        // Poll removes the smallest element (Min-Heap behavior)
        while (!minHeap.isEmpty()) {
            finalOutput.append(minHeap.poll()).append(" ");
        }

        // Save the result
        rawResultText = finalOutput.toString();
        outputArea.setText(rawResultText);
        encryptButton.setEnabled(true); 
    }

    @Override
    public String getSortedResults() {
        return rawResultText;
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
                encryptedText.append(letter);
            }
        }

        outputArea.setText(encryptedText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new group_project().setVisible(true));
    }
}