import javafx.scene.input.DataFormat;
import javafx.scene.text.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Tim on 17.03.2017.
 */
public class MySimpleNotesFrame extends JFrame {
    final static int WIDTH = 1000;
    final static int HIGHT = 800;
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HIGHT = 20;

    File[] choosenFile = new File[1];

    MySimpleNotesFrame(String name){

        super(name);
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        JPanel westPanel = new JPanel();
        westPanel.setBackground(Color.ORANGE);
        JPanel canvasPanel = new JPanel();
        canvasPanel.setBackground(Color.ORANGE);
        //canvasPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel northPanel = new JPanel();
        northPanel.setBackground(Color.ORANGE);
        pane.add(BorderLayout.NORTH, northPanel);

        canvasPanel.setLayout(new BoxLayout(canvasPanel, BoxLayout.PAGE_AXIS));

//        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
//        buttonPanel.setPreferredSize(new Dimension(50, HEIGHT-50));
//        canvasPanel.setPreferredSize(new Dimension(WIDTH-50, HIGHT-50));
//        menuPanel.setPreferredSize(new Dimension(WIDTH, 50));

        class MyButton extends JButton{

            MyButton(String name, int width, int hight){
            super(name);
                // nothing works to set a size of button , only layout managers work correctly
                //Any manager has to be used instead of direct definition of the size
                setPreferredSize(new Dimension(width, hight));
//                setSize(width, hight);
//                setMinimumSize(new Dimension(width, hight));
//                setContentAreaFilled(true);
//                setBorder(BorderFactory.createEmptyBorder(hight, width, 0, 0));
                setFont(new Font(null, Font.ITALIC, 15));
                setBackground(Color.yellow);
                setHorizontalTextPosition(AbstractButton.CENTER); // = 0

            }
        }

        ArrayList<MyButton> buttons = new ArrayList<>();

        JButton addNewPart = new MyButton("Add new part", BUTTON_WIDTH, BUTTON_HIGHT);
        JButton removePart = new MyButton("Remove part", BUTTON_WIDTH, BUTTON_HIGHT);
        JButton exitButton = new MyButton("Exit", BUTTON_WIDTH, BUTTON_HIGHT);
        JButton searchButton = new MyButton("Search", BUTTON_WIDTH, BUTTON_HIGHT);
        JButton addNote = new MyButton("Add", BUTTON_WIDTH+10, BUTTON_HIGHT);
        JButton clearText = new MyButton("Clear", BUTTON_WIDTH+10, BUTTON_HIGHT);

//        pane.add
        JPanel southPanel = new JPanel();
        JPanel southButtons = new JPanel();
        pane.add(southPanel, BorderLayout.SOUTH);
        southPanel.setLayout(new GridBagLayout());
        //it is not still so clear wat this part f code is doing
        GridBagConstraints gbcSouth = new GridBagConstraints();
        //southPanel.add(southButtons, gbcSouth);

        gbcSouth.gridx = 0;
        gbcSouth.gridy = 0;
        gbcSouth.gridheight = 1;
        gbcSouth.weightx = 1;
        gbcSouth.weighty = 1;
        gbcSouth.gridwidth = 1;
        gbcSouth.ipadx = 20;
        //gbcSouth.fill = GridBagConstraints.HORIZONTAL;
        gbcSouth.anchor = GridBagConstraints.SOUTHEAST;
        southPanel.add(addNote, gbcSouth);
        //southPanel.add(addNote);


        gbcSouth.gridx = 1;
        gbcSouth.gridy = 0;
        gbcSouth.gridheight = 1;
        gbcSouth.weightx = 0;
        gbcSouth.weighty = 1;
        gbcSouth.gridwidth = 1;
        gbcSouth.ipadx = 20;
        gbcSouth.anchor = GridBagConstraints.SOUTHEAST;
        //gbcSouth.fill = GridBagConstraints.HORIZONTAL;
        southPanel.add(clearText, gbcSouth);
        //southPanel.add(clearText);
        // gbcSouth.weighty = 500; // still unclear
        southPanel.setBackground(Color.ORANGE);


        gbcSouth.weightx = 1;
        gbcSouth.weighty = 1;
        gbcSouth.gridheight = 1;
        gbcSouth.gridwidth = 1;
        gbcSouth.gridy = 1;
        gbcSouth.gridx = 0;
        gbcSouth.ipadx = 20;
        gbcSouth.anchor = GridBagConstraints.NORTHWEST;

        //showing the current time. It is not working now. I have to check it
        JLabel timeLabel = new JLabel();
        //timeLabel.setText("djwhskdhsjk");
        //timeLabel.setText(timeThread.getTime());
        new CurrentDateImager().getTime(timeLabel);
        southPanel.add(timeLabel, gbcSouth);


        //southPanel.add(southButtons);
        //southButtons.add(addNote);
        //southButtons.add(clearText);
        //southPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        JTextPane textPane = new JTextPane();

        //attempt to make a own border
                class Borders extends JFrame {
                    JPanel showBorder(Border b, String title) {
                        JPanel jp = new JPanel();
                        jp.setLayout(new BorderLayout());
                        jp.add(new Label(title, Label.CENTER));
                        jp.setBorder(b);
                        return jp;
                    }
                }

//TODO several ways to make a border
        //canvasPanel.add(new Borders().showBorder(new TitledBorder("Title"), ""));
        //canvasPanel.add(new Borders().showBorder(new EtchedBorder(), "Title"));
        //canvasPanel.add(new Borders().showBorder(new LineBorder(Color.RED), "Title"));
        //canvasPanel.add(new Borders().showBorder(new MatteBorder(5,5,30,30,Color.BLACK), "Title"));
        //canvasPanel.add(new Borders().showBorder(new BevelBorder(BevelBorder.RAISED), "Title"));
        //canvasPanel.add(new Borders().showBorder(new SoftBevelBorder(BevelBorder.LOWERED), "Title"));
        //canvasPanel.add(new Borders().showBorder(new CompoundBorder(new EtchedBorder(), new LineBorder(Color.YELLOW)), "Title"));

        textPane.setBorder(new TitledBorder("Note's text"));
//TODO set tabbed pane
//        String[] flavors = {"chocolate","vanilla", "mocachino", "etc"};
//        JTabbedPane tp = new JTabbedPane();
//        int i=0;
//        for(String tab: flavors) {
//            tp.addTab(tab, new Button("Tabbed pane " + i++));
//        }
//            tp.addChangeListener(new ChangeListener() {
//                @Override
//                public void stateChanged(ChangeEvent e) {
//                    if (tp.getSelectedIndex() == 0) {
//                        JOptionPane.showConfirmDialog(null, "It is a title", "This is a text for message",
//                                JOptionPane.YES_NO_OPTION);
//
//                    } else if (tp.getSelectedIndex() == 1) {
//                        String[] options = {"Option 1", "Option 2",};
//                        //it is used the same as confirmation dialog
//                        JOptionPane.showOptionDialog(null, "Privet", "Kukushka",
//                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
//                                null, options, options[0]);
//
//                    } else if (tp.getSelectedIndex() == 2) {
//                        String str = JOptionPane.showInputDialog("How many times have you heard the truth");
//                        textPane.setText(str);
//                    }
//                }
//            });
//
//        southButtons.add(tp);

        JComboBox comboBox = new JComboBox();
        comboBox.setBackground(Color.YELLOW);
        comboBox.setBorder(BorderFactory.createTitledBorder("Table of content"));

        String[] textFile = {""};
        TreeMap<String, String> description = new TreeMap<>();
        ArrayList<String> comboBoxStrings = new ArrayList<>();
        //File subjectsFile = new File("F:\\Java\\Projects\\MySimpleNotes");
        //File subjectsFile = new File("..\\MySimpleNotes\\subjects");//instead of absolute path we can use this line
        File subjectsFile = choosenFile[0];
        if(subjectsFile != null) {
            description.putAll(MyWorkWithFiles.getAllParts(subjectsFile.getAbsolutePath()));

            for (String str : description.values())
                comboBoxStrings.add(str);

            for (String str : description.values()) {
                comboBox.addItem(str);


                comboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int i = 0;
                        for (Map.Entry entry : description.entrySet()) {
                            if (i == comboBox.getSelectedIndex())
                                textFile[0] = (String) entry.getKey();
                            i++;
                        }
                        repaint();
                    }

                });

            }
            comboBox.setSelectedIndex(0);
        }


