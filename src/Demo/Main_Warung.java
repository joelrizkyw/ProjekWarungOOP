package Demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import Database.Connect;
import Model.Keranjang_Warung;
import Model.Pelanggan_Warung;
import Model.Penjual_Warung;
import Model.Penjualan_Warung;
import Model.Produk_Warung;

public class Main_Warung {
 
	ArrayList<Produk_Warung> listProduk = new ArrayList<>();
	ArrayList<Keranjang_Warung> listKeranjang = new ArrayList<>();
	Pelanggan_Warung data = null;
    Scanner scan = new Scanner(System.in);
    

	public void showMenuUtama() {

        System.out.println("Menu Manajemen Warung");
        System.out.println("===========================");
        System.out.println("1. Login Penjual");
        System.out.println("2. Login Pembeli");
        System.out.println("3. Exit");
        System.out.print(">> ");
    }

    public void showMenuPenjual() {

        System.out.println("Menu Penjual (Status: Logged In)");
        System.out.println("===========================");
        System.out.println("1. Tambah Produk");
        System.out.println("2. Edit Produk");
        System.out.println("3. Hapus Produk");
        System.out.println("4. Lihat Produk");
        System.out.println("5. Lihat Penjualan");
        System.out.println("6. Log Out Penjual");
        System.out.print(">> ");
    }

    public void loginPenjual() {

        String userInput = "";
        String passInput = "";
        int input = 0;
    	
    	String username = "";
    	String password = "";
    	
    	Penjual_Warung penjual = new Penjual_Warung();
    	Produk_Warung produk = new Produk_Warung();
    	Penjualan_Warung penjualan = new Penjualan_Warung();
    	
    	penjual.setDataPenjual();
    	
    	username = penjual.getDataPenjual().get(0).getUsername();
    	password = penjual.getDataPenjual().get(0).getPassword();
    	
    	System.out.println("Login Penjual");
        System.out.println("===========================");
    	
    	do {

            System.out.print("Input username [ admin ]: ");

            userInput = scan.nextLine();

        } while(!userInput.equals(username));

        do {

            System.out.print("Input password [ admin1234 ]: ");

            passInput = scan.nextLine();

        } while(!passInput.equals(password));
        
        System.out.println("Login Sukses !");
        System.out.println();
        
        do {
        	
        	showMenuPenjual();
        	
        	input = scan.nextInt();
        	scan.nextLine();
        	
        	switch(input) {
        	
	        	case 1:
	        		
	        		System.out.println();
	        		produk.tambahProduk();
	        		System.out.println();
	        		break;
	        	case 2:
	        		
	        		System.out.println();
	        		produk.ubahProduk();
	        		System.out.println();
	        		break;
	        	case 3:
	        		
	        		System.out.println();
	        		produk.hapusProduk();
	        		System.out.println();
	        		break;
	        	case 4:
	        		
	        		System.out.println();
	        		produk.lihatProduk();
	        		System.out.println();
	        		break;
	        	case 5:
	        		
	        		System.out.println();
	        		penjualan.tampilDataPenjualan();
	        		System.out.println();
	        		break;
        	}
        	
        	System.out.println();
        	
        } while(input != 6);
        
        System.out.println("Log out penjual sukses !");
        System.out.println();
    }
    
    public void lihatDaftarProduk()
	{
		
		listProduk.clear();
		listProduk = Produk_Warung.load_produk();
		
		System.out.println("Daftar Produk");
	    System.out.println("===============================================================================");
	    System.out.println("| No | ID produk | Nama Produk | Stok Produk | Harga Produk | Tanggal Expired |");
		
		if(listProduk.isEmpty())
		{
			System.out.println("List Produk Kosong");
			System.out.println("Tekan enter untuk lanjut..."); 
			scan.nextLine();
		}
		
		else
		{
			int i = 0;
			
			for(Produk_Warung  produk : listProduk)
			{
				System.out.printf("| %d | %s | %s | %d | %s | %s | \n", i+1, produk.getId_produk(), produk.getNama_produk(), produk.getStok_produk(), "Rp. " + produk.getHarga_produk(), produk.getTanggal_expired());
				i++;
			}
		}
		
		System.out.println();
    	
    	
    	int noProduk = 0;
    	String choice = "";
    	int stock = 0;
    	Keranjang_Warung keranjang = null;
    	Produk_Warung produk = null;
    	
    	do {
    		
    		do {
        		
        		System.out.print("Pilih no. produk (1 - " + listProduk.size() + "): ");
        		noProduk = Integer.parseInt(scan.nextLine());
        		
        	} while(noProduk < 1 || noProduk > listProduk.size());
    		
    		System.out.println();
    		
    		do {
    			
    			System.out.print("Jumlah produk? (min. 1): ");
    			stock = scan.nextInt();
    			scan.nextLine();
    			
    		} while(stock <= 0);
    		
    		produk = listProduk.get(noProduk - 1);
    		
    		System.out.print("Apa masih ingin menambah produk? (Ya/Tidak): ");
    		choice = scan.nextLine();
    		
    		listKeranjang.add(new Keranjang_Warung(data.getId_pelanggan(), produk.getId_produk(), stock));
    		
    		System.out.println();
    		
    	} while(choice.equals("Ya"));
    	
    	
    	System.out.println("Tekan enter untuk keluar ...");
    	scan.nextLine();
    	
	}	
    
