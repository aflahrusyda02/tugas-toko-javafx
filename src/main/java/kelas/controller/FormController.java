package kelas.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import kelas.Navigator;
import kelas.data.DataProduk;

public class FormController {
    @FXML private TextField kodeField;
    @FXML private TextField namaField;
    @FXML private RadioButton makananRadio;
    @FXML private RadioButton minumanRadio;
    @FXML private TextField hargaField;
    @FXML private Label pesanLabel;

    private Navigator navigator;
    private DataProduk data;

    @FXML
    private void initialize() {
        // Satu grup supaya hanya satu kategori yang bisa dipilih.
        ToggleGroup grupKategori = new ToggleGroup();
        makananRadio.setToggleGroup(grupKategori);
        minumanRadio.setToggleGroup(grupKategori);
        makananRadio.setSelected(true);
    }

    public void setKonteks(Navigator navigator, DataProduk data) {
        this.navigator = navigator;
        this.data = data;
    }

    @FXML
    private void simpan() {
        try {
            String kategori = makananRadio.isSelected() ? "Makanan" : "Minuman";
            long harga = Long.parseLong(hargaField.getText().trim());
            data.tambah(kodeField.getText(), namaField.getText(), kategori, harga);
            navigator.bukaDaftar();
        } catch (NumberFormatException e) {
            pesanLabel.setText("Harga harus berupa angka bulat tanpa titik/koma.");
        } catch (IllegalArgumentException e) {
            pesanLabel.setText(e.getMessage());
        } catch (IOException e) {
            pesanLabel.setText("Halaman daftar gagal dibuka.");
            e.printStackTrace();
        }
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
}