# Laporan Refactor Toko Buku

Implementasi `TokoBukuSolid.java` berhasil dibangun dan keluarannya disimpan di
`keluaran-refactor.txt`. Namun, file awal `TokoBukuKotor.java` tidak tersedia di
repository ini, sehingga kesamaan keluaran dengan program awal belum dapat
diverifikasi dan commit awal sesuai ketentuan belum dapat dibuat dengan benar.

## S — Single Responsibility Principle

**(a) Kode awal**

```java
class Toko {
    ...
    void checkout(String pelanggan, String alamatEmail, String metode) {
        System.out.println("================ STRUK =================");
        ...
        int bayar = totalBayar(metode, total);
        ...
        riwayat.simpan(pelanggan + ";" + keranjang.size() + " buku;" + formatRupiah(bayar));
        email.kirim(alamatEmail, "Terima kasih " + pelanggan + ", total bayar " + formatRupiah(bayar));
    }
}
```

**(b) Masalah.** Class `Toko` memegang terlalu banyak tugas sekaligus: menyimpan
keranjang, menghitung harga dan ongkir, menghitung biaya metode bayar, mencetak
struk, menyimpan riwayat, mengirim email, dan memformat rupiah. Perubahan format
struk, aturan ongkir, atau isi email semuanya memaksa kita mengedit class yang sama.

**(c) Perbaikan.** Tiap tugas dipindah ke class sendiri: `Keranjang` (isi dan total
belanja), `StrukKonsol` (mencetak struk), `MetodeBayar` beserta implementasinya
(biaya pembayaran), `FileRiwayat` (riwayat), `EmailSender` (notifikasi), dan
`Rupiah` (format). `Toko` kini hanya mengatur urutan checkout.

## O — Open/Closed Principle

**(a) Kode awal**

```java
int hargaAkhir(Buku b) {
    switch (b.jenis) {
        case "CETAK":   return b.getHarga() + hitungOngkir(b);
        case "DIGITAL": return b.getHarga() * 80 / 100;
        case "AUDIO":   return b.getHarga() * 90 / 100;
        default:        return b.getHarga();
    }
}

int totalBayar(String metode, int totalBelanja) {
    if (metode.equals("TUNAI")) { ... } else if (metode.equals("TRANSFER")) { ... }
    ...
}
```

**(b) Masalah.** Menambah jenis buku baru atau metode bayar baru berarti mengedit
`hargaAkhir`, `keterangan`, `namaMetode`, dan `totalBayar` di `Toko`. Kode lama yang
sudah teruji harus dibongkar setiap kali ada fitur baru, dan satu `switch` yang
terlewat menjadi bug.

**(c) Perbaikan.** Perilaku yang berbeda per jenis dipindah ke polimorfisme.
`BukuCetak`, `BukuDigital`, dan `BukuAudio` masing-masing mengimplementasikan
`hargaAkhir()` dan `keterangan()`. `Tunai`, `Transfer`, dan `EWallet`
mengimplementasikan `MetodeBayar`. Jenis buku atau metode bayar baru cukup
ditambah sebagai class baru tanpa mengubah `Toko`, `Keranjang`, maupun `StrukKonsol`.

## L — Liskov Substitution Principle

**(a) Kode awal**

```java
public int getBeratGram() {
    if (!jenis.equals("CETAK")) {
        throw new UnsupportedOperationException(judul + " tidak punya berat");
    }
    return beratGram;
}
```

**(b) Masalah.** `Buku` berjanji memenuhi kontrak `Produk`, tetapi untuk buku digital
dan audio, `getBeratGram()` dan `linkUnduh()` melempar exception. Subtipe tidak bisa
dipakai menggantikan tipe induknya dengan aman. Itu sebabnya `checkout` harus
mengecek `jenis` dulu sebelum memanggilnya, padahal seharusnya cukup memanggil
metode kontrak.

**(c) Perbaikan.** Tidak ada lagi metode yang dipaksakan lalu dibatalkan dengan
exception. Setiap subclass hanya menjanjikan apa yang benar-benar bisa ia penuhi,
sehingga `BukuCetak`, `BukuDigital`, dan `BukuAudio` dapat dipakai di mana pun
`Produk` diharapkan tanpa kejutan.

## I — Interface Segregation Principle

**(a) Kode awal**

```java
interface Produk {
    String getJudul();
    String getPenulis();
    int getHarga();
    int getBeratGram();      // hanya masuk akal untuk buku cetak
    String linkUnduh();      // hanya masuk akal untuk buku digital & audio
    int getDurasiMenit();    // hanya masuk akal untuk buku audio
}
```

**(b) Masalah.** Interface `Produk` gemuk. Setiap implementor dipaksa menyediakan
metode yang tidak relevan baginya, dan ini langsung menjadi akar pelanggaran LSP di
atas.

**(c) Perbaikan.** `Produk` dirampingkan menjadi metode yang berlaku untuk semua
produk. Dua role interface baru dibuat: `Dikirim` (`getBeratGram()`, hanya
diimplementasikan `BukuCetak`) dan `Diunduh` (`linkUnduh()`, diimplementasikan
`BukuDigital` dan `BukuAudio`). `getDurasiMenit()` kini hanya ada di `BukuAudio`
karena memang hanya relevan di sana.

## D — Dependency Inversion Principle

**(a) Kode awal**

```java
class Toko {
    ArrayList<Buku> keranjang = new ArrayList<>();
    EmailSender email = new EmailSender();
    FileRiwayat riwayat = new FileRiwayat();
    ...
}
```

**(b) Masalah.** `Toko` (logika tingkat tinggi) membuat sendiri class konkret
`EmailSender` dan `FileRiwayat`. Mengganti email dengan SMS, atau file dengan
database, berarti mengedit `Toko`. Menguji `Toko` tanpa benar-benar "mengirim email"
juga tidak mungkin.

**(c) Perbaikan.** Dibuat interface `PenyimpanRiwayat`, `PengirimNotifikasi`, dan
`StrukPrinter`. `FileRiwayat`, `EmailSender`, dan `StrukKonsol` mengimplementasikannya.
`Toko` menerima ketiganya (dan `Keranjang`) lewat constructor dan hanya bergantung
pada interface. Hanya `main` di `TokoBukuSolid` yang memanggil `new` untuk
merakit semuanya.

## Bagian yang sengaja tidak diberi abstraksi (YAGNI/KISS)

Beberapa bagian sengaja dibiarkan konkret. `Rupiah` cukup berupa class utilitas
statis karena hanya ada satu cara memformat rupiah dan tidak ada kebutuhan
menggantinya. `Keranjang` tidak dibuatkan interface karena hanya ada satu
implementasi dan tidak ada alasan nyata untuk variasinya. Aturan ongkir dan diskon
tetap berupa konstanta serta rumus sederhana di dalam masing-masing class buku,
tanpa class `Ongkir`, `Diskon`, atau pola Strategy/Factory, karena aturannya
sederhana dan hanya dipakai di satu tempat. Menambah lapisan-lapisan itu sekarang
hanya akan membuat kode lebih panjang tanpa manfaat, dan baru layak dilakukan bila
aturannya kelak benar-benar bervariasi.