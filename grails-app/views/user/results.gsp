<html>
    <head>
        <title>Search Results</title>
        <meta name="layout" content="main"/>
    </head>
    
    <body>
        <h1>Results</h1>
        <p>
        Found ${profiles.size()} hits.
        </p>
        <div class="list" style="margin: 10px">
        <table style="width: 600px;">
          <thead>
           <tr>
            <th>Full Name</th>
            <th>Email</th>
            <th>Country</th>
            <th>Time Zone</th>
           </tr>
          </thead>
          <tbody>
           <g:each in="${profiles}" status="i" var="profile">
           <tr class="\${(i % 2) == 0 ? 'odd' : 'even'}">
              <td>${profile.fullName}</td>
              <td>${profile.email}</td>
              <td>${profile.country}</td>
              <td>${profile.timeZone}</td>
           </tr>
          </g:each>
          </tbody>
        </table>
        </div>

        <g:link action='search'>Search Again</g:link>
    </body>
</html>
