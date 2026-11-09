package kelas.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import kelas.Navigator;
import kelas.model.Produk;

public class DetailController {
    @FXML private Label kodeLabel;
    @FXML private Label namaLabel;
    @FXML private Label kategoriLabel;
    @FXML private Label hargaLabel;
    @FXML private Label pesanLabel;

    private Navigator navigator;

    // Dipanggil Navigator setelah FXML dimuat; menerima objek Produk terpilih.
    public void setData(Navigator navigator, Produk produk) {
        this.navigator = navigator;
        kodeLabel.setText(produk.getKode());
        namaLabel.setText(produk.getNama());
        kategoriLabel.setText(produk.getKategori());

        NumberFormat rupiah = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("id-ID"));
        rupiah.setMaximumFractionDigits(0);
        hargaLabel.setText(rupiah.format(produk.getHarga()));
    }

    @FXML
    private void bukaDaftar() {
        try {
            navigator.bukaDaftar();
        } catch (IOException e) {
            pesanLabel.setText("Halaman daftar gagal dibuka.");
            e.printStackTrace();
        }
    }

    @FXML
    private void tambahLagi() {
        try {
            navigator.bukaForm();
        } catch (IOException e) {
            pesanLabel.setText("Form gagal dibuka.");
            e.printStackTrace();
        }
    }
}