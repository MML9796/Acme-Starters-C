<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="inventor.part.list.label.name" path="name"/>
	<acme:list-column code="inventor.part.list.label.cost" path="cost"/>
	<acme:list-hidden path="description"/>
	<acme:list-hidden path="kind"/>
</acme:list>

<jstl:if test="${draftMode == true}">
        <acme:button code="inventor.part.button.create" action="/inventor/part/create?inventionId=${inventionId}"/>
</jstl:if>