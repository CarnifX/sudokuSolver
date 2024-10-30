import java.util.Arrays;

public class UtilMethods {

    //Metoden oppretter en 9x9 matrise med stringverdier "0"
    public static String[][] createSudokuBoard(){
        return new String[][]{
                {"0", "0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0", "0"}
        };
    }

    //Metoden (solveSudoku) som løser sudokuen.
    //Tanken var at for hvert tall som passer i ei tom rute på brettet,
    //kaller man på samme metode igjen for å sjekke neste rute på sudokubrettet.
    //Dersom man kommer over ei rute hvor ingen av mulighetene 1-9 fungerer,
    //vil man gå bakover i metodekallinga, og prøve andre tall i tidligere ruter.
    //Dette pågår helt til alle rutene er fylt ut, og metoden returnerer verdien "true".
    //Viktig å bemerke at dette bare vil gi èn løsning av brettet. Dersom flere
    //løsninger eksisterer, vil man ikke kunne se dette.

    public static boolean solveSudoku(String[][] sudoku_board) {

        //To for-loops som går gjennom alle rutene på sudokubrettet.
        for (int row = 0; row < 9; row++){
            for (int column = 0; column < 9; column++){

                //Skipper ruter som allerede er fylt ut:
                if (sudoku_board[row][column].equals("0")){

                    //Oppretter et nummer som scanner mellom 1 og 9
                    for (int number = 1; number < 10; number++){

                        //Kaller på "checkNumber" metoden som finner ut av om "number" passer inn på brettet.
                        //Dersom det passer, skrives verdien inn på brettet.
                        if (checkNumber(sudoku_board, row, column, String.valueOf(number))){
                            sudoku_board[row][column] = String.valueOf(number);

                            //Kaller på løsningmetoden nok en gang, hvor alle rutene
                            //skippes frem til for-loopene finner en "0" verdi på brettet. (aka neste rute)
                            if(solveSudoku((sudoku_board))){
                                return true;
                            }

                            //Dersom solveSudoku returnerer med verdien "false", betyr dette at koden
                            //fant ei rute hvor den ikke klarte å plassere inn en verdi mellom 1-9.
                            //For at tallet som koden plasserte i nåværende rute (på kodelinje 44) ikke skal påvirke
                            //checkNumber funksjonen da koden hopper bakover igjen, må nåværende
                            //rute settes til "0".
                            sudoku_board[row][column] = "0";
                        }
                    }
                    return false;
                }
            }
        }

        //Setter stringtabellen fra Main klassen til å være lik løsningsbrettet.
        Main.solvedSudokuBoard = sudoku_board;
        return true;
    }

    //Denne metoden sjekker om et tall fra gitt rad og kolonne passer inn på brettet "sudokuBoard".
    public static boolean checkNumber(String[][] sudokuBoard, int row, int col, String number) {

        //Kjører gjennom alle rutene for gitt rad og kolonne vi vet at "number" befinner seg atm. på brettet.
        //Dersom enten raden gir utslag eller kolonnen, eksisterer tallet allerede, og verdien "false" returneres.
        for (int i = 0; i < 9; i++) {
            if (sudokuBoard[row][i].equals(number) || sudokuBoard[i][col].equals(String.valueOf(number))) {
                return false;
            }
        }

        //Om hverken rad eller kolonne gir utslag, må siste betingelse sjekkes:
        //om tallet eksisterer i samme 3x3 boks.
        //Her finner vi ut av hvilke rute som er startruten for nåværende 3x3 boks.
        //Siden hver 3x3 boks består av 3 ruter pr dimensjon, deler vi enten rad eller kolonne på 3
        //med nedrundene operasjon, og trekker dette fra opprinnelig rad-/kolonne- nummer.
        int startRow = row - row % 3;
        int startCol = col - col % 3;

        //Går gjennom alle rutene for å sjekke etter matchene nummer.
        //Dersom det ikke eksisterer, vil metoden returne "true", ellers "false".
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (sudokuBoard[i + startRow][j + startCol].equals(number)) {
                    return false;
                }
            }
        }

        return true;
    }
}
