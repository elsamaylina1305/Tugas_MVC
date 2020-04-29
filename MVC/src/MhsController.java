
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class MhsController {

    MhsModel mhsmodel; // untuk import class Model
    MhsView mhsview; // untuk import class view
    MhsDAO mhsdao;

    public MhsController(final MhsModel mhsmodel, final MhsView mhsview, final MhsDAO mhsdao) {
        this.mhsmodel = mhsmodel;
        this.mhsview = mhsview;
        this.mhsdao = mhsdao;

        if (mhsdao.getJmldata() != 0) {//disini untuk mengecek apakah database berisi data atau tidak
            String dataMahasiswa[][] = mhsdao.readMahasiswa();
            mhsview.tabel.setModel((new JTable(dataMahasiswa, mhsview.namaKolom)).getModel());
        } else {
            //Kalau tidak ada maka muncul pop up
            JOptionPane.showMessageDialog(null, "Data Tidak Ada");
        }
        
        mhsview.simpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nim = mhsview.getNim();
                String nama = mhsview.getNama();
                String alamat = mhsview.getAlamat();
                if (nim.isEmpty() || alamat.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Harap Mengisi Semua Field");
                } else {
                    //memasukkan data kedalam model
                    mhsmodel.setMhsModel(nim, nama, alamat);
                    //menjalankan perintah insert di DAO
                    mhsdao.Insert(mhsmodel);

                    String dataMahasiswa[][] = mhsdao.readMahasiswa();
                    mhsview.tabel.setModel((new JTable(dataMahasiswa, mhsview.namaKolom)).getModel());
                }
            }
        });
        
        mhsview.delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                String nim = mhsview.getNim();
                String nama = mhsview.getNama();
                String alamat = mhsview.getAlamat();
                if(nim.isEmpty() || alamat.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Field Tidak Boleh Kosong");                   
                }else{
                    int input = JOptionPane.showConfirmDialog(null, "Apakah anda ingin menghapus Data "+nim+"?","Pilih Opsi...",JOptionPane.YES_NO_OPTION);
          
                    if(input==0){
                    mhsmodel.setMhsModel(nim, nama, alamat);
                    mhsdao.Delete(mhsmodel);
                    
                    String dataMahasiswa[][]= mhsdao.readMahasiswa();
                    mhsview.tabel.setModel((new JTable(dataMahasiswa , mhsview.namaKolom)).getModel());
                    
                    }
                    else JOptionPane.showMessageDialog(null, "Tidak Jadi Dihapus");
                }
            }
        });
    }
}
