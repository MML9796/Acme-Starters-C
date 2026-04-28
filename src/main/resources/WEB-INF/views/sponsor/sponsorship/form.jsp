<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="sponsor.sponsorship.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="sponsor.sponsorship.form.label.name" path="name"/>
	<acme:form-textarea code="sponsor.sponsorship.form.label.description" path="description"/>
	<acme:form-moment code="sponsor.sponsorship.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="sponsor.sponsorship.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="sponsor.sponsorship.form.label.moreInfo" path="moreInfo"/>

	<jstl:if test="${_command != 'create'}">
    <acme:form-double code="sponsor.sponsorship.form.label.monthsActive" path="monthsActive" readonly="true"/>
    <acme:form-double code="sponsor.sponsorship.form.label.totalMoney" path="totalMoney" readonly="true"/>
    </jstl:if>
    
	<jstl:choose>
    <jstl:when test="${_command == 'create' }">
        <acme:submit code="sponsor.sponsorship.button.create" action="/sponsor/sponsorship/create"/>
    </jstl:when>

    <jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') }">
        
        <acme:button code="sponsor.sponsorship.button.donation" action="/sponsor/donation/list?sponsorshipId=${id}"/>

        <jstl:if test="${draftMode == true}">
            <acme:submit code="sponsor.sponsorship.button.update" action="/sponsor/sponsorship/update"/>
            <acme:submit code="sponsor.sponsorship.button.delete" action="/sponsor/sponsorship/delete"/>
            <acme:submit code="sponsor.sponsorship.button.publish" action="/sponsor/sponsorship/publish"/>
        </jstl:if>

    </jstl:when>

	</jstl:choose>

</acme:form>