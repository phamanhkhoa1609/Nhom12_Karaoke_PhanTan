package entities.enums;

public enum TrangThaiHoaDon {
	CHƯATHANHTOÁN("Chưa thanh toán"),ĐÃTHANHTOÁN("Đã thanh toán"),ĐÃHẾTHẠN("Đã hết hạn");
	private String trangThaiHoaDon;

	TrangThaiHoaDon(String trangThaiHoaDon) {
		this.trangThaiHoaDon = trangThaiHoaDon;
	}

	public String getTrangThaiHoaDon() {
		return trangThaiHoaDon;
	}

	public void setTrangThaiHoaDon(String trangThaiHoaDon) {
		this.trangThaiHoaDon = trangThaiHoaDon;
	}
	
}