// here we add buttons to the pane with buttons
        buttons.add((MyButton)addNewPart);
        buttons.add((MyButton)removePart);
        buttons.add((MyButton)searchButton);
        buttons.add((MyButton)exitButton);

        // subjectTextField;
        JTextField subjectTextField = new JTextField("Enter the subject");
         textPane.setPreferredSize(new Dimension(500, 800));
       // textArea.setLineWrap(true); //That is great for textArea!!!! Replace the text if the end of a line is reached! Great!
        textPane.setAutoscrolls(true);
        //textArea.setPreferredSize(new Dimension(500, 700));
        JScrollPane scrollPane = new JScrollPane(textPane);
//        scrollPane.setAutoscrolls(true);
//        scrollPane.setMaximumSize(new Dimension(textArea.getColumns(), textArea.getLineCount()));
//        scrollPane.createVerticalScrollBar();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(buttons.size()+1,1));
        //buttonPanel.add(comboBox);
        canvasPanel.add(comboBox);
        for(MyButton button : buttons){
            buttonPanel.add(button);
        }

        JTextField searchingText = new JTextField(15);
        searchingText.setBorder(BorderFactory.createTitledBorder("Search..."));
        buttonPanel.add(searchingText);
        buttonPanel.setBorder(new TitledBorder("Actions"));
        buttonPanel.setBackground(Color.YELLOW);

        //functionality for adding a note
        addNewPart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            MyWorkWithFiles.readFromFile(textPane, subjectTextField, comboBox, comboBoxStrings);
            }
        });

        //clear the text Pane from the text
        clearText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPane.setText("");
            }
        });

        //functionality for removing a note
        removePart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // remove the text from the Notes file
                String str = comboBoxStrings.get(comboBox.getSelectedIndex());
                int answer = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to remove " +str,
                        "Deleting the part...",
                        JOptionPane.YES_NO_OPTION);
                if(answer == 0)
                    MyWorkWithFiles.deletePart(comboBox, comboBoxStrings);
            }
        });

        //functionality for searching a note
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyWorkWithFiles.loadingToTextPane(textPane, textFile[0]);
            }
        });

        addNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyWorkWithFiles.readFromFile(textPane, subjectTextField, comboBox, comboBoxStrings);
            }
        });

        //functionality for exit th program
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int answer = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you would like to close the program now?",
                        "Closing the program",
                        JOptionPane.YES_NO_OPTION
                );
                if(answer == 0)
                System.exit(0);
            }
        });

        //it is not still so clear wat this part f code is doing
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;


        westPanel.add(buttonPanel, gbc);

        class MyLabel extends Label{
            MyLabel(String text, int fontSize, Color clr){
                setText(text);
                setFont(new Font(getName(),Font.BOLD, fontSize));
                setAlignment(CENTER);
                setForeground(clr);
            }
        }

        subjectTextField.setFont(new Font(null, Font.ITALIC, 16));
        canvasPanel.add(new MyLabel("Subject", 20, Color.RED));
        canvasPanel.add(subjectTextField);
        canvasPanel.add(new MyLabel("Text", 18, Color.BLUE));
        canvasPanel.add(scrollPane);

        add(pane);

        pane.add(canvasPanel, BorderLayout.CENTER);
        pane.add(westPanel, BorderLayout.WEST);
        pane.add(northPanel, BorderLayout.NORTH);

