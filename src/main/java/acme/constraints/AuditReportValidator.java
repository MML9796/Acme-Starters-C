
package acme.validators;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.audit_reports.AuditReport;
import acme.entities.audit_reports.AuditReportRepository;
import acme.validation.ValidAuditReport;

@Validator
public class AuditReportValidator extends AbstractValidator<ValidAuditReport, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditReportRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidAuditReport annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final AuditReport auditReport, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (auditReport == null)
			result = true;
		else {
			{
				boolean uniqueAuditReport;
				AuditReport existingAuditReport;

				existingAuditReport = this.repository.findAuditReportByTicker(auditReport.getTicker());
				uniqueAuditReport = existingAuditReport == null || existingAuditReport.equals(auditReport);

				super.state(context, uniqueAuditReport, "ticker", "acme.validation.audit-report.duplicated-ticker.message");
			}

			{
				if (auditReport.getDraftMode() != null && !auditReport.getDraftMode()) {
					boolean atLeastOneAuditSection;
					int existingAuditSections;

					existingAuditSections = this.repository.findAuditSectionsSizeById(auditReport.getId());
					atLeastOneAuditSection = existingAuditSections >= 1;

					super.state(context, atLeastOneAuditSection, "*", "acme.validation.audit-report.no-audit-sections.message");
				}
			}
			{
				boolean correctStartEndFutureInterval;

				if (auditReport.getStartMoment() != null && auditReport.getEndMoment() != null) {
					if (auditReport.getDraftMode() != null && auditReport.getDraftMode())
						correctStartEndFutureInterval = MomentHelper.isAfter(auditReport.getEndMoment(), auditReport.getStartMoment());
					else
						correctStartEndFutureInterval = MomentHelper.isFuture(auditReport.getStartMoment()) && MomentHelper.isAfter(auditReport.getEndMoment(), auditReport.getStartMoment());
				} else
					correctStartEndFutureInterval = false;

				super.state(context, correctStartEndFutureInterval, "startMoment, endMoment", "acme.validation.audit-report.invalid-start-end-interval.message");
			}
			result = !super.hasErrors(context);
		}
		return result;
	}

}
