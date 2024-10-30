import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    static String[] emptyColumn = {"", "", "", "", "", "", "", "", ""};

    //Oppretter en stringtabell for å lagre det ferdigløste sudokubrettet.
    static String[][] solvedSudokuBoard;

    public static void main(String[] args) {

        //Oppretter et sudoku brett - 9x9 hvor alle elementene har string verdi "0".
        String[][] sudokuBoard = UtilMethods.createSudokuBoard();

        //Sikkert bedre måter å gjøre dette på, men jeg valgte å la brukeren
        //få plotte inn sudokubrettet sitt ved hjelp av en tabell i JFrame:
        JFrame frame = new JFrame("Fill out the sudoku board!");
        JPanel panel = new JPanel();


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(400, 220);

        panel.setLayout(null);
        frame.setLocationRelativeTo(null);

        DefaultTableModel tableModel = new DefaultTableModel(sudokuBoard, emptyColumn);
        JTable table = new JTable(tableModel);

        JButton retrieveButton = getJButton(tableModel);
        frame.add(table, "Center");
        frame.add(retrieveButton, "South");
        frame.setVisible(true);
    }

    //Metode som forteller hva som skal skje da man trykker "Solve! knappen"
    private static JButton getJButton(DefaultTableModel tableModel) {
        JButton retrieveButton = new JButton("Solve!");

        retrieveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowCount = tableModel.getRowCount();
                int colCount = tableModel.getColumnCount();
                String[][] inputSudokuBoard = new String[rowCount][colCount];

                //For loop som samler data som brukeren har plottet inn,
                //i en string array "inputSudokuBoard"
                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < colCount; j++) {
                        inputSudokuBoard[i][j] = (String) tableModel.getValueAt(i, j);
                    }
                }

                //Her kjøre vi metoden som skal løse brettet.
                boolean solvedBoard = UtilMethods.solveSudoku(inputSudokuBoard);

                //En liten if-setning hvor det ferdigløste sudokubrettet blir skrevet ut dersom
                //programmet klarte å løse problemet.
                if (solvedBoard) {
                    JFrame secondFrame = new JFrame("Your sudoku is solved!");

                    DefaultTableModel tableModel = new DefaultTableModel(solvedSudokuBoard, emptyColumn);
                    JTable table = new JTable(tableModel);

                    secondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    secondFrame.setSize(400,200);
                    secondFrame.setLocationRelativeTo(null);
                    secondFrame.add(table, "Center");
                    secondFrame.setVisible(true);

                }

            }
        });

        return retrieveButton;
    }
}