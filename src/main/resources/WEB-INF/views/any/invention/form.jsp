<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.invention.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="any.invention.form.label.name" path="name"/>
	<acme:form-textbox code="any.invention.form.label.description" path="description"/>
	<acme:form-textbox code="any.invention.form.label.startMoment" path="startMoment"/>
	<acme:form-textbox code="any.invention.form.label.endMoment" path="endMoment"/>
	<acme:form-textbox code="any.invention.form.label.monthsActive" path="monthsActive"/>
	<acme:form-textbox code="any.invention.form.label.cost" path="cost"/>
	<acme:form-textbox code="any.invention.form.label.moreInfo" path="moreInfo"/>
	<acme:button code="any.invention.button.parts" action="/any/part/list?inventionId=${id}"/>
	<acme:button code="any.invention.button.inventor" action="/any/inventor/show?id=${inventorId}"/>
</acme:form>