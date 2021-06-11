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
  
  ![activity](https://user-images.githubusercontent.com/64127539/121619298-9217c780-ca92-11eb-9f8c-62235cb40293.png)

  
  * **Class diagram**
 
  ![uml](https://user-images.githubusercontent.com/64127539/121619156-4b29d200-ca92-11eb-812f-1c99cbe918be.png)

  
  * **Entity Relationship Diagram**

 ![erd](https://user-images.githubusercontent.com/64127539/121619208-6bf22780-ca92-11eb-8e85-6828f354aee5.png)

  
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
   
   * Admin Control
   Admin memiliki akses penuh CRUD
   ![admintugas](https://user-images.githubusercontent.com/64127539/121619631-1b2efe80-ca93-11eb-9682-c792633145af.jpeg)
   
   ![adminedittugas](https://user-images.githubusercontent.com/64127539/121619649-1ff3b280-ca93-11eb-9920-d46b2c9671e9.jpeg)



### Fungsi CRUD
 * **Create**
   - Admin dapat menambahkan list tugas
 * **Read**
   - Admin dapat melihat list admin lain, list angkatan aktif, dan list tugas
   - Mahasiswa GFM dapat melihat list tugas
   - Mahasiswa GFM dapat melihat materi perkuliahan
 * **Update**
   - Admin dapat mengupdate tugas di setiap angkatan
   - Mahasiswa GFM dapat mengubah data pribadi seperti alamat dan nomor telepon
 * **Delete**
   - Admin utama dapat menghapus tugas di setiap angkatan

### Hasil implementasi
 * Screenshot sistem

 * Link aplikasi 
   [Aplikasi Himagreto](https://drive.google.com/file/d/1cR_NrnA_hwXClFN_6p-_QRSMkW99Paud/view?usp=sharing)
   Aplikasi sudah diupload dan menunggu rilis dari google play store
   
   ![WhatsApp Image 2021-06-11 at 04 21 16](https://user-images.githubusercontent.com/79287863/121613646-2419d300-ca87-11eb-8f61-c6ca14bc7dd3.jpeg)

### Testing
<table>
    <thead>
        <tr>
            <th>Deskripsi Pengujian</th>
            <th>Prosedur Pengujian</th>
            <th>Data Masukan</th>
            <th>Keluaran Yang Diharapkan</th>
            <th>Hal Yang Didapati</th>
            <th>Hasil Uji</th>    
        </tr>
    </thead>
    <tbody>
         <tr>
          <thead>
            <th colspan='6'>Login</th>
          </thead>
            <td>Pengguna mendaftar dengan mengisi nama pengguna, kata sandi dan konfirmasi kata sandi yang benar</td>
            <td>Positive test case</td>
            <td>
             Nama Pengguna: G24180096
             Kata Sandi: G24180096
            </td>
            <td>Pendaftaran berhasil dan langsung menuju halaman utama</td>
            <td>Pendaftaran berhasil dan langsung menuju halaman utama</td>
            <td>Diterima</td>
         </tr>
         <tr>
            <td>Pengguna mendaftar dengan mengisi nama pengguna yang salah, atau kata sandi dan konfirmasi kata sandi yang salah</td>
            <td>Negative test case</td>
            <td>
             Nama Pengguna: G24180096
             Kata Sandi: G24180096
            </td>
            <td>Peringatan nama pengguna berupa NIM dan password harus benar</td>
            <td>Peringatan konfirmasi NIM dan password harus diisi dengan benar</td>
            <td>Diterima</td>
          </tr>
          <tr>
          <thead>
           <th colspan='6'>Melihat Daftar Materi</th>
          </thead>
            <td>Pengguna dapat masuk dengan NIM dan password yang benar</td>
            <td>Positive test case</td>
            <td></td>
            <td>Muncul daftar materi per semester beserta mata kuliah dan bab-babnya</td>
            <td>Muncul daftar materi</td>
            <td>Diterima</td>
          </tr>
     <tr>
       <td>Pengguna dapat masuk dengan NIM dan password yang benar</td>
       <td>Negative test case</td>
       <td></td>
       <td>Muncul daftar materi tetapi tidak dapat melihat bab-babnya</td>
       <td>Daftar materi dan bab tidak muncul</td>
       <td>Diterima</td>
     </tr>
     <tr>
          <thead>
           <th colspan='6'>Mengunduh Materi</th>
          </thead>
            <td>Pengguna memilih menu materi dan memilih bab yang akan diunduh</td>
            <td>Positive test case</td>
            <td></td>
            <td>Bahan materi dapat diunduh dengan menekan tulisan bab</td>
            <td>Materi dapat terunduh</td>
            <td>Diterima</td>
          </tr>
     <tr>
       <td>Pengguna memilih menu materi dan memilih bab yang akan diunduh</td>
       <td>Negative test case</td>
       <td></td>
       <td>Bahan materi tidak dapat diunduh karena tidak merespon</td>
       <td>Bahan materi tidak dapat diunduh</td>
       <td>Diterima</td>
     </tr>
     <tr>
          <thead>
           <th colspan='6'>Melihat Daftar Tugas</th>
          </thead>
            <td>Pengguna memilih menu tugas setelah masuk</td>
            <td>Positive test case</td>
            <td></td>
            <td>Pengguna langsung melihat daftar tugas setiap harinya di minggu tersebut</td>
            <td>Terdapat daftar tugas</td>
            <td>Diterima</td>
     </tr>
     <tr>
          <thead>
           <th colspan='6'>Melihat Profil</th>
          </thead>
            <td>Pengguna dapat masuk dengan NIM dan password yang benar</td>
            <td>Positive test case</td>
            <td></td>
            <td>Pengguna menekan simbol profil dan langsung melihat profil yang masih kosong</td>
            <td>Menu profil tertampil</td>
            <td>Diterima</td>
     </tr>
     <tr>
          <thead>
           <th colspan='6'>Mengedit Profil</th>
          </thead>
            <td>Pengguna dapat melihat menu profil</td>
            <td>Positive test case</td>
            <td></td>
            <td>Pengguna mengedit biodata profil dengan menekan simbol pena</td>
            <td>Pengguna dapat mengedit biodata</td>
            <td>Diterima</td>
     </tr>
     <tr>
       <td>Pengguna dapat melihat menu profil</td>
       <td>Negative test case</td>
       <td></td>
       <td>Pengguna tidak dapat mengedit biodata profil dengan menekan simbol pena karena tidak berfungsi</td>
       <td>Pengguna tidak dapat mengedit biodata profil</td>
       <td>Diterima</td>
     </tr>
     <tr>
          <thead>
           <th colspan='6'>Logout</th>
          </thead>
            <td>Pengguna keluar dari akun dengan menekan tombol kembali</td>
            <td>Positive test case</td>
            <td></td>
            <td>Muncul alert dialog dengan pesan "Apakah anda ingin keluar?" , tombol batal dan keluar</td>
            <td>muncul alert dialog dengan pesan "Apakah anda ingin keluar?" , tombol batal dan keluar</td>
            <td>Diterima</td>
     </tr>
     <tr>
       <td>Pengguna dapat melihat menu profil</td>
       <td>Negative test case</td>
       <td></td>
       <td>Pengguna tidak dapat mengedit biodata profil dengan menekan simbol pena karena tidak berfungsi</td>
       <td>Pengguna tidak dapat mengedit biodata profil</td>
       <td>Diterima</td>
     </tr>
     </tbody>
</table>

### Dokumentasi Project
    [Gsite](ipb.link/rplgsitehimagreto)
    [Trello](https://trello.com/b/WDlrdTpQ/rpl)
    [Asset Data](ipb.link/drivehimagretoapp)
    [Figma](https://www.figma.com/file/Bz3drsD0eTgVYBXVJ4nhH5/RPL?node-id=0%3A1)
 
### Saran untuk pengembangan selanjutnya
    Menyelesaikan fitur lainnya yang sudah dirancang sebelumnya seperti informasi lomba, beasiswa, tracking alumni, informasi dosen GFM, dan pasca kampus
    Memperbaiki UX design agar user nyaman menggunakannya
    Menambahkan alarm pengingat ketika menuju deadline tugas
 
