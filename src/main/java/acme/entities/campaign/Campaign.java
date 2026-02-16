
package acme.entities.campaign;

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
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.realms.Spokesperson;
import lombok.Getter;
import lombok.Setter;
import validation.ValidHeader;
import validation.ValidText;
import validation.ValidTicker;

@Entity
@Getter
@Setter
public class Campaign extends AbstractEntity {

	@Transient
	@Autowired
	private CampaignRepository	campaignRepository;
	// Serialisation identifier
	private static final long	serialVersionUID	= 1L;

	//Attributes
	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	//Derived attributes


	@Transient
	public double getMonthsActive() {
		long diffMillis = this.endMoment.getTime() - this.startMoment.getTime();
		double dias = diffMillis / (1000.0 * 60 * 60 * 24);
		double meses = dias / 30.436875;
		return Math.round(meses * 10.0) / 10.0;
	}

	public Double getEffort() {
		Double total = this.campaignRepository.sumEffortByCampaignId(this.getId());
		return total != null ? total : 0.0;
	}


	//Relationship
	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Spokesperson spokesperson;
}
