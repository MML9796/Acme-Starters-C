<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
    <acme:form-textbox code="fundraiser.tactic.form.label.name" path="name"/>    
    <acme:form-double code="fundraiser.tactic.form.label.expectedPercentage" path="expectedPercentage"/>
    <acme:form-select code="fundraiser.tactic.form.label.kind" path="kind" choices="${listaKinds}"/>   
    <acme:form-textarea code="fundraiser.tactic.form.label.notes" path="notes"/>
    
    <jstl:choose>
	
    	<jstl:when test="${_command == 'create' }">
        	<acme:submit code="fundraiser.tactic.button.create" action="/fundraiser/tactic/create?strategyId=${strategyId}"/>
    	</jstl:when>

    	<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') }">
    		<jstl:if test="${draftMode == true}">
            	<acme:submit code="fundraiser.tactic.button.update" action="/fundraiser/tactic/update?strategyId=${strategyId}"/>
            	<acme:submit code="fundraiser.tactic.button.delete" action="/fundraiser/tactic/delete?strategyId=${strategyId}"/>
      		</jstl:if>
    	</jstl:when>

	</jstl:choose>
</acme:form>