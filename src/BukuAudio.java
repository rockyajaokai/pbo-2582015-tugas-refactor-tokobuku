public class BukuAudio extends Buku implements Diunduh {
    private static final int DISKON_PERSEN = 10;

    private final int durasiMenit;

    public BukuAudio(String judul, String penulis, int harga, int durasiMenit) {
        super(judul, penulis, harga);
        this.durasiMenit = durasiMenit;
    }

    public int getDurasiMenit() {
        return durasiMenit;
    }

    @Override
    public int hargaAkhir() {
        return getHarga() * (100 - DISKON_PERSEN) / 100;
    }

    @Override
    public String keterangan() {
        return durasiMenit + " menit, diskon " + DISKON_PERSEN + "%";
    }

    @Override
    public String linkUnduh() {
        return "https://toko.buku/audio/" + slug();
    }

    @Override
    public void masukKe(Keranjang keranjang) {
        keranjang.tambahProduk(this);
        keranjang.tambahUnduhan(this);
    }
}
