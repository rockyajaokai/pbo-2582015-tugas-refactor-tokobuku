public class BukuDigital extends Buku implements Diunduh {
    private static final int DISKON_PERSEN = 20;

    private final double ukuranMB;

    public BukuDigital(String judul, String penulis, int harga, double ukuranMB) {
        super(judul, penulis, harga);
        this.ukuranMB = ukuranMB;
    }

    @Override
    public int hargaAkhir() {
        return getHarga() * (100 - DISKON_PERSEN) / 100;
    }

    @Override
    public String keterangan() {
        return ukuranMB + " MB, diskon " + DISKON_PERSEN + "%";
    }

    @Override
    public String linkUnduh() {
        return "https://toko.buku/ebook/" + slug();
    }

    @Override
    public void masukKe(Keranjang keranjang) {
        keranjang.tambahProduk(this);
        keranjang.tambahUnduhan(this);
    }
}
