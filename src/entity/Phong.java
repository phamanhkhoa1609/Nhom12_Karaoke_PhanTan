package entity;
//idphong, tenphong, thietbi, tinhtrang, loaiphong, ghichu, giaphong
public class Phong {
	private String maPhong, tenPhong, loaiPhong;
	private String thietBi, tinhTrang, ghiChu;
	private float giaPhong;
	public Phong() {
		super();
	}
	public Phong(String maPhong, String tenPhong, String loaiPhong, String thietBi, String tinhTrang, String ghiChu, float giaPhong) {
		super();
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
		this.loaiPhong = loaiPhong;
		this.thietBi = thietBi;
		this.tinhTrang = tinhTrang;
		this.ghiChu = ghiChu;
		this.giaPhong = giaPhong;
	}
	public String getMaPhong() {
		return maPhong;
	}
	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}
	public String getTenPhong() {
		return tenPhong;
	}
	public void setTenPhong(String tenPhong) {
		this.tenPhong = tenPhong;
	}
	public String getLoaiPhong() {
		return loaiPhong;
	}
	public void setLoaiPhong(String loaiPhong) {
		this.loaiPhong = loaiPhong;
	}
	public String getThietBi() {
		return thietBi;
	}
	public void setThietBi(String thietBi) {
		this.thietBi = thietBi;
	}
	public String getTinhTrang() {
		return tinhTrang;
	}
	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public float getGiaPhong() {
		return giaPhong;
	}
	public void setGiaPhong(float giaPhong) {
		this.giaPhong = giaPhong;
	}
	
}
