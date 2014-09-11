<html>
	<head>
		<title>
			<g:layoutTitle default="Welcome to GR01" />
		</title>
		<link rel="stylesheet" href="<g:createLinkTo dir='css' file='main.css' />" />
			<g:layoutHead />
			<g:javascript library="application" />
	</head>
	<body>
	  <div id="page">
		<div id="header" style="font-size: 18pt">
			   Welcome
			   <g:if test="${session.user != null}">
			     ${session.user.profile.fullName}
			   </g:if>
		</div>
		<div id="content">
			  <g:layoutBody />
		</div>
		<g:render template="/footer" />
	</div>
	</body>
</html>