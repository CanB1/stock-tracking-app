import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Kayit extends JFrame {


    private JLabel labelAd = new JLabel("Ad"),labelSoyad = new JLabel("Soyad"),labelUsername = new JLabel("Kullanıcı Adı"),
            labelMail = new JLabel("E-Posta"),labelPass = new JLabel("Şifre"),labelRepass = new JLabel("Şifre Tekrar");
    private JPanel panel;
    private JTextField txtAd = new JTextField(),txtSoyad = new JTextField(),txtUsername = new JTextField(),txtMail = new JTextField();
    private JPasswordField txtPass = new JPasswordField(),txtRepass = new JPasswordField();
    private JButton btnKayit = new JButton("Üye Ol"),btnGeri = new JButton("Geri");
    private Font font = new Font("Montserrat",1,20);
    private int a = 0;
    JFrame frame = new JFrame("Kayıt Ol");
    Islem islem = new Islem();
    Form form = new Form();

    public Kayit(){
        frameAyarla(frame);
        yerlestir();
        btnKayit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] sifre;
                char[] sifreTekrar;
                if (txtAd.getText().trim().equals("") || txtSoyad.getText().trim().equals("") ||
                        txtUsername.getText().trim().equals("") || txtMail.getText().trim().equals("") ||
                txtPass.getPassword() == null || txtRepass.getPassword() == null) {
                    JOptionPane.showMessageDialog(null,"Boş Alan Bırakmayınız");
                }
                else {
                    sifre = txtPass.getPassword();
                    sifreTekrar = txtRepass.getPassword();
                    String sfr = "";
                    String sfrTkr = "";
                    for (int i = 0; i < sifre.length; i++) {
                        sfr += sifre[i];
                    }
                    for (int i = 0; i < sifreTekrar.length; i++) {
                        sfrTkr += sifreTekrar[i];
                    }
                    if (!sfr.equals(sfrTkr)){
                        JOptionPane.showMessageDialog(null,"Şifreler Uyuşmuyor");
                    }
                    else {
                        try {
                            islem.uyeEkle(txtAd.getText(),txtSoyad.getText(),txtUsername.getText(),txtMail.getText(),sfr);
                            form.setFrame(true);
                            frame.dispose();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }




                    }
                }
            }
        });
        btnGeri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form.setFrame(true);
                frame.dispose();
            }
        });

    }
    private void frameAyarla(JFrame frame) {
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    private void yerlestir(){

        ayarla(labelAd,300,a,100,50);
        ayarla(txtAd,300,a+50,150,30);
        a+=80;
        ayarla(labelSoyad,300,a,100,50);
        ayarla(txtSoyad,300,a+50,150,30);
        a+=80;
        ayarla(labelUsername,300,a,150,50);
        ayarla(txtUsername,300,a+50,150,30);
        a+=80;
        ayarla(labelMail,300,a,100,50);
        ayarla(txtMail,300,a+50,150,30);
        a+=80;
        ayarla(labelPass,300,a,100,50);
        ayarla(txtPass,300,a+50,150,30);
        a+=80;
        ayarla(labelRepass,300,a,150,50);
        ayarla(txtRepass,300,a+50,150,30);
        ayarla(btnKayit,315,a+90,120,50);
        ayarla(btnGeri,600,50,80,30);
        btnGeri.setBackground(Color.WHITE);
        btnKayit.setBackground(Color.WHITE);



    }
    private void ayarla(JComponent component,int x,int y,int width,int height){

        component.setBounds(x, y, width, height);
        if (component.getClass().getName().equals("javax.swing.JLabel")) {
            component.setFont(font);
        }
        frame.add(component);



    }
}
