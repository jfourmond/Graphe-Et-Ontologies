# Graphe Et Ontologies

## Travaux Encadrés de Recherche

Sous la supervision de :
* Dr. RICHER Jean-Michel
* Dr. AÏT EL MEKKI Touria

Par :
* [FOURMOND Jérôme](https://github.com/jfourmond)

En développement sous la version :
	- 1.8.0_77 de **Java**

---

## L'objectif

On désire créer une application en Java pour la navigation dans un document électronique à l'aide d'un graphe d'ontologie afin d’en faciliter l’étude.
[Plus de spécification](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/B-ontologie.pdf)

---

## Etapes & Avancement

### Première étape

- [x] [Diagramme de classe](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/class_diagram)
- [ ] Framework pour la gestion de graphe :package: 
- [ ] Visualisation

---

## Compilation & Exécution

### Compilation

	ant build
	
La génération de la javadoc est disponible en exécutant la cible :

	ant javadoc

### Exécution

L'exécution s'effectue (*pour le moment*) ainsi, en ligne de commande :

	java -cp bin Main

---

## Le Frawework pour la gestion de graphe

L'utilisation du **framework** s'effectue aisément.

### Sommet -> *Vertex*
Dans un premier temps, il est nécessaire d'implémenter l'interface [Vertex](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Vertex.java).
Cette nouvelle classe sera utilisé dans l'utilisation du graphe.
Par exemple :

	DefaultVertex sommet1 = new DefaultVertex(11);
	DefaultVertex sommet2 = new DefaultVertex(22);

### Arc -> *Edge*
La classe de l'implémentation de **Vertex** et le type du libellé de l'arc sont requis dans la déclaration de [Edge](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Edge.java).
Par exemple, en conservant l'implémentation précédente :

	Edge<DefaultVertex, Integer> arc = new Edge<>(sommet1, sommet2, 33);
	
Ce qui déclare et définit un arc entre le *sommet1* et le *sommet2*, ayant pour libellé *33*.

### Graphe -> *Tree*
Dans la déclaration de [Tree](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Tree.java), deux types doivent être précisés : la classe de l'implémentation du Vertex, et le type du libellé de l'arc.
L'ajout d'un sommet dans le graphe s'effectue grâce à la méthode : **addVertex**.
L'ajout d'un arc dans le graphe s'effectue grâce à la méthode : **addEdge**
Par exemple :

	Tree<DefaultVertex, Integer> tree = new Tree<>();
	tree.addVertex(sommet1);
	tree.addVertex(sommet2);
	tree.addEdge(arc);