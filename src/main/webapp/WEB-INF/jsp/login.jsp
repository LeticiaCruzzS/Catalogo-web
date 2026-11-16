<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <%@ include file="_head.jspf" %>
  <title>Entrar - Meu Catálogo</title>
</head>
<body>
<div class="auth">
  <section class="auth-hero">
    <div class="brand">Meu Catálogo</div>
    <div>
      <h1>Livros, séries e filmes que eu já vi.</h1>
    </div>
    <small>Projeto Integrador - Ciência da Computação</small>
  </section>

  <section class="auth-panel">
    <div class="auth-box">
      <h2>Entrar</h2>

      <c:if test="${not empty erro}"><div class="alert alert-err"><c:out value="${erro}"/></div></c:if>
      <c:if test="${not empty sessionScope.mensagem}">
        <div class="alert alert-ok"><c:out value="${sessionScope.mensagem}"/></div>
        <c:remove var="mensagem" scope="session"/>
      </c:if>

      <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="field">
          <label for="login">Usuário</label>
          <input id="login" name="login" type="text" value="<c:out value='${login}'/>" required>
        </div>
        <div class="field">
          <label for="senha">Senha</label>
          <input id="senha" name="senha" type="password" required>
        </div>
        <button class="btn btn-primary btn-block" type="submit">Entrar</button>
      </form>

      <p class="auth-foot">Não tem conta? <a href="${pageContext.request.contextPath}/cadastro">Criar conta</a></p>
    </div>
  </section>
</div>
</body>
</html>
