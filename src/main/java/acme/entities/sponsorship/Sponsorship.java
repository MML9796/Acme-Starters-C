
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


	@Transient
	public double getMonthsActive() {
		long diffMillis = this.endMoment.getTime() - this.startMoment.getTime();
		double dias = diffMillis / (1000.0 * 60 * 60 * 24);
		double meses = dias / 30.436875;
		return Math.round(meses * 10.0) / 10.0;
	}

	@Transient
	public Money getTotalMoney() {

		Double sum = this.repository.getSumTotalMoneyBySponsorship(this.getId());
		Money resultado = new Money();
		resultado.setAmount(sum);
		resultado.setCurrency("EUR");
		return resultado;
	}


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Sponsor sponsor;
}
