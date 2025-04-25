package entities;

import java.io.Serializable;

import entities.enums.GioiTinh;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class NguoiDung implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mand_seq")
    @SequenceGenerator(name = "mand_seq", sequenceName = "mand_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "mand", columnDefinition = "bigint")
    protected long maND;

    @Column(name = "hoten", columnDefinition = "nvarchar(25)", nullable = false)
    protected String hoTen;

    @Enumerated(EnumType.STRING)
    @Column(name = "gioitinh", nullable = false)
    protected GioiTinh gioiTinh;

    @Column(name = "sdt", nullable = false, unique = true)
    protected String sdt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sdt", referencedColumnName = "sdt", unique = true, insertable = false, updatable = false)
    protected TaiKhoan taiKhoan;
}

