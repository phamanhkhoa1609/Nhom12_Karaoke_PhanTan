package entities;

import java.time.LocalDate;



import entities.enums.ChucVu;
import entities.enums.TrangThaiNhanVien;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "nhanvien")
@Getter
@Setter
@ToString
public class NhanVien extends NguoiDung{
	@Column(name = "ngaysinh", columnDefinition = "date")
	private LocalDate ngaySinh;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "chucvu",nullable = false)
	private ChucVu chucVu;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "trangthai",nullable = false)
	private TrangThaiNhanVien trangThai = TrangThaiNhanVien.NGHỈ;
	
	@Column(name = "publicid", columnDefinition = "nvarchar(255)")
	private String publicID;
	
	@Column(name = "cccd", columnDefinition = "varchar(12)", nullable = false)
	private String cccd;
	
	@Column(name = "diachi", columnDefinition = "nvarchar(255)")
	private String diaChi;


	
	
}
