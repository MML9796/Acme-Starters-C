<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="spokesperson.milestone.list.label.title" path="title"/>
	<acme:list-column code="spokesperson.milestone.list.label.achievements" path="achievements"/>
</acme:list>

 <jstl:if test="${draftMode == true}">
        <acme:button code="spokesperson.milestone.button.create" action="/spokesperson/milestone/create?campaignId=${campaignId}"/>
    </jstl:if>