<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="spokesperson.campaign.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="spokesperson.campaign.form.label.name" path="name"/>
	<acme:form-textbox code="spokesperson.campaign.form.label.description" path="description"/>
	<acme:form-textbox code="spokesperson.campaign.form.label.startMoment" path="startMoment"/>
	<acme:form-textbox code="spokesperson.campaign.form.label.endMoment" path="endMoment"/>
	<acme:form-textbox code="spokesperson.campaign.form.label.monthsActive" path="monthsActive"/>
	<acme:form-textbox code="spokesperson.campaign.form.label.effort" path="effort"/>
	<acme:form-textbox code="spokesperson.campaign.form.label.moreInfo" path="moreInfo"/>
	<acme:button code="spokesperson.campaign.button.milestone" action="/spokesperson/milestone/list?campaignId=${id}"/>
</acme:form>