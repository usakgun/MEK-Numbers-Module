import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.*;

public class userInterface extends JFrame implements ActionListener {
    JFrame myFrame = new JFrame();
    GridLayout g;
    JLabel textArea;
    JLabel numberArea;
    String textValue;
    String numberValue;
    JButton editList;
    JButton generateNew;
    JButton showText;

    int listNumber;
    String listAttribute;
    int listAttributeIndex;

    boolean isVisible = false;

    String textFilePath = "dataList.txt";
    ArrayList<String> nameList = new ArrayList<String>();

    JFrame jf;
    JLabel textBox;
    JLabel numberBox;
    JTextField textBoxArea;
    JTextField numberBoxArea;
    JButton changeList;
    JButton goBackButton;

    public void createMenu() {
        attendTexts();
        g = new GridLayout(6, 1);
        myFrame.setLayout(g);
        myFrame.setSize(600, 400);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);

        textArea = new JLabel("Text : " + listAttribute);
        numberArea = new JLabel("Number : " + listAttributeIndex);
        showText = new JButton("Show Text");

        editList = new JButton("Edit List");
        generateNew = new JButton("Generate");

        showText.addActionListener(this);
        editList.addActionListener(this);
        generateNew.addActionListener(this);

        myFrame.add(textArea);
        myFrame.add(numberArea).setVisible(false);
        myFrame.add(editList);
        myFrame.add(generateNew);
        myFrame.add(showText);
    }

    public void editListPage() {
        jf = new JFrame("Edit List");
        GridLayout grid = new GridLayout(6, 1);
        jf.setLayout(grid);
        jf.setSize(600, 400);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textBox = new JLabel("Enter the input you wanna push into list");
        textBoxArea = new JTextField();
        numberBox = new JLabel("Enter the index you wanna change in list");
        numberBoxArea = new JTextField();
        changeList = new JButton("Change list input");
        goBackButton = new JButton("Back");

        changeList.addActionListener(this);
        goBackButton.addActionListener(this);

        jf.add(textBox);
        jf.add(textBoxArea);
        jf.add(numberBox);
        jf.add(numberBoxArea);
        jf.add(changeList);
        jf.add(goBackButton);
    }

    public void listOperations(int numInput, String textInput) {
        ArrayList<String> tempList = new ArrayList<>();
        numInput = numInput + 10;

        try (BufferedReader in = new BufferedReader(new FileReader(textFilePath))) {
            String str;
            while ((str = in.readLine()) != null) {
                tempList.addAll(Arrays.asList(str.split(",")));
            }
            if (numInput >= 0 && numInput < tempList.size()) {
                tempList.set(numInput, textInput);
            }
            try (BufferedWriter out = new BufferedWriter(new FileWriter(textFilePath))) {
                for (int i = 0; i < tempList.size(); i++) {
                    out.write(tempList.get(i));
                    if (i < tempList.size() - 1) {
                        out.write(",");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToList() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(textFilePath));
            String str;
            while ((str = in.readLine()) != null) {
                // str.replaceAll("\",","");
                nameList.addAll(Arrays.asList(str.split("\"")));
                nameList.removeIf(s -> s.contains(","));
            }
            for (int i = 0; i < nameList.size(); i++) {
                if (nameList.get(i).equals(""))
                    nameList.remove(i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent clickAction) {
        if (clickAction.getSource() == generateNew) {
            attendTexts();
            textArea.setText("Text : " + listAttribute);
            numberArea.setText("Number : " + listAttributeIndex);
            if (isVisible) {
                numberArea.setVisible(false);
                isVisible = false;
            }
        } else if (clickAction.getSource() == editList) {
            editListPage();
        } else if (clickAction.getSource() == showText) {
            if (!isVisible) {
                numberArea.setVisible(true);
                isVisible = true;
            } else {
                numberArea.setVisible(false);
                isVisible = false;
            }
        } else if (clickAction.getSource() == changeList) {
            listOperations(Integer.parseInt(numberBoxArea.getText()), textBoxArea.getText());
        } else if (clickAction.getSource() == goBackButton) {
            jf.setVisible(false);
        }
    }

    public void attendTexts() {
        try {
            addToList();
            randomGenerator();
            listAttributeIndex = listNumber + 10;
            listAttribute = nameList.get(listNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void randomGenerator() {
        try {
            Random rndm = new Random();
            listNumber = rndm.nextInt(90);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}