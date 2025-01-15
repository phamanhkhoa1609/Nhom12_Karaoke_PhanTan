package entities.enums;

public enum GioiTinh {
	NAM("Nam"),
	NỮ("Nữ");
	GioiTinh(String gioiTinh) {
		// TODO Auto-generated constructor stub
		this.gioiTinh = gioiTinh;
	}

	private String gioiTinh;

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	
}
