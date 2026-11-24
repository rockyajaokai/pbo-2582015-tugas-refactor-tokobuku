class EmailSender {
    void kirim(String alamat, String isi) {
        System.out.println("[EMAIL " + alamat + "] " + isi);
    }
}

class FileRiwayat {
    void simpan(String baris) {
        System.out.println("[FILE riwayat.txt] " + baris);
    }
}

class Toko {
    Keranjang keranjang = new Keranjang();
    EmailSender email = new EmailSender();
    FileRiwayat riwayat = new FileRiwayat();

    void tambah(Produk produk) {
        keranjang.tambah(produk);
    }

    void checkout(String pelanggan, String alamatEmail, MetodeBayar metode) {
        System.out.println("================ STRUK =================");
        System.out.println("Pelanggan : " + pelanggan);
        for (Produk p : keranjang.getProduk()) {
            System.out.println(p.getJudul() + " (" + p.getPenulis() + ")");
            System.out.println("  Harga       : " + Rupiah.format(p.getHarga()));
            System.out.println("  Keterangan  : " + p.keterangan());
            System.out.println("  Harga akhir : " + Rupiah.format(p.hargaAkhir()));
        }
        int total = keranjang.hitungTotal();
        int bayar = metode.hitungTotalBayar(total);
        System.out.println("-----------------------------------------");
        System.out.println("Total belanja : " + Rupiah.format(total));
        System.out.println("Metode bayar  : " + metode.getNama());
        System.out.println("Total bayar   : " + Rupiah.format(bayar));
        System.out.println("Berat kiriman : " + keranjang.hitungTotalBerat() + " gram");

        System.out.println("-------------- LINK UNDUH ---------------");
        for (Diunduh d : keranjang.getUnduhan()) {
            System.out.println(d.linkUnduh());
        }
        System.out.println("-----------------------------------------");

        riwayat.simpan(pelanggan + ";" + keranjang.jumlah() + " buku;" + Rupiah.format(bayar));
        email.kirim(alamatEmail, "Terima kasih " + pelanggan + ", total bayar " + Rupiah.format(bayar));
    }
}

public class TokoBukuKotor {
    public static void main(String[] args) {
        Toko toko = new Toko();
        toko.tambah(new BukuCetak("Pemrograman Java", "John Doe", 120000, 750));
        toko.tambah(new BukuCetak("Algoritma Dasar", "Jane Smith", 95000, 400));
        toko.tambah(new BukuDigital("Belajar OOP", "Ani Wijaya", 80000, 12.5));
        toko.tambah(new BukuAudio("Kisah Programmer", "Budi Santoso", 60000, 240));
        toko.checkout("Sari", "sari@mail.com", new EWallet());
    }
}
