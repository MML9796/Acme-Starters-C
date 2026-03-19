<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.donation.list.label.name" path="name"/>
	<acme:list-column code="sponsor.donation.list.label.money" path="money"/>
	<acme:list-hidden path="notes"/>
	<acme:list-hidden path="kind"/>
</acme:list>

 <jstl:if test="${draftMode == true}">
        <acme:button code="sponsor.donation.button.create" action="/sponsor/donation/create?sponsorshipId=${sponsorshipId}"/>
    </jstl:if>