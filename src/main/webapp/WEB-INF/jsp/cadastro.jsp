<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <%@ include file="_head.jspf" %>
  <title>Criar conta - Meu Catálogo</title>
</head>
<body>
<div class="auth">
  <section class="auth-hero">
    <div class="brand">Meu Catálogo</div>
    <div>
      <h1>Criar conta</h1>
    </div>
    <small>Projeto Integrador - Ciência da Computação</small>
  </section>

  <section class="auth-panel">
    <div class="auth-box">
      <h2>Criar conta</h2>

      <c:if test="${not empty erro}"><div class="alert alert-err"><c:out value="${erro}"/></div></c:if>

      <form action="${pageContext.request.contextPath}/cadastro" method="post">
        <div class="field">
          <label for="nome">Nome</label>
          <input id="nome" name="nome" type="text" value="<c:out value='${nome}'/>">
        </div>
        <div class="field">
          <label for="login">Usuário</label>
          <input id="login" name="login" type="text" value="<c:out value='${login}'/>">
        </div>
        <div class="field">
          <label for="senha">Senha</label>
          <input id="senha" name="senha" type="password">
        </div>
        <div class="field">
          <label for="confirmacao">Confirmar senha</label>
          <input id="confirmacao" name="confirmacao" type="password">
        </div>
        <button class="btn btn-primary btn-block" type="submit">Criar conta</button>
      </form>

      <p class="auth-foot">Já tem conta? <a href="${pageContext.request.contextPath}/login">Entrar</a></p>
    </div>
  </section>
</div>
</body>
</html>
