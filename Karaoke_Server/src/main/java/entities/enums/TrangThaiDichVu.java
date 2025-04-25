package entities.enums;

public enum TrangThaiDichVu {
	KINHDOANH("Kinh doanh"),NGỪNGKINHDOANH("Ngừng kinh doanh");
	TrangThaiDichVu(String trangThaiDichVu) {
		// TODO Auto-generated constructor stub
		this.trangThaiDichVu = trangThaiDichVu;
	}
	private String trangThaiDichVu;
	public String getTrangThaiDichVu() {
		return trangThaiDichVu;
	}
	public void setTrangThaiDichVu(String trangThaiDichVu) {
		this.trangThaiDichVu = trangThaiDichVu;
	}
	
}
