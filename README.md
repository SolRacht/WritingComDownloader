# Save the interactives

This utility uses your writing.com login credentials to save your favorite writing.com interactive stories. It scrapes the website and records chapter information in a database.

This utility does _not_ include a way to view or browse the saved stories. Its only purpose is to scrape the site and download stories. You will need to find another program that lets you read the stories in a convenient way, or you could make one yourself.

This utility requires SBT (https://www.scala-sbt.org/) and Scala 2.12 (https://scala-lang.org/).


## Usage

### Configuration

Configuration can be found in `src/main/resources/applicaton.conf`. See that file for instructions.

### Database setup

Stories are saved to a local Sqlite database, which you will need to create, following these steps:

From the base directory of your cloned repository:
`cd db`
`touch db.db`
`sqlite3 db.db < migration.sql`

### Execution

From the base directory of your cloned repository, Run `sbt run`. This will kick off a bunch of Akka actors that will visit writing.com using your credentials and scrape the interactives you listed during the configuration step. Chapter and story data will be downloaded to your local database.


This utility succeeds https://github.com/SolRacht/writing.com-archival because it is faster, strongly-typed, and a little better written.