//        //scrollPane.setSize();
//        canvasPanel.add(scrollPane);
//
//        JMenuBar menus = new JMenuBar();
//
//        menuPanel.add(menus);
//        JTabbedPane tabs = new JTabbedPane();
//        String[] tab = {"New file","Settings","About"};
//        for(String str: tab) {
//            tabs.add(str, new JButton(str){
//                @Override
//                public void addActionListener(ActionListener l) {
//                    super.addActionListener(l);
//                }
//            });
//        }
//
//        menuPanel.add(tabs);
//        pack();
// TODO here I start to construct a navigation menu
        northPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcNorth = new GridBagConstraints();
        northPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        gbcSouth.anchor = GridBagConstraints.WEST;
        gbcSouth.weightx = 1;


        JMenuBar mb = new JMenuBar();
        mb.setBackground(Color.ORANGE);

        JMenu file = new JMenu("File");

        JMenuItem[] fileContents = {
                new JMenuItem("New"),
                new JMenuItem("Open"),
                new JMenuItem("Settings"),
                new JMenuItem("Exit")
        };
        for(JMenuItem item: fileContents) {
            file.add(item);
            item.setBackground(Color.ORANGE);
        }


        fileContents[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int val =  chooser.showOpenDialog(MySimpleNotesFrame.this);
                if(val == JFileChooser.APPROVE_OPTION){
                    choosenFile[0] = chooser.getSelectedFile();
                    //description.putAll(MyWorkWithFiles.getAllParts(choosenFile[0].getAbsolutePath()));
                    e.
                }


            }
        });

        JMenu help = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem("Help");
        help.add(helpItem);

        JMenu about = new JMenu("About");
        JMenuItem aboutItem = new JMenuItem("About");
        about.add(aboutItem);

        mb.add(file);
        mb.add(help);
        mb.add(about);

        northPanel.add(mb, gbcSouth);


        setSize(WIDTH, HIGHT);
        setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
           public void run(){
               new MySimpleNotesFrame("My notes app");
           }
        });

    }
}
