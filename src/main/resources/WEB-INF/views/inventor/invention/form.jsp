<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="inventor.invention.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="inventor.invention.form.label.name" path="name"/>
	<acme:form-textarea code="inventor.invention.form.label.description" path="description"/>
	<acme:form-moment code="inventor.invention.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="inventor.invention.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="inventor.invention.form.label.moreInfo" path="moreInfo"/>

	<jstl:if test="${_command == 'show'}">
    <acme:form-double code="inventor.invention.form.label.monthsActive" path="monthsActive" readonly="true"/>
    <acme:form-double code="inventor.invention.form.label.cost" path="cost" readonly="true"/>
    </jstl:if>
    
	<jstl:choose>
    <jstl:when test="${_command == 'create' }">
        <acme:submit code="inventor.invention.button.create" action="/inventor/invention/create"/>
    </jstl:when>

    <jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') }">
        
        <acme:button code="inventor.invention.button.parts" action="/inventor/part/list?inventionId=${id}"/>

        <jstl:if test="${draftMode == true}">
            <acme:submit code="inventor.invention.button.update" action="/inventor/invention/update"/>
            <acme:submit code="inventor.invention.button.delete" action="/inventor/invention/delete"/>
            <acme:submit code="inventor.invention.button.publish" action="/inventor/invention/publish"/>
        </jstl:if>

    </jstl:when>

	</jstl:choose>

</acme:form>