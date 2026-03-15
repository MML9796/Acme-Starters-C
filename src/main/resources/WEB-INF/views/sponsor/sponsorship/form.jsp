<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="sponsor.sponsorship.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="sponsor.sponsorship.form.label.name" path="name"/>
	<acme:form-textbox code="sponsor.sponsorship.form.label.description" path="description"/>
	<acme:form-textbox code="sponsor.sponsorship.form.label.startMoment" path="startMoment"/>
	<acme:form-textbox code="sponsor.sponsorship.form.label.endMoment" path="endMoment"/>
	<acme:form-textbox code="sponsor.sponsorship.form.label.monthsActive" path="monthsActive"/>
	<acme:form-textbox code="sponsor.sponsorship.form.label.totalMoney" path="totalMoney"/>
	<acme:form-textbox code="sponsor.sponsorship.form.label.moreInfo" path="moreInfo"/>
	<acme:button code="sponsor.sponsorship.button.donation" action="/sponsor/donation/list?sponsorshipId=${id}"/>
</acme:form>