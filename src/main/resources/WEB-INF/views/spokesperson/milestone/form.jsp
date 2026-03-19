<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="spokesperson.milestone.form.label.title" path="title"/>
	<acme:form-textbox code="spokesperson.milestone.form.label.achievements" path="achievements"/>
	<acme:form-double code="spokesperson.milestone.form.label.effort" path="effort"/>
	<acme:form-select code="spokesperson.milestone.form.label.kind" path="kind" choices="${listaKinds}"/>
	
	<jstl:choose>
	
    <jstl:when test="${_command == 'create' }">
        <acme:submit code="spokesperson.milestone.button.create" action="/spokesperson/milestone/create?campaignId=${campaignId}"/>
    </jstl:when>

    <jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') }">
    <jstl:if test="${draftMode == true}">
            <acme:submit code="spokesperson.milestone.button.update" action="/spokesperson/milestone/update?campaignId=${campaignId}"/>
            <acme:submit code="spokesperson.milestone.button.delete" action="/spokesperson/milestone/delete?campaignId=${campaignId}"/>
      </jstl:if>
    </jstl:when>

</jstl:choose>
</acme:form>