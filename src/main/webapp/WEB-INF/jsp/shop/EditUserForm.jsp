<%@ include file="jspf/IncludeTop.jspf" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- Support for Spring errors object -->
<spring:bind path="userForm.*">
    <c:forEach var="error" items="${status.errorMessages}">
        <B><FONT color=RED>
                <BR><c:out value="${error}"/>
            </FONT></B>
        </c:forEach>
    </spring:bind>

<c:if test="${userForm.newUser}">
    <form action="<c:url value="/shop/newUser.html"/>" method="post">
    </c:if>
    <c:if test="${!userForm.newUser}">
        <form action="<c:url value="/shop/editUser.html"/>" method="post">
        </c:if>

        <table cellpadding="10" cellspacing="0" align="center" border="1" bgcolor="#000000">
            <tr>
                <td>

                    <font color="darkgreen"><h3>User Information</h3></font>
                    <table border="0" cellpadding="3" cellspacing="1" bgcolor="#99FF22">
                        <tr bgcolor="#99FF22"><td>
                                Username</td><td>
                                <c:if test="${userForm.newUser}">
                                    <spring:bind path="userForm.user.username">
                                        <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                                    </spring:bind>
                                </c:if>
                                <c:if test="${!userForm.newUser}">
                                    <c:out value="${userForm.user.username}"/>
                                </c:if>
                            </td></tr><tr bgcolor="#99FF22"><td>
                                New password:</td><td>
                                <spring:bind path="userForm.user.password">
                                    <input type="password" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                                </spring:bind>
                            </td></tr><tr bgcolor="#99FF22"><td>
                                Repeat password:</td><td>
                                <spring:bind path="userForm.repeatedPassword">
                                    <input type="password" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                                </spring:bind>
                            </td></tr>
                    </table>

                    <%@ include file="jspf/IncludeUserFields.jspf" %>

                </td>
            </tr>

            <!-- kaptcha -->
            <tr>
                <td>
                    <%@ include file="../../jspf/kaptcha.jspf" %>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <input type="submit" name="submit" value="submit" />
                </td>
            </tr>
        </table>
    </form>

    <%@ include file="jspf/IncludeBottom.jspf" %>
