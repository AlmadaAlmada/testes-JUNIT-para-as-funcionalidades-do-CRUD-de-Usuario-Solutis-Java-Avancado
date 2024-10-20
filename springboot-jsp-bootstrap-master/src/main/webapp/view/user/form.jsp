<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css">

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">


<script type="text/javascript" src="${path}/webjars/jquery/3.2.0/jquery.min.js"></script>
<script type="text/javascript" src="${path}/js/jquery.boot.js"></script>

<script type="text/javascript">

	$(function() {
		$("#submitUserForm").submit(function(e) {
			e.preventDefault();
			var frm = $("#submitUserForm");
			var data = {};
			$.each(this, function(i, v){
				var input = $(v);
				data[input.attr("name")] = input.val();
				delete data["undefined"];
			});
		//	alert(JSON.stringify(data));
			/*delete data._csrf;
			alert(JSON.stringify(data));*/

			///another sabe with fecth api
			postData(frm.attr("action"), data, frm.attr("method")).then((data) => {
				console.log(data); // JSON data parsed by `data.json()` call
			});

		//	saveRequestedData(frm, data, "user");
		});

		$("#submitAddressForm").submit(function(e) {
			e.preventDefault();
			var frm = $("#submitAddressForm");
			var data = {};
			$.each(this, function(i, v){
				var input = $(v);
				data[input.attr("name")] = input.val();
				delete data["undefined"];
			});
			saveRequestedData(frm, data, "address");
		});
	});

	// Example POST method implementation:
	async function postData(url = "", data = {}, pMethod = "") {
		// Default options are marked with *
		const response = await fetch(url, {
			method: pMethod, // *GET, POST, PUT, DELETE, etc.
			mode: "cors", // no-cors, *cors, same-origin
			cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
			credentials: "same-origin", // include, *same-origin, omit
			headers: {
				"Content-Type": "application/json",
				// 'Content-Type': 'application/x-www-form-urlencoded',
			},
			redirect: "follow", // manual, *follow, error
			referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
			body: JSON.stringify(data), // body data type must match "Content-Type" header
		});
		return response.json(); // parses JSON response into native JavaScript objects
	}


	function saveRequestedData(frm, data, type) {

		$.ajax({
			contentType:"application/json; charset=utf-8",
			type:frm.attr("method"),
			url:frm.attr("action"),
			dataType:'json',
			data:JSON.stringify(data),
			success:function(data) {
				if(data.status == "success") {
					toastr.success(data.message, data.title, {
						closeButton:true
					});
					fetchList(type);
				} else {
					alert(JSON.stringify(data));
					toastr.error(data.message, data.title, {
						allowHtml:true,
						closeButton:true
					});
				}
			},
			error:function(data) {
			 	alert(JSON.stringify(data));
			}
		});
	}

</script>
<div class="panel panel-default">
	<div class="panel-heading">
		<strong>
			<c:choose>
				<c:when test="${isNew}"><span class="glyphicon glyphicon-plus-sign"></span></c:when>
				<c:otherwise><span class="glyphicon glyphicon-edit"></span></c:otherwise>
			</c:choose> User
		</strong>
	</div>
	<form:form method="post" class="form-horizontal" action="${path}/user/add" commandName="userForm" id="submitUserForm">
		<form:hidden path="id"/>
		<div class="panel-body">
			<div class="form-group">
				<label class="col-md-2 control-label">Full Name : </label>
				<div class="col-md-4">
					<form:input class="form-control" path="fullName" placeholder="Enter Full Name" required="true"/>
				</div>
				
				<label class="col-md-2 control-label">User Id : </label>
				<div class="col-md-4">
					<form:input class="form-control" path="userId" placeholder="Enter User Id" required="true"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label">User Name : </label>
				<div class="col-md-4">
					<form:input class="form-control" path="userName" placeholder="Enter User Name" required="true"/>
				</div>
				
				<label class="col-md-2 control-label">Password : </label>
				<div class="col-md-4">
					<form:password class="form-control" path="password" placeholder="Enter Password" required="true"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label">Email : </label>
				<div class="col-md-4">
					<form:input class="form-control" path="email" placeholder="Enter Email Address" required="true"/>
				</div>
				
				<label class="col-md-2 control-label">Mobile : </label>
				<div class="col-md-4">
					<form:input class="form-control" path="mobile" placeholder="Enter Mobile Number" required="true"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label">Role :</label>
				<div class="col-md-4">
					<form:select class="form-control" path="roleId">
						<c:forEach items="${roles}" var="role">
							<form:option value="${role.id}">${role.name}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
		</div>
		<div class="panel-footer">
			<form:button value="Save" class="btn btn-xs btn-default">
				<span class="glyphicon glyphicon-floppy-disk"></span>
				<c:choose>
					<c:when test="${isNew}"> Save</c:when>
					<c:otherwise> Update</c:otherwise>
				</c:choose>
			</form:button>
		</div>
	</form:form>
</div>