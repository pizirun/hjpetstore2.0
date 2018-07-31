<%@ include file="jspf/IncludeTop.jspf" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- Support for Spring errors holder -->
<spring:bind path="orderForm.*">
    <c:forEach var="error" items="${status.errorMessages}">
        <B><FONT color=RED>
                <BR><c:out value="${error}"/>
            </FONT></B>
        </c:forEach>
    </spring:bind>

<center>
<form action="<c:url value="/shop/newOrder.html"/>" method="post">

    <TABLE bgcolor="#000000" border=0 cellpadding=3 cellspacing=1 >
        <TR bgcolor="#99FF22"><TD colspan=2>
                <FONT color="black" size=4><B>Payment Details</B></FONT>
            </TD></TR><TR bgcolor="#99FF22"><TD>
                Card Type:</TD><TD>
                <spring:bind path="orderForm.order.cardType">
                    <select name="<c:out value="${status.expression}"/>">
                        <c:forEach var="cardType" items="${creditCardTypes}">
                            <option <c:if test="${cardType == status.value}">selected</c:if> value="<c:out value="${cardType}"/>">
                                <c:out value="${cardType}"/></option>
                            </c:forEach>
                    </select>
                </spring:bind>
            </TD></TR>
        <TR bgcolor="#99FF22"><TD>
                Card Number:</TD><TD>
                <spring:bind path="orderForm.order.cardNumber">
                    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                </spring:bind>
                <font color=red size=2>* Use a fake number!</font>
            </TD></TR>
        <TR bgcolor="#99FF22"><TD>
                Expiry Date (MM/YYYY):</TD><TD>
                <spring:bind path="orderForm.order.expireDate">
                    <input type="text" name="<c:out value="${status.expression}"/>"
                           value='<fmt:formatDate value="${orderForm.order.expireDate.time}" pattern="MM/yyyy"/>' />
                </spring:bind>
            </TD></TR>
        <TR bgcolor="#99FF22"><TD colspan=2>
                <FONT color="black" size=4><B>Billing Address</B></FONT>
            </TD></TR>

        <TR bgcolor="#99FF22"><TD>
                First name:</TD><TD>
                <spring:bind path="orderForm.order.billToFirstname">
                    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                </spring:bind>
            </TD></TR>
        <TR bgcolor="#99FF22"><TD>
                Last name:</TD><TD>
                <spring:bind path="orderForm.order.billToLastname">
                    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                </spring:bind>
            </TD></TR>
        <TR bgcolor="#99FF22"><TD>
                Address 1:</TD><TD>
                <spring:bind path="orderForm.order.billAddress.addr1">
                    <input type="text" size="40" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                </spring:bind>
            </TD></TR>
        <TR bgcolor="#99FF22"><TD>
                Address 2:</TD><TD>
                <spring:bind path="orderForm.order.billAddress.addr2">
                    <input type="text" size="40" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                </spring:bind>
            </TD></TR>
        <TR bgcolor="#99FF22"><TD>
                City: </TD><TD>
                <spring:bind path="orderForm.order.billAddress.city">
                    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                </spring:bind>
            </TD></TR>
        <TR bgcolor="#99FF22"><TD>
                State:</TD><TD>
                <spring:bind path="orderForm.order.billAddress.state">
                    <input type="text" size="4" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                </spring:bind>
            </TD></TR>
        <TR bgcolor="#99FF22"><TD>
                Zip:</TD><TD>
                <spring:bind path="orderForm.order.billAddress.zipcode">
                    <input type="text" size="10" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                </spring:bind>
            </TD></TR>
        <TR bgcolor="#99FF22"><TD>
                Country:</TD><TD>
                <spring:bind path="orderForm.order.billAddress.country">
                    <input type="text" size="15" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                </spring:bind>
            </TD></TR>

        <TR bgcolor="#99FF22"><TD colspan=2>
                <spring:bind path="orderForm.shippingAddressRequired">
                    <input type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked</c:if>>
		Ship to different address...
                </spring:bind>
            </TD></TR>

    </TABLE>
    <P>
        <input type="image" src="../images/button_submit.gif">

</form>
</center>
<%@ include file="jspf/IncludeBottom.jspf" %>
