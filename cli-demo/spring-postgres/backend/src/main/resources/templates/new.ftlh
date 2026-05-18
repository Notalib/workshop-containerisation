<!DOCTYPE HTML>
<html>
<head>
  <title>New Greeting</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <style>body { margin: 40px; font-family: Roboto; font-size: 20px; }</style>
</head>
<body>
  <h1>Add a greeting</h1>
  <form action="/greetings" method="post">
    <label for="name">Name:</label>
    <input type="text" id="name" name="name" required />
    <button type="submit">Save</button>
  </form>
</body>
</html>
