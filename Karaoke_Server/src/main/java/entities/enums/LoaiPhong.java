package entities.enums;

import java.util.ArrayList;
import java.util.List;

public enum LoaiPhong {
	CỔĐIỂN("Cổ điển"), HIỆNĐẠI("Hiện đại"), HOANGDÃ("Hoang dã"), HOÀNGGIA("Hoàng gia"), CAFE("Cà phê");
	LoaiPhong(String loaiPhong) {
		// TODO Auto-generated constructor stub
		this.loaiPhong = loaiPhong;
	}

	private String loaiPhong;

	public String getLoaiPhong() {
		return loaiPhong;
	}

	public void setLoaiPhong(String loaiPhong) {
		this.loaiPhong = loaiPhong;
	}
}
