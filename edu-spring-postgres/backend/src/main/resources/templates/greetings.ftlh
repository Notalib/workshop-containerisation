<!DOCTYPE HTML>
<html>
<head>
  <title>Greetings</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <style>body { margin: 40px; font-family: Roboto; font-size: 20px; } h1 { font-size: 1.5rem }</style>
</head>
<body>
  <h1>Greetings</h1>
  <table border="1" cellpadding="6" cellspacing="0">
    <thead>
      <tr><th>ID</th><th>Name</th></tr>
    </thead>
    <tbody>
      <#list greetings as greeting>
        <tr>
          <td>${greeting.id}</td>
          <td><a href="/greetings/${greeting.id}">${greeting.name}</a></td>
        </tr>
      <#else>
        <tr><td colspan="2">No greetings yet.</td></tr>
      </#list>
    </tbody>
  </table>
  <p>
    <a href="/new">Add a greeting</a>
    <form action="/seed" method="post" style="display:inline">
      <button type="submit">Seed default data</button>
    </form>
  </p>
</body>
</html>
