<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="All Users" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">
		<c:set var = "pageName" value = "users.jsp"/>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%>
				
				<h1><fmt:message key='users_jsp.label.allUsers'/></h1> <c:choose>
					<c:when test="${fn:length(userList) == 0}">
						No users exists.
					</c:when>
					<c:otherwise>
						<table id="list_user_table">
							<thead>
								<tr>
									<td>№</td>
									<td><fmt:message key='users_jsp.tableLabel.role'/></td>
									<td><fmt:message key='users_jsp.tableLabel.login'/></td>
									<td><fmt:message key='users_jsp.tableLabel.password'/></td>
									<td><fmt:message key='users_jsp.tableLabel.firstName'/></td>
									<td><fmt:message key='users_jsp.tableLabel.lastName'/></td>
									<td><fmt:message key='users_jsp.tableLabel.email'/></td>
									<td><fmt:message key='users_jsp.tableLabel.status'/></td>
									<td><fmt:message key='users_jsp.tableLabel.locking_unlocking'/></td>
									<td><fmt:message key='users_jsp.tableLabel.delete'/></td>
								</tr>
							</thead>
							<c:forEach var="user" items="${userList}">
								<tr>
									<td>${user.id}</td>
									<td><c:choose>
											<c:when test="${user.roleId == 0}">
												admin
											</c:when>
											<c:otherwise>
												user
											</c:otherwise>
										</c:choose></td>
									<td>${user.login}</td>
									<td>${user.password}</td>
									<td>${user.firstName}</td>
									<td>${user.lastName}</td>
									<td>${user.email}</td>
									<td>
										<st4tag:getLockStatusName lockStatusId = "${user.lockStatusId}"></st4tag:getLockStatusName>
									</td>
									<td>
										<c:if test = "${sessionScope.user.id != user.id}">
											<c:choose>
												<c:when test="${user.lockStatusId == 0 ||user.lockStatusId == 2}">
													 <form id="unlockUserForm" action="controller">
														<input type="hidden" name="command" value="unlockUser" />
														<input type="hidden" name="userId" value="${user.id}" />
														<input value="<fmt:message key='users_jsp.button.unlock'/>" type="submit" />
													</form>
												</c:when>
												<c:otherwise>
													<form id="lockUserForm" action="controller">
														<input type="hidden" name="command" value="lockUser" />
														<input type="hidden" name="userId" value="${user.id}" /> 
														<input value="<fmt:message key='users_jsp.button.lock'/>" type="submit" />
													</form>
												</c:otherwise>
											</c:choose>
										</c:if>
									</td>
									<td>
										<c:if test="${user.roleId == 1}">
											<form id="deleteUserForm" action="controller">
												<input type="hidden" name="command" value="deleteUser" />
												<input type="hidden" name="userId" value="${user.id}" /> 
												<input value="<fmt:message key='users_jsp.button.delete'/>" type="submit" />
											</form>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose> <%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>