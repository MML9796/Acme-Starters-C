<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="inventor.part.form.label.name" path="name"/>
	<acme:form-textbox code="inventor.part.form.label.description" path="description"/>
	<acme:form-money code="inventor.part.form.label.cost" path="cost"/>
	<acme:form-select code="inventor.part.form.label.kind" path="kind" choices="${listaKinds}"/>
	<jstl:choose>
	
    <jstl:when test="${_command == 'create' }">
        <acme:submit code="inventor.part.button.create" action="/inventor/part/create?inventionId=${inventionId}"/>
    </jstl:when>

    <jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') }">
    <jstl:if test="${draftMode == true}">
            <acme:submit code="inventor.part.button.update" action="/inventor/part/update?id=${id}"/>
            <acme:submit code="inventor.part.button.delete" action="/inventor/part/delete?id=${id}"/>
      </jstl:if>
    </jstl:when>

</jstl:choose>
</acme:form>