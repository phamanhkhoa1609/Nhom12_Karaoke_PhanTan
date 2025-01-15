package entities.enums;

public enum TrangThaiPhong {
	TRỐNG("Trống"), ĐANGSỬDỤNG("Đang sử dụng"),ĐANGBẢOTRÌ("Đang bảo trì");
	private String trangThaiPhong;

	TrangThaiPhong(String trangThaiPhong) {
		this.trangThaiPhong = trangThaiPhong;
	}

	public String getTrangThaiPhong() {
		return trangThaiPhong;
	}

	public void setTrangThaiPhong(String trangThaiPhong) {
		this.trangThaiPhong = trangThaiPhong;
	}
	
}
