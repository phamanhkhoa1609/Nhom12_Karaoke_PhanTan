package entity;

public class DatDichVu {
	private String maHoaDon, maDichVu, maKhachHang, tenDichVu, tenKhachHang;
	private int soLuong;
	private float gia;
	
	public DatDichVu() {
		super();
	}

	public DatDichVu(String maHoaDon, String maDichVu, String maKhachHang, String tenDichVu, String tenKhachHang,
			int soLuong, float gia) {
		super();
		this.maHoaDon = maHoaDon;
		this.maDichVu = maDichVu;
		this.maKhachHang = maKhachHang;
		this.tenDichVu = tenDichVu;
		this.tenKhachHang = tenKhachHang;
		this.soLuong = soLuong;
		this.gia = gia;
	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public String getMaDichVu() {
		return maDichVu;
	}

	public void setMaDichVu(String maDichVu) {
		this.maDichVu = maDichVu;
	}

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public String getTenDichVu() {
		return tenDichVu;
	}

	public void setTenDichVu(String tenDichVu) {
		this.tenDichVu = tenDichVu;
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public float getGia() {
		return gia;
	}

	public void setGia(float gia) {
		this.gia = gia;
	}
	
	public float thanhTien() {
		float thanhTien = 0;
		thanhTien = this.soLuong * this.gia;
		return thanhTien;
	}
	
}
