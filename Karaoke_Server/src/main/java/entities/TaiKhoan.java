package entities;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name = "taikhoan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaiKhoan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6311422502433518075L;
	@Id
	private String sdt;
	@Column(name = "matkhau", columnDefinition = "varchar(255)")
	private String matKhau;
}
