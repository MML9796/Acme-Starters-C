
package acme.entities.donation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoney;
import acme.entities.sponsorship.Sponsorship;
import lombok.Getter;
import lombok.Setter;
import validation.ValidHeader;
import validation.ValidText;

@Entity
@Getter
@Setter
public class Donation extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
	@Column
	private String				notes;

	@Mandatory
	@ValidMoney(min = 0.0)
	@Column
	private Money				money;

	@Mandatory
	@Valid
	@Column
	private DonationKind		kind;


	enum DonationKind {
		ALTRUIST, REWARDED, CELEBRATED
	};


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Sponsorship sponsorship;

}
