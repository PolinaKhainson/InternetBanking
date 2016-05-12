<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Payments" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<table id="main-container">
		<c:set var = "pageName" value = "all_payments.jsp"/>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<tr>
			<td class="content">
				<%-- CONTENT --%>
				
				<h1><fmt:message key='all_payments_jsp.label.allPayments'/></h1>
					<c:choose>
						<c:when test="${fn:length(allUserPaymentBeanList) == 0}">
							There are no payments for this user
						</c:when>
						<c:otherwise>
							<table id="list_all_payments_table">
								<thead>
									<tr>
										<td><fmt:message key='all_payments_jsp.tableLabel.numberOfPayment'/></td>
										<td><fmt:message key='all_payments_jsp.tableLabel.numberOfCreditCard'/></td>
										<td><fmt:message key='all_payments_jsp.tableLabel.numberOfAccount'/></td>
										<td><fmt:message key='all_payments_jsp.tableLabel.accountStatus'/></td>
										<td><fmt:message key='all_payments_jsp.tableLabel.sumOfPayment'/></td>
										<td><fmt:message key='all_payments_jsp.tableLabel.dateTimeOfPayment'/></td>
										<td><fmt:message key='all_payments_jsp.tableLabel.statusOfPayment'/></td>
									</tr>
								</thead>
								<c:set var="k" value="0" />
								<c:forEach var="item" items="${allUserPaymentBeanList}">
									<c:set var="k" value="${k+1}" />
									<tr>
										<td><c:out value="${k}" /></td>
										<td>${item.creditCardNumber}</td>
										<td>${item.accountNumber}</td>
										<td>
										<st4tag:getLockStatusName lockStatusId = "${item.accountStatus}"/>
										</td>
										<td>${item.sumOfMoney}</td>
										<td>${item.dateOfPayment} ${item.timeOfPayment}</td>
										<td>${item.paymentStatusName}</td>
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
