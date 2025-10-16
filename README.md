<img src="imgs/UNIFOR_logo1b.png" width="400"> 

# T290 - Resolução de Problemas com Grafos
Orientador: Prof. Me Ricardo Carubbi

## T2 - Problema do Caixeira Viajante

<img src="imgs/logo.png" width="400"> 

### Introdução

Dado um conjunto de $N$ pontos no plano, queremos um tour (ciclo hamiltoniano) que visite cada ponto exatamente uma vez e retorne ao início, minimizando a distância total. Consideraremos o TSP simétrico e métrico, com custo dado pela distância euclidiana. Como o TSP é NP-difícil, adotaremos a **heurística gulosa Vizinho Mais Próximo (Nearest Neighbor, NN)**: partindo de um vértice inicial, sempre escolhemos, entre os ainda não visitados, o de menor distância ao vértice atual. O objetivo é implementar o **NN**, reportar o tour encontrado, o custo total e o tempo de execução, discutindo brevemente os resultados.

<img src="imgs/TSP-1000.png" width="800"> 

### Perspectiva

A relevância do TSP vai muito além de “vendedores” minimizando rotas. Ele modela problemas reais em:

- Logística e transportes: roteamento de veículos e coletas.
- Manufatura/eletrônica: perfuração de PCBs, projeto VLSI, corte/usinagem.
- Robótica: planejamento de trajetórias.
- Ciência/engenharia: cristalografia de raios-X, microscopia por varredura.
- Computação científica: escalonamento de tarefas e biologia computacional (ex.: ordenação de fragmentos).

Por ser NP-difícil, o TSP motiva o estudo de heurísticas rápidas que produzem soluções boas em tempo polinomial.

### Heurísticas Gulosas (revisada)

Enumerar todos os ciclos é inviável: no TSP simétrico há $(N−1)!/2$ tours distintos (ordem ~N!). Para N grande, não há algoritmo conhecido que garanta o ótimo em tempo polinomial. Neste trabalho, você implementará a **heurística gulosa Vizinho Mais Próximo (Nearest Neighbor, NN)**.

#### Vizinho Mais Próximo (NN)

Ideia: começar em um vértice $s$ e, repetidamente, ir para o vértice não visitado mais próximo do vértice atual. Ao visitar todos, fechar o ciclo retornando a $s$.

### Tipo de dado Point

```java
public class Point {
    Point(double x, double y)  // cria o ponto (x, y)
    String toString()          // representação em string
    void draw()                // desenha o ponto
    void drawTo(Point that)    // desenha segmento até outro ponto
    double distanceTo(Point that)  // distância euclidiana
}
```

A classe `Point` é fornecida para uso.

### Tipo de dado Tour

Implemente a classe `Tour` representando o ciclo como uma lista encadeada circular de `Node`s:

```java
private class Node {
    private Point p;
    private Node next;
}
```

API da classe `Tour`:

```java
public class Tour {
    Tour()  // cria um ciclo vazio
    Tour(Point a, Point b, Point c, Point d) // cria ciclo a->b->c->d->a
    void show()
    void draw()
    int size()
    double distance()
    void insertNearest(Point p)
}
```

### Entrada e Testes

O formato de entrada é: dois inteiros w e h, seguidos por pares (x, y).

Exemplo de entrada (`tsp1000.txt`):

```
775 768
185.0411 457.8824
247.5023 299.4322
701.3532 369.7156
...
```

## Objetivo
Template para implementar a **heurística do Vizinho Mais Próximo** (Nearest Neighbor, NN) no TSP. As atividades estão divididas em duas etapas: começar com a versão ingênua e, em seguida, otimizar com `KdTree`.

## Etapas

As atividades estão divididas em duas etapas: começar com a versão ingênua e, em seguida, otimizar com `KdTree`.

1. Implementar `insertNearestNaive` em `Tour.java`, varrendo a lista encadeada circular para encontrar o vizinho mais próximo.
2. Implementar `algs4/KdTree.java` e adaptar `insertNearest` para utilizar `insertNearestKd`, reduzindo o custo de busca.
3. Preencher `questoes.txt` com respostas conceituais, comprimentos de tour e medições de tempo (comparando as duas abordagens).

## Estrutura do template
- `src/`: classes principais (`Tour`, `Point`, `NearestInsertion`, `TSPVisualizer`, `TSPTimer`).
- `src/algs4/`: utilitários necessários da biblioteca algs4, incluindo um stub de `KdTree`.
- `data/`: coloque aqui os arquivos de teste (p. ex. `tsp10.txt`, `tsp100.txt`, ...).
- `results/`: contém referências de saída para `tsp10.txt`.
- `docs/`: contém referência para diversas heurísticas para o problema.
- `imgs`: contém as imagens para o `README.md`.

## Fluxo sugerido
1. Complete os métodos faltantes em `Tour` utilizando apenas a varredura ingênua e valide com `NearestInsertion`.
2. Implemente `KdTree` e a versão otimizada, comparando os tempos com `TSPTimer` (semente fixa em `StdRandom.setSeed(123456789L)`).
3. Atualize `questoes.txt` com os comprimentos obtidos e as tabelas de tempo (ingênuo vs. `KdTree`).

## Compilação 
No diretório raiz do projeto, execute:

```bash
javac -cp src src/algs4/*.java src/*.java 
```
## Execução
No diretório raiz do projeto, execute:

```bash
java -cp src NearestInsertion data/tsp10.txt
java -cp src TSPVisualizer data/tsp10.txt 
java -cp src TSPTimer 10
```

## Referências de resultado
- `results/tsp10-nearest.ans`: evolução do tour gerado pela heurística nearest (útil para depurar).
- `results/tsp10-optimal.ans`: solução ótima conhecida para comparação.

## Entrega
Submeta `Tour.java`, `KdTree.java` e `questoes.txt` preenchidos com as análises solicitadas.