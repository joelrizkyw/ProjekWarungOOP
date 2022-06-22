package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.Connect;

public class Pelanggan_Warung {
	
	private String id_pelanggan, nama_pelanggan;

	public Pelanggan_Warung() {
		// TODO Auto-generated constructor stub
	}

	public Pelanggan_Warung(String id_pelanggan, String nama_pelanggan) {
		super();
		this.id_pelanggan = id_pelanggan;
		this.nama_pelanggan = nama_pelanggan;
	}
	
	public String getId_pelanggan() {
		return id_pelanggan;
	}

	public void setId_pelanggan(String id_pelanggan) {
		this.id_pelanggan = id_pelanggan;
	}

	public String getNama_pelanggan() {
		return nama_pelanggan;
	}

	public void setNama_pelanggan(String nama_pelanggan) {
		this.nama_pelanggan = nama_pelanggan;
	}
	
	public static String generateId() {
		
	  	  String next_id_pelanggan = null;
	  	  
	  	  String query = "SELECT id_pelanggan FROM pelanggan \r\n" + 
	  	  	     "ORDER BY id_pelanggan DESC LIMIT 1";
	  	  Connect con = Connect.getConnection();
	  	  ResultSet res = con.executeQuery(query);
	  	  
	  	  try {
	  	  
	  	   if(res.next()) {
	  		   next_id_pelanggan = res.getString("id_pelanggan");
	  		   Integer idInt = Integer.parseInt(next_id_pelanggan.substring(1)) + 1; // K001
	  		   next_id_pelanggan = String.format("K%03d", idInt);
	  	   }
	  	   
	  	   else {
	  		   next_id_pelanggan = "K001";
	  	   }
	  	   
	  	  } catch (SQLException e) {
	  	   // TODO Auto-generated catch block
	  	   e.printStackTrace();
	  	  }
	  	  
	  	  return next_id_pelanggan;
	}
	
	public void tambahPelanggan() {
		
		setId_pelanggan(generateId());
		
		Connect c = Connect.getConnection();
    	String query = String.format("insert into pelanggan values ('%s', '%s')", getId_pelanggan(), getNama_pelanggan());
    	c.executeUpdate(query);
	}
}