    public void lihatKeranjang() {
    	
    	if(listKeranjang.isEmpty()) {
    		
    		System.out.println("Keranjang kosong...");
    		
    	}
    	
    	else {
    		
    		// no, nama produk, jumlah produk, harga
    		System.out.println("Keranjang anda");
    		System.out.println("==============");
    		System.out.println("| No | Nama Produk | Jumlah Produk | Harga");
    		
    		for(int i = 0; i < listKeranjang.size(); i++) {
    			
    			Keranjang_Warung data = listKeranjang.get(i);
    			Produk_Warung produk = Produk_Warung.getData(data.getId_produk());
    			
    			System.out.printf("| %d | %s | %d | %s |\n", i + 1, produk.getNama_produk(), data.getJumlah_produk(), "Rp. " + produk.getHarga_produk());
    		}
    	}
    	
    	System.out.println();
    	System.out.println("Tekan enter untuk keluar ...");
    	scan.nextLine();
    }
    
    public void pembayaran() {
    	
    	if(listKeranjang.isEmpty()) {
    		
    		System.out.println("Keranjang kosong...");
    		
    	}
    	
    	else {
    		
    		// no, nama produk, jumlah produk, harga
    		System.out.println("Keranjang anda");
    		System.out.println("==============");
    		System.out.println("| No | Nama Produk | Jumlah Produk | Harga");
    		
    		double totalHarga = 0;
    		
    		for(int i = 0; i < listKeranjang.size(); i++) {
    			
    			Keranjang_Warung data = listKeranjang.get(i);
    			Produk_Warung produk = Produk_Warung.getData(data.getId_produk());
    			
    			System.out.printf("| %d | %s | %d | %s |\n", i + 1, produk.getNama_produk(), data.getJumlah_produk(), "Rp. " + produk.getHarga_produk());
    			
    			totalHarga = totalHarga + (data.getJumlah_produk() * produk.getHarga_produk());
    		}
    		
    		System.out.println();
    		System.out.printf("Total Harga: %s\n", "Rp. " + totalHarga);
    		
    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
            Date date = new Date();
        	
        	System.out.println();
        	
        	int money = 0;
        	
        	do {
        		
        		System.out.print("Masukkan uang anda: Rp. ");
        		money = scan.nextInt();
        		
        		if(money < totalHarga) {
        			
        			System.out.println("Uang anda kurang !!!");
        		}
        		
        		else {
        			
        			break;
        		}
        		
        		System.out.println();
        		
        	} while(true);
        	
        	System.out.println();
        	
        	System.out.println("Uang kembalian anda: Rp. " + (money - totalHarga));
        	
        	Penjualan_Warung jual = new Penjualan_Warung();
            	
            for(int i = 0; i < listKeranjang.size(); i++) {
            		
            	Keranjang_Warung keranjang = listKeranjang.get(i);
            		
            	jual.setId_pelanggan(keranjang.getId_pelanggan());
            	jual.setId_produk(keranjang.getId_produk());
            	jual.setJumlah_produk(keranjang.getJumlah_produk());
            	jual.setTgl_pembelian(formatter.format(date));
            		
            	jual.insert_penjualan();
            }
            
            System.out.println();
            System.out.println("Terima kasih sudah berbelanja !!!");
    	}
    }
    
    
    public void loginPembeli()
    {
    	String nama_pembeli = "";
    	
    	do {
			
    		System.out.print("Masukkan nama: ");
			nama_pembeli = scan.nextLine();
			
		} while (nama_pembeli.length() <= 0);
    	
    	data = new Pelanggan_Warung();
    	
    	data.setNama_pelanggan(nama_pembeli);
    	data.tambahPelanggan();
    	
    	// tampil menu pembeli
    	
    	int choice = 0;
    	
    	do {
    		
    		System.out.println();
        	System.out.println("Selamat datang, " + data.getNama_pelanggan());
        	System.out.println("================");
        	System.out.println("1. Lihat Daftar Produk");
        	System.out.println("2. Lihat Keranjang");
        	System.out.println("3. Pembayaran");
        	System.out.println("4. Exit Toko");
        	System.out.print(">> ");
        	
        	choice = scan.nextInt();
        	scan.nextLine();
        	
        	switch(choice) {
        	
	        	case 1:
	        		
	        		System.out.println();
	        		lihatDaftarProduk();
	        		System.out.println();
	        		break;
	        	case 2:
	        		
	        		System.out.println();
	        		lihatKeranjang();
	        		System.out.println();
	        		break;
	        	case 3:
	        		
	        		System.out.println();
	        		pembayaran();
	        		System.out.println();
	        		break;
        	}
    		
    	} while(choice != 4);
    	
    	
    	
//    	System.out.println("\n=============");
//    	System.out.println("Data anda:");
//    	System.out.println("=============");
//    	// System.out.println("ID Pembeli: " + generate_pembeli_id());
//    	System.out.println("Nama: " + nama_pembeli);
//    	
//    	lihat_pembeli();
    }
    
    public Main_Warung() {

        int input = 0;

    	do {
            
			showMenuUtama();

            input = scan.nextInt();
            scan.nextLine();

            switch(input) {

                case 1:
                    
                    System.out.println();
                    loginPenjual();
                    break;
                case 2:
                	
                	System.out.println();
                    loginPembeli();
                    break;
            }
            
            System.out.println();

		} while (input != 3);
        
        scan.close();
    }
   

    public static void main(String[] args) {
    	
    	// Test komentar buat github
        new Main_Warung();
    }
}
