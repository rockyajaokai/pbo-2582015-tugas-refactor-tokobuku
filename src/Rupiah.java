// Utilitas pemformat. Sengaja tidak dibuat interface (YAGNI).
public final class Rupiah {
    private Rupiah() {
    }

    public static String format(int angka) {
        return String.format("Rp%,d", angka).replace(',', '.');
    }
}
