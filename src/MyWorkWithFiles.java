import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tim on 04.04.2017.
 */
public class MyWorkWithFiles {


//TODO adding text to the file
    static void readFromFile(JTextPane textPane, JTextField subjectTextField, JComboBox combo, ArrayList<String> list){
        String fileName = list.get(combo.getSelectedIndex());

        File textFile = new File(fileName);
        //BufferedWriter in Combinaten
        // BufferedWriter bw = null;
        FileWriter fw = null;
        // adding the text to the Notes file
        if (textPane.getText() == null || textPane.getText().equals(null) || textPane.getText().equals("") || textPane.getText() == "") {
            JOptionPane.showMessageDialog(null, "The textPane is Empty! Check your note.", "Hey", JOptionPane.ERROR_MESSAGE);
        } else {
            if (subjectTextField.getText() == null || subjectTextField.getText().equals(null) || subjectTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Subject's field is empty. Write the subject", "Check it",JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    //  bw = new BufferedWriter(new PrintWriter(textFile)); // it works
                    fw = new FileWriter(textFile, true);
                    String subject = subjectTextField.getText();
                    String note = textPane.getText();
//                    bw in use
//                    bw.write(str);
//                    bw.flush();
//                    bw.close();
                    fw.write("Subject: " + subject + "\n");
                    fw.append("Note: \n");
                    fw.write(note + "\n");

                    DateFormat dataFormat = new SimpleDateFormat("dd.MM.yy HH.mm.ss");
                    fw.write(dataFormat.format(new Date()) + "\n");

                    Calendar calendar = Calendar.getInstance();
                    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                    int dayNumber = calendar.get(Calendar.DAY_OF_WEEK);
                    fw.append(days[dayNumber - 1] + "\n");
                    fw.append("end\n");
                    fw.append("---------------------------------------------------------\n");
                    fw.flush();
                    fw.close();

                } catch (FileNotFoundException e1) {
                    try {
                        textFile.createNewFile();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                } catch (IOException e1) {

                } finally {
                    // close the file
                    try {
                        fw.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    static void loadingToTextPane(JTextPane textPane, String fileName){
        // search the text from the Notes file
        // till it is showing information from file
        FileReader fr = null;
        fileName = fileName + ".txt";
        File file = new File(fileName);

        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            //StringBuilder stringBuilder = new StringBuilder(); // it is one of ways
            ArrayList<String> strings = new ArrayList<String>();

            while(br.ready()) { //read the whole file
                strings.add(br.readLine());
            }

            for(String string: strings){
                // is there a way to format text for output right here? I need to look at
                // Style component to learn how it works and to create a doc with a certain
                //style inside...
//                        if(string.substring(1).equals("Subject")){
//                            string.setFont(new Font(string, Font.BOLD, 16));
//                        }
                textPane.setText(textPane.getText()+string+"\n");
                //textPane.setText("\n");

            }

            br.close();
            fr.close();

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    static void deletePart(JComboBox combo, ArrayList<String> combStr){
        String str = combStr.get(combo.getSelectedIndex());
        str = "..\\MySimpleNotes\\subjects\\" + str + ".txt";
        File removingFile = new File(str);

        try {
            Files.delete(removingFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static TreeMap<String, String> PartsArray = new TreeMap<>();

    static Map<String, String> getAllParts(String findFile){
        //final String regex = "?\\.\\[(a-z||A-Z)&&[txt]]";

        File file = new File(findFile);
        File[] files = file.listFiles();

        for (File fileName : files) {
            if (fileName.isDirectory()) {
                getAllParts(findFile+"\\"+fileName.getName());
            } else {
                if(fileName.getName().endsWith(".txt")) {
                    int lengthAbs = fileName.getAbsolutePath().length();
                    int lengthName = fileName.getName().length();
                    PartsArray.put(
                            fileName.getAbsolutePath().substring(0, lengthAbs-4),
                            fileName.getName().substring(0, lengthName-4)
                    );
                }
            }
        }
        return PartsArray;
    }


}
