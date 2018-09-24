/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criamenu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class CriaMenu {

    private final static String TITLED_PANE1 = "<TitledPane prefWidth=\"300.0\" expanded=\"false\" stylesheets=\"@../styles/insidePane.css\" text=\"";
    private final static String TITLED_PANE12 = "\" fx:id=\"tp";
    private final static String TITLED_PANE2 = "\">\n"
            + "                     <content>\n"
            + "                        <VBox>\n"
            + "                           <padding>\n"
            + "                              <Insets left=\"25.0\" />\n"
            + "                           </padding>\n"
            + "                           <children> \n";
    private final static String FIM = "</children> \n"
            + "                        </VBox> \n"
            + "                     </content> \n"
            + "                 </TitledPane> \n";
    private final static String BUTTON0 = "<JFXButton fx:id=\"btn";
    private final static String BUTTON01 = "\" onAction=\"#onActionBtn";
    private final static String BUTTON1 = "\" alignment=\"TOP_LEFT\" maxWidth=\"1.7976931348623157E308\" mnemonicParsing=\"false\" style=\"-fx-background-color: #cdcdcd;\" text=\"";
    private final static String BUTTON2 = "\" /> \n";

    private final static String DECLARE_TP1 = "    @FXML\n    private TitledPane tp";
    private final static String DECLARE_TP2 = ";\n";

    private final static String FXML_BUTTON2
            = "    @FXML\n"
            + "    private void onActionBtn";

    private final static String FXML_BUTTON3
            = "(ActionEvent event) throws IOException{\n"
            + "        Process p =Runtime.getRuntime().exec(\"cmd /c \\\"cd /d ";

    //dar um path de um .EXE
    private final static String FXML_BUTTON4
            = "\\\"\");\n"
            + "    }\n";

    private final static String FXML_TP1 = "tp";
    private final static String FXML_TP2 = ".expandedProperty().addListener((observable, wasExpanded, newValue) -> {\n"
            + "            fecha(wasExpanded, tp";
    private final static String FXML_TP3 = ");"
            + "});\n";

    private static int countButton = 1;
    private static int countTP = 1;

    private static OutputStreamWriter fxmlWriter;
    private static OutputStreamWriter declarationWriter;
    private static OutputStreamWriter listenerWriter;
    private static BufferedReader reader;
    private static boolean teste;
    private static boolean inside2;

    //C:\Users\Usuario\Desktop\SCI\config_menu\
    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        inside2 = false;

        Scanner in = new Scanner(System.in);

        reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\Usuario\\Desktop\\SCI\\config_menu\\" + in.nextLine()), "UTF8"));

        fxmlWriter = new OutputStreamWriter(new FileOutputStream("C:/Users/Usuario/Desktop/SCI/panel.fxml"), "UTF8");
        declarationWriter = new OutputStreamWriter(new FileOutputStream("C:/Users/Usuario/Desktop/SCI/declaration.java"));
        listenerWriter = new OutputStreamWriter(new FileOutputStream("C:/Users/Usuario/Desktop/SCI/listeners.java"));

        countButton = Integer.parseInt(reader.readLine());
        countTP = Integer.parseInt(reader.readLine()) + 1;

        String first = formata(reader.readLine());
        fxmlWriter.write(TITLED_PANE1 + first + TITLED_PANE12 + String.valueOf(countTP) + TITLED_PANE2);
        printDeclareTP();
        printListener();

        int n = in.nextInt();

        for (int i = 0; i < n; i++) {
            String s = formata(reader.readLine());

            System.out.println(s);

            teste = true;

            imprime(s);
        }

        declarationWriter.flush();
        declarationWriter.close();

        reader.close();
        fxmlWriter.write(FIM);
        fxmlWriter.flush();
        fxmlWriter.close();
    }

    private static void imprime(String s) throws IOException {
        if (s.charAt(0) == '1' && teste) {
            teste = false;
            if (inside2) {
                printFxml(FIM);
            }
            inside2 = false;

            if (s.charAt(1) == '1') {

                countTP++;
                printListener();
                printDeclareTP();
                printFxml(TITLED_PANE1 + formata(s.substring(3)) + TITLED_PANE12 + String.valueOf(countTP) + TITLED_PANE2);

                s = formata(reader.readLine());
                if (s.charAt(0) == 's') {

                    s = formata(reader.readLine());
                }
                imprime(s);

                printFxml(FIM);

            } else {

                if (s.charAt(1) == '0') {
                    countButton++;

                    printFxml(BUTTON0 + String.valueOf(countButton) + BUTTON01 + String.valueOf(countButton) + BUTTON1 + s.substring(3) + BUTTON2);

                    printHandle(reader.readLine());
                }
            }

            fxmlWriter.flush();
        } else {

            if (s.charAt(0) == '2') {
                if (s.charAt(1) == '0') {

                    if (inside2) {
                        printFxml(FIM);
                    }

                    inside2 = false;
                    countButton++;

                    printFxml(BUTTON0 + String.valueOf(countButton) + BUTTON01 + String.valueOf(countButton) + BUTTON1 + s.substring(3) + BUTTON2);

                    printHandle(reader.readLine());
                    fxmlWriter.flush();

                    next();

                } else {
                    if (s.charAt(1) == '1') {
                        if (inside2) {
                            printFxml(FIM);
                        }
                        inside2 = true;
                        countTP++;
                        printListener();
                        printDeclareTP();
                        printFxml(TITLED_PANE1 + formata(s.substring(3)) + TITLED_PANE12 + String.valueOf(countTP) + TITLED_PANE2);

                        imprime(formata(reader.readLine()));
                        //imprime(scan.readLine());
                    }
                }
            } else {
                if (s.charAt(0) == '3') {

                    countButton++;

                    printFxml(BUTTON0 + String.valueOf(countButton) + BUTTON01 + String.valueOf(countButton) + BUTTON1 + s.substring(3) + BUTTON2);

                    printHandle(reader.readLine());
                    fxmlWriter.flush();

                    next();
                }
            }
        }
    }

    private static void printFxml(String s) throws IOException {
        fxmlWriter.write(s);
    }

    //trocar para substring (2)
    private static void printHandle(String ss) throws IOException {
        declarationWriter.write(FXML_BUTTON2 + String.valueOf(countButton) + FXML_BUTTON3 + ss.substring(0) + FXML_BUTTON4);
        declarationWriter.flush();
    }

    private static void printListener() throws IOException {
        listenerWriter.write(FXML_TP1 + String.valueOf(countTP) + FXML_TP2 + String.valueOf(countTP) + FXML_TP3);
        listenerWriter.flush();
    }

    private static void printDeclareTP() throws IOException {
        declarationWriter.write(DECLARE_TP1 + String.valueOf(countTP) + DECLARE_TP2);
    }

    private static void next() throws IOException {
        if (reader.ready()) {
            imprime(formata(reader.readLine()));
            //imprime(scan.readLine());
        }
    }

    private static String formata(String s) {
        s = s.replaceAll("c;", "ç");
        s = s.replaceAll("a-", "ã");
        s = s.replaceAll("a'", "á");
        s = s.replaceAll("e'", "é");
        s = s.replaceAll("o-", "õ");
        s = s.replaceAll("i'", "í");
        s = s.replaceAll("o'", "ó");
        s = s.replaceAll("u'", "ú");
        s = s.replaceAll("e]", "ê");
        s = s.replaceAll("o]", "ô");
        s = s.replaceAll("a]", "â");
        return s;
    }
}
