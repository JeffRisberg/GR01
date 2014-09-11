<html>
	<head>
		<title>Select Activity Types for ${profile.fullName}</title>
		<meta name="layout" content="main" />
		<style>
			.profilePic {
			border: 1px dotted gray;
			background: lightyellow;
			padding: 1em;
			font-size: 1em;
			}
</style>
	</head>
	<body>
		<div class="profilePic">
			<g:if test="${profile.photo}">
				<img src="
					<g:createLink controller='image' action='renderImage'
						id='${user.userId}' />
					"/>
			</g:if>
			<p>
				Select Activity Types for <strong>${profile.fullName}</strong>
			</p>
		</div>

		<g:form controller="user">
		  <g:each var="tuple" in="${activityTypes}" >
		     <g:checkBox name="ActType_${tuple[0].id}" value="${tuple[1]}"/>
		        ${tuple[0].name} (${tuple[0].activityCategory.name})
		    <br>
		    </br>
		  </g:each>
			<g:actionSubmit action="postActivityTypes" value="Submit" />
			<g:actionSubmit action="cancelActivityTypes" value="Cancel" />
		</g:form>
	</body>
</html>
