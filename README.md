# Graphe Et Ontologies

Travaux Encadrés de Recherche sous la supervision de :
* Dr. RICHER Jean-Michel
* Dr. AÏT EL MEKKI Touria

Par :
* [FOURMOND Jérôme](https://github.com/jfourmond)

En développement sous la version :
- *1.8.0_77* de **Java**

---

## I. L'objectif

On désire créer une application en Java pour la navigation dans un document électronique à l'aide d'un graphe d'ontologie afin d’en faciliter l’étude.
[Plus de spécification](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/B-ontologie.pdf)

---

## II. Etapes & Avancement

### 1. Première étape

- [x] [Diagramme de classe](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/class_diagram)
- [x] Framework pour la gestion de graphe :package: 
- [x] Visualisation

### 2. Seconde étape

...

---

## III. Compilation & Exécution

### 1. Compilation

	ant build
	
### 2. Exécution

L'exécution s'effectue ainsi, en ligne de commande :
- pour le Main basique

		ant run-default

- pour le CityExample, exemple concret du programme :
	
		ant run-city

- pour le FxCityExample, exemple concret du programme sous JavaFX :

		ant run-cityfx
		
### 3. Javadoc

La génération de la javadoc est disponible en exécutant la cible :

	ant javadoc

### 4. Java Archive

La génération d'une archive jar exécutable en exécutant la cible :
- pour le Main basique :

		ant jar-default
	
- pour le CityExample, exemple concret du programme :

		ant jar-city

- pour le FxCityExample, exemple concret du programme sous JavaFX :

		ant jar-cityfx

---

## IV. Le Frawework pour la gestion de graphe

L'utilisation du **framework** s'effectue aisément.

### 1. Sommet - *Vertex*

Dans un premier temps, il est nécessaire d'implémenter l'interface [Vertex](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Vertex.java).
Cette nouvelle classe sera utilisé dans l'utilisation du graphe.
Par exemple :

	DefaultVertex sommet1 = new DefaultVertex(11);
	DefaultVertex sommet2 = new DefaultVertex(22);

### 2. Arc - *Edge*

La classe de l'implémentation de **Vertex** et le type du libellé de l'arc sont requis dans la déclaration de [Edge](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Edge.java).
Par exemple, en conservant l'implémentation précédente :

	Edge<DefaultVertex, Integer> arc = new Edge<>(sommet1, sommet2, 33);
	
Ce qui déclare et définit un arc entre le *sommet1* et le *sommet2*, ayant pour libellé *33*.

### 3. Graphe - *Tree*

Dans la déclaration de [Tree](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Tree.java), deux types doivent être précisés : la classe de l'implémentation du Vertex, et le type du libellé de l'arc.
L'ajout d'un sommet dans le graphe s'effectue grâce à la méthode : **addVertex**.
L'ajout d'un arc dans le graphe s'effectue grâce à la méthode : **addEdge**
Par exemple :

	Tree<DefaultVertex, Integer> tree = new Tree<>();
	tree.addVertex(sommet1);
	tree.addVertex(sommet2);
	tree.addEdge(arc);

### 4. Positionnement - *Placement*

[Placement](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Placement.java) est une classe reprenant le concept de Random de Java. A chaque appel de sa méthode next(), l'instance de la classe retourne le Point suivant contenu dans un tableau. Lorsque ce dernier est vide, un appel à next() générera aléatoirement un Point.
**Placement** contient, pour le moment, 24 Point *statiques*. 

---

## V. L'Affichage

L'affichage d'un arbre peut s'effectuer sous Swing ou sous JavaFX.

### 1. Swing

#### a. Sommet - *VertexView*

Un sommet est représenté par un **JComponent**. Ce dernier dessine un cercle et lui accorde un espace en accord avec le rayon du cercle (d'où l'affichage limité des informations brèves du sommet).

#### b. Arc - *EdgeView*

Un arc est représenté par un **JComponent**. Ce dernier dessigne une ligne entre deux **VertexView**, son espace est calculé en fonction de l'écart entre les deux sommets.

Il est à noter qu'à proprement parlé la ligne n'est pas placé par cette classe mais bien par **TreeView**.

#### c. Graphe - *TreeView*

L'arbre est dessiné comme un ensemble de **VertexView** et de **EdgeView**, dans un JPanel. **TreeView** affiche l'arbre au centre, ainsi que des informations sur ce dernier ou le dernier sommet *cliqué* à droite dans un **JTextArea**.
Cette classe place les sommets en leur associant des coordonnées aléatoires, pouvant donner un affichage satisfaisant comme un affichage illisible. **Ce point est donc à retravailler.**
Les sommets sont cliquables et peuvent être déplacées, contrairement aux arcs.

#### d. La Fenêtre - *Window*

Le tout est contenu dans un **JFrame** possédant différents menus :
- Fichier
	* Ouvrir : permet d'ouvrir un fichier représentant une ontologie.
	* Quitter : quitte l'application.
- Edition
	* Ontologie : ouvre l'éditeur par défaut du système sur le fichier actuellement ouvert, pour modification.
	
### 2. JavaFX

#### a. Sommet - *VertexFxView*

Un sommet est représenté par un **Group**. Ce dernier contient et dessine un cercle et un label, contenant la description brève du sommet correspondant.

#### b. Arc - *EdgeFxView*

Un arc est représenté par un **Group**. Ce dernier contient et dessine une ligne entre deux **VertexView** et un label, contenant la valeur de l'arc correspondant.

#### c. Graphe - *TreeFxView*

L'arbre contient l'ensemble des **Group** (**VertexFxView** & **EdgeFxView**) et est lui-même un **Group**. En fonction de la variable de type **Tree** passée en paramètre, il construit ses sommets et ses arcs et leur accorde différents *listeners*.
Les sommets sont cliquables (l'affichage s'effectue, pour le moment dans la console).

#### d. Application - *FxCityExample*

Cette classe permet d'ouvrir l'application, d'y créer la fenêtre ainsi que le menu. Elle possède différents menus :
- Fichier
	* Ouvrir : permet d'ouvrir un fichier représentant une ontologie.
	* Quitter : quitte l'application.
- Edition
	* Ontologie : ouvre l'éditeur par défaut du système sur le fichier actuellement ouvert, pour modification.
