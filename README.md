# Graphe Et Ontologies

[![Build Status](https://travis-ci.org/jfourmond/Graphe-Et-Ontologies.svg?branch=master)](https://travis-ci.org/jfourmond/Graphe-Et-Ontologies)

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
- [x] Framework pour la gestion de graphe		:package: 
- [x] Visualisation

Le développement de cette partie se retrouve dans le commit [suivant](https://github.com/jfourmond/Graphe-Et-Ontologies/commit/9fc66969087589aeebd0c4a2a8ac063c2ddcccdc). Certaines parties ne convenant pas aux besoins nécessaires, le code a été réorganisé et reproduit. L'interface **SWING** a été retirée pour se concentrer essentiellement sur **JavaFX**.

### 2. Seconde étape

- [x] Lecture d'Ontologies		:deciduous_tree:
- [x] Visualisation d'Ontologies

### 3. Troisième étape

- [x] Sauvegarde d'Ontologies
- [ ] Interaction avec l'Ontologie

### 4. Quatrième étape

- [ ] Manuel d'utilisation

---

## III. Compilation & Exécution

### 1. Compilation

	ant build
	
### 2. Exécution

L'exécution s'effectue ainsi, en ligne de commande :

	ant run

### 3. Javadoc

La génération de la javadoc est disponible en exécutant la cible :

	ant javadoc

### 4. Java Archive

La génération d'une archive jar exécutable en exécutant la cible :

	ant jar

---

## IV. Le Frawework pour la gestion de graphe

L'utilisation du [framework](https://github.com/jfourmond/Graphe-Et-Ontologies/tree/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework) s'effectue aisément. Il est composé de 9 classes, dont 3 pour les exceptions. La plupart sont dépendantes entre elles, mais certaines peuvent s'utiliser en solitaire.
Un graphe doit pouvoir être crée en lisant un fichier xml avec une dtd reprenant une définition valide pour le programme. Des chaînes de caractère (**String**) sont donc principalement utilisées.

### 1. Pair<Type 1, Type 2>

[Pair](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Pair.java) est une classe générique. Elle permet de manipuler des paires d'*Object*.
Par exemple :

	Pair<String, Integer> p1 = new Pair<>();					// Création d'une paire
	p1.setFirst("Population");								// Edition du premier membre
	p1.setSecond(7348);										// Edition du second membre
	Pair<Integer, String> p2 = new Pair<>(1, "Numéro 1");	// Création d'une paire, initialisée

### 2. Sommet - *Vertex*

[Vertex](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Vertex.java) est une classe représentant un sommet.
Elle comporte deux attributs :
- Un *ID* : sous la forme d'un **String**
- Une **Map<String, String>** associant attributs et valeurs
Et différentes méthodes pour sa manipulation, ainsi qu'une classe pour la gestion des **Exceptions**.
Cette classe sera utilisée dans la modélisation du graphe.
Par exemple :

	Vertex sommet = new Vertex("1");	// Création d'un sommet avec l'ID "1"
	sommet.add("Nom");					// Création d'un attribut "Nom"
	sommet.set("Nom", "Jerome");			// Edition de la valeur de l'attribut "Nom"
	String nom = sommet.get("Nom");		// Récupération de la valeur de l'attribut "Nom"

### 3. Relation - *Relation*

La modélisation d'un graphe utilisée ici peut posséder plusieurs relations (*types d'arcs*). La classe [Relation](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Relation.java) est une classe représentant une *Relation* et les différents arcs en découlant. Elle est composée de deux attributs :
- Un *nom* : sous la forme d'un **String**, fonctionne comme un identifiant
- Une Liste de [Pair](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Pair.java)<Vertex, Vertex> représentant les arcs entre les différents sommets
Bien entendu, la classe contient également différentes méthodes pour la manipulation de ses instances, ainsi qu'une classe pour la gestion des **Exceptions**.
Cette classe sera utilisée dans la modélisation du graphe.
Par exemple :

	Vertex v1 = new Vertex("1");
	Vertex v2 = new Vertex("2");
	Relation relation = new Relation("est voisin de");		// Création d'une relation
	Pair<Vertex, Vertex> pair = new Pair<>(v1, v2);
	relation.add(pair);										// Ajout de la paire à la relation

### 4. Graphe - *Tree*

[Tree](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Tree.java) représente un graphe. Il est composé de deux attributs *principaux* :
- Une liste de [Vertex](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Vertex.java), dont l'identifiant de chacun doit être unique
- Une liste de [Relation](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Relation.java), dont le nom de chacune doit être unique
Il peut être construit dynamiquement.
La classe possède également ses propres **Exceptions**.

L'ajout d'un sommet dans le graphe s'effectue grâce à différentes méthodes : 
	
	Tree tree = new Tree();				// Création de l'arbre
	tree.createVertex("1");				// Création d'un sommet portant l'identifant "1" dans l'arbre
	Vertex vertex = new Vertex("2");
	tree.createVertex(vertex);			// Ajout d'un sommet dans l'arbre

L'ajout d'un arc dans le graphe s'effectue après différentes étapes, il faut créer la relation, puis y ajouter les paires.

	tree.createRelation("est voisin de");					// Création d'une relation dans l'arbre
	tree.addPair("est voisin de", "1", "2");				// Ajout d'une paire dans la relation "est voisin de", entre le sommet portant l'identifiant "1" et le sommet portant l'identifiant "2"
	
	Relation relation = new Relation("est parent de");
	Vertex p1 = new Vertex("3");
	Vertex p2 = new Vertex("4");
	Pair<Vertex, Vertex> pair = new Pair<>(p1, p2);
	relation.add(pair);
	tree.createRelation(relation);							// Création d'une relation dans l'arbre

### 5. Chargement de l'arbre - *TreeLoader*

[TreeLoader](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/TreeLoader.java) est une classe permettant de gérer le chargement d'un fichier XML.

	TreeLoader loader = new TreeLoader(tree, F);
	new Thread(loader).start();

### 6. Sauvegarde de l'arbre - *TreeSaver*

[TreeSaver](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/TreeSaver.java) est une classe permettant de gérer la sauvegarde d'un arbre dans un fichier XML.

	TreeSaver saver = new TreeSaver(tree);
	new Thread(saver).start();

### 7. Positionnement - *Placement*

[Placement](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Placement.java) est une classe reprenant le concept de la classe Random de Java. A chaque appel de sa méthode next(), l'instance de la classe retourne le Point suivant contenu dans un tableau. Lorsque ce dernier est vide, un appel à next() générera aléatoirement un Point.
**Placement** contient, pour le moment, 24 Point *statiques*.
Par exemple :

	Placement p = new Placement();
	Point point = p.next();

### 8. Couleur - *ColorDistribution*

[ColorDistribution](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/ColorDistribution.java) est une classe reprenant le concept de la classe Random de Java. A chaque appel de sa méthode next(), l'instance de la classe retourne la Color suivante contenue dans un tableau. Lorsque ce dernier est vide, un appel à next() générera aléatoirement une Color.
**ColorDistribution** contient, pour le moment, 9 Color *statiques*.
Par exemple :

	ColorDistribution cd = new ColorDistribution();
	Color color = cd.next();
	
**N.B** : Color, ici, fait partie des packages de **JavaFX** (*javafx.scene.paint*)

---

## V. L'Affichage

L'affichage d'un arbre s'effectue sous JavaFX.

### 1. Sommet - *VertexView*

Un sommet est représenté par un **Group**. Ce dernier contient et dessine un cercle et un label, contenant la description brève du sommet correspondant.

#### b. Arc - *EdgeView*

Un arc est représenté par un **Group**. Ce dernier contient et dessine une ligne entre deux **VertexView** et un label, contenant le/la nom/valeur de l'arc correspondant.

#### c. Graphe - *TreeView*

L'arbre contient l'ensemble des **Group** (**VertexView** & **EdgeView**) et est lui-même un **Group**. En fonction de la variable de type **Tree** passée en paramètre, il construit ses sommets et ses arcs et leur accorde différents *listeners*.

#### d. Application - *Launcher*

Cette classe permet d'ouvrir l'application.

--- 

## VI. Les Ontologies, en XML

### 1. La DTD (*Document Type Definition*)

Le fichier XML utilisé dans la construction d'un arbre d'ontologie doit respecter une DTD précise. Elle doit reprendre la construction des fichiers suivants (*Utilisés comme exemple ici*) :
- [Index327.dtd](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Ontologies/index327.dtd)
- [Villes.dtd](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Ontologies/villes.dtd)

### 2. Le Chargement

Lors du chargement du fichier, le programme vérifie s'il est valide. Puis crée un [Tree](https://github.com/jfourmond/Graphe-Et-Ontologies/blob/master/Graphe-Et-Ontologies/src/fr/fourmond/jerome/framework/Tree.java) manipulable par la suite.

### 3. L'Ecriture

L'écriture du fichier s'effectue grâce à la libraire JDOM. Dans le fichier spécifié par l'attribut *file* de l'instance.