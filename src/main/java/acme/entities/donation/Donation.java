
package acme.entities.donation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.entities.sponsorship.Sponsorship;
import acme.validation.ValidHeader;
import acme.validation.ValidMoney2;
import acme.validation.ValidText;
import lombok.Getter;
import lombok.Setter;

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
	@ValidMoney2(min = 0.0)
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
