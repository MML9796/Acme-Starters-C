
package acme.entities.part;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoney;
import acme.entities.invention.Invention;
import lombok.Getter;
import lombok.Setter;
import validation.ValidHeader;
import validation.ValidText;

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
	@ValidMoney(min = 0.0)
	@Column
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
