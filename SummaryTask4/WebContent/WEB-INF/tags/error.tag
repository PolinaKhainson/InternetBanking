<%@ attribute name="fieldName" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${errors.containsKey(fieldName)}">
	<p class = "error">${errors.get(fieldName)}</p>
</c:if>