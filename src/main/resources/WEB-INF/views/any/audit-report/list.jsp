<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.audit-report.list.label.ticker" path="ticker" width="20%"/>
	<acme:list-column code="any.audit-report.list.label.name" path="name" width="40%"/>
	<acme:list-hidden path="description"/>
	<acme:list-column code="any.audit-report.list.label.startMoment" path="startMoment" width="20%"/>
	<acme:list-column code="any.audit-report.list.label.endMoment" path="endMoment" width="20%"/>
	<acme:list-hidden path="moreInfo"/>
	<acme:list-hidden path="monthsActive"/>
	<acme:list-hidden path="hours"/>
</acme:list>
