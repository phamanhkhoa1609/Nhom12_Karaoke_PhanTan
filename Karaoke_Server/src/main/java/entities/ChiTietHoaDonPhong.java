package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name = "chitiethoadonphong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietHoaDonPhong implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	@Column(name = "thoigianbatdau", columnDefinition = "datetime", nullable = false)
	private LocalDateTime thoiGianBatDau = LocalDateTime.now();
	@Column(name = "thoigianketthuc", columnDefinition = "time", nullable = false)
	private LocalTime thoiGianKetThuc = LocalTime.MIDNIGHT;
	@Column(name = "dongia", nullable = false, columnDefinition = "bigint")
	private long donGia;
	
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "maPhong", columnDefinition = "bigint")
	private Phong phong;
	
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mahd", columnDefinition = "bigint")
	private HoaDon hoaDon;

	@Override
	public String toString() {
		return "ChiTietHoaDonPhong [thoiGianBatDau=" + thoiGianBatDau + ", thoiGianKetThuc=" + thoiGianKetThuc
				+ ", donGia=" + donGia + ", phong=" + phong + "]";
	}
	
	
}
