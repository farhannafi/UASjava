import java.util.Scanner;


class Buku {
    protected String judul;
    protected int hargaBeli;
    protected int hargaJual;
    protected int jumlahStok;

    public Buku(String judul, int hargaBeli, int hargaJual, int jumlahStok) {
        this.judul = judul;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.jumlahStok = jumlahStok;
    }

    public void tambahStok(int jumlah) {
        jumlahStok += jumlah;
    }

    public void kurangiStok(int jumlah) {
        jumlahStok -= jumlah;
        if (jumlahStok <= 0) {
            System.out.println("Stok sudah mencapai nol.");
        }
    }
}

class BukuFiksi extends Buku {
    public BukuFiksi(String judul, int hargaBeli, int hargaJual, int jumlahStok) {
        super(judul, hargaBeli, hargaJual, jumlahStok);
    }
}


class BukuNonFiksi extends Buku {
    public BukuNonFiksi(String judul, int hargaBeli, int hargaJual, int jumlahStok) {
        super(judul, hargaBeli, hargaJual, jumlahStok);
    }
}


class Majalah extends Buku {
    private int nomorEdisi;

    public Majalah(String judul, int hargaBeli, int hargaJual, int jumlahStok, int nomorEdisi) {
        super(judul, hargaBeli, hargaJual, jumlahStok);
        this.nomorEdisi = nomorEdisi;
    }

    public int getNomorEdisi() {
        return nomorEdisi;
    }
}

public class ManajemenPenjualanBuku {
    private static int modalAwal = 1000000;
    private static int modalBerjalan = modalAwal;
    private static Buku[] stokBuku = new Buku[100];
    private static int jumlahBuku = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pilihan = 0;

        
        tambahBuku(new BukuFiksi("Si Udin pindah ke isekai", 20000, 30000, 10));
        tambahBuku(new BukuNonFiksi("Sebuah seni untuk bersikap bodo amat ", 25000, 35000, 8));
        tambahBuku(new Majalah("Family Life", 15000, 20000, 5, 1));

        while (pilihan != 5) {
            tampilkanMenu();
            pilihan = scanner.nextInt();
            switch (pilihan) {
                case 1:
                    tampilkanLaporan();
                    break;
                case 2:
                    tampilkanStokBuku();
                    break;
                case 3:
                    lakukanPenjualan(scanner);
                    break;
                case 4:
                    lakukanPembelian(scanner);
                    break;
                case 5:
                    System.out.println("Program Selesai");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }

        scanner.close();
    }

    private static void tampilkanMenu() {
        System.out.println("\n===== Manajemen Penjualan Buku =====");
        System.out.println("1. Tampilkan Laporan");
        System.out.println("2. Tampilkan Stok Buku");
        System.out.println("3. Lakukan Penjualan");
        System.out.println("4. Lakukan Pembelian");
        System.out.println("5. Keluar");
        System.out.print("Masukkan pilihan Anda: ");
    }

    private static void tambahBuku(Buku buku) {
        if (jumlahBuku < stokBuku.length) {
            stokBuku[jumlahBuku] = buku;
            jumlahBuku++;
        } else {
            System.out.println("Stok buku sudah penuh.");
        }
    }

    private static void tampilkanLaporan() {
        System.out.println("\n===== Laporan =====");
        System.out.println("Modal Awal: Rp " + modalAwal);
        System.out.println("Modal Berjalan: Rp " + modalBerjalan);
    }

    private static void tampilkanStokBuku() {
        System.out.println("\n===== Stok Buku =====");
        for (int i = 0; i < jumlahBuku; i++) {
            Buku buku = stokBuku[i];
            System.out.println("Judul: " + buku.judul);
            System.out.println("Stok: " + buku.jumlahStok);
            if (buku instanceof Majalah) {
                Majalah majalah = (Majalah) buku;
                System.out.println("Nomor Edisi: " + majalah.getNomorEdisi());
            }
            System.out.println();
        }
    }

    private static void lakukanPenjualan(Scanner scanner) {
        System.out.println("\n===== Penjualan =====");
        System.out.print("Masukkan judul buku: ");
        String judul = scanner.next();
        scanner.nextLine();  
    
        System.out.print("Masukkan jumlah yang terjual: ");
        int jumlah = scanner.nextInt();
    

        Buku buku = cariBuku(judul);
        if (buku != null) {
            if (buku.jumlahStok >= jumlah) {
                buku.kurangiStok(jumlah);
                modalBerjalan += buku.hargaJual * jumlah;
                System.out.println("Penjualan sukses.");
            } else {
                System.out.println("Stok tidak cukup.");
            }
        } else {
            System.out.println("Buku tidak ditemukan.");
        }
    }

    private static void lakukanPembelian(Scanner scanner) {
        System.out.println("\n===== Pembelian =====");
        System.out.print("Masukkan judul buku: ");
        scanner.nextLine(); 
        String judul = scanner.nextLine();
        System.out.print("Masukkan jumlah yang dibeli: ");
        int jumlah = scanner.nextInt();

        Buku buku = cariBuku(judul);
        if (buku != null) {
            int totalHarga = buku.hargaBeli * jumlah;
            if (modalBerjalan >= totalHarga) {
                buku.tambahStok(jumlah);
                modalBerjalan -= totalHarga;
                System.out.println("Pembelian sukses.");
            } else {
                System.out.println("Modal tidak cukup.");
            }
        } else {
            System.out.println("Buku tidak ditemukan.");
        }
    }

    private static Buku cariBuku(String judul) {
        for (int i = 0; i < jumlahBuku; i++) {
            Buku buku = stokBuku[i];
            if (buku.judul.equalsIgnoreCase("Family Life")) {
                return buku;
            }
        }
        return null;
    }
}
