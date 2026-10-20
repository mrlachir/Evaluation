# Evaluation — Three independent applications

Java 11+ and Maven. Each exercise has its own packages, application.properties,
HibernateUtil, generic IDao, CRUD services, demo and tests. They are separate Maven
modules because their required packages overlap. No Spring or application server is needed.

## Run

From PowerShell:

```powershell
cd C:\Users\PC\Documents\study_5\JEE\labs\Evaluation
mvn clean test
mvn -pl exercice1-stock compile exec:java
mvn -pl exercice2-projets compile exec:java
mvn -pl exercice3-etat-civil compile exec:java
```

Tests: 4 per exercise, 12 total. Each test uses an isolated H2 database.
In IntelliJ open the root pom.xml and run ma.projet.App from the desired module.
There are three App classes; choose the correct module's classpath in the run configuration.
Run each module's EvaluationTest, or run all tests from the root.

## Databases

The demo defaults to an in-memory H2 database, independently for each exercise.
The application.properties files contain MySQL configurations. To use MySQL:

1. Run each exercise's database.sql against your MySQL server.
2. Configure DB_URL, DB_USER and DB_PASSWORD environment variables, or edit the
   exercise's application.properties. Do not commit real credentials.
3. Add -Deval.db=mysql to the Maven command or IntelliJ VM options.

Example:

```powershell
mvn -pl exercice3-etat-civil compile exec:java "-Deval.db=mysql"
```

Hibernate creates/updates tables for every registered entity. MySQL must already be
running and the configured account must have schema permissions. MySQL database creation
and execution have not been verified locally; the supplied scripts/configuration enable them.
H2 is for runnable demos/tests and does not create a MySQL database.
Repeated demo runs against MySQL should use fresh data (the stock demo uses unique names/references).

## Exercise 1 — Stock

Entities: Categorie, Produit, Commande, LigneCommande in ma.projet.classes.
All four requested services implement IDao via AbstractService.

- Products by category.
- Distinct ordered products between two inclusive order dates.
- Order details with reference, price and quantity.
- Named query for prices strictly greater than 100 DH (100 itself is excluded).
- Demo order: ES12 120 DH x7, ZR85 100 DH x14, EE85 200 DH x5.
- Persistent order IDs are generated; they need not equal the example's ID 4.
- The model stores product prices, not historical line prices; changing a price affects
  later order displays. No stock-movement ledger was specified, so none is inferred.

## Exercise 2 — Projects

Entities: Employe, Projet, Tache, EmployeTache in ma.projet.classes.

- Employee's completed tasks and managed projects.
- Project's planned tasks and completed task assignments with actual dates.
- Named query for task prices strictly above 1000 DH.
- Completed tasks between two dates: actual start >= lower bound and actual end <= upper
  bound; ongoing assignments (null real end) are excluded.
- An assignment models the employee/task association and its real dates.
  The same task may have several employee assignments; its detail rows are per assignment.
- Global project cost is the sum of task prices, once per task. The statement supplies
  no hourly rates or hours, so hourly payroll costing is not assumed.
- Demo: four planned tasks, three completed by Alice, two over 1000, total cost 5700 DH.

## Exercise 3 — Civil status

Entities: Homme, Femme, Mariage in ma.projet.beans.

- Seeds ten women and five men, lists women and selects the oldest by birth date.
- Wives query finds marriages overlapping the inclusive interval.
- Named native query aggregates children for marriages STARTING inside the inclusive interval.
  The available model stores a child count per marriage, not child birth dates.
  It therefore cannot count births actually occurring in that period.
- Named JPQL query counts marriage records to identify women married at least twice.
- Criteria API counts distinct wives per man with marriages overlapping the interval
  and selects exactly four. It does not establish four simultaneous marriages throughout
  the entire interval; that stronger rule would need a separately specified time rule.
- A null marriage end means ongoing; a non-null end means ended, as in the statement's output.
- Detailed displays show the woman, start/end dates and child count.
- Dates are validated; negative child counts are rejected. These are educational model
  rules, not civil-law validation or a legal marriage registration system.

## Technical notes

One SessionFactory per application is shared as the JPA EntityManagerFactory.
Every service call closes its EntityManager; writes roll back on failures.
Entities returned by services are detached. Required to-one relationships are eager in this
small model; there are no exposed lazy collections. Query input values use bound parameters.
No diagram was supplied, so attributes and relationships were derived from the requested queries.

## Screenshots to take

Save real screenshots under screenshots/ (Win + Shift + S).

### Exercise 1
1. Entity/service project tree and application.properties.
2. Products by category and ordered products between dates.
3. Order detail table: ES12 / ZR85 / EE85, prices and quantities.
4. Named query code and results: ES12, EE85 (not ZR85).
5. Four passing stock tests.

### Exercise 2
1. Entity/service tree and employee/project queries.
2. Planned tasks and completed assignments with actual dates.
3. Named-query results above 1000, date search, and global cost 5700 DH.
4. Four passing project tests.

### Exercise 3
1. Ten women/five men population output and oldest woman SALIMA RAMI.
2. Wives query and native-query children result 4.
3. Women married twice: KARIMA ALAMI.
4. Criteria query and four-wife result: HOMME2 Prenom2; count 1 for 2010..2020.
5. SAFI SAID marriage details: three ongoing and one ended.
6. Four passing civil-status tests.
7. If running MySQL, capture the generated tables in MySQL Workbench as database evidence.
   Do not label an H2 screenshot as MySQL.
