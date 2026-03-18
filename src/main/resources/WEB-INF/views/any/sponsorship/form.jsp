<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.sponsorship.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="any.sponsorship.form.label.name" path="name"/>
	<acme:form-textbox code="any.sponsorship.form.label.description" path="description"/>
	<acme:form-textbox code="any.sponsorship.form.label.startMoment" path="startMoment"/>
	<acme:form-textbox code="any.sponsorship.form.label.endMoment" path="endMoment"/>
	<acme:form-textbox code="any.sponsorship.form.label.moreInfo" path="moreInfo"/>
	<acme:form-textbox code="any.sponsorship.form.label.monthsActive" path="monthsActive"/>
	<acme:form-textbox code="any.sponsorship.form.label.totalMoney" path="totalMoney"/>
	<acme:button code="any.sponsorship.button.donation" action="/any/donation/list?sponsorshipId=${id}"/>
	<acme:button code="any.sponsorship.button.sponsor" action="/any/sponsor/show?id=${sponsorId}"/>
</acme:form>