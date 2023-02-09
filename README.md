# GenericProductList

![Android CI](https://github.com/TosinRoger/GenericProductList/actions/workflows/androidCI_main.yml/badge.svg?branch=main)
[![Coverage](.github/badges/jacoco.svg)](https://github.com/TosinRoger/GenericProductList/actions/workflows/androidCI_main.yml)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/TosinRoger/GenericProductList)

## Descrição em Português

Lista de produtos genérico

Um projeto para testar algumas possibilidades de uso do JetPack Android, em um projeto parecido com um projeto real.
A ideia inicial é ter uma lista de produtos, seja vinda de um JSON remoto ou adicionado localmente, possibilitando adicionar, editar e excluir este produtos. Possibilitar filtrar a lista, seja por filtros pré definidos ou busca por texto.

# Recursos avançados:
- Poder visualizar a lista por categorias (no esquema seção e subseção);
- Ter hierarquia nas subseções (ex: Categoria "eletrônico" subcategoria "telefone");
- Ter migração de DB, para editar as tabela existes ou  adicionar mais tabelas;

# Tecnologias que devem ser usada:
- [ ] Paging -> para lista pagina no RecyclerView
- [ ] Room -> para banco de dados
- [ ] LiveData -> para notificação da alteração dos dados
- [ ] Teste de unidade -> teste automatizado para garantir regra de negócio, apresentação dos dados, etc;
- [ ] Navigation -> para gerenciar a transição de Fragments;
- [ ] Coroutines -> para gerenciamento de thread;

# Tecnologia que podem ser estudadas:
- [ ] Hilt -> injeção de dependência, que deve ser usado na implementação da requesta HTTP;
- [ ] Sincronização -> usar arquitetura similar à sugerida pelo Google (link);
