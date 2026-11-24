// SRP: Toko hanya mengatur alur checkout.
public class Toko {
    private final Keranjang keranjang = new Keranjang();
    private final StrukKonsol printer = new StrukKonsol();
    private final EmailSender email = new EmailSender();
    private final FileRiwayat riwayat = new FileRiwayat();

    public void tambah(Produk produk) {
        keranjang.tambah(produk);
    }

    public void checkout(String pelanggan, String alamatEmail, MetodeBayar metode) {
        int total = keranjang.hitungTotal();
        int bayar = metode.hitungTotalBayar(total);

        printer.cetak(pelanggan, keranjang, metode, total, bayar);

        riwayat.simpan(pelanggan + ";" + keranjang.jumlah() + " buku;" + Rupiah.format(bayar));
        email.kirim(alamatEmail, "Terima kasih " + pelanggan + ", total bayar " + Rupiah.format(bayar));
    }
}
