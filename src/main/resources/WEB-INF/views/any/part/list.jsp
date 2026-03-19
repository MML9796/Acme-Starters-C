<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.part.list.label.name" path="name"/>
	<acme:list-column code="any.part.list.label.cost" path="cost"/>
	<acme:list-hidden path="description"/>
	<acme:list-hidden path="kind"/>
</acme:list>