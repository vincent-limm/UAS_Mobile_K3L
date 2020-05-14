# UAS Mobile K3L
Projek Tugas Akhir Mata Kuliah Pemrograman Aplikasi Mobile


# Anggota Kelompok :
Grendi Nugroho <br>
Viriya Lucky <br>
Vincent

# Deskripsi Aplikasi
Aplikasi yang kami rancang adalah Kesehatan Keselamatan Keamanan Lingkungan (K3L) berbasis mobile untuk universitas. Aplikasi kami digunakan untuk melaporkan  kendala dan kerusakan yang pengguna temukan di dalam lingkungan kampus. Aplikasi ini menggunakan Firebase untuk penyimpanan gambar laporan dan data laporan.

# Daftar Fitur
1. Login dan Sign-in
	Fitur login diimplementasikan dengan Firebase untuk authentication nya. Aplikasi ini tidak memiliki fitur sign-up karena dengan asumsi bahwa aplikasi ini sudah diimplementasikan dengan database yang dimiliki oleh universitas. Pengguna harus login berisikan e-mail universitas dan password yang sama dalam database.
2. Pembuatan Laporan
	Pengguna membuat laporan kerusakan dan kendala yang ditemukan setelah melakukan login dan sign-up. Pengguna harus memasukan gambar dengan pilihan melalui kamera atau diambil dari galeri, gambar yang dipilih akan dimunculkan thumbnail nya dan pengguna juga dapat memilih ulang gambar jika terjadi kesalahan dalam memilih gambar yang ingin di upload. Lalu mengisikan deskripsi laporan, lokasi dimana kendala ditemukan dan nomor telepon pelapor. 
3. Pengiriman Laporan
	Laporan di upload (unggah) ke dalam Firebase dalam format Collection dan Document untuk deskripsi laporan dan nama dari gambar yang diupload. Document tersebut disimpan dalam format report_DD-MM-YYYY-JAM-MENIT-DETIK sehingga unik. Sedangkan untuk file gambar nya sendiri dimasukkan ke dalam Firebase Storage. Kemudian pengguna dapat mengirim laporan baru yang akan dikirimkan ke database admin dengan menekan “new form”.
4. Pencetakan dan Penyimpanan Laporan
	Pengguna dapat mencetak laporan yang sudah dikirimkan dalam bentuk file pdf dan disimpan di dalam Application Specific Folder. Laporan tersebut berisi deskripsi, lokasi, nomor telepon yang telah diisi di Pembuatan Laporan dan email pengguna. Pengguna dapat menekan tombol “Back to Form” untuk kembali ke menu Pembuatan Laporan.

# Spesifikasi Aplikasi
IDE : Android Studio <br>
Bahasa Pemrograman : Java <br>
minSdkVersion : 21 <br>
targetSdkVersion : 28 <br>
Library : Android Firebase, Glide
