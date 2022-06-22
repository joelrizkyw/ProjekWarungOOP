package Demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Database.Connect;
import Model.Penjualan_Warung;
import Model.Pelanggan_Warung;

public class Repository {

	ArrayList<Penjualan_Warung> penjualan = new ArrayList<>();
	Scanner scan = new Scanner(System.in);
	
	public void insert_penjualan() {
		String id_pelanggan, id_produk, tanggal_pembelian;
		Integer jumlah_produk;
		
		System.out.print("Input id_pelanggan: " );
		id_pelanggan = scan.nextLine();
		
		System.out.print("Input id_produk: " );
		id_produk = scan.nextLine();
		
		System.out.print("Input jumlah_produk: " );
		jumlah_produk = scan.nextInt(); scan.nextLine();
		
		System.out.print("Input tanggal_pembelian: " );
		tanggal_pembelian = scan.nextLine();
		
		String query = String.format("INSERT INTO `Penjualan`(`id_pelanggan`, `id_produk`, `jumlah_produk`, `tanggal_pembelian`) "
    			+ "VALUES ('%s','%s','%d','%s')", id_pelanggan, id_produk, jumlah_produk, tanggal_pembelian);
    	Connect.getConnection().executeUpdate(query);
    	
    	System.out.println("Penjualan berhasil dimasukkan\n");
		
		System.out.println("==================");
		System.out.println("DETAIL PENJUALAN BARU");
		System.out.println("==================");
		System.out.println("id_pelanggan: " + id_pelanggan);
		System.out.println("id_produk: " + id_produk);
		System.out.println("jumlah_produk: " + jumlah_produk);
		System.out.println("tanggal_pembelian: " + tanggal_pembelian);
		
		
		System.out.println("\nPress enter to continue...");
		scan.nextLine();
		
	}
	
	public void view_Penjualan()
	{
		load_transaction();
		
		System.out.println("\n==============");
		System.out.println("LIST PENJUALAN");
		System.out.println("==============");
		for (Penjualan_Warung penjualan_Warung : penjualan) {
			System.out.println("id_pelanggan: " + penjualan_Warung.getId_pelanggan());
			System.out.println("id_produk: " + penjualan_Warung.getId_produk());
			System.out.println("jumlah_produk: " + penjualan_Warung.getJumlah_produk());
			System.out.println("tanggal_pembelian: " + penjualan_Warung.getTgl_pembelian());
			System.out.println("");
		}
		
	}
	
	public void load_transaction()
	{
		penjualan.clear();
		
		String id_pelanggan, id_produk, tanggal_pembelian;
		Integer jumlah_produk;
		
		String query = "SELECT `id_pelanggan`, `id_produk`, `jumlah_produk`, `tanggal_pembelian`"
				+ "FROM `Penjualan`";
		ResultSet res = Connect.getConnection().executeQuery(query);
		
		try {
			while(res.next())
			{
				id_pelanggan = res.getString("id_pelanggan");
				id_produk = res.getString("id_produk");
				jumlah_produk = res.getInt("jumlah_produk");
				tanggal_pembelian = res.getString("tanggal_pembelian");
				penjualan.add(new Penjualan_Warung(id_pelanggan, id_produk, jumlah_produk, tanggal_pembelian));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
