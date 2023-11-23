import networkx as nx
import matplotlib.pyplot as plt
import csv
import numpy as np
import time

# Leer la matriz desde el archivo CSV
matriz_adyacencia = []
with open('matriz.csv', 'r') as archivo_csv:
    lector_csv = csv.reader(archivo_csv)
    for fila in lector_csv:
        fila_enteros = [int(valor) if valor.strip() else 0 for valor in fila]
        matriz_adyacencia.append(fila_enteros)


# Crear un grafo no dirigido
G = nx.Graph()

# Agregar nodos al grafo
nodos = range(1,len(matriz_adyacencia)+1)
G.add_nodes_from(nodos)

# Agregar aristas con pesos desde la matriz de adyacencia
for i in nodos:
    for j in range(i + 1, len(nodos)+1):
        if matriz_adyacencia[i-1][j-1] > 0:
            G.add_edge(i, j, weight=matriz_adyacencia[i-1][j-1])

# Dibujar el grafo
pos = nx.spring_layout(G)
labels = nx.get_edge_attributes(G, 'weight')
nx.draw(G, pos, with_labels=True, node_size=500, node_color='skyblue', font_size=10)
nx.draw_networkx_edge_labels(G, pos, edge_labels=labels)

# Guardar el grafo en un archivo JPG
plt.savefig("grafo_no_dirigido_con_pesos.jpg", format="jpg")
plt.pause(3)
plt.close()