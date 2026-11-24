// Kontrak minimal yang berlaku untuk SEMUA produk (ISP: tidak ada
// metode yang hanya masuk akal untuk sebagian tipe).
public interface Produk {
    String getJudul();
    String getPenulis();
    int getHarga();
    int hargaAkhir();
    String keterangan();

    // Produk sendiri yang tahu "laci" mana di keranjang yang cocok untuknya.
    void masukKe(Keranjang keranjang);
}
