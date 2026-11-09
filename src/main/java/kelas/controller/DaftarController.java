package kelas.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kelas.Navigator;
import kelas.data.DataProduk;
import kelas.model.Produk;

public class DaftarController {
    @FXML private TableView<Produk> tabel;
    @FXML private TableColumn<Produk, String> kodeCol;
    @FXML private TableColumn<Produk, String> namaCol;
    @FXML private TableColumn<Produk, String> kategoriCol;
    @FXML private TableColumn<Produk, Long> hargaCol;
    @FXML private TextField cariField;
    @FXML private Label pesanLabel;

    private Navigator navigator;
    private DataProduk data;

    // Isi tabel; daftar asli tetap aman di DataProduk.
    private final ObservableList<Produk> hasilTampil =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        kodeCol.setCellValueFactory(new PropertyValueFactory<>("kode"));
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        kategoriCol.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        hargaCol.setCellValueFactory(new PropertyValueFactory<>("harga"));

        // Nilai kolom tetap Long supaya urut numerik; hanya tampilannya diformat.
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("id-ID"));
        rupiah.setMaximumFractionDigits(0);
        hargaCol.setCellFactory(kolom -> new TableCell<Produk, Long>() {
            @Override
            protected void updateItem(Long nilai, boolean kosong) {
                super.updateItem(nilai, kosong);
                if (kosong || nilai == null) {
                    setText(null);
                } else {
                    setText(rupiah.format(nilai));
                }
            }
        });

        tabel.setItems(hasilTampil);
        tabel.setPlaceholder(new Label("Belum ada produk yang cocok."));
    }

    public void setKonteks(Navigator navigator, DataProduk data) {
        this.navigator = navigator;
        this.data = data;
        cari();
    }

    @FXML
    private void cari() {
        if (data == null) {
            return;
        }
        hasilTampil.clear();
        String kata = cariField.getText().trim().toLowerCase(Locale.ROOT);
        for (Produk p : data.getDaftar()) {
            if (p.getNama().toLowerCase(Locale.ROOT).contains(kata)) {
                hasilTampil.add(p);
            }
        }
        tabel.sort();
        pesanLabel.setText("Menampilkan " + hasilTampil.size()
                + " dari " + data.getDaftar().size() + " produk.");
    }

    @FXML
    private void resetPencarian() {
        cariField.clear();
        cari();
    }

    @FXML
    private void tambah() {
        try {
            navigator.bukaForm();
        } catch (IOException e) {
            pesanLabel.setText("Form gagal dibuka.");
            e.printStackTrace();
        }
    }

    @FXML
    private void detail() {
        Produk dipilih = tabel.getSelectionModel().getSelectedItem();
        if (dipilih == null) {
            pesanLabel.setText("Klik satu baris produk dulu.");
            return;
        }
        try {
            navigator.bukaDetail(dipilih);
        } catch (IOException e) {
            pesanLabel.setText("Halaman detail gagal dibuka.");
            e.printStackTrace();
        }
    }
}