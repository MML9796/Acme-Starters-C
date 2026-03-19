<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="auditor.audit-section.form.label.name" path="name"/>
	<acme:form-textarea code="auditor.audit-section.form.label.notes" path="notes"/>
	<acme:form-integer code="auditor.audit-section.form.label.hours" path="hours"/>
	<acme:form-select code="auditor.audit-section.form.label.kind" path="kind" choices="${kinds}"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && auditReport.draftMode == true}">
			<acme:submit code="auditor.audit-section.form.button.update" action="/auditor/audit-section/update"/>
			<acme:submit code="auditor.audit-section.form.button.delete" action="/auditor/audit-section/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit-section.form.button.create" action="/auditor/audit-section/create?auditReportId=${auditReport.id}"/>
		</jstl:when>	
	</jstl:choose>
</acme:form>