package entities.enums;

public enum LoaiDichVu {
	NƯỚCNGỌT("Nước ngọt"),
	TRÁICÂY("Trái cây"),
	BIA("Bia"),
	RƯỢU("Rượu"),
	ĐỒKHÔ("Đồ khô"),
	BÁNH("Bánh"),
	KẸO("Kẹo");
	private String loaiDichVu;
	LoaiDichVu(String loaiDichVu) {
		// TODO Auto-generated constructor stub
		this.loaiDichVu = loaiDichVu;
	}
	public String getLoaiDichVu() {
		return loaiDichVu;
	}
	public void setLoaiDichVu(String loaiDichVu) {
		this.loaiDichVu = loaiDichVu;
	}
	
}
