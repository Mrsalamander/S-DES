import des.DecryptCreator;
import des.EncryptCreator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Window extends JFrame implements ActionListener {
    ForcelyAttack f = new ForcelyAttack();
    JTextField textPlain,textKey,textCipher,textA;//视图
    JTextArea showArea;//视图
    JButton EncryptButton;//控制器
    JButton BruteForceButton;//控制器
    Window () {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    void init () {
        textPlain = new JTextField(7);
        textKey = new JTextField(7);
        textCipher = new JTextField(7);
        showArea = new JTextArea();
        EncryptButton = new JButton("Encrypt/Decrypt");
        BruteForceButton = new JButton("Brute Force Attack");
        JPanel pNorth = new JPanel();
        pNorth.add(new JLabel("Plaintext"));
        pNorth.add(textPlain);
        pNorth.add(new JLabel("Ciphertext"));
        pNorth.add(textCipher);
        pNorth.add(new JLabel("Key"));
        pNorth.add(textKey);
        pNorth.add(EncryptButton);
        pNorth.add(BruteForceButton);
        EncryptButton.addActionListener(this);
        BruteForceButton.addActionListener(this);
        add(pNorth,BorderLayout.NORTH);
        add(new JScrollPane(showArea), BorderLayout.CENTER);
        showArea.append("       Welcome to use the S-DES encryption algorithm ! " +
                " The plaintext/ciphertext input box can enter an 8-bit binary string" +
                " or 8 ASCII " + "\n" +
                "characters or less.The key input box's key must be an 10-bit binary string." + "\n");
        showArea.append("\n");
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == EncryptButton) {
            try {
                String plainText = textPlain.getText();
                String keyText = textKey.getText();
                String cipherText = textCipher.getText();
                if ((!cipherText.isEmpty()) && (plainText.isEmpty()) && (!keyText.isEmpty())) {
                    methodD(cipherText, keyText);
                }
                else if ((cipherText.isEmpty()) && (!plainText.isEmpty()) && (!keyText.isEmpty())) {
                    methodE(plainText, keyText);
                }
                else if ((!cipherText.isEmpty()) && (!plainText.isEmpty()) && (keyText.isEmpty())) {
                    showArea.append("Error!: If you want to encrypt or decrypt, delete one of the plaintext or ciphertext; "
                            + "\n" +
                            "           If you want to brute force, click the Brute Force button."+ "\n");
                }
                else if ((!cipherText.isEmpty()) && (!plainText.isEmpty()) && (!keyText.isEmpty())) {
                    showArea.append("Error!: Check your input."+ "\n");
                }
                else {
                    showArea.append("Error!: The plaintext or the ciphertext is empty"+ "\n");
                }
                //判定输入内容是否为空，来决定采用的的方法
            } catch (Exception ex) {
                showArea.append("Error!: Please check the input format." + "\n" + ex + "\n");
            }
        } else if (e.getSource() == BruteForceButton) {
            try {
                String plainText = textPlain.getText();
                String cipherText = textCipher.getText();
                String keyText = textKey.getText();
                if ((!cipherText.isEmpty()) && (!plainText.isEmpty()) && (keyText.isEmpty())) {
                    methodBF(plainText, cipherText);
                }
            }
            catch (Exception ex) {
                showArea.append("Error!" + "\n" + ex + "\n");
            }
        }
    }
    public void methodE(String strP, String strK ){
        String p = strP;
        String key = strK;
        EncryptCreator dir = new EncryptCreator(p,key);
        String c = dir.encrypt();
        showArea.append("Encrypted:"+"\n");
        showArea.append("Plaintext :" + p + "   " + "Key :" + key + "\n");
        showArea.append("Ciphertext:");
        showArea.append(c+"\n");
    }
    public void methodD(String strC, String strK ){
        String c = strC;
        String key = strK;
        DecryptCreator dir = new DecryptCreator(c, key);
        String newp = dir.decrypt();
        showArea.append("Decrypted:"+"\n");
        showArea.append("Ciphertext :" + c + "   " + "Key :" + key + "\n");
        showArea.append("Plaintext:");
        showArea.append(newp+"\n");
    }
    public void methodBF(String strP,String strC) {
        String p = strP;
        String c = strC;
//        ForcelyAttack f = new ForcelyAttack();
        long timestamp1 = System.currentTimeMillis();
//        showArea.append(timestamp1+"\n");
        String keys = f.run(p,c);
        long timestamp2 = System.currentTimeMillis();
//        showArea.append(timestamp2+"\n");
        String elapsedTime = String.valueOf(timestamp2 - timestamp1);
        showArea.append("Elapsed time : "+elapsedTime+"ms"+"\n");
        String key = f.find();
        showArea.append(keys);
        showArea.append("\n");
        showArea.append(key);
        showArea.append("\n");
    }
}
