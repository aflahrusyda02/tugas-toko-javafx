package kelas.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kelas.model.Produk;

public class DataProduk {
    private final ObservableList<Produk> daftar =
            FXCollections.observableArrayList();

    // Daftar hanya bisa dibaca dari luar; perubahan lewat tambah().
    public ObservableList<Produk> getDaftar() {
        return FXCollections.unmodifiableObservableList(daftar);
    }

    // Membuat Produk dari input form. Produk sendiri yang memvalidasi
    // kode, nama, kategori, dan harga (IllegalArgumentException jika salah).
    // Setelah valid, kode dicek agar tidak kembar.
    public Produk tambah(String kode, String nama, String kategori, long harga) {
        Produk baru = new Produk(kode, nama, kategori, harga);
        if (kodeSudahAda(baru.getKode())) {
            throw new IllegalArgumentException(
                    "Kode " + baru.getKode() + " sudah dipakai produk lain.");
        }
        daftar.add(baru);
        return baru;
    }

    private boolean kodeSudahAda(String kode) {
        for (Produk p : daftar) {
            if (p.getKode().equalsIgnoreCase(kode)) {
                return true;
            }
        }
        return false;
    }
}