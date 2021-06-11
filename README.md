## Laporan Akhir Projek
- Nama sistem, Paralel, Kelompok, Nama Asisten Praktikum
  * Nama sistem: HimagretoApp 
  * Paralel: 3
  * Kelompok: 3
  * Nama Asisten Praktikum:
    - Qorry Atul Chairunnisa (G64170014)
    - Indah Puspita (G64170035)
- Nama anggota kelompok dan masing-masing role
  * Permata Mei Kartika           (G24180004) sebagai UI/UX Researcher dan Front-End
  * Eka Devi Oktaviani            (G24180017) sebagai Data Miner
  * Ikhlas Taufiqul Hakim         (G24180023) sebagai Back-End dan Project Manager
  * Mochammad Gilang Rimbawan     (G24180084) sebagai UI/UX Designer
- Deskripsi singkat aplikasi
  * Aplikasi Himagreto App dibuat untuk membantu mahasiswa khususnya mahasiswa departemen Geofisika dan Meteorologi (GFM) IPB dalam melakukan kegiatan perkuliahan baik secara akademik maupun non akademik, dan juga kegiatan pasca kampus. Fitur-fitur yang tersedia pada aplikasi ini pun memang dibuat untuk memenuhi dan mempermudah kebutuhan mahasiswa GFM dalam hal akademik maupun non akademik selama perkuliahan. Selain itu aplikasi ini juga dibuat dalam rangka “branding” himpunan profesi meteorologi IPB, yaitu Himagreto.
- User analysis 
  * User story
    - Sebagai pengguna terdaftar, agar tidak terus mengisi halaman login, saya dapat langsung masuk ke aplikasi tanpa login kembali.
    - Sebagai pengguna terdaftar, agar dapat membantu perkuliahan, saya dapat mengunduh materi kuliah.
    - Sebagai pengguna terdaftar, agar dapat mengubah teks sapaan dalam aplikasi, saya dapat mengubah username saya.
    - Sebagai admin utama, agar dapat mengisi reminder tugas harian tiap angkatan, saya dapat men- crud tiap reminder tugas di setiap harinya.
    - Sebagai mahasiswa aktif, agar dapat mengetahui tugas harian, saya dapat melihat reminder tugas.
- Spesifikasi teknis lingkungan pengembangan
  * Software
    - Operating System: Android
    - Database : Google Firebase
    - Server : Google
    - Text Editor/IDE : Notepad (untuk pembuatan bot akun), Android Studio (pembuatan aplikasi)
    - Library : Firebase
  * Hardware
  * Komputer
    - Processor : Intel Core i7-2600 @3.40GHz (8CPUs)
    - Graphics Card : NVIDIA GeForce GT 1030
    - RAM : 16 GB
    - Storage : 1 TB
  * Smartphone
    - Processor : Android 9+
    - RAM : 4GB
  * Tech Stack
    - Version Control dan Collaboration Platform : Github
    - Teknologi : Java, xml

