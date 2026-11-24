public class StrukKonsol implements StrukPrinter {
    @Override
    public void cetak(String pelanggan, Keranjang keranjang, MetodeBayar metode,
                      int totalBelanja, int totalBayar) {
        System.out.println("================ STRUK =================");
        System.out.println("Pelanggan : " + pelanggan);
        for (Produk p : keranjang.getProduk()) {
            System.out.println(p.getJudul() + " (" + p.getPenulis() + ")");
            System.out.println("  Harga       : " + Rupiah.format(p.getHarga()));
            System.out.println("  Keterangan  : " + p.keterangan());
            System.out.println("  Harga akhir : " + Rupiah.format(p.hargaAkhir()));
        }
        System.out.println("-----------------------------------------");
        System.out.println("Total belanja : " + Rupiah.format(totalBelanja));
        System.out.println("Metode bayar  : " + metode.getNama());
        System.out.println("Total bayar   : " + Rupiah.format(totalBayar));
        System.out.println("Berat kiriman : " + keranjang.hitungTotalBerat() + " gram");

        System.out.println("-------------- LINK UNDUH ---------------");
        for (Diunduh d : keranjang.getUnduhan()) {
            System.out.println(d.linkUnduh());
        }
        System.out.println("-----------------------------------------");
    }
}
