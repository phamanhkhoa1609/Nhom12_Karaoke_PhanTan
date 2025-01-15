package entities.enums;

public enum LoaiNguoiDung {
	QUẢNLÝ("Quản lý"),
	NHÂNVIÊN("Nhân viên"),
	KHÁCHHÀNG("Khách hàng");
	private String loaiNguoiDung;
	LoaiNguoiDung(String loaiNguoiDung) {
		// TODO Auto-generated constructor stub
		this.loaiNguoiDung = loaiNguoiDung;
	}
	public String getLoaiNguoiDung() {
		return loaiNguoiDung;
	}
	public void setLoaiNguoiDung(String loaiNguoiDung) {
		this.loaiNguoiDung = loaiNguoiDung;
	}
	
}
