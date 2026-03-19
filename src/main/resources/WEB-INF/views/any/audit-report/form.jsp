<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.audit-report.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="any.audit-report.form.label.name" path="name"/>
	<acme:form-textarea code="any.audit-report.form.label.description" path="description"/>
	<acme:form-moment code="any.audit-report.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="any.audit-report.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="any.audit-report.form.label.moreInfo" path="moreInfo"/>
	<acme:form-double code="any.audit-report.form.label.monthsActive" path="monthsActive"/>
	<acme:form-integer code="any.audit-report.form.label.hours" path="hours"/>
	
	<acme:button code="any.audit-report.form.label.auditor" action="/any/auditor/show?id=${auditor.id}"/>
	<acme:button code="any.audit-report.form.label.audit-sections" action="/any/audit-section/list?auditReportId=${id}"/>
</acme:form>

