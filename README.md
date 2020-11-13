# Save the interactives!

This program scrapes writing.com interactive stories. You can:

- Save stories to a local database
- Read them in your browser

You need a premium writing.com account to use it. It cannot handle the "Interactive Stories are Temporarily Unavailable" error page. 

## Setup

To run this program, you need [Docker](https://www.docker.com/) and [Sqlite3](https://sqlite.org/index.html).

### Configuration

In `src/main/resources/applicaton.conf`, you will need to change:

- `my_session` and `user_ntoken`: These are your writing.com cookies.
  - If writing.com logs you out, you will need to log in again and update `application.conf` with your new cookies
- `use_config_stories`: this must be either `true` or `false`
  - `false`: save your favorite interactive stories. These appear in `writing.com/main/my_favorites`
  - `true`: save the stories in `stories`. See the instructions in `application.conf`

### Database setup

This will save stories to a local Sqlite database. This is just a file on your computer.
You will need to create it by following these steps:

```
# Create an empty file
touch wcomdownloader.db
# Run the migrations (assumes you are in in the root directory of this repo)
sqlite3 wcomdownloader.db < db/migration.sql
```

### Docker image setup

```
docker-compose build
```

You will need to run this command every time the code updates, or if you update the configuration.

## Usage

### Save the stories 

This will scrape writing.com for stories and save them to your local database.

Run the docker-compose service `scrape`, supplying the path to the database you created earlier as an environment variable:

```
DB=/path/to/wcomdownloader.db docker-compose run scrape
```

### Rendering HTML

This will render your stories into HTML that you can read in your browser. Start at `outline.html`. 
Note that this is a _very_ basic generator. It writes semantic HTML with no CSS styling.

Run the docker-compose service `render`, supplying the path to your database and the directory in which stories will be written.
Supply the same directory over multiple runs: if new chapters are saved, the renderer will add them to the existing story and re-render preceding chapters so that the links work.

```
DB=/path/to/wcomdownloader.db RENDER=/path/to/directory/ docker-compose run render
```
