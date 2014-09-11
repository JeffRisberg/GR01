<html>
    <head>
        <title>Profile: ${profile.fullName}</title>
        <meta name="layout" content="main"/>
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
                <img src="<g:createLink controller='image' action='renderImage' id='${userId}'/>"/>
            </g:if>
            <p>Profile for <strong>${profile.fullName}</strong></p>
            <p>Email: ${profile.email}</p>
            <p>Country: ${profile.country}</p>
            <p>Time Zone: ${profile.timeZone}</p>
        </div>
    </body>
</html>
