public class BukuCetak extends Buku implements Dikirim {
    private static final int GRAM_PER_PAKET = 500;
    private static final int ONGKIR_PER_PAKET = 5000;

    private final int beratGram;

    public BukuCetak(String judul, String penulis, int harga, int beratGram) {
        super(judul, penulis, harga);
        this.beratGram = beratGram;
    }

    @Override
    public int getBeratGram() {
        return beratGram;
    }

    private int hitungOngkir() {
        int kelipatan = (beratGram + GRAM_PER_PAKET - 1) / GRAM_PER_PAKET;
        return kelipatan * ONGKIR_PER_PAKET;
    }

    @Override
    public int hargaAkhir() {
        return getHarga() + hitungOngkir();
    }

    @Override
    public String keterangan() {
        return beratGram + " gram, ongkir " + Rupiah.format(hitungOngkir());
    }

    @Override
    public void masukKe(Keranjang keranjang) {
        keranjang.tambahProduk(this);
        keranjang.tambahKiriman(this);
    }
}