### Hasil dan pembahasan
  * **Use case diagram**
  
  ![user case](https://user-images.githubusercontent.com/79287863/121612839-71954080-ca85-11eb-924d-a0ac686a393a.png)
  
  * **Activity diagram**
  
  ![Untitled Diagram-Activity (1)](https://user-images.githubusercontent.com/79287863/121612878-870a6a80-ca85-11eb-82eb-a3143a4defcd.png)
  
  * **Class diagram**
  
  ![Untitled Diagram-UML](https://user-images.githubusercontent.com/79287863/121612933-a903ed00-ca85-11eb-9b1a-fd62fb360d81.png)
  
  * **Entity Relationship Diagram**
 
 ![erd](https://user-images.githubusercontent.com/79287863/121612954-b3be8200-ca85-11eb-8653-6f6594c76e1b.png)
  
  * **Arsitektur sistem**
  
  ![arsitektur](https://user-images.githubusercontent.com/79287863/121612977-be791700-ca85-11eb-97ac-9dc668d4dac5.png)

### Fungsi utama yang dikembangkan
 * Materi Perkuliahan
   Mahasiswa GFM dapat membaca dan mengunduh materi seperti slide kuliah, rangkuman, contoh soal, maupun master.

   ![Screenshot_20210611-053615_Himagreto](https://user-images.githubusercontent.com/79287863/121613129-09932a00-ca86-11eb-95e8-bf02ede36499.jpg)

 * List dan Reminder Tugas
   Mahasiswa GFM dapat melihat list tugas harian maupun mingguan sehingga membantu dalam reminder tugas.

   ![Screenshot_20210611-053606_Himagreto](https://user-images.githubusercontent.com/79287863/121613148-14e65580-ca86-11eb-9fa3-0f9cf28f521d.jpg)

 * Edit Profil
   Mahasiswa GFM dapat mengubah dan melengkapi data pribadi seperti alamat, nomor telepon dan tanggal lahir. Hal ini dapat membantu dalam kebutuhan database anggota Himagreto. 
   
   ![Screenshot_20210611-053727_Himagreto](https://user-images.githubusercontent.com/79287863/121613170-1dd72700-ca86-11eb-907c-3badb51c29ff.jpg)
   ![Screenshot_20210611-053848_Himagreto](https://user-images.githubusercontent.com/79287863/121613174-1fa0ea80-ca86-11eb-8ab4-abfbf6cdda19.jpg)

### Fungsi CRUD

CRUD
Admin
Mahasiswa GFM
Create
Admin dapat membuat akun sebagai admin
Mahasiswa GFM dapat membuat akun sebagai mahasiswa
Admin dapat menambahkan list tugas dan materi perkuliahan
Read
Admin dapat melihat list tugas
Mahasiswa GFM dapat melihat list tugas
Admin dapat melihat materi perkuliahan
Mahasiswa GFM dapat melihat materi perkuliahan
Update
Admin dapat mengupdate list tugas
Mahasiswa GFM dapat mengubah data pribadi seperti alamat dan nomor telepon
Admin dapat mengubah materi perkuliahan
Delete
Admin utama dapat menghapus admin di setiap angkatan
Mahasiswa GFM dapat menghapus akun 
Admin dapat menghapus list tugas dan materi perkuliahan yang sudah dibuat


Hasil implementasi
Screenshot sistem


Link aplikasi 
ipb.link/himagretoapp → Pilih himagreto.apk
Aplikasi sudah diupload dan menunggu rilis dari google play store

Testing (Test cases)
Deskripsi Pengujian
Prosedur Pengujian
Data Masukan
Keluaran Yang Diharapkan
Hal Yang Didapati
Hasil Uji
Login
Pengguna mendaftar dengan mengisi nama pengguna, kata sandi dan konfirmasi kata sandi yang benar
Positive test case
Nama Pengguna: G24180096
Kata Sandi: G24180096
Pendaftaran berhasil dan langsung menuju halaman utama
Pendaftaran berhasil dan langsung menuju halaman utama
Diterima
Pengguna mendaftar dengan mengisi nama pengguna yang salah, atau kata sandi dan konfirmasi kata sandi yang salah
Negative test case
Nama Pengguna: G24180096
Kata Sandi: G24180096
Peringatan nama pengguna berupa NIM dan password harus benar
Peringatan konfirmasi NIM dan password harus diisi dengan benar
Diterima
Melihat Daftar Materi
Pengguna dapat masuk dengan NIM dan password yang benar
Positive test case
 
Muncul daftar materi per semester beserta mata kuliah dan bab-babnya
Muncul daftar materi
Diterima
Pengguna dapat masuk dengan NIM dan password yang benar
Negative test case
 
Muncul daftar materi tetapi tidak dapat melihat bab-babnya
Daftar materi dan bab tidak muncul
Diterima
Mengunduh Materi
Pengguna memilih menu materi dan memilih bab yang akan diunduh
Positive test case
 
Bahan materi dapat diunduh dengan menekan tulisan bab
Materi dapat terunduh
Diterima
Pengguna memilih menu materi dan memilih bab yang akan diunduh
Negative test case
 
Bahan materi tidak dapat diunduh karena tidak merespon
Bahan materi tidak dapat diunduh
Diterima
Melihat Daftar Tugas
Pengguna memilih menu tugas setelah masuk
Positive test case
 
Pengguna langsung melihat daftar tugas setiap harinya di minggu tersebut
Terdapat daftar tugas
Diterima
Melihat Profil
Pengguna dapat masuk dengan NIM dan password yang benar
Positive test case
 
Pengguna menekan simbil profil dan langsung melihat profil yang masih kosong
Menu profil tertampil
Diterima
Mengedit Profil
Pengguna dapat melihat menu profil
Positive test case
 
Pengguna mengedit biodata profil dengan menekan simbol pena
Pengguna dapat mengedit biodata
Diterima
Pengguna dapat melihat menu profil
Negative test case
 
Pengguna tidak dapat mengedit biodata profil dengan menekan simbol pena karena tidak berfungsi
Pengguna tidak dapat mengedit biodata profil
 
Diterima
Logout
Pengguna keluar dari akun dengan menekan tombol kembali
 Positive test case
 
Muncul alert dialog dengan pesan "Apakah anda ingin keluar?" , tombol batal dan keluar
muncul lert dialog dengan pesan "Apakah anda ingin keluar?" , tombol batal dan keluar
Diterima



Dokumentasi Project
App
ipb.link/himagretoapp
Gsite
ipb.link/rplgsitehimagreto
Trello
https://trello.com/b/WDlrdTpQ/rpl
Github
https://github.com/ikhlasth/Himagreto
Asset data
ipb.link/drivehimagretoapp
INI FIGMA:
https://www.figma.com/file/Bz3drsD0eTgVYBXVJ4nhH5/RPL?node-id=0%3A1
 
Saran untuk pengembangan selanjutnya
Menyelesaikan fitur lainnya yang sudah dirancang sebelumnya seperti informasi lomba, beasiswa, tracking alumni, informasi dosen GFM, dan pasca kampus
Memperbaiki UX design agar user nyaman menggunakannya
Menambahkan alarm pengingat ketika menuju deadline tugas
 
