<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>qlks</title>
  </head>
  <body>
    <script>
      const link = document.createElement("a");

      link.href = "${url?no_esc}";

      document.body.appendChild(link);

      link.click();
      document.removeChild(link)
    </script>
  </body>
</html>
