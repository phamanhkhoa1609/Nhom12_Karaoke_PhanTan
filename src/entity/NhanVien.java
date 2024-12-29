package entity;

public class NhanVien {
	private String IDNhanVien, TenNV, SDT;
	private int Tuoi;
	private String boPhan;
	private float luong;
	private String gioiTinh;

	public NhanVien(String iDNhanVien, String tenNV, String sDT, int tuoi, String boPhan, float luong,
			String gioiTinh) {
		super();
		IDNhanVien = iDNhanVien;
		TenNV = tenNV;
		SDT = sDT;
		Tuoi = tuoi;
		this.boPhan = boPhan;
		this.luong = luong;
		this.gioiTinh = gioiTinh;
	}

	public String getIDNhanVien() {
		return IDNhanVien;
	}

	public void setIDNhanVien(String iDNhanVien) {
		IDNhanVien = iDNhanVien;
	}

	public String getTenNV() {
		return TenNV;
	}

	public void setTenNV(String tenNV) {
		TenNV = tenNV;
	}

	public String getSDT() {
		return SDT;
	}

	public void setSDT(String sDT) {
		SDT = sDT;
	}

	public int getTuoi() {
		return Tuoi;
	}

	public void setTuoi(int tuoi) {
		Tuoi = tuoi;
	}

	public String getBoPhan() {
		return boPhan;
	}

	public void setBoPhan(String boPhan) {
		this.boPhan = boPhan;
	}

	public float getLuong() {
		return luong;
	}

	public void setLuong(float luong) {
		this.luong = luong;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

}
