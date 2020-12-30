package chapter6.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id = UUID.randomUUID().toString();

	private Integer version;

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AbstractEntity)) {
			return false;
		}
		AbstractEntity other = (AbstractEntity) obj;
		String id1 = this.getId();
		String id2 = other.getId();
		boolean equal = id1.equals(id2);
		return equal;
	}

	@Id
	public String getId() {
		return id;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		}
		return super.hashCode();
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
