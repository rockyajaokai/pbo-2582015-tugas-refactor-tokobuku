import java.util.ArrayList;
import java.util.List;

// Menyimpan isi belanja dan menghitung total. Tidak tahu tipe buku apa pun.
public class Keranjang {
    private final List<Produk> produk = new ArrayList<>();
    private final List<Dikirim> kiriman = new ArrayList<>();
    private final List<Diunduh> unduhan = new ArrayList<>();

    public void tambah(Produk p) {
        p.masukKe(this);
    }

    public void tambahProduk(Produk p) {
        produk.add(p);
    }

    public void tambahKiriman(Dikirim d) {
        kiriman.add(d);
    }

    public void tambahUnduhan(Diunduh d) {
        unduhan.add(d);
    }

    public List<Produk> getProduk() {
        return produk;
    }

    public List<Diunduh> getUnduhan() {
        return unduhan;
    }

    public int jumlah() {
        return produk.size();
    }

    public int hitungTotal() {
        int total = 0;
        for (Produk p : produk) {
            total += p.hargaAkhir();
        }
        return total;
    }

    public int hitungTotalBerat() {
        int total = 0;
        for (Dikirim d : kiriman) {
            total += d.getBeratGram();
        }
        return total;
    }
}
