# Aplikasi Nota Pro

Aplikasi nota yang kaya dengan ciri untuk Android, direka untuk membantu anda mencatat idea, pemikiran, dan tugasan dengan mudah.

## Ciri-ciri

*   **Editor Teks Kaya:** Formatkan nota anda dengan teks tebal, condong, senarai berbutir, dan banyak lagi.
*   **Sokongan Imej & GIF:** Tambahkan imej dan GIF untuk menjadikan nota anda lebih visual dan menarik.
*   **Pilihan Penyesuaian:**
    *   **Palet Warna:** Pilih daripada 99 warna latar belakang yang berbeza untuk nota anda.
    *   **Gaya Grid:** Pilih antara latar belakang bergaris, bertitik, atau kosong untuk disesuaikan dengan gaya penulisan anda.
*   **Penyusunan Mudah:** Susun nota anda dengan tag dan folder.
*   **Pangkalan Data Tempatan:** Semua nota anda disimpan dengan selamat pada peranti anda menggunakan pangkalan data Room.

## Teknologi yang Digunakan

*   **Kotlin:** Bahasa pengaturcaraan utama.
*   **Android Jetpack:**
    *   **Room:** Untuk pengurusan pangkalan data.
    *   **ViewModel:** Untuk mengurus data berkaitan UI.
    *   **LiveData/Flow:** Untuk membina aplikasi yang reaktif.
    *   **Navigation Component:** Untuk mengendalikan navigasi dalam aplikasi.
*   **Coroutines:** Untuk pengurusan tugas latar belakang.
*   **Material Components:** Untuk reka bentuk UI yang moden dan konsisten.

## Pemasangan

Untuk membina dan menjalankan projek ini, anda memerlukan Android Studio Arctic Fox | 2020.3.1 atau lebih baharu.

### Langkah-langkah

1.  **Klon Repositori**

    Buka terminal atau Git Bash dan klon repositori ini ke mesin tempatan anda:
    ```bash
    git clone https://github.com/USERNAME/note-app-pro.git
    ```

2.  **Buka Projek dalam Android Studio**

    *   Lancarkan Android Studio.
    *   Pilih **Open an Existing Project**.
    *   Navigasi ke direktori tempat anda mengklon repositori dan pilihnya.
    *   Android Studio akan mengambil sedikit masa untuk menyegerakkan dan membina projek menggunakan Gradle.

3.  **Sediakan Emulator atau Peranti**

    *   **Emulator:**
        *   Dalam Android Studio, pergi ke **Tools > AVD Manager**.
        *   Klik **Create Virtual Device...**.
        *   Pilih definisi perkakasan (contoh: Pixel 5) dan klik **Next**.
        *   Pilih imej sistem (disyorkan API 30 atau lebih tinggi) dan klik **Next**.
        *   Sahkan konfigurasi dan klik **Finish**.
    *   **Peranti Fizikal:**
        *   Dayakan **Developer Options** dan **USB Debugging** pada peranti Android anda.
        *   Sambungkan peranti ke komputer anda melalui USB.
        *   Pilih **File Transfer** atau **PTP** pada peranti anda.

4.  **Jalankan Aplikasi**

    *   Pilih emulator atau peranti anda yang disambungkan dari menu lungsur turun peranti di bahagian atas tetingkap Android Studio.
    *   Klik butang **Run 'app'** (ikon segi tiga hijau) atau gunakan pintasan `Shift + F10`.
    *   Aplikasi akan dipasang dan dilancarkan pada peranti/emulator yang dipilih.

## Sumbangan

Sumbangan adalah dialu-alukan! Sila buat *pull request* untuk sebarang penambahbaikan atau pembetulan pepijat.
