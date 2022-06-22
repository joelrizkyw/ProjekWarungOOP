package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import Database.Connect;


public class Produk_Warung {

	static ArrayList<Produk_Warung> produkList = new ArrayList<>();
	Scanner scan = new Scanner(System.in);
	Connect con = Connect.getConnection();

	private String id_produk;
	private String nama_produk;
	private int stok_produk;
	private double harga_produk;
	private String tanggal_expired;
  
	
	
	public Produk_Warung() {
		
	}


	public Produk_Warung(String id_produk, String nama_produk, int stok_produk, double harga_produk,
			String tanggal_expired) {
		super();
		this.id_produk = id_produk;
		this.nama_produk = nama_produk;
		this.stok_produk = stok_produk;
		this.harga_produk = harga_produk;
		this.tanggal_expired = tanggal_expired;
	}

	public void setProdukList(ArrayList<Produk_Warung> produkList) {
		this.produkList = produkList;
	}

	public String getId_produk() {
		return id_produk;
	}

	public void setId_produk(String id_produk) {
		this.id_produk = id_produk;
	}

	public String getNama_produk() {
		return nama_produk;
	}

	public void setNama_produk(String nama_produk) {
		this.nama_produk = nama_produk;
	}

	public int getStok_produk() {
		return stok_produk;
	}

	public void setStok_produk(int stok_produk) {
		this.stok_produk = stok_produk;
	}

	public double getHarga_produk() {
		return harga_produk;
	}

	public void setHarga_produk(double harga_produk) {
		this.harga_produk = harga_produk;
	}

	public String getTanggal_expired() {
		return tanggal_expired;
	}

	public void setTanggal_expired(String tanggal_expired) {
		this.tanggal_expired = tanggal_expired;
	}
	
	public String generateId() {
		
	  	  String next_id_produk = null;
	  	  
	  	  String query = "SELECT id_produk FROM produk \r\n" + 
	  	  	     "ORDER BY id_produk DESC LIMIT 1";
	  	  Connect con = Connect.getConnection();
	  	  ResultSet res = con.executeQuery(query);
	  	  
	  	  try {
	  	  
	  	   if(res.next()) {
	  		   next_id_produk = res.getString("id_produk");
	  		   Integer idInt = Integer.parseInt(next_id_produk.substring(1)) + 1; // K001
	  		   next_id_produk = String.format("P%03d", idInt);
	  	   }else {
	  		   next_id_produk = "P001";
	  	   }
	  	   
	  	  } catch (SQLException e) {
	  	   // TODO Auto-generated catch block
	  	   e.printStackTrace();
	  	  }
	  	  
	  	  return next_id_produk;
	}
    
	public void tambahProduk(){
		
	   String nama_produk;
	   int stok_produk;
	   double harga_produk;
	   String tanggal_expired;
	   
	   String tanggalPattern = "^\\d{2}/\\d{2}/\\d{4}$";
	   
	   System.out.println("Tambah Produk");
       System.out.println("===========================");
	   	   
	   do{
		   System.out.print("Masukkan Nama Produk: ");
		   nama_produk = scan.nextLine();
		   
	   } while(nama_produk.length() <= 0 );

	   do{
		   System.out.print("Masukkan Stok Produk: ");
		   stok_produk = scan.nextInt();
		   scan.nextLine();
		   
	   } while(stok_produk <= 0);

	   do{
		   System.out.print("Masukkan Harga Produk: ");
		   harga_produk = scan.nextDouble();
		   scan.nextLine();
		   
	   } while(harga_produk <= 0);

	   do{
		   System.out.print("Masukkan Tanggal Expired Produk [ - | dd/mm/yyyy ]: ");
		   tanggal_expired = scan.nextLine();
		   
	   } while(!tanggal_expired.equals("-") && !tanggal_expired.matches(tanggalPattern));
       
	   setId_produk(generateId());
	   setNama_produk(nama_produk);
	   setStok_produk(stok_produk);
	   setHarga_produk(harga_produk);
	   setTanggal_expired(tanggal_expired);
	   
	   System.out.println();
	   insert();
		   
	   System.out.print("Produk Berhasil Ditambahkan");
	}
	
	public void insert() {
		Connect con = Connect.getConnection();
		
		String query  = String.format("INSERT INTO produk VALUES ('%s', '%s', '%d' , '%f', '%s' )", getId_produk(), getNama_produk(), getStok_produk(), getHarga_produk(), getTanggal_expired());
		con.executeUpdate(query);
	}
	
