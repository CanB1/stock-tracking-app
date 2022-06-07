import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class Anasayfa {

    private JPanel urunEkle = new JPanel();
    private JPanel urunSil = new JPanel();
    private JPanel urun;
    private JPanel deneme;
    private JTabbedPane tp = new JTabbedPane();
    private JButton btnUpload = new JButton("Resim Yükle"), btnEkle,btnSil,btnArti,btnEksi,btnKaydet;
    private JFrame frame = new JFrame("Anasayfa");
    private JLabel lblAd, lblStok,lblResim,lblSil,lblUrun,lblAdet;
    private JTextField txtAd,txtStok,txtAdet;
    private Font font = new Font("Montserrat",1,20);
    private Font txt = new Font("Montserrat",1,15);
    private String path;
    private boolean resimEklendiMi;
    private Islem islem = new Islem();
    private JComboBox cmBoxUrun, cmBoxSilme;
    private ArrayList <String> cmBoxItemList;
    private String silinenItem;
    private  String[] cmBoxText;







    public Anasayfa() throws IOException {
        try {
            islem.idBul();
            islem.urunListele();
        }
        catch (Exception e){

        }
        arrayOlustur();
        frameAyarla(frame);
        tabAyarla();
        labelAyarla();
        girisAyarla();



        btnUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser file = new JFileChooser();
                file.setCurrentDirectory(new File(System.getProperty("user.home")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images","jpg","gif","png","jpeg");
                file.addChoosableFileFilter(filter);
                int result = file.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){
                    File selectedFile = file.getSelectedFile();
                    path = selectedFile.getAbsolutePath();
                    resimEklendiMi = true;
                    }
                else if(result == JFileChooser.CANCEL_OPTION) {
                    resimEklendiMi = false;
                }
            }
        });
        btnEkle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Islem islem = new Islem();
                if (txtAd.getText().trim().equals("") || txtStok.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(null,"Boş Alan Bırakmayınız");
                }
                else if (!resimEklendiMi){
                    JOptionPane.showMessageDialog(null,"Resim Seçiniz");
                }
                else {
                    try{
                        islem.urunEkle(txtAd.getText(),txtStok.getText(),path);

                    }
                    catch (Exception exception ) {
                        JOptionPane.showMessageDialog(null,"Ürün Eklenemedi");
                    }
                    finally {
                        cmBoxUrun.addItem(txtAd.getText());
                        cmBoxSilme.addItem(txtAd.getText());
                    }
                }

            }
        });
        btnSil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                silinenItem = String.valueOf(cmBoxSilme.getSelectedItem());
                int input = JOptionPane.showConfirmDialog(null,"Emin misiniz");
                if (input == 0){
                    try {
                        islem.urunSil(silinenItem);
                        JOptionPane.showMessageDialog(null,"Ürün Başarıyla Silindi");
                    }
                    catch (Exception ex){

                    }
                    finally {
                       cmBoxSilme.removeItem(silinenItem);
                       cmBoxUrun.removeItem(silinenItem);

                    }


                }
                else {

                }

            }
        });
        cmBoxUrun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    islem.urunler.removeAll(islem.urunler);
                    islem.adet.removeAll(islem.adet);
                    islem.link.removeAll(islem.link);
                    islem.urunListele();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                txtAdet.setText(islem.adet.get(cmBoxUrun.getSelectedIndex()));
            }
        });
        btnArti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sayi = Integer.parseInt(txtAdet.getText());
                sayi++;
                txtAdet.setText(String.valueOf(sayi));
            }
        });
        btnEksi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sayi = Integer.parseInt(txtAdet.getText());
                sayi--;
                txtAdet.setText(String.valueOf(sayi));
            }
        });
        btnKaydet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    islem.stokGuncelle(txtAdet.getText(),String.valueOf(cmBoxUrun.getSelectedItem()));
                    JOptionPane.showMessageDialog(null,"İşlem Başarılı");
                    islem.adet.set(cmBoxUrun.getSelectedIndex(),txtAdet.getText());
                }
                catch (SQLException ex){
                    JOptionPane.showMessageDialog(null,"İşlem Başarısız");
                }

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
    private void tabAyarla(){
        urun = new JPanel();
        tp.setBounds(0,0,800,600);
        tp.add("Ürün Ekle",urunEkle);
        tp.add("Ürün Sil",urunSil);
        tp.add("Ürünler",urun);
        urun.setLayout(null);
        urunEkle.setLayout(null);
        urunSil.setLayout(null);
        frame.add(tp);
    }
    private void labelAyarla(){
        lblAd = new JLabel("Ürün Adı");
        lblStok = new JLabel("Stok Adedi");
        lblResim = new JLabel("Ürün Resmi");
        lblSil = new JLabel("Ürün Sil");
        lblUrun = new JLabel("Ürünler");
        lblAdet = new JLabel("Stok");
        lblAd.setBounds(50+180,50+100,130,50);
        lblStok.setBounds(50+180,90+100,130,50);
        lblResim.setBounds(50+180,130+100,130,50);
        lblSil.setBounds(350,50,100,50);
        lblUrun.setBounds(350,50,100,50);
        lblAdet.setBounds(360,120,100,50);
        lblAd.setFont(font);
        lblStok.setFont(font);
        lblResim.setFont(font);
        lblSil.setFont(font);
        lblUrun.setFont(font);
        lblAdet.setFont(font);

        urunEkle.add(lblAd);
        urunEkle.add(lblStok);
        urunEkle.add(lblResim);
        urunSil.add(lblSil);
        urun.add(lblUrun);
        urun.add(lblAdet);



    }
    private void girisAyarla(){
        txtAd = new JTextField();
        txtStok = new JTextField();
        txtAd.setBounds(200+180,50+15+100,200,20);
        txtStok.setBounds(200+180,90+15+100,200,20);
        btnUpload.setBounds(200+180,130+15+100,120,20);
        btnUpload.setBackground(Color.WHITE);
        urunEkle.add(txtAd);
        urunEkle.add(txtStok);
        urunEkle.add(btnUpload);
        btnEkle = new JButton("Ürün Ekle");
        btnEkle.setBounds(340,320,100,50);
        btnEkle.setBackground(Color.WHITE);
        urunEkle.add(btnUpload);
        urunEkle.add(btnEkle);
        txtAdet = new JTextField();
        txtAdet.setBounds(330,180,100,50);
        txtAdet.setEditable(false);
        txtAdet.setFont(txt);
        txtAdet.setHorizontalAlignment(SwingConstants.CENTER);
        txtAdet.setAlignmentX(SwingConstants.CENTER);
        urun.add(txtAdet);
        cmBoxUrun = new JComboBox(cmBoxText);
        cmBoxSilme = new JComboBox(cmBoxText);
        cmBoxSilme.setBounds(330,100,120,20);
        cmBoxUrun.setBounds(330,100,120,20);
        urunSil.add(cmBoxSilme);
        urun.add(cmBoxUrun);
        btnSil = new JButton("Ürün Sil");
        btnSil.setBounds(340,140,100,20);
        btnSil.setBackground(Color.WHITE);
        urunSil.add(btnSil);
        btnArti = new JButton("+");
        btnEksi = new JButton("-");
        btnEksi.setBounds(270,180,50,50);
        btnArti.setBounds(440,180,50,50);
        btnEksi.setBackground(Color.WHITE);
        btnArti.setBackground(Color.WHITE);
        btnKaydet = new JButton("Kaydet");
        btnKaydet.setBackground(Color.WHITE);
        btnKaydet.setBounds(330,250,100,50);

        urun.add(btnArti);
        urun.add(btnEksi);
        urun.add(btnKaydet);

    }
    private void arrayOlustur(){
        cmBoxItemList = new ArrayList<>();
        cmBoxItemList = islem.getUrunler();
        cmBoxText = new String[cmBoxItemList.size()];
        for (int i = 0; i < cmBoxItemList.size() ; i++) {
            cmBoxText[i] = cmBoxItemList.get(i);
        }

    }

}
