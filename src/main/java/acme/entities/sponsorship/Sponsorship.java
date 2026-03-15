
package acme.entities.sponsorship;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.realms.Sponsor;
import acme.validation.ValidHeader;
import acme.validation.ValidMoney2;
import acme.validation.ValidSponsorship;
import acme.validation.ValidText;
import acme.validation.ValidTicker;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidSponsorship
public class Sponsorship extends AbstractEntity {

	@Transient
	@Autowired
	private SponsorshipRepository	repository;

	private static final long		serialVersionUID	= 1L;

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String					ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String					name;

	@Mandatory
	@ValidText
	@Column
	private String					description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					endMoment;

	@Optional
	@ValidUrl
	@Column
	private String					moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean					draftMode;


	@Mandatory
	@Valid
	@Transient
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return 0.0;
		long diffMillis = this.endMoment.getTime() - this.startMoment.getTime();
		if (diffMillis <= 0)
			return 0.0;
		double meses = diffMillis / (1000.0 * 60 * 60 * 24 * 30);
		return Math.round(meses * 10.0) / 10.0;
	}

	@Mandatory
	@ValidMoney2(min = 0.0, max = 1000000.0)
	@Transient
	public Money getTotalMoney() {
		Money resultado = new Money();
		resultado.setCurrency("EUR");
		resultado.setAmount(0.0);

		if (this.repository == null)
			return resultado;

		Double sum = this.repository.getSumTotalMoneyBySponsorship(this.getId());
		if (sum != null)
			resultado.setAmount(sum);

		return resultado;
	}


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Sponsor sponsor;
}
