<html>
	<head>
		<title>Chart for: ${profile.fullName}</title>
		<meta name="layout" content="main" />
</style>
	</head>
	<body>
	  <div>
			<p>
				Chart for <strong>${profile.fullName}</strong>
			</p>
		</div>

    <g:pieChart type="3d"
        title="Activity Counts by Type"
        size="${[600,200]}"
        labels="${activitiesOnDay.keySet()}"
        dataType="simple"
        data="${activitiesOnDay.values().asList()}" />
        
    <g:lineChart type="lc"
        title="Activity Counts by Type"
        size="${[600,200]}"
        axes="x,y"
        gridLines="33,25"
        legend="${[ 'Count' ]}"
        axesLabels="${[0:activitiesOnDay.keySet(),
                       1:[0,activitiesOnDay.values().max()/2,
                           activitiesOnDay.values().max()]]}"
        dataType="simple"
        data="${[activitiesOnDay.values().asList()]}" />
	</body>
</html>
