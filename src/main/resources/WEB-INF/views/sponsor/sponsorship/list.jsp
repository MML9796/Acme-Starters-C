<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.sponsorship.list.label.ticker" path="ticker"/>
	<acme:list-column code="sponsor.sponsorship.list.label.name" path="name"/>
	<acme:list-column code="sponsor.sponsorship.list.label.startMoment" path="startMoment"/>
	<acme:list-column code="sponsor.sponsorship.list.label.endMoment" path="endMoment"/>
</acme:list>