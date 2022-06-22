package Model;

import java.util.ArrayList;

public class Keranjang_Warung {
	
	private String id_pelanggan, id_produk;
	private int jumlah_produk;
	
	public Keranjang_Warung() {
		// TODO Auto-generated constructor stub
	}

	public Keranjang_Warung(String id_pelanggan, String id_produk, int jumlah_produk) {
		super();
		this.id_pelanggan = id_pelanggan;
		this.id_produk = id_produk;
		this.jumlah_produk = jumlah_produk;
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

	
}
