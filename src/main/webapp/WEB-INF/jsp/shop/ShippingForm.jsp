<%@ include file="jspf/IncludeTop.jspf" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<center>
    <!-- Support for Spring errors holder -->
    <spring:bind path="orderForm.*">
        <c:forEach var="error" items="${status.errorMessages}">
            <B><FONT color=RED>
                    <BR><c:out value="${error}"/>
                </FONT></B>
            </c:forEach>
        </spring:bind>


    <form action="<c:url value="/shop/newOrder.html"/>" method="post">

        <TABLE bgcolor="#000000" border=0 cellpadding=3 cellspacing=1 >
            <TR bgcolor="#99FF22"><TD colspan=2>
                    <FONT color="white" size=4><B>Shipping Address</B></FONT>
                </TD></TR>

            <TR bgcolor="#99FF22"><TD>
                    First name:</TD><TD>
                    <spring:bind path="orderForm.order.shipToFirstname">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                    </spring:bind>
                </TD></TR>
            <TR bgcolor="#99FF22"><TD>
                    Last name:</TD><TD>
                    <spring:bind path="orderForm.order.shipToLastname">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                    </spring:bind>
                </TD></TR>
            <TR bgcolor="#99FF22"><TD>
                    Address 1:</TD><TD>
                    <spring:bind path="orderForm.order.shipAddress.addr1">
                        <input type="text" size="40" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                    </spring:bind>
                </TD></TR>
            <TR bgcolor="#99FF22"><TD>
                    Address 2:</TD><TD>
                    <spring:bind path="orderForm.order.shipAddress.addr2">
                        <input type="text" size="40" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                    </spring:bind>
                </TD></TR>
            <TR bgcolor="#99FF22"><TD>
                    City: </TD><TD>
                    <spring:bind path="orderForm.order.shipAddress.city">
                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                    </spring:bind>
                </TD></TR>
            <TR bgcolor="#99FF22"><TD>
                    State:</TD><TD>
                    <spring:bind path="orderForm.order.shipAddress.state">
                        <input type="text" size="4" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                    </spring:bind>
                </TD></TR>
            <TR bgcolor="#99FF22"><TD>
                    Zip:</TD><TD>
                    <spring:bind path="orderForm.order.shipAddress.zipcode">
                        <input type="text" size="10" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                    </spring:bind>
                </TD></TR>
            <TR bgcolor="#99FF22"><TD>
                    Country: </TD><TD>
                    <spring:bind path="orderForm.order.shipAddress.country">
                        <input type="text" size="15" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                    </spring:bind>
                </TD></TR>

        </TABLE>
        <P>
            <input type="image" src="../images/button_submit.gif">

    </form>
</center>
<%@ include file="jspf/IncludeBottom.jspf" %>
