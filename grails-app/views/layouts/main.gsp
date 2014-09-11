<html>
	<head>
		<title>
			<g:layoutTitle default="Welcome to GR01" />
		</title>
		<nav:resources />
		<link rel="stylesheet" href="<g:createLinkTo dir='css' file='main.css' />" />
		<g:layoutHead />
		<g:javascript library="application" />
	</head>
	<body>
	  <div id="page">
	  <g:render template="/header" />
		<div id="content">
		    <nav:render group="tabs" actionMatch="true" />
			  <g:layoutBody />
		</div>
		<g:render template="/footer" />
		</div>
	</body>
</html>