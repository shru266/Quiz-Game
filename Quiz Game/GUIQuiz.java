import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class GUIQuiz extends JFrame {
    private String[] questions = {
            "1. What is the capital of France?",
            "2. Which planet is known as the Red Planet?",
            "3. What is the largest mammal in the world?",
            "4. What is the currency of Japan?",
            "5. Who painted the Mona Lisa?",
            "6. What is the largest desert in the world?"
    };

    private String[][] options = {
            {"A. Rome", "B. Paris", "C. Madrid", "D. Berlin"},
            {"A. Jupiter", "B. Mars", "C. Venus", "D. Saturn"},
            {"A. Elephant", "B. Blue Whale", "C. Giraffe", "D. Gorilla"},
            {"A. Won", "B. Yen", "C. Ringgit", "D. Peso"},
            {"A. Leonardo da Vinci", "B. Michelangelo", "C. Vincent van Gogh", "D. Pablo Picasso"},
            {"A. Sahara Desert", "B. Arabian Desert", "C. Gobi Desert", "D. Antarctic Desert"}
    };

    private char[] correctAnswers = {'B', 'B', 'B', 'B', 'A', 'A'};

    private char[] userAnswers = new char[questions.length];
    private int score = 0;

    private int currentQuestionIndex = 0;
    private JLabel scoreLabel;
    private JLabel questionLabel;
    private ButtonGroup optionGroup;

    public GUIQuiz() {
        setTitle("Multiple Choice Quiz");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set lavender background color
        getContentPane().setBackground(new Color(230, 230, 250));

        scoreLabel = new JLabel(" ");
        scoreLabel.setForeground(Color.BLACK);
        add(scoreLabel);

        questionLabel = new JLabel(questions[currentQuestionIndex]);
        questionLabel.setForeground(Color.BLACK);
        add(questionLabel);

        optionGroup = new ButtonGroup();

        for (int i = 0; i < options[currentQuestionIndex].length; i++) {
            JRadioButton optionButton = new JRadioButton(options[currentQuestionIndex][i]);
            optionButton.setForeground(Color.BLACK);
            optionButton.setActionCommand(options[currentQuestionIndex][i].substring(0, 1));
            optionGroup.add(optionButton);
            add(optionButton);
        }

        JButton nextButton = new JButton("Next");
        nextButton.setForeground(Color.BLACK);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processAnswer();
                currentQuestionIndex++;

                if (currentQuestionIndex < questions.length) {
                    updateQuestion();
                } else {
                    showScoreboard();
                }
            }
        });
        add(nextButton);

        updateQuestion();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setVisible(true);
    }

    private void updateQuestion() {
        questionLabel.setText(questions[currentQuestionIndex]);

        optionGroup.clearSelection();

        Enumeration<AbstractButton> buttons = optionGroup.getElements();
        for (int i = 0; buttons.hasMoreElements(); i++) {
            AbstractButton button = buttons.nextElement();
            button.setText(options[currentQuestionIndex][i]);
        }
    }

    private void processAnswer() {
        String selectedOption = getSelectedOption();
        if (selectedOption != null) {
            userAnswers[currentQuestionIndex] = selectedOption.charAt(0);
            if (userAnswers[currentQuestionIndex] == correctAnswers[currentQuestionIndex]) {
                score++;
            }
        }
    }

    private String getSelectedOption() {
        for (Enumeration<AbstractButton> buttons = optionGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    private void showScoreboard() {
        StringBuilder scoreboard = new StringBuilder();

        for (int i = 0; i < questions.length; i++) {
            scoreboard.append("Q").append(i + 1).append(": ");
            scoreboard.append(options[i][correctAnswers[i] - 'A'].substring(3)).append(" - ");
            scoreboard.append(userAnswers[i] == 0 ? "Not answered" : options[i][userAnswers[i] - 'A'].substring(3));
            scoreboard.append("\n");
        }

        scoreboard.append("\nYour Score: ")
                .append(score)
                .append("/")
                .append(questions.length);

        scoreLabel.setText("Score: " + score + "/" + questions.length);

        JOptionPane.showMessageDialog(this, scoreboard.toString(), "Quiz Results", JOptionPane.INFORMATION_MESSAGE);

        JOptionPane.showMessageDialog(this, "Thanks for playing!", "Quiz Finished", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUIQuiz();
            }
        });
    }
}







