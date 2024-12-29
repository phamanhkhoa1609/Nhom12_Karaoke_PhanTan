package entity;

public class KhachHang {
	private String maKH, tenKH, trangThai, sDT;
	private int tuoi;
	private String gioiTinh;
	public KhachHang() {
		super();
	}
	public KhachHang(String maKH, String tenKH, int tuoi, String gioiTinh, String sDT, String trangThai) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.tuoi = tuoi;
		this.gioiTinh = gioiTinh;
		this.sDT = sDT;
		this.trangThai = trangThai;
	}
	public String getMaKH() {
		return maKH;
	}
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
	public String getTenKH() {
		return tenKH;
	}
	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public String getsDT() {
		return sDT;
	}
	public void setsDT(String sDT) {
		this.sDT = sDT;
	}
	public int getTuoi() {
		return tuoi;
	}
	public void setTuoi(int tuoi) {
		this.tuoi = tuoi;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	
}
