import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class Form {

    static JTextField textField = new JTextField();
    static JPasswordField passField = new JPasswordField();
    static JButton buton = new JButton();
    static JFrame frame = new JFrame("Giriş");

    public static void setFrame(boolean kontrol) {
        Form.frame.setVisible(kontrol);
    }

    public static boolean kontrol = false;

    public static Font font = new Font("Montserrat", 1, 30);

    public static Font butonFont = new Font("Montserrat", 1, 20);

    public static void frameAyarla(JFrame frame) {
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static JLabel labelAyarla(String name, String text, int x, int y, int width, int height) {
        JLabel label = new JLabel();
        label.setName(name);
        label.setText(text);
        label.setBounds(x, y, width, height);
        label.setFont(font);

        return label;

    }

    public static JButton butonAyarla(String text, int x, int y, int width, int height) {
        JButton buton = new JButton();
        buton.setText(text);
        buton.setBounds(x, y, width, height);
        buton.setFont(butonFont);
        buton.setBackground(Color.WHITE);

        return buton;
    }

    public static JTextField textFieldAyarla(String name, int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setName(name);
        textField.setBounds(x, y, width, height);

        return textField;
    }

    public static JPasswordField passFieldAyarla(String name, int x, int y, int width, int height) {
        JPasswordField passField = new JPasswordField();
        passField.setName(name);
        passField.setBounds(x, y, width, height);

        return passField;
    }

    public static void main(String[] args) {
        Islem islem = new Islem();
        buton = butonAyarla("Giriş Yap", 340, 370, 140, 50);
        textField = textFieldAyarla("userText", 300, 210, 220, 30);
        passField = passFieldAyarla("passText", 300, 310, 220, 30);
        JTextField finalTextField = textField;
        JPasswordField finalPassField = passField;
        buton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    islem.uyeGiris(finalTextField.getText(), finalPassField.getPassword());

                } catch (SQLException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });
        JLabel labelLink = new JLabel("Henüz Üye Değil Misin?");
        labelLink.setBounds(345, 410, 150, 50);
        labelLink.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setVisible(false);
                Kayit kayit = new Kayit();
                if (kontrol) {
                    frame.setVisible(true);
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        frame.add(labelLink);
        frame.add(labelAyarla("userLabel", "Kullanıcı Adı", 300, 150, 220, 50));
        frame.add(textField);
        frame.add(labelAyarla("passLabel", "Şifre", 300, 250, 220, 50));
        frame.add(passField);
        frame.add(buton);
        frameAyarla(frame);


    }


}
