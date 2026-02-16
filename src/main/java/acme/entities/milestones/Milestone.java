
package acme.entities.milestones;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidNumber;
import acme.entities.campaign.Campaign;
import lombok.Getter;
import lombok.Setter;
import validation.ValidHeader;
import validation.ValidText;

@Entity
@Getter
@Setter
public class Milestone extends AbstractEntity {

	// Serialisation identifier
	private static final long	serialVersionUID	= 1L;

	//Attributes
	@Mandatory
	@ValidHeader
	@Column
	private String				title;

	@Mandatory
	@ValidText
	@Column
	private String				achievements;

	@Mandatory
	@ValidNumber(min = 0.0)
	@Column
	private Double				effort;

	@Mandatory
	@Valid
	@Column
	private MilestoneKind		kind;

	//Relationship
	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Campaign			campaign;
}
