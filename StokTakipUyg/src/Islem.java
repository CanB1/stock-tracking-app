import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Islem {


    private static String kullaniciId;
    private static String userName;

    public static ArrayList<String> getUrunler() {
        return urunler;
    }

    public static ArrayList<String> urunler = new ArrayList<>();
    public static ArrayList<String> adet= new ArrayList<>();
    public static ArrayList<String> link = new ArrayList<>();

    public static void uyeEkle(String ad, String soyad,String kullaniciAdi,String mail,String sifre) throws SQLException {
        Connection connection = null;
        Database db = new Database();
        PreparedStatement statement = null;
        try {
            connection = db.getConnection();
            String sql = "insert into kullanicilar (ad,soyad,kullaniciAdi,mail,sifre) values (?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, ad);
            statement.setString(2, soyad);
            statement.setString(3, kullaniciAdi);
            statement.setString(4, mail);
            statement.setString(5, sifre);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null,"Kayıt Başarılı");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Bu Kullanıcı Adı Zaten Alınmış");
        } finally {
            statement.close();
            connection.close();
        }
    }

    public static void uyeGiris(String kullaniciAdi, char[] sifre) throws SQLException {
        Connection connection = null;
        Database db = new Database();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sifreText = "";
        Form form = new Form();

        int id = -1;

        for (int i = 0; i < sifre.length; i++) {
            sifreText += "" + sifre[i];
        }
        try {
            connection = db.getConnection();
            String sql = "select * from kullanicilar where kullaniciAdi = (?) AND sifre = (?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, kullaniciAdi);
            statement.setString(2, sifreText);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                id = resultSet.getInt("id");

            }
            if (id != -1) {
                JOptionPane.showMessageDialog(null, "Giriş Başarılı");
                userName = kullaniciAdi;
                form.setFrame(false);
                try {
                    Anasayfa anasayfa = new Anasayfa();
                }
                catch (Exception e){

                }

            } else {
                JOptionPane.showMessageDialog(null, "Hatalı Kullanıcı Adı veya Şifre");
            }

        } catch (SQLException e) {
            db.ShowError(e);
        } finally {
            statement.close();
            connection.close();
        }
    }

    public static void urunEkle(String urunAd,String urunStok,String resimUrl) throws SQLException{
        Connection connection = null;
        Database db = new Database();
        PreparedStatement statement = null;
        try {
            connection = db.getConnection();
            String sql = "insert into users.urunler (urunAd,urunStok,resimUrl,stokKod,kullaniciId) values (?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,urunAd);
            statement.setString(2,urunStok);
            statement.setString(3,resimUrl);
            statement.setString(4,rndSayi());
            statement.setString(5,kullaniciId);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null,"Ürün Eklendi");
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Ürün Eklenemedi");
        } finally {
            statement.close();
            connection.close();
        }
    }

    public static void idBul() throws SQLException {
        Connection connection = null;
        Database db = new Database();
        PreparedStatement statement = null;
        ResultSet resultSet;
        try{
            connection = db.getConnection();
            String sql = "select * from users.kullanicilar where kullaniciAdi = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,userName);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                kullaniciId = resultSet.getString("id");
            }

        }
        catch (SQLException e) {
            db.ShowError(e);
        } finally {
            statement.close();
            connection.close();
        }
    }

    public static void urunListele() throws SQLException {
        Connection connection = null;
        Database db = new Database();
        PreparedStatement statement = null;
        ResultSet resultSet;
        try{
            connection = db.getConnection();
            String sql = "select * from users.urunler where kullaniciId = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,kullaniciId);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                urunler.add(resultSet.getNString("urunAd"));
                adet.add(resultSet.getNString("urunStok"));
                link.add(resultSet.getNString("resimUrl"));
            }

        }
        catch (SQLException e) {
            db.ShowError(e);
        } finally {
            statement.close();
            connection.close();
        }
    }

    public static void urunSil(String urunAd) throws SQLException {
        Connection connection = null;
        Database db = new Database();
        PreparedStatement statement = null;
        try {
            connection = db.getConnection();
            String sql = "delete  from users.urunler where urunAd = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,urunAd);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            db.ShowError(e);
        } finally {
            statement.close();
            connection.close();
        }
    }

    public static void stokGuncelle(String stok,String urunAd) throws SQLException{
        Connection connection = null;
        Database db = new Database();
        PreparedStatement statement = null;
        try {
            connection = db.getConnection();
            String sql = "update users.urunler set urunStok = ? where urunAd = ? and kullaniciId = ?" ;
            statement = connection.prepareStatement(sql);
            statement.setString(1,stok);
            statement.setString(2,urunAd);
            statement.setString(3,kullaniciId);
            statement.executeUpdate();
        }
        catch (SQLException e){
            db.ShowError(e);
        }
        finally {
            statement.close();
            connection.close();
        }
    }

    public static int adetBul(String urunAd) throws SQLException{
        Connection connection = null;
        Database db = new Database();
        PreparedStatement statement = null;
        ResultSet resultSet;
        int adet = 0;
        try {
            connection = db.getConnection();
            String sql = "select * from users.urunler where kullaniciId = ? AND urunAd = ?" ;
            statement = connection.prepareStatement(sql);
            statement.setString(1,kullaniciId);
            statement.setString(2,urunAd);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                adet = resultSet.getInt("urunStok");
            }


        }
        catch (SQLException e){
            db.ShowError(e);

        }
        finally {
            statement.close();
            connection.close();
            return adet;
        }
    }



    private static String rndSayi() {
        Random rnd = new Random();
        int sayi = rnd.nextInt(89999)+10000;
        String strSayi = String.valueOf(sayi);



        return strSayi;
    }


}
