package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Database.Connect;

public class Penjualan_Warung {
	
	private String id_pelanggan, id_produk, tgl_pembelian;
	private int jumlah_produk;
	
	ArrayList<Penjualan_Warung> listPenjualan = new ArrayList<>();
	Scanner scan = new Scanner(System.in);
	
    public Penjualan_Warung() {
		
	}

	public Penjualan_Warung(String id_pelanggan, String id_produk, int jumlah_produk, String tgl_pembelian) {
		super();
		this.id_pelanggan = id_pelanggan;
		this.id_produk = id_produk;
		this.jumlah_produk = jumlah_produk;
		this.tgl_pembelian = tgl_pembelian;
	}

	public String getId_pelanggan() {
		return id_pelanggan;
	}

	public void setId_pelanggan(String id_pelanggan) {
		this.id_pelanggan = id_pelanggan;
	}

	public String getId_produk() {
		return id_produk;
	}

	public void setId_produk(String id_produk) {
		this.id_produk = id_produk;
	}

	public int getJumlah_produk() {
		return jumlah_produk;
	}

	public void setJumlah_produk(int jumlah_produk) {
		this.jumlah_produk = jumlah_produk;
	}

	public String getTgl_pembelian() {
		return tgl_pembelian;
	}

	public void setTgl_pembelian(String tgl_pembelian) {
		this.tgl_pembelian = tgl_pembelian;
	}
	
	public void insert_penjualan() {
//		String id_pelanggan, id_produk, tanggal_pembelian;
//		Integer jumlah_produk;
//		
//		System.out.print("Input id_pelanggan: " );
//		id_pelanggan = scan.nextLine();
//		
//		System.out.print("Input id_produk: " );
//		id_produk = scan.nextLine();
//		
//		System.out.print("Input jumlah_produk: " );
//		jumlah_produk = scan.nextInt(); scan.nextLine();
//		
//		System.out.print("Input tanggal_pembelian: " );
//		tanggal_pembelian = scan.nextLine();
		
		String query = String.format("INSERT INTO `Penjualan`(`id_pelanggan`, `id_produk`, `jumlah_produk`, `tanggal_pembelian`) "
    			+ "VALUES ('%s','%s','%d','%s')", getId_pelanggan(), getId_produk(), getJumlah_produk()
    			, getTgl_pembelian());
    	Connect.getConnection().executeUpdate(query);
    	
//    	System.out.println("Penjualan berhasil dimasukkan\n");
//		
//		System.out.println("==================");
//		System.out.println("DETAIL PENJUALAN BARU");
//		System.out.println("==================");
//		System.out.println("id_pelanggan: " + id_pelanggan);
//		System.out.println("id_produk: " + id_produk);
//		System.out.println("jumlah_produk: " + jumlah_produk);
//		System.out.println("tanggal_pembelian: " + tanggal_pembelian);
//		
//		
//		System.out.println("\nPress enter to continue...");
//		scan.nextLine();
		
	}
	
	public int cekBulan(String bulan) {
		
		int numMonth = 0;
		
		switch(bulan) {
		
			case "Januari":
				
				numMonth = 1;
				break;
			case "Februari":
				
				numMonth = 2;
				break;
			case "Maret":
				
				numMonth = 3;
				break;
			case "April":
				
				numMonth = 4;
				break;
			case "Mei":
				
				numMonth = 5;
				break;
			case "Juni":
				
				numMonth = 6;
				break;
			case "Juli":
				
				numMonth = 7;
				break;
			case "Agustus":
				
				numMonth = 8;
				break;
			case "September":
				
				numMonth = 9;
				break;
			case "Oktober":
				
				numMonth = 10;
				break;
			case "November":
				
				numMonth = 11;
				break;
			case "Desember":
				
				numMonth = 12;
				break;
		}
		
		return numMonth;
	}
	
	public ArrayList<Penjualan_Warung> getDataByMonth(int numMonth) {
		
		ArrayList<Penjualan_Warung> foundData = new ArrayList<Penjualan_Warung>();
		
		String id_produk;
		String id_pelanggan;
		int jumlahProduk;
		String tanggal_pembelian;
		
		Connect con = Connect.getConnection();
		String query = "SELECT * FROM penjualan";
		ResultSet rs = con.executeQuery(query);
		
		try {
			
			while(rs.next()) {
				
				// 31/09/2020
				int monthDb = Integer.parseInt(rs.getString("tanggal_pembelian").substring(3, 5));
				
				if(monthDb == numMonth) {
					
					id_produk = rs.getString("id_produk");
					id_pelanggan = rs.getString("id_pelanggan");
					jumlahProduk = rs.getInt("jumlah_produk");
					tanggal_pembelian = rs.getString("tanggal_pembelian");
					
					foundData.add(new Penjualan_Warung(id_pelanggan, id_produk, jumlahProduk, tanggal_pembelian));
				}
			}
			
			return foundData;
		}
		
		catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void tampilDataPenjualan()
	{
		
		ArrayList<Penjualan_Warung> listData = new ArrayList<>();
		
		listData.clear();
		listData = loadPenjualan();
		
		if(!listData.isEmpty()) {
			
			System.out.println("\n==============");
			System.out.println("List Penjualan");
			System.out.println("==============");
			
			System.out.println("| No | Nama Produk | ID Pelanggan | Jumlah Produk | Total Harga |");
			
			int i = 0;
			
			for (Penjualan_Warung item : listData) {
				
				Produk_Warung dataProduk = Produk_Warung.getData(item.getId_produk());
				
				System.out.printf("| %d | %s | %s | %s | %s |\n", i + 1, dataProduk.getNama_produk(), item.getId_pelanggan(),
					item.getJumlah_produk(), "Rp. " + (dataProduk.getHarga_produk() * item.getJumlah_produk()));
				
				i++;
			}
		}
		
		System.out.println();
		System.out.println("List Penjualan kosong...");
		System.out.println("Tekan enter untuk lanjut..."); 
		scan.nextLine();
		
		listData.clear();
	}
	
	public ArrayList<Penjualan_Warung> loadPenjualan()
	
	{
		listPenjualan.clear();
		
		String id_pelanggan, id_produk, tanggal_pembelian;
		Integer jumlah_produk;
		
		String query = "SELECT `id_pelanggan`, `id_produk`, `jumlah_produk`, `tanggal_pembelian`"
				+ "FROM `penjualan`";
		ResultSet res = Connect.getConnection().executeQuery(query);
		
		try {
			while(res.next())
			{
				id_pelanggan = res.getString("id_pelanggan");
				id_produk = res.getString("id_produk");
				jumlah_produk = res.getInt("jumlah_produk");
				tanggal_pembelian = res.getString("tanggal_pembelian");
				
				listPenjualan.add(new Penjualan_Warung(id_pelanggan, id_produk, jumlah_produk, tanggal_pembelian));
			}
			
			return listPenjualan;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
