
package acme.realms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.Valid;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;
import validation.ValidHeader;
import validation.ValidText;

@Entity
@Getter
@Setter
public class Fundraiser extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidHeader
	@Column
	private String				bank;

	@Mandatory
	@ValidText
	@Column
	private String				statement;

	@Mandatory
	@Valid
	@Column
	private Boolean				agent;

}
