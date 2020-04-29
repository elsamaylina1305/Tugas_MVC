
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class MhsDAO {

    private int jmlData;
    private Connection koneksi;
    private Statement statement;

    //constructor berfungsi untuk melakukan sebuah koneksi saat ada object baru dibuat
    public  MhsDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/mahasiswa";
            koneksi = DriverManager.getConnection(url, "root", "");
            statement = koneksi.createStatement();
            JOptionPane.showMessageDialog(null, "Koneksi Berhasil");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Class Not Found : " + ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Exception : " + ex);
        }
    }

    public void Insert(MhsModel Model) {
        try {
            String query = "INSERT INTO data_mhs VALUES('" + Model.getNama() + "','" + Model.getNim() + "','" + Model.getAlamat() + "')";
            statement.executeUpdate(query); //execute query nya
            JOptionPane.showMessageDialog(null, "Data disimpan");
        } catch (Exception sql) {
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }
    }

    public void Update(MhsModel Model) {
        try {
            String query = "UPDATE data_mhs SET nim= '" + Model.getNim() + "', nama = '" + Model.getNama() + "', alamat = '" + Model.getAlamat() + "' WHERE nim = '" + Model.getNim() + "'";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Berhasil di update");
        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
        }
    }

    public void Delete(MhsModel Model) {
        try {
            String query = "DELETE FROM data_mhs WHERE nim= '" + Model.getNim() + "'";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Berhasil dihapus");
        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
        }
    }

        //untuk mengambil data dari database dan mengatur ke dalam tabel
    public String[][] readMahasiswa(){
        try {
            int jmlData = 0;//Menampung jumlah data
            //baris sejumlah data,kolom nya ada 3
            String data[][]= new String[getJmldata()][3];
            //pengambilan data dalam java dari database
            String query = "SELECT * FROM data_mhs";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){ //lanjut ke data selanjutnta jmlData bertambah
                data[jmlData][0] = resultSet.getString("nim");// kolom nama harus sama
                data[jmlData][1] = resultSet.getString("nama");// besar kecil nya dengan database
                data[jmlData][2] = resultSet.getString("alamat");// 
                jmlData++;
            }
            return data;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("SQL Error");
            return null;
        }
    }
    
    public int getJmldata(){
        int jmlData = 0;
        try {
            //pengambilan data kedalam java dari database
            String query = "SELECT *FROM data_mhs";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){ //lanjut kedata selanjutnya, jmlData bertambah
                jmlData++;
            }
            return jmlData;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("SQL Error");
            return 0;
        }
    }
}
