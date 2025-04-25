package entities.enums;

public enum TrangThaiNhanVien {
	ĐANGLÀM("Đang làm"),NGHỈ("Nghỉ");
	private String trangThaiNhanVien;

	TrangThaiNhanVien(String trangThaiNhanVien) {
		this.trangThaiNhanVien = trangThaiNhanVien;
	}

	public String getTrangThaiNhanVien() {
		return trangThaiNhanVien;
	}

	public void setTrangThaiNhanVien(String trangThaiNhanVien) {
		this.trangThaiNhanVien = trangThaiNhanVien;
	}
	
}
