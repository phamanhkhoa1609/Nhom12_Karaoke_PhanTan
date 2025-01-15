package entities;

import entities.enums.GioiTinh;
import entities.enums.HoiVien;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name = "khachhang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KhachHang extends NguoiDung{
	@Enumerated(value = EnumType.STRING)
	@Column(name = "hoivien",nullable = false)
	private HoiVien hoiVien = HoiVien.ĐỒNG;
}
