<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <%@ include file="_head.jspf" %>
  <title>Meu Catálogo</title>
</head>
<body>
<%@ include file="_topo.jspf" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<main class="container">
  <div class="page-head">
    <h1>Meu catálogo</h1>
    <a class="btn btn-primary" href="${ctx}/itens?acao=novo">+ Novo item</a>
  </div>

  <c:if test="${not empty sessionScope.mensagem}">
    <div class="alert alert-ok"><c:out value="${sessionScope.mensagem}"/></div>
    <c:remove var="mensagem" scope="session"/>
  </c:if>

  <div class="stats">
    <a class="stat ${empty tipo ? 'ativo' : ''}" href="${ctx}/itens">
      <span>Todos</span><strong>${totalLivros + totalSeries + totalFilmes}</strong>
    </a>
    <a class="stat ${tipo == 'LIVRO' ? 'ativo' : ''}" href="${ctx}/itens?tipo=LIVRO">
      <span>Livros</span><strong>${totalLivros}</strong>
    </a>
    <a class="stat ${tipo == 'SERIE' ? 'ativo' : ''}" href="${ctx}/itens?tipo=SERIE">
      <span>Séries</span><strong>${totalSeries}</strong>
    </a>
    <a class="stat ${tipo == 'FILME' ? 'ativo' : ''}" href="${ctx}/itens?tipo=FILME">
      <span>Filmes</span><strong>${totalFilmes}</strong>
    </a>
  </div>

  <div class="card lista">
    <div class="card-head">
      <form class="search" action="${ctx}/itens" method="get">
        <input type="text" name="busca" value="<c:out value='${busca}'/>" placeholder="Buscar por título, autor ou gênero">
        <button class="btn btn-ghost" type="submit">Buscar</button>
      </form>
    </div>

    <c:if test="${empty itens}">
      <div class="empty">Nenhum item encontrado.</div>
    </c:if>

    <div class="livros">
      <c:forEach var="i" items="${itens}">
        <article class="livro">
          <div class="capa capa-${i.tipo}">
            <small>${i.tipo.descricao}</small>
            <b><c:out value="${i.titulo}"/></b>
          </div>
          <div class="livro-corpo">
            <div class="livro-meta">
              <c:out value="${i.criador}"/>
              <c:if test="${not empty i.genero}"> - <c:out value="${i.genero}"/></c:if>
            </div>
            <div class="estrelas">${i.estrelas}</div>
          </div>
          <div class="livro-rodape">
            <span class="ano">${i.ano}</span>
            <div class="actions">
              <a class="btn btn-sm btn-edit" href="${ctx}/itens?acao=editar&id=${i.id}">Editar</a>
              <form action="${ctx}/itens" method="post" onsubmit="return confirm('Excluir este item?');">
                <input type="hidden" name="acao" value="excluir">
                <input type="hidden" name="id" value="${i.id}">
                <button class="btn btn-sm btn-del" type="submit">Excluir</button>
              </form>
            </div>
          </div>
        </article>
      </c:forEach>
    </div>
  </div>
</main>
</body>
</html>
