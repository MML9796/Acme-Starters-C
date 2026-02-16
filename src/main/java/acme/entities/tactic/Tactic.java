
package acme.entities.tactic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidScore;
import acme.entities.strategy.Strategy;
import lombok.Getter;
import lombok.Setter;
import validation.ValidHeader;
import validation.ValidText;

@Entity
@Getter
@Setter
public class Tactic extends AbstractEntity {

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
	@ValidScore
	@Column
	private Double				expectedPercentage;

	@Mandatory
	@Valid
	@Column
	private TacticKind			kind;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Strategy			strategy;
}
