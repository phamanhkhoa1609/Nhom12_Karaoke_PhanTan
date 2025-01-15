package entities.enums;

public enum HoiVien {
	ĐỒNG("Đồng"),
	BẠC("Bạc"),
	VÀNG("Vàng"),
	BẠCHKIM("Bạch kim"),
	KIMCƯONG("Kim cương");
	private String hoiVien;
	HoiVien(String hoiVien) {
		// TODO Auto-generated constructor stub
		this.hoiVien = hoiVien;
	}
	public String getHoiVien() {
		return hoiVien;
	}
	public void setHoiVien(String hoiVien) {
		this.hoiVien = hoiVien;
	}
}
