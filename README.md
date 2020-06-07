# Save the interactives!

This utility scrapes writing.com interactive stories. You can:
- Save stories to a local database
- Read them in your browser

You need a premium writing.com account to use it. It cannot handle the "Interactive Stories are Temporarily Unavailable" error page. 

## Dependencies

You will need to install [Scala](https://scala-lang.org/), [SBT](https://www.scala-sbt.org/), and [Sqlite3](https://sqlite.org/index.html). Make sure you can build and run an SBT project.

### Before your first use

You will need to configure it and create the database.

#### Configuration

In `src/main/resources/applicaton.conf`, you will need to change:

- `username`,`my_session`,`user_ntoken`: These are your writing.com cookies
  - If writing.com logs you out, you will need to log in again and update `application.conf` with your new cookies
- `use_config_stories`: this must be either `true` or `false`
  - if `true`: This will scrape stories whose IDs appear in `stories`. See the instructions in `application.conf`
  - if `false`: This will scrape all interactive stories you have favorited. These appear on `writing.com/main/my_favorites`
- `rendered_stories_directory`: This will create HTML files in this directory. (This can delete files. I recommend using a new, empty directory.)

#### Database setup

This saves stories to a local Sqlite database. You will need to create it by following these steps:

From this directory:

```
touch db/db.db
sqlite3 db/db.db < db/migration.sql
```

## Usage

### Scraping stories

Run `sbt "run scrape"`. Stories will be saved to your local database.

### Rendering HTML

After scraping, run `sbt "run render"`. This renders your stories into HTML that you can read in your browser. Start at `outline.html`. 
Note that this is a _very_ basic generator. It writes semantic HTML with no CSS styling.