	public void ubahProduk() {
		
		ArrayList<Produk_Warung> listProduk = new ArrayList<>();
		
		listProduk.clear();
		listProduk = load_produk();
		
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
		
		do {
			
			System.out.print("Pilih no. produk yang ingin diubah datanya (1 - " + listProduk.size() + "): ");
			noProduk = scan.nextInt();
			scan.nextLine();
			
		} while(noProduk < 1 || noProduk > listProduk.size());
		
		System.out.println();
		
		// data yang terpilih
		int index = noProduk - 1;
		Produk_Warung choosen = listProduk.get(index);
		
		String nama_produk = "";
		int stok_produk = 0;
		double harga_produk = 0;
		String tanggal_expired = "";
		
		String tanggalPattern = "^\\d{2}/\\d{2}/\\d{4}$";
		
		do {
			System.out.print("Masukkan Nama Produk: ");
			nama_produk = scan.nextLine();
			   
		} while(nama_produk.length() <= 0 );

		do{
			System.out.print("Masukkan Stok Produk: ");
			stok_produk = scan.nextInt();
			scan.nextLine();
			   
		} while(stok_produk <= 0);

		do{
			System.out.print("Masukkan Harga Produk: ");
			harga_produk = scan.nextDouble();
			scan.nextLine();
			   
		} while(harga_produk <= 0);

		do{
			System.out.print("Masukkan Tanggal Expired Produk [ - | dd/mm/yyyy ]: ");
			tanggal_expired = scan.nextLine();
			   
		} while(!tanggal_expired.equals("-") && !tanggal_expired.matches(tanggalPattern));
		
		Connect con = Connect.getConnection();
		String query = String.format("UPDATE `produk` SET `nama_produk`='%s',`stok_produk`='%d',`harga_produk`='%f',`tanggal_expired`='%s' WHERE `id_produk`='%s'", 
			nama_produk, stok_produk, harga_produk, tanggal_expired, choosen.getId_produk());
	
		con.executeUpdate(query);
		
		System.out.println("Produk berhasil diubah !");
		
		listProduk.clear();
	}
	
	public void hapusProduk() {
		
		ArrayList<Produk_Warung> listProduk = new ArrayList<>();
		
		listProduk.clear();
		listProduk = load_produk();
		
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
		
		do {
			
			System.out.print("Pilih no. produk yang ingin dihapus datanya (1 - " + listProduk.size() + "): ");
			noProduk = scan.nextInt();
			scan.nextLine();
			
		} while(noProduk < 1 || noProduk > listProduk.size());
		
		System.out.println();
		
		// data yang terpilih
		int index = noProduk - 1;
		Produk_Warung choosen = listProduk.get(index);
		
		Connect con = Connect.getConnection();
		String query = String.format("DELETE FROM produk WHERE id_produk = '%s'", choosen.getId_produk());
		con.executeUpdate(query);
		
		System.out.println("Produk berhasil dihapus !");
		
		listProduk.clear();
		
	}
	
	public void lihatProduk() {
		
		ArrayList<Produk_Warung> listProduk = new ArrayList<>();
		
		listProduk.clear();
		listProduk = load_produk();
		
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
				System.out.printf("| %d | %s | %s | %d | %s | %s | \n", i+1, produk.getId_produk(), produk.getNama_produk(), produk.getStok_produk(), "Rp. " +  produk.getHarga_produk(), produk.getTanggal_expired());
				i++;
			}
		}
		
		System.out.println();
		
		System.out.println("Tekan enter untuk keluar ...");
    	scan.nextLine();
    	
    	listProduk.clear();
	}
	
	public static ArrayList<Produk_Warung> load_produk()
	{
		String id_produk;
		String nama_produk;
		int stok_produk;
		double harga_produk;
		String tanggal_expired; 
		
		String query = "SELECT * FROM produk";
		
		Connect con = Connect.getConnection();
    	ResultSet rs = con.executeQuery(query);
    	
    	try {
    		while(rs.next()) {
    			id_produk = rs.getString("id_produk");
    		    nama_produk = rs.getString("nama_produk");
    		    stok_produk = rs.getInt("stok_produk");
    		    harga_produk = rs.getDouble("harga_produk");
    		    tanggal_expired = rs.getString("tanggal_expired");
    		    
    		    produkList.add(new Produk_Warung(id_produk, nama_produk, stok_produk, harga_produk, tanggal_expired));
    		}
    		
    		return produkList;
    	}catch (SQLException e) {
    	    e.printStackTrace();
    	}
    	
    	return null;
	}
	
	public static Produk_Warung getData(String idProduk) {
		
		String query = String.format("SELECT * FROM produk WHERE id_produk='%s'", idProduk);
		Produk_Warung produk = new Produk_Warung();
		
		Connect con = Connect.getConnection();
    	ResultSet rs = con.executeQuery(query);
    	
    	try {
    		while(rs.next()) {
    			
    			produk.setId_produk(rs.getString("id_produk"));
    			produk.setNama_produk(rs.getString("nama_produk"));
    			produk.setStok_produk(rs.getInt("stok_produk"));
    			produk.setHarga_produk(rs.getDouble("harga_produk"));
    			produk.setTanggal_expired(rs.getString("tanggal_expired"));
    		}
    		
    		return produk;
    		
    	}catch (SQLException e) {
    	    e.printStackTrace();
    	}
    	
    	return null;
	}
	
	
	public void lihat_penjual()
	{
		System.out.println("Lihat Penjualan");
	    System.out.println("===========================");
		
		if(produkList.isEmpty())
		{
			System.out.println("List Produk Kosong");
			System.out.println("Tekan enter untuk lanjut..."); 
			scan.nextLine();
		}
		
		else
		{
			
			for(int i=0; i < produkList.size(); i++)
			{
				System.out.println("ID Produk: " + produkList.get(i).getId_produk());
				System.out.println("Nama Produk: " + produkList.get(i).getNama_produk());
				System.out.println("Stok Produk: " + produkList.get(i).getStok_produk());
				System.out.println("Harga Produk: " + produkList.get(i).getHarga_produk());
				System.out.println("Tanggal Expired: " + produkList.get(i).getTanggal_expired());
				System.out.println();
			}
		}
	}
}
