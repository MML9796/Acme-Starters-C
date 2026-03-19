
package acme.entities.audit_reports;

import java.time.temporal.ChronoUnit;
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
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidAuditReport;
import acme.realms.Auditor;
import acme.validation.ValidHeader;
import acme.validation.ValidText;
import acme.validation.ValidTicker;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidAuditReport
public class AuditReport extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

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

	// Derived attributes -----------------------------------------------------

	@Transient
	@Autowired
	private AuditReportRepository	repository;


	@Mandatory
	@Valid
	@Transient
	public Double getMonthsActive() {
		Double result;

		if (this.getStartMoment() != null && this.getEndMoment() != null)
			result = MomentHelper.computeDifference(this.getStartMoment(), this.getEndMoment(), ChronoUnit.MONTHS);
		else
			result = null;

		return result;
	}

	@Mandatory
	@ValidNumber(min = 0)
	@Transient
	public Integer getHours() {
		Integer result;
		Integer wrapper;

		wrapper = this.repository.computeHours(this.getId());
		result = wrapper == null ? 0 : wrapper;

		return result;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Auditor auditor;

}
