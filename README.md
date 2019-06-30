# Save the interactives!

This utility saves writing.com interactive stories. When run, it scrapes the website for a list of stories you supply, and records story and chapter data to a local database. You can then generate HTML pages from the data.

This succeeds https://github.com/SolRacht/writing.com-archival because it is faster, strongly-typed, and a little better written.


## Usage

Dependencies include [SBT](https://www.scala-sbt.org/), [Scala](https://scala-lang.org/), and [Sqlite3](https://sqlite.org/index.html). If you can build and run an SBT Scala project, you should be able to use this.

### Configuration

Configuration can be found in `src/main/resources/applicaton.conf`. See that file for configuration instructions. You will need to provide a list of interactives you want downloaded, as well as your writing.com cookies. You are required to use cookies from a **premium** writing.com account. I have no interest in writing logic to account for the rate limiter.

### Database setup

Stories are saved to a local Sqlite database, which you will need to create, following these steps:

From the base directory of your cloned repository:

```
cd db
touch db.db
sqlite3 db.db < migration.sql
```

### Execution

From the base directory of your cloned repository, Run `sbt run`. This will kick off the script that visits writing.com and scrapes the stories you listed during the configuration step. Chapter and story data will be downloaded to your local database.

Once you've downloaded all the chapters you want, run `sbt run render` to generate (and re-generate) HTML pages you can open in your browser under `stories/`. Start at `stories/{story}/outline.html`. Be warned that this is a _very_ basic generator: the intention of this utility is to scrape stories and save them to a database, not to provide a nice way to view them.
