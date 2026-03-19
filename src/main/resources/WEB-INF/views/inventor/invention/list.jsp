<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="inventor.invention.list.label.ticker" path="ticker"/>
	<acme:list-column code="inventor.invention.list.label.name" path="name"/>
	<acme:list-column code="inventor.invention.list.label.startMoment" path="startMoment"/>
	<acme:list-column code="inventor.invention.list.label.endMoment" path="endMoment"/>
	<acme:list-hidden path="description"/>
	<acme:list-hidden path="moreInfo"/>
</acme:list>

<acme:button code="inventor.invention.button.create" action="/inventor/invention/create"/>