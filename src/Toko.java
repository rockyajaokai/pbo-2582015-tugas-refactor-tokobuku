// Hanya mengorkestrasi alur checkout. Semua kolaborator disuntik lewat constructor.
public class Toko {
    private final Keranjang keranjang;
    private final StrukPrinter printer;
    private final PenyimpanRiwayat riwayat;
    private final PengirimNotifikasi notifikasi;

    public Toko(Keranjang keranjang, StrukPrinter printer,
                PenyimpanRiwayat riwayat, PengirimNotifikasi notifikasi) {
        this.keranjang = keranjang;
        this.printer = printer;
        this.riwayat = riwayat;
        this.notifikasi = notifikasi;
    }

    public void tambah(Produk produk) {
        keranjang.tambah(produk);
    }

    public void checkout(String pelanggan, String alamatEmail, MetodeBayar metode) {
        int total = keranjang.hitungTotal();
        int bayar = metode.hitungTotalBayar(total);

        printer.cetak(pelanggan, keranjang, metode, total, bayar);

        riwayat.simpan(pelanggan + ";" + keranjang.jumlah() + " buku;" + Rupiah.format(bayar));
        notifikasi.kirim(alamatEmail,
                "Terima kasih " + pelanggan + ", total bayar " + Rupiah.format(bayar));
    }
}
