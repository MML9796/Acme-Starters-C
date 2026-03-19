<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.audit-section.list.label.name" path="name" width="50%"/>
	<acme:list-hidden path="notes"/>
	<acme:list-column code="auditor.audit-section.list.label.hours" path="hours" width="20%"/>
	<acme:list-column code="auditor.audit-section.list.label.kind" path="kind" width="30%"/>
</acme:list>

<jstl:if test="${showCreate}">
	<acme:button code="auditor.audit-section.list.button.create" action="/auditor/audit-section/create?auditReportId=${auditReportId}"/>
</jstl:if>