<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <%@ include file="_head.jspf" %>
  <title>Erro - Meu Catálogo</title>
</head>
<body>
<main class="erro-page">
  <div class="erro-box">
    <h1>Ops, deu algum erro.</h1>
    <p>A página não existe ou aconteceu um problema.</p>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/itens">Voltar</a>
  </div>
</main>
</body>
</html>
