<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@include file="/include/header.jsp" %>
<h1>Add Student</h1> 
<form action="add" method="POST">
  <p>ID: <input type="text" name="id"  /></p>
  <p>Name: <input type="text" name="name"  /></p>
  <p>Email: <input type="text" name="email"  /></p>
  <input type="submit" value="Add Student to DB" />
</form>
<h3>${message}</h3>
<p><c:forEach var="student" items="${students}">
    <ul>
      <li>${student}</li>
    </ul>
</c:forEach>
<%@include file="/include/footer.jsp" %>    
