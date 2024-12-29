package entity;

import java.time.LocalDateTime;

public class LapHoaDon {
	private String maHoaDon, maKhachHang, maPhong, maNhanVien, tenPhong;
	private int phieuGiamGia, luongKhach;
	private LocalDateTime thoiGianThue, thoiGianKetThuc;
	private int soLuong;
	private float thanhTien;
	public LapHoaDon(String maHoaDon, String maKhachHang, String maPhong, String maNhanVien, String tenPhong,
			int phieuGiamGia, int luongKhach, LocalDateTime thoiGianThue, LocalDateTime thoiGianKetThuc, int soLuong,
			float thanhTien) {
		super();
		this.maHoaDon = maHoaDon;
		this.maKhachHang = maKhachHang;
		this.maPhong = maPhong;
		this.maNhanVien = maNhanVien;
		this.tenPhong = tenPhong;
		this.phieuGiamGia = phieuGiamGia;
		this.luongKhach = luongKhach;
		this.thoiGianThue = thoiGianThue;
		this.thoiGianKetThuc = thoiGianKetThuc;
		this.soLuong = soLuong;
		this.thanhTien = thanhTien;
	}
	public String getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public String getMaKhachHang() {
		return maKhachHang;
	}
	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}
	public String getMaPhong() {
		return maPhong;
	}
	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}
	public String getMaNhanVien() {
		return maNhanVien;
	}
	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}
	public String getTenPhong() {
		return tenPhong;
	}
	public void setTenPhong(String tenPhong) {
		this.tenPhong = tenPhong;
	}
	public int getPhieuGiamGia() {
		return phieuGiamGia;
	}
	public void setPhieuGiamGia(int phieuGiamGia) {
		this.phieuGiamGia = phieuGiamGia;
	}
	public int getLuongKhach() {
		return luongKhach;
	}
	public void setLuongKhach(int luongKhach) {
		this.luongKhach = luongKhach;
	}
	public LocalDateTime getThoiGianThue() {
		return thoiGianThue;
	}
	public void setThoiGianThue(LocalDateTime thoiGianThue) {
		this.thoiGianThue = thoiGianThue;
	}
	public LocalDateTime getThoiGianKetThuc() {
		return thoiGianKetThuc;
	}
	public void setThoiGianKetThuc(LocalDateTime thoiGianKetThuc) {
		this.thoiGianKetThuc = thoiGianKetThuc;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public float getThanhTien() {
		return thanhTien;
	}
	public void setThanhTien(float thanhTien) {
		this.thanhTien = thanhTien;
	}
	
	
	
}
