
package acme.entities.part;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.entities.invention.Invention;
import acme.validation.ValidHeader;
import acme.validation.ValidMoney2;
import acme.validation.ValidText;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Part extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@Column
	@ValidMoney2(min = 0.0)
	private Money				cost;

	@Mandatory
	@Valid
	@Column
	private PartKind			kind;

	// Custom enum values -----------------------------------------------------


	enum PartKind {
		CORE, MANDATORY, OPTIONAL
	};

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Invention invention;

}
