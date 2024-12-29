package entity;

import java.time.LocalDateTime;

public class DatPhong {
	private String maHoaDon, maPhong, tenPhong, maKhachHang, tenKhachHang, maNhanVien;
	private LocalDateTime tgianDat;
	private int soLuongKhach;
	private String ghiChu;
	
	
	public DatPhong(String maHoaDon, String maPhong, String tenPhong, String maKhachHang, String tenKhachHang,
			String maNhanVien, LocalDateTime tgianDat, int soLuongKhach, String ghiChu) {
		super();
		this.maHoaDon = maHoaDon;
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
		this.maKhachHang = maKhachHang;
		this.tenKhachHang = tenKhachHang;
		this.maNhanVien = maNhanVien;
		this.tgianDat = tgianDat;
		this.soLuongKhach = soLuongKhach;
		this.ghiChu = ghiChu;
	}
	
	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getMaPhong() {
		return maPhong;
	}
	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}
	public int getSoLuongKhach() {
		return soLuongKhach;
	}
	public void setSoLuongKhach(int soLuongKhach) {
		this.soLuongKhach = soLuongKhach;
	}
	public String getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public String getTenPhong() {
		return tenPhong;
	}
	public void setTenPhong(String tenPhong) {
		this.tenPhong = tenPhong;
	}
	public String getMaKhachHang() {
		return maKhachHang;
	}
	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}
	public String getTenKhachHang() {
		return tenKhachHang;
	}
	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}
	public LocalDateTime getTgianDat() {
		return tgianDat;
	}
	public void setTgianDat(LocalDateTime tgianDat) {
		this.tgianDat = tgianDat;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	
	
}
