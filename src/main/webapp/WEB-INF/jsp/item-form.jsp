<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <%@ include file="_head.jspf" %>
  <title>Item - Meu Catálogo</title>
</head>
<body>
<%@ include file="_topo.jspf" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<main class="container">
  <div class="page-head">
    <h1>${empty item.id ? 'Novo item' : 'Editar item'}</h1>
  </div>

  <div class="card form-card">
    <c:if test="${not empty erro}"><div class="alert alert-err"><c:out value="${erro}"/></div></c:if>

    <form action="${ctx}/itens" method="post">
      <input type="hidden" name="acao" value="salvar">
      <input type="hidden" name="id" value="${item.id}">

      <div class="field">
        <label>Tipo *</label>
        <div class="tipo-opcoes">
          <c:forEach var="t" items="${tipos}">
            <label class="tipo-opcao">
              <input type="radio" name="tipo" value="${t}" ${item.tipo == t ? 'checked' : ''}>
              <span>${t.descricao}</span>
            </label>
          </c:forEach>
        </div>
      </div>

      <div class="field">
        <label for="titulo">Título *</label>
        <input id="titulo" name="titulo" type="text" value="<c:out value='${item.titulo}'/>">
      </div>

      <div class="grid-2">
        <div class="field">
          <label for="criador">Autor / Diretor</label>
          <input id="criador" name="criador" type="text" value="<c:out value='${item.criador}'/>">
        </div>
        <div class="field">
          <label for="genero">Gênero</label>
          <input id="genero" name="genero" type="text" value="<c:out value='${item.genero}'/>">
        </div>
      </div>

      <div class="grid-2">
        <div class="field">
          <label for="ano">Ano</label>
          <input id="ano" name="ano" type="text" value="<c:out value='${item.ano}'/>">
        </div>
        <div class="field">
          <label for="nota">Nota</label>
          <select id="nota" name="nota">
            <option value="">Sem nota</option>
            <c:forEach var="n" begin="1" end="5">
              <option value="${n}" ${item.nota == n ? 'selected' : ''}>${n}</option>
            </c:forEach>
          </select>
        </div>
      </div>

      <div class="field">
        <label for="descricao">Comentário</label>
        <textarea id="descricao" name="descricao" rows="4"><c:out value="${item.descricao}"/></textarea>
      </div>

      <div class="form-actions">
        <a class="btn btn-ghost" href="${ctx}/itens">Cancelar</a>
        <button class="btn btn-primary" type="submit">Salvar</button>
      </div>
    </form>
  </div>
</main>
</body>
</html>
