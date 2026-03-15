<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="sponsor.donation.form.label.name" path="name"/>
	<acme:form-textbox code="sponsor.donation.form.label.notes" path="notes"/>
	<acme:form-textbox code="sponsor.donation.form.label.money" path="money"/>
	<acme:form-textbox code="sponsor.donation.form.label.kind" path="kind"/>
	<jstl:choose>
	
    <jstl:when test="${_command == 'create' }">
        <acme:submit code="sponsor.donation.button.create" action="/sponsor/donation/create?sponsorshipId=${sponsorshipId}"/>
    </jstl:when>

    <jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') }">
    <jstl:if test="${draftMode == true}">
            <acme:submit code="sponsor.donation.button.update" action="/sponsor/donation/update?sponsorshipId=${sponsorshipId}"/>
            <acme:submit code="sponsor.donation.button.delete" action="/sponsor/donation/delete?sponsorshipId=${sponsorshipId}"/>
      </jstl:if>
    </jstl:when>

</jstl:choose>
</acme:form>