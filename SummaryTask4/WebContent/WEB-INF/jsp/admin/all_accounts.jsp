<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<c:set var="title" value="Accounts" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>

	<table id="main-container">
		<c:set var = "pageName" value = "all_accounts.jsp"/>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%>
				
				<h1><fmt:message key='all_accounts_jsp.label.allAccounts'/></h1> 
				<div id = "bottomMargin">				
				<form id="createAccountForm" action="controller">
					<input type="hidden" name="command" value="createAccount" />
					<input value="<fmt:message key='all_accounts_jsp.button.createAccount'/>" type="submit" />
				</form>
				<form id="createCreditCardForm" action="controller">
					<input type="hidden" name="command" value="createCreditCard" /> 
					<input value="<fmt:message key='all_accounts_jsp.button.createCreditCard'/>" type="submit" />
				</form>
				</div>
				<c:choose>
					<c:when test="${fn:length(accountCreditCardsBeanList) == 0}">
						There are no any accounts.
					</c:when>
					<c:otherwise>
						<table id="list_all_accounts_table">
							<thead>
								<tr>
									<td>№</td>
									<td><fmt:message key='all_accounts_jsp.tableLabel.accountNumber'/></td>
									<td><fmt:message key='all_accounts_jsp.tableLabel.sumOnAccount'/></td>
									<c:if test="${orderOwner == null||orderOwner == 'asc'}">
										<td><a href="controller?command=sortAccountsByOwner&orderOwner=desc">
										<fmt:message key='all_accounts_jsp.tableLabel.owner'/></a></td>
									</c:if>
									<c:if test="${orderOwner  == 'desc'}">
										<td><a href="controller?command=sortAccountsByOwner&orderOwner=asc">
										<fmt:message key='all_accounts_jsp.tableLabel.owner'/></a></td>
									</c:if>
									
									<td><fmt:message key='all_accounts_jsp.tableLabel.assignedCreditCardsNumberAndDate'/></td>
									<td><fmt:message key='all_accounts_jsp.tableLabel.lockStatus'/></td>
									<td><fmt:message key='all_accounts_jsp.tableLabel.locking_unlocking'/></td>
									<td><fmt:message key='all_accounts_jsp.tableLabel.assignCreditCard'/></td>
									<td class="noBorder"></td>
								</tr>
							</thead>
							<c:set var="k" value="0" />
							<c:forEach var="item" items="${accountCreditCardsBeanList}">
							<c:set var="k" value="${k+1}" />
								<tr>
									<td><c:out value="${k}" /></td>
									<td>${item.account.accountNumber}</td>
									<td>${item.account.sumOnAccount}</td>
									<td>${item.ownerFirstName} ${item.ownerLastName}</td>
									<td><c:choose>
											<c:when test="${fn:length(item.creditCardslist) == 0}">
												No assingned credit cards.
											</c:when>
											<c:otherwise>
												<c:forEach var="card" items="${item.creditCardslist}">
													${card.cardNumber} /(${card.endingDate})<br>
												</c:forEach>
											</c:otherwise>
										</c:choose></td>
									<td>
									<st4tag:getLockStatusName lockStatusId = "${item.account.lockStatusId}"/>
									</td>
									<td><c:choose>
											<c:when test="${item.account.lockStatusId == 0 ||item.account.lockStatusId == 2}">
												 <form id="unlockAccountForm" action="controller">
													<input type="hidden" name="command" value="unlockAccount" />
													<input type="hidden" name="accountId" value="${item.account.id}" /> 
													<input value="<fmt:message key='all_accounts_jsp.button.unlock'/>"
														type="submit" />
												</form>
											</c:when>
											<c:otherwise>
												<form id="lockAccountForm" action="controller">
													<input type="hidden" name="command" value="lockAccount" />
													<input type="hidden" name="accountId" value="${item.account.id}" /> 
													<input value="<fmt:message key='all_accounts_jsp.button.lock'/>" type="submit" />
												</form>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<form name="assignCrediCardForm" id="assignCrediCardForm"
													action="controller" method="post">
											<input type="hidden" name="command" value="assignCreditCard" /> 
											<input type="hidden" name="accountId" value="${item.account.id}" />	
											<select id="assignedCreditCardId" name="assignedCreditCardId">
												<option value="-1" selected="selected"><fmt:message key='all_accounts_jsp.label.chooseCreditCard'/></option>
												<c:forEach var="creditCard" items="${item.creditCards}">
													<option value="${creditCard.key}" ${creditCard.key == assignedCreditCardId ? 'selected' : ''}>${creditCard.value}</option>
												</c:forEach>
											</select>	
											<input name="assignCreditCard" value="<fmt:message key='all_accounts_jsp.button.assignCrediCard'/>"
														type="submit" onclick="return validate(<c:out value='${item.account.id}'/>)" />
										</form>	
									</td>
									<td class="noBorder">
										<c:if test="${errors.containsKey('assignedCreditCardId') && accountId == item.account.id}">
											<p class="error">${errors.get('assignedCreditCardId')}</p>

										</c:if> 
										<c:if test="${successfulAssignCreditCard != null && accountId == item.account.id}">
											<span class = "successful">${successfulAssignCreditCard}</span>
										</c:if> 
											<span id="sumOfMoneyError${item.account.id}" class="error"></span>
									</td>
								</tr>
							</c:forEach>
						</table>
						
						<h3>User total sum of Money</h3>
						<table id="list_all_accounts_table">
							<thead>
								<tr>
									<td><fmt:message key='all_accounts_jsp.tableLabel.user'/></td>
									<td><fmt:message key='all_accounts_jsp.tableLabel.totalSum'/></td>
								</tr>
							</thead>
								<c:forEach var="userSumOfMoney" items="${userSumOfMoneyMap}">
									<tr>
										<td>${userSumOfMoney.key}</td>
										<td>${userSumOfMoney.value}</td>
									</tr>
								</c:forEach>
						</table>
						
					</c:otherwise>
				</c:choose> 

				<%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>
